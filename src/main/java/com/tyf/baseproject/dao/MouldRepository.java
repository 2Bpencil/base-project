package com.tyf.baseproject.dao;

import com.tyf.baseproject.base.repository.ExpandJpaRepository;
import com.tyf.baseproject.entity.Mould;
import org.springframework.stereotype.Repository;

@Repository
public interface MouldRepository extends ExpandJpaRepository<Mould,Integer> {
}
