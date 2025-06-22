package com.senla.exception;

public class PaginationException extends DaoException {
    public PaginationException() {
        super("Page not found");
    }
}
