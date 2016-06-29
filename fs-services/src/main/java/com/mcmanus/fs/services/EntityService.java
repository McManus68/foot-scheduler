package com.mcmanus.fs.services;

import com.mcmanus.fs.model.jpa.AbstractEntity;

import java.util.List;

public interface EntityService<T extends AbstractEntity> {

    List<T> getAll();

    T get(Long id);

    Long save(T entity);

    Long update(T entity);

    void delete(Long id) ;

}
