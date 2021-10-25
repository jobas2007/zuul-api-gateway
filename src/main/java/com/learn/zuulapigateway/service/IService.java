package com.learn.zuulapigateway.service;

import java.util.Collection;

public interface IService<T> {

    Collection<T> findAll();

    T findById(Long id);

    T saveOrUpdate(T b);

    String deleteById(Long id);
}
