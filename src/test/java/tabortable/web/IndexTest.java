package tabortable.web;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tabortable.tables.Table;
import tabortable.tables.TableService;

@RunWith(MockitoJUnitRunner.class)
public class IndexTest {

	@Mock
	private TableService tableService;

	private MockMvc mockMvc;

	@Before
	public void setup() {

		mockMvc = MockMvcBuilders.standaloneSetup(new Index(tableService)).build();
	}

	@Test
	public void ensureHasTablesCollection() throws Exception {
		
		when(tableService.getTables()).thenReturn(Arrays.asList(new Table()));
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(model().attributeExists("tables"))
				.andExpect(view().name("index"));
		
		verify(tableService).getTables();
	}

}
