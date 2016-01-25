package tabortable.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tabortable.tables.TableService;

/**
 * Provides end-points and handlers for the index web page.
 */
@Controller
public class Index {

	private TableService tableService;

	@Autowired
	public Index(TableService tableService) {

		this.tableService = tableService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@RequestParam(name = "t", required = false) String table, Model model) {

		final Optional<String> maybeSelected = Optional.ofNullable(table);

		model.addAttribute("tables", tableService.getTables(maybeSelected));
		model.addAttribute("table", maybeSelected.map(tableService::findTable).orElseGet(tableService::findFirstTable)
				.orElseThrow(() -> new TableNotFoundException(maybeSelected)));

		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String selectTable() {

		return "foobar";
	}

}
