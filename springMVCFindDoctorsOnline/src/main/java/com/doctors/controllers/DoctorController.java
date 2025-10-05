package com.doctors.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.doctors.beans.Doctor;
import com.doctors.dao.DoctorDao;

@Controller
@SessionAttributes("doctor")
public class DoctorController {
	@Autowired
	DoctorDao dao;

	@Autowired
	public DoctorController(DoctorDao dao) {
		this.dao = dao;
	}

	@ModelAttribute("doctor")
	public Doctor getDoctor() {
		return new Doctor();
	}

	// for searchform
	@RequestMapping("/doctorsearchform")
	public String searchform(Model m) {
		m.addAttribute("command", new Doctor());
		return "doctorsearchform";
	}

	// It provides a facility to check doctors online
	@RequestMapping(value = "/checkDoctorsOnline", method = RequestMethod.POST)
	public ModelAndView calculateAmountForConsumedUnits(@ModelAttribute("doctor") Doctor doctor) {

		ModelAndView mav = null;
		Doctor doctor1 = null;
		try {
			if (doctor.getDoctorName() != null && doctor.getDoctorName() != "") {
				doctor1 = dao.getDoctorsByName(doctor.getDoctorName());
			}
			if (doctor.getDoctorRegistrationNumber() != null && doctor.getDoctorRegistrationNumber() != "") {
				doctor1 = dao.getDoctorsByRegistrationNumber(doctor.getDoctorRegistrationNumber());
			}
			mav = new ModelAndView("welcome");
			if (null != doctor1) {
				System.out.println(doctor1.getId() + "..." + doctor1.getDoctorName() + ".."
						+ doctor1.getDoctorRegistrationNumber() + doctor1.getGender());
				boolean isAvailable = false;

				mav.addObject("DoctorName", doctor1.getDoctorName());
				mav.addObject("RegistrationNumber", doctor1.getDoctorRegistrationNumber());
				mav.addObject("Gender", doctor1.getGender());
				mav.addObject("Qualification", doctor1.getQualification());
			} else {
				mav.addObject("DoctorName", doctor1.getDoctorName());
				mav.addObject("RegistrationNumber", "Not available Online");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mav;
	}

}