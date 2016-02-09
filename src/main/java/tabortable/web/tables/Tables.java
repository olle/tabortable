package tabortable.web.tables;

import org.springframework.stereotype.Controller;

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

	// Tests first!

}
