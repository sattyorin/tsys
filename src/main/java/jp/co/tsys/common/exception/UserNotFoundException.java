package jp.co.tsys.common.exception;

/**
 * UserNameNotFoundException.
 */
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
