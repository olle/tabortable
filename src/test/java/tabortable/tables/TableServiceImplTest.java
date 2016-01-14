package tabortable.tables;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tabortable.database.DatabaseService;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceImplTest {

	@Mock
	private DataSource dataSource;
	@Mock
	DatabaseService databaseService;

	private TableServiceImpl service;

	@Before
	public void setup() {

		service = new TableServiceImpl(dataSource, databaseService);
	}

	@Test
	public void ensureFetchesDetachedTabelListWithNames() {

		List<String> mockNames = stubMockThreeKnownTableNames();

		List<Table> tables = service.getTables();

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
		verifyNoMoreInteractions(databaseService, dataSource);
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
