package tabortable.tables;

import java.util.List;
import java.util.Optional;

/**
 * Provides capabilities for retrieving tables and table-collections.
 */
public interface TableService {

	/**
	 * Returns the list of the tables (public) available in the currently active
	 * data-source.
	 * 
	 * @param maybeSelectedTable
	 *            name, marking any table that is selected, never {@code null}
	 * 
	 * @return an unmodifiable list of tables found, may be empty but never
	 *         {@code null}
	 */
	List<Table> getTables(Optional<String> maybeSelectedTable);

	/**
	 * Tries to retrieve the first table found.
	 * 
	 * @return optionally the table found, may be empty but never {@code null}
	 */
	Optional<Table> findFirstTable();

	/**
	 * Tries to retrieve the table identified by the given name.
	 * 
	 * @param byName
	 *            identifying the table to find, never {@code null}
	 * 
	 * @return optionally the table found, may be empty but never {@code null}
	 */
	Optional<Table> findTable(String byName);

}
