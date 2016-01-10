package tabortable.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tabortable.tables.TableService;

@Controller
public class Index {

	private TableService tableService;

	@Autowired
	public Index(TableService tableService) {
		this.tableService = tableService;
	}

	@RequestMapping("/")
	public String index(Model model) {

		model.addAttribute("tables", tableService.getTables());

		return "index";
	}
}
