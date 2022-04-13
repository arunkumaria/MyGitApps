package com.te.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.ems.dao.EmpDao;
import com.te.ems.dto.EmployeeBean;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmpDao userDao;

// adding & updating

	@Override
	public boolean addRegister(EmployeeBean employeeBean) {

		if (employeeBean.getFirstName() == null || employeeBean.getLastName() == null
				|| employeeBean.getEmail() == null || employeeBean.getMobile() == 0
				|| employeeBean.getSalary() == 0.0) {
			
			System.out.println(employeeBean.getFirstName());
			System.out.println("failure");
			return false;
			

		} else {

			userDao.save(employeeBean);
			System.out.println("success");
			return true;

		}

	}

// reading

	@Override
	public List<EmployeeBean> readRegister() {

		if (userDao.findAll() != null) {
			return (List<EmployeeBean>) userDao.findAll();
		} else {

			return (List<EmployeeBean>) userDao.findAll();

		}
	}

//deleting

	@Override
	public boolean deleteRegister(EmployeeBean employee) {

		if (userDao.findById(employee.getId()).isEmpty()) {
			System.out.println("failure");
			return false;

		} else {

			userDao.deleteById(employee.getId());
			System.out.println("success");
			return true;

		}

	}

}
