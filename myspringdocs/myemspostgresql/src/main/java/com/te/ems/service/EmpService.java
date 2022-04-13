package com.te.ems.service;

import java.util.List;

import com.te.ems.dto.EmployeeBean;

public interface EmpService {


	public boolean addRegister(EmployeeBean employeeBean);

	public List<EmployeeBean> readRegister();
	
	public boolean deleteRegister(EmployeeBean employeeBean);

	

}
