package tabortable.web.tables;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tabortable.tables.TableService;
import tabortable.web.SessionService;

@RunWith(MockitoJUnitRunner.class)
public class TablesTest {

	@Mock
	TableService tableService;

	@Mock
	SessionService sessionService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new Tables(sessionService, tableService)).build();
	}

	@Test
	public void ensureHandlesTableSelectionPost() throws Exception {

		when(tableService.hasTable(anyString())).thenReturn(true);

		mockMvc.perform(post("/").param("table", "some-table-name")).andExpect(redirectedUrl("/"));

		verify(tableService).hasTable("some-table-name");
		verify(sessionService).setSelectedTable(eq(Optional.of("some-table-name")));
	}

}
