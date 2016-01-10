package tabortable.tables;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
				
				 return results.stream().filter((r) -> r.startsWith("PUBLIC:")).map((r) -> r.split(":")[1]).map((s) -> new Table(s.toString())).collect(Collectors.toList());
				
			} catch (MetaDataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

	}

}
