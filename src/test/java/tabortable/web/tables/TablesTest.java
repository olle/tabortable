package tabortable.web.tables;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tabortable.tables.TableService;
import tabortable.web.SessionService;

@RunWith(MockitoJUnitRunner.class)
public class TablesTest {

	@Mock
	TableService tableService;

	@Mock
	SessionService sessionService;

	@Test
	public void ensureHandlesTableSelectionPost() {
		MockMvcBuilders.standaloneSetup(new Tables(sessionService, tableService)).build();
	}

}
