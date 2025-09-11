package penguin;

/**
 * Exception for corrupted lines in save file.
 */
class CorruptedLineException extends Exception {
    CorruptedLineException(String msg) {
        super(msg);
    }
}
