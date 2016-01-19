package tabortable.query;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import tabortable.tables.Table;

@RunWith(MockitoJUnitRunner.class)
public class QueryServiceImplTest {

	@Mock
	JdbcTemplate jdbcTemplate;

	QueryServiceImpl service;

	@Before
	public void setup() {

		service = new QueryServiceImpl(jdbcTemplate);
	}

	@Test
	public void ensureQueryIsDelegatedToJdbcTemplate() {

		service.selectAsteriskFrom(new Table("foobar", false));

		verify(jdbcTemplate).query(eq("SELECT * FROM `foobar` LIMIT 100"), any(RowCallbackHandler.class));
	}
}
