package tabortable.web;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * In case neither no tables could be found at all.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TableNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TableNotFoundException(Optional<String> maybeNamed) {
		super(maybeNamed.map(s -> String.format("Unable to find table called `%s`", s)).orElse("No tables found"));
	}

}
