package tabortable.tables;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tabortable.DbSetupTeardown;
import tabortable.TaborTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TaborTable.class)
public class TableServiceIT extends DbSetupTeardown {

	@Autowired
	private TableService tableService;

	@Test
	public void ensureReturnsDefaultTableWithKnownNumberOfRows() {

		Table defaultTable = tableService.getDefaultTable();

		assertEquals("Wrong table name", "DEPARTMENTS", defaultTable.name);
		assertEquals("Wrong amount of rows", 9, defaultTable.getRows().size());
		assertEquals("Wrong amount of columns", 2, defaultTable.getColumns().size());
	}

}
