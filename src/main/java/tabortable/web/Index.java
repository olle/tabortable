package tabortable.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tabortable.tables.Table;
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
	public String index(Model model) {
		
		if (!model.containsAttribute("table")) {
			Table t = tableService.findFirstTable().orElseThrow(() -> new TableNotFoundException(Optional.empty()));
			model.addAttribute("table", t);
			model.addAttribute("selected", t.name);
		}

		model.addAttribute("tables", tableService.getTables(Optional.empty()));

		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String selectTable(String table, RedirectAttributes redirectAttributes) {

		Table t = tableService.findTable(table).orElseGet(() -> tableService.findFirstTable().get());

		redirectAttributes.addFlashAttribute("table", t);

		return "redirect:/";
	}

}
