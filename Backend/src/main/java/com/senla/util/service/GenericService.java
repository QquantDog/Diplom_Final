package com.senla.util.service;

import com.senla.exception.DaoException;
import com.senla.util.Identifiable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericService<T extends Identifiable<ID>, ID extends Serializable> {
    Optional<T> findById(ID id) throws DaoException;
    List<T> findAll() throws DaoException;
    T create(T entity) throws DaoException;
    T update(T entity) throws DaoException;
    void deleteById(ID id) throws DaoException;
    boolean existsById(ID id) throws DaoException;
    long count();
}
