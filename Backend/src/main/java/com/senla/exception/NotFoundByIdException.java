package com.senla.exception;

public class NotFoundByIdException extends Exception {
    public NotFoundByIdException(Class<?> clazzOfEntity) {
        super("Not found " + clazzOfEntity.getSimpleName() + " by id");
    }
    public NotFoundByIdException(String message, Class<?> clazzOfEntity) {
        super("Not found " + clazzOfEntity.getSimpleName() + " by id " + message);
    }
}
