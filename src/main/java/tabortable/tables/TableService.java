package tabortable.tables;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Service;

public interface TableService {

	List<Table> getTables();

	@Service
	class TableServiceImpl implements TableService {

		private final DataSource dataSource;

		@Autowired
		public TableServiceImpl(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		@Override
		public List<Table> getTables() {

			final List<String> results = new ArrayList<>();

			try {
				JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {

					@Override
					public Object processMetaData(DatabaseMetaData dbmd) throws SQLException, MetaDataAccessException {

						ResultSet rs = dbmd.getTables(null, null, null, null);
						while (rs.next()) {
							results.add(rs.getObject(2) + ":" + rs.getObject(3));
						}
						return null;
					}
				});

				return results.stream().filter((r) -> r.startsWith("PUBLIC:")).map((r) -> r.split(":")[1])
						.map((s) -> new Table(s.toString())).collect(Collectors.toList());

			} catch (MetaDataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
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

	Table getDefaultTable();

	Table getTable(String name);

}
