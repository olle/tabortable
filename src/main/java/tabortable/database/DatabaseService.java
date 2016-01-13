package tabortable.database;

import java.util.List;

/**
 * Provides management and utility services for databases.
 */
public interface DatabaseService {

	/**
	 * Returns the list of public table names found in the current database.
	 * 
	 * @return list of public tables names, or an empty list, never {@code null} 
	 */
	List<String> getPublicTableNames();
}
