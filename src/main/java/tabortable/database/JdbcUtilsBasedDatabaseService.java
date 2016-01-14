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

	@Autowired
	public JdbcUtilsBasedDatabaseService(DataSource dataSource) {

		this.dataSource = dataSource;
	}

	@Override
	public List<String> getPublicTableNames() {

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

			LOG.debug("Found {} public tables", results.size());

			return Collections.unmodifiableList(results);

		} catch (Exception e) {
			throw new DatabaseServiceException("Unable to fetch table names", e);
		}
	}

}
