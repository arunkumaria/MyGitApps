package com.doctors.dao;

import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.doctors.beans.Doctor;

public class DoctorDao {
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public Doctor getDoctorsByName(String doctorName) throws SQLException {
		String sql = "select * from doctorsdetails where doctorname=?";
		return template.queryForObject(sql, new Object[] { doctorName },
				new BeanPropertyRowMapper<Doctor>(Doctor.class));
	}

	public Doctor getDoctorsByRegistrationNumber(String registrationNumber) throws SQLException {
		String sql = "select * from doctorsdetails where doctorRegistrationNumber=?";
		return template.queryForObject(sql, new Object[] { registrationNumber },
				new BeanPropertyRowMapper<Doctor>(Doctor.class));
	}

	public Doctor getDoctorsById(int id) throws SQLException {
		String sql = "select * from doctorsdetails where id =?";
		return template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Doctor>(Doctor.class));
	}

	public Doctor getDoctorsByGender(String gender) throws SQLException {
		String sql = "select * from doctorsdetails where gender=?";
		return template.queryForObject(sql, new Object[] { gender }, new BeanPropertyRowMapper<Doctor>(Doctor.class));
	}

}