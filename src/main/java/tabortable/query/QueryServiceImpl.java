package tabortable.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import tabortable.tables.Table;

/**
 * Query service implementation, using a Spring JDBC template.
 */
@Service
public class QueryServiceImpl implements QueryService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public QueryServiceImpl(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Table selectAsteriskFrom(Table table) {

		// TODO: Immutable table, please!

		String query = String.format("SELECT * FROM `%s` LIMIT 100", table.name);

		jdbcTemplate.query(query, rs -> {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			readRows(rs, metaData, columnCount, table);
			readColumns(metaData, columnCount, table);
		});

		return table;
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

}
