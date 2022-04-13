package com.te.ems.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.te.ems.dto.EmployeeBean;

@Repository
public interface EmpDao extends CrudRepository<EmployeeBean, Integer> {

}
