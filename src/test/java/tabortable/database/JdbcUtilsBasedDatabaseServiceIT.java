package tabortable.database;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tabortable.DbSetupTeardown;
import tabortable.Application;

@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcUtilsBasedDatabaseServiceIT extends DbSetupTeardown {

	@Autowired()
	JdbcUtilsBasedDatabaseService databaseService;

	@Test
	public void ensureHasKnownListOfTableNames() {

		List<String> names = databaseService.getPublicTableNames();

		assertNotNull("Table names are missing", names);
		assertFalse("Table names is empty", names.isEmpty());

		Stream.of("PET", "DEPARTMENTS", "DEPT_MANAGER").forEach(t -> {
			assertTrue("Missing table '" + t + "' " + names, names.contains(t));
		});
	}

}
