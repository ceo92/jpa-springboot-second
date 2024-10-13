package jpa.mvc.exception;

import org.springframework.dao.DataAccessException;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
    }

    public DuplicateMemberException(String message) {
        super(message);
    }
}
