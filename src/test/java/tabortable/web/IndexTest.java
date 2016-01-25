package tabortable.web;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
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
	public void ensureHandlesTableParameter() throws Exception {

		Table t1 = new Table("foo", false);
		Table t2 = new Table("bar", true);
		Table t3 = new Table("baz", false);

		when(tableService.getTables(any())).thenReturn(Arrays.asList(t1, t2, t3));
		when(tableService.findTable(anyString())).thenReturn(Optional.of(t2));

		mockMvc.perform(get("/").param("t", "some-table")).andExpect(status().isOk())
				.andExpect(model().attributeExists("table", "tables")).andExpect(view().name("index"));

		verify(tableService).getTables(Optional.of("some-table"));
		verify(tableService).findTable("some-table");

	}
	
	@Test
	public void ensureThrowsOnMissingTables() throws Exception {
		
		when(tableService.getTables(any())).thenReturn(Collections.emptyList());
		when(tableService.findTable(anyString())).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/").param("t", "missing-table")).andExpect(status().isNotFound());
		
		verify(tableService).getTables(any());
		verify(tableService).findTable("missing-table");
		
	}
	
	@Test
	public void ensureHandlesTableSelectionPost() throws Exception {
		
		mockMvc.perform(post("/")).andExpect(status().isOk());
		
	}

}
