package tabortable.database;

/**
 * Thrown in case of a database service failure.
 */
public class DatabaseServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseServiceException(String message, Throwable cause) {

		super(message, cause);
	}

}
