package tabortable.web;

import java.util.Optional;

/**
 * Provides an API, that is a business oriented indirection against the current
 * session.
 */
public interface SessionService {

	/**
	 * Sets the current selected table name, for this session.
	 * 
	 * @param table
	 *            may be empty, but never {@code null}
	 */
	void setSelectedTable(Optional<String> table);

}
