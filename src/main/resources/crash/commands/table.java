import java.util.Map;

import org.crsh.cli.Argument;
import org.crsh.cli.Command;
import org.crsh.cli.Required;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.crsh.command.InvocationContext;
import org.springframework.beans.factory.BeanFactory;

import tabortable.tables.TableService;

@Usage("Display table contents")
public class table extends BaseCommand {

	@Usage("Shows the contents of the named table")
	@Command
	public void show(@Required @Argument String table, @SuppressWarnings("rawtypes") InvocationContext<Map> ctx) {

		((BeanFactory) ctx.getAttributes().get("spring.beanfactory")).getBean(TableService.class).findTable(table)
				.ifPresent(t -> {
					t.forMapper(m -> {
						try {
							ctx.provide(m);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				});
	}
}
