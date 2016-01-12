package tabortable.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tabortable.tables.TableService;

@Controller
public class Index {

	private TableService tableService;

	@Autowired
	public Index(TableService tableService) {

		this.tableService = tableService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@RequestParam(name = "t", required = false) String tableName, Model model) {

		model.addAttribute("tables", tableService.getTables());
		model.addAttribute("table", Optional.ofNullable(tableName).map((t) -> tableService.getTable(t))
				.orElse(tableService.getDefaultTable()));

		return "index";
	}
	
}
