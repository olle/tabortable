package tabortable.web.tables;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tabortable.tables.TableService;
import tabortable.web.SessionService;

/**
 * Implements end-points that control table interaction.
 */
@Controller
public class Tables {

	private final SessionService sessionService;
	private final TableService tableService;

	public Tables(SessionService sessionService, TableService tableService) {
		this.sessionService = sessionService;
		this.tableService = tableService;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String changeTableSelection(String table) {

		if (tableService.hasTable(table)) {
			sessionService.setSelectedTable(Optional.of(table));
		}

		return "redirect:/";
	}

}
