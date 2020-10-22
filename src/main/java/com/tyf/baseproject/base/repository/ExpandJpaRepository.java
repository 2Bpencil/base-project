package com.tyf.baseproject.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;


@NoRepositoryBean
public interface ExpandJpaRepository<T , ID extends Serializable> extends JpaRepository<T,ID> {



}
