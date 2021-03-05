package com.rest.soap.wrapper.repository;

import com.rest.soap.wrapper.entitiy.ChildLogEnt;
import com.rest.soap.wrapper.entitiy.ParentLogEnt;
import org.springframework.data.repository.CrudRepository;

public interface ParentLogRepository extends CrudRepository<ParentLogEnt,Integer> {
}
