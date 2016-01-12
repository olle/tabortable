package tabortable.tables;

import java.util.List;
import java.util.Optional;

public interface TableService {

	List<Table> getTables();

	List<Table> getTables(Optional<String> maybeSelectedTable);

	Table getDefaultTable();

	Table getTable(String name);

}
