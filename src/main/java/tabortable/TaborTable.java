package tabortable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Tabor-table is a web GUI application, for data-sources, providing a simple
 * and intuitive table and query view editor.
 */
@EnableCaching
@SpringBootApplication
public class TaborTable {

	public static void main(String[] args) {
		SpringApplication.run(TaborTable.class, args);
	}

}
