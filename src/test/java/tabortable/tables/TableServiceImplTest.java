package tabortable.tables;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tabortable.database.DatabaseService;
import tabortable.query.QueryService;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceImplTest {

	@Mock
	DatabaseService databaseService;
	@Mock
	QueryService queryService;

	TableServiceImpl service;

	@Before
	public void setup() {

		service = new TableServiceImpl(databaseService, queryService);
	}

	@Test
	public void ensureFetchesFirstFoundTable() throws Exception {

		when(databaseService.getPublicTableNames()).thenReturn(Arrays.asList("foo", "bar", "baz"));
		when(queryService.selectAsteriskFrom(any())).thenAnswer(i -> i.getArguments()[0]);

		Table table = service.findFirstTable().get();

		assertEquals("Wrong table name", "foo", table.name);

		verify(databaseService).getPublicTableNames();
		verify(queryService).selectAsteriskFrom(table);
	}

	@Test
	public void ensureReturnsEmptyOptionalForMissingTables() throws Exception {

		when(databaseService.getPublicTableNames()).thenReturn(Collections.emptyList());

		Optional<Table> noTable = service.findFirstTable();

		assertFalse("Must not be found", noTable.isPresent());

		verify(databaseService).getPublicTableNames();
		verifyNoMoreInteractions(queryService);
	}

	@Test
	public void ensureFetchesTableByName() throws Exception {

		when(databaseService.getPublicTableNames()).thenReturn(Arrays.asList("foo", "bar", "baz"));
		when(queryService.selectAsteriskFrom(any())).thenAnswer(i -> i.getArguments()[0]);

		Table table = service.findTable("baz").get();

		assertEquals("Wrong table name", "baz", table.name);

		verify(databaseService).getPublicTableNames();
		verify(queryService).selectAsteriskFrom(table);
	}

	@Test
	public void ensureFetchesDetachedTabelListWithNames() {

		List<String> mockNames = stubMockThreeKnownTableNames();

		List<Table> tables = service.getTables(Optional.empty());

		assertCalledDatabaseServiceNothingElse();
		assertHasExactlyThreeTables(tables);
		assertHasTablesWithExpectedNames(mockNames, tables);

		assertFalse("Table must not be selected", tables.stream().anyMatch((t) -> "selected".equals(t.selected)));
	}

	private void assertHasTablesWithExpectedNames(List<String> mockNames, List<Table> tables) {

		assertTrue("Missing table or bad name: " + tables, tables.stream().allMatch((t) -> mockNames.contains(t.name)));
	}

	private void assertCalledDatabaseServiceNothingElse() {

		verify(databaseService).getPublicTableNames();
		verifyNoMoreInteractions(databaseService, queryService);
	}

	private void assertHasExactlyThreeTables(List<Table> tables) {

		assertNotNull("Missing tables", tables);
		assertEquals("Wrong amount of tables", 3, tables.size());
	}

	private List<String> stubMockThreeKnownTableNames() {

		List<String> mockNames = Arrays.asList("foo", "bar", "baz");
		when(databaseService.getPublicTableNames()).thenReturn(mockNames);
		return mockNames;
	}

	@Test
	public void ensureFetchesAndMarksSelectedTableByName() throws Exception {

		List<String> mockNames = stubMockThreeKnownTableNames();
		List<Table> tables = service.getTables(Optional.of("bar"));

		assertCalledDatabaseServiceNothingElse();
		assertHasExactlyThreeTables(tables);
		assertHasTablesWithExpectedNames(mockNames, tables);

		assertTrue("No table selected", tables.stream().anyMatch((t) -> "selected".equals(t.selected)));
	}

}
