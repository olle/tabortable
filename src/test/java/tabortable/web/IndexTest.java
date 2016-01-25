package tabortable.web;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

		Table t1 = new Table("foo", false);
		Table t2 = new Table("bar", false);
		Table t3 = new Table("baz", false);
		List<Table> tables = Arrays.asList(t1, t2, t3);

		when(tableService.getTables(any())).thenReturn(tables);
		when(tableService.findFirstTable()).thenReturn(Optional.of(t1));

		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(model().attribute("table", t1))
				.andExpect(model().attribute("tables", tables)).andExpect(view().name("index"));

		verify(tableService).getTables(Optional.empty());
		verify(tableService).findFirstTable();
	}


	@Test
	public void ensureHandlesTableSelectionPostForTableFound() throws Exception {

		Table table = new Table("some-table", false);
		when(tableService.findTable(anyString())).thenReturn(Optional.of(table));

		mockMvc.perform(post("/").param("table", "some-table")).andExpect(redirectedUrl("/"))
				.andExpect(flash().attribute("table", table));

		verify(tableService).findTable("some-table");
	}

	@Test
	public void ensureHandlesTableSelectionPostForUnknownTable() throws Exception {

		when(tableService.findTable(anyString())).thenReturn(Optional.empty());
		
		Table table = new Table("some-table", false);
		when(tableService.findFirstTable()).thenReturn(Optional.of(table));

		mockMvc.perform(post("/").param("table", "other-table")).andExpect(redirectedUrl("/"))
				.andExpect(flash().attribute("table", table));

		verify(tableService).findTable("other-table");
		verify(tableService).findFirstTable();
	}
	
}
