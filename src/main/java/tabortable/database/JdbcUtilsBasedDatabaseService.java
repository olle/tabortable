package tabortable.database;

import java.util.List;

import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

/**
 * A database service that uses {@link JdbcUtils}.
 */
@Service
class JdbcUtilsBasedDatabaseService implements DatabaseService {

	@Override
	public List<String> getPublicTableNames() {
		
		// TODO Auto-generated method stub
		return null;
	}

}
