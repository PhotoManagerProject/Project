package Files.exception;

import Files.constants.ErrorCode;


public class PhotoManagerException extends RuntimeException{

    private final ErrorCode errorCode; // κωδικοί σφάλματος χρησιμοποιούνται σε διαφορετικά στάδια της εφαρμογής
    private final String message; // μήνυμα σφάλματος

    public PhotoManagerException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
