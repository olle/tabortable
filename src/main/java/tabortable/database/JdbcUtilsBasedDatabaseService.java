package tabortable.database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Service;

/**
 * A database service that uses {@link JdbcUtils}.
 */
@Service
class JdbcUtilsBasedDatabaseService implements DatabaseService {

	private static final Logger LOG = LoggerFactory.getLogger(JdbcUtilsBasedDatabaseService.class);

	private static final int TABLE_SCHEM = 2;
	private static final int TABLE_NAME = 3;

	private static final String NO_FILTER = null;
	private static final String[] NO_FILTERS = null;

	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcUtilsBasedDatabaseService(DataSource dataSource, JdbcTemplate jdbcTemplate) {

		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable("table-names")
	public List<String> getPublicTableNames() {

		LOG.info("Fetching list of all current tables");
		
		List<String> tableNames = new ArrayList<>();
		
		tableNames = getTableNamesFromShowTablesQuery();
		
		if (tableNames.isEmpty()) {
			LOG.debug("No table names found, trying meta data query");
			tableNames = getTableNamesFromDatabaseMetaData();
		}
				
		LOG.info("Found {} public tables", tableNames.size());		
		
		return Collections.unmodifiableList(tableNames);
	}

	private List<String> getTableNamesFromShowTablesQuery() {
		
		final List<String> results = new ArrayList<>();
		
		jdbcTemplate.query("SHOW TABLES", (r) -> {
			results.add(r.getString(1));
		});
		
		LOG.debug("Found {} public tables through show tables query", results.size());
		
		return results;
	}

	private List<String> getTableNamesFromDatabaseMetaData() {
		try {

			final List<String> results = new ArrayList<>();

			JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {

				@Override
				public Object processMetaData(DatabaseMetaData dbmd) throws SQLException, MetaDataAccessException {

					ResultSet rs = dbmd.getTables(NO_FILTER, NO_FILTER, NO_FILTER, NO_FILTERS);

					while (rs.next()) {
						if ("PUBLIC".equals(rs.getObject(TABLE_SCHEM))) {
							results.add((String) rs.getObject(TABLE_NAME));
						}
					}

					return null;
				}
			});

			LOG.debug("Found {} public tables through meta-data", results.size());

			return results;

		} catch (Exception e) {
			throw new DatabaseServiceException("Unable to fetch table names", e);
		}
	}

}
