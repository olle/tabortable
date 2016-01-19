package tabortable.tables;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tabortable.database.DatabaseService;
import tabortable.query.QueryService;

/**
 * Simple service implementation, providing table listing and querying.
 */
@Service
class TableServiceImpl implements TableService {

	private final static Logger LOG = LoggerFactory.getLogger(TableServiceImpl.class);

	private final DatabaseService databaseService;
	private final QueryService queryService;

	@Autowired
	public TableServiceImpl(DatabaseService databaseService, QueryService queryService) {

		this.databaseService = databaseService;
		this.queryService = queryService;
	}

	@Override
	public Optional<Table> findFirstTable() {

		return findAndMap(ts -> ts.findFirst());
	}

	private Optional<Table> findAndMap(Function<Stream<Table>, Optional<Table>> streamFindOperation) {

		return streamFindOperation.apply(getTables(Optional.empty()).stream()).map(queryService::selectAsteriskFrom);
	}

	@Override
	public Optional<Table> findTable(String byName) {

		return findAndMap(ts -> ts.filter(t -> byName.equals(t.name)).findFirst());
	}

	@Override
	public List<Table> getTables(final Optional<String> maybeSelectedTable) {

		return databaseService.getPublicTableNames().stream().map(n -> {
			Table table = new Table(n, maybeSelectedTable.map(s -> n.equals(s)).orElse(false));
			LOG.debug("Found {}", table);
			return table;
		}).collect(Collectors.toList());
	}

}