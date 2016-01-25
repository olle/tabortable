import java.util.stream.Collectors;

import org.crsh.cli.Command;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.crsh.command.InvocationContext;
import org.springframework.beans.factory.BeanFactory;

import tabortable.database.DatabaseService;

public class tables extends BaseCommand {

	@Usage("Displays the list of known table names")
	@Command
	public Object main(@SuppressWarnings("rawtypes") InvocationContext ctx) {
		return ((BeanFactory) ctx.getAttributes().get("spring.beanfactory")).getBean(DatabaseService.class)
				.getPublicTableNames().stream().collect(Collectors.joining(System.lineSeparator()));
	}
}
