package tabortable.tables;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import tabortable.database.DatabaseService;

/**
 * Simple service implementation, providing table listing and querying.
 */
@Service
class TableServiceImpl implements TableService {

	private final static Logger LOG = LoggerFactory.getLogger(TableServiceImpl.class);

	private final DataSource dataSource;
	private final DatabaseService databaseService;

	@Autowired
	public TableServiceImpl(DataSource dataSource, DatabaseService databaseService) {

		this.dataSource = dataSource;
		this.databaseService = databaseService;
	}

	@Override
	public List<Table> getTables() {

		return getTables(Optional.empty());
	}

	@Override
	public List<Table> getTables(final Optional<String> maybeSelectedTable) {

		return databaseService.getPublicTableNames().stream().map(n -> {
			Table table = new Table(n, maybeSelectedTable.map(s -> n.equals(s)).orElse(false));
			LOG.debug("Found {}", table);
			return table;
		}).collect(Collectors.toList());
	}

	@Override
	public Table getDefaultTable() {

		final Table table = getTables().get(0);
		populate(table);
		return table;
	}

	private void populate(final Table table) {
		new JdbcTemplate(dataSource).query("SELECT * FROM " + table.name + " LIMIT 100", (rs) -> {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			readRows(rs, metaData, columnCount, table);
			readColumns(metaData, columnCount, table);
		});
	}

	private void readRows(ResultSet rs, ResultSetMetaData metaData, int columns, Table table) {
		try {
			do {
				List<List<Object>> cols = new ArrayList<>();
				for (int i = 1; i <= columns; i++) {
					String columnName = metaData.getColumnName(i);
					String columnTypeName = metaData.getColumnTypeName(i);
					Object value = rs.getObject(i);
					cols.add(Arrays.asList(columnName, columnTypeName, value));
				}
				table.addRow(cols);
			} while (rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readColumns(ResultSetMetaData metaData, int columns, Table table) {

		try {
			for (int i = 1; i <= columns; i++) {
				String columnName = metaData.getColumnName(i);
				String columnTypeName = metaData.getColumnTypeName(i);
				table.addColumn(columnName, columnTypeName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Table getTable(String name) {

		final Table table = getTables().stream().filter((t) -> name.equals(t.name)).findFirst()
				.orElseThrow(() -> new RuntimeException(String.format("No table called '%s' found.", name)));
		populate(table);
		return table;
	}

}