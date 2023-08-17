package jangseop.tokyosubwayroutesearch.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(Long id) {
        super(String.format("Route id (%s) is not existed", id));
    }
}
