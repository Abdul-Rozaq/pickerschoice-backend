package com.pickerschoice.pickerschoice.exception;

public class AppAuthException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppAuthException(String message) {
        super(message);
    }

    public AppAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppAuthException(Throwable cause) {
        super(cause);
    }
}
