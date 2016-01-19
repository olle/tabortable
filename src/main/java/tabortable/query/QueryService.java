package tabortable.query;

import tabortable.tables.Table;

/**
 * Provides a query facility API for the application.
 */
public interface QueryService {

	Table selectAsteriskFrom(Table table);

}
