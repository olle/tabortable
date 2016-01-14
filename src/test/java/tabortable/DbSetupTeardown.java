package tabortable;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

/**
 * Ensures setup and teardown of integration test database. Inheritance... to
 * the rescue.
 */
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:it-schema.sql",
		"classpath:it-data.sql" })
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, statements = { "DROP TABLE pet", "DROP TABLE departments",
		"DROP TABLE dept_manager" })
public abstract class DbSetupTeardown {

	// Just extend already!
}
