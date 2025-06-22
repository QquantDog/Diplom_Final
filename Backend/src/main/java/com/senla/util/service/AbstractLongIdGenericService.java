package com.senla.util.service;


import com.senla.util.Identifiable;
import com.senla.util.dao.AbstractLongDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractLongIdGenericService<T extends Identifiable<Long>> implements GenericService<T, Long> {

    protected AbstractLongDao<T> abstractDao;

    protected abstract void init();

    @Override
    public List<T> findAll() {
        return abstractDao.findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        return abstractDao.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return abstractDao.existsById(id);
    }

    @Override
    @Transactional
    public T create(T entity) {
        return abstractDao.create(entity);
    }


    @Override
    @Transactional
    public T update(T entity) {
        return abstractDao.update(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        abstractDao.deleteById(id);
    }

    @Override
    public long count() {
        return abstractDao.count();
    }
}
