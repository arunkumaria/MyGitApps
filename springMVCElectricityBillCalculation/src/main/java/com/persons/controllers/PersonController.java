package com.persons.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.persons.beans.Person;
import com.persons.dao.PersonDao;

@Controller
@SessionAttributes("person")
public class PersonController {
	@Autowired
	PersonDao dao;

	@Autowired
	public PersonController(PersonDao dao) {
		this.dao = dao;
	}

	@ModelAttribute("person")
	public Person getPerson() {
		return new Person();
	}

	// for searchform
	@RequestMapping("/personsearchform")
	public String searchform(Model m) {
		m.addAttribute("command", new Person());
		return "personsearchform";
	}

	// It provides check persons and calculate the amount based on consumed units
	@RequestMapping(value = "/calculateAmount", method = RequestMethod.POST)
	public ModelAndView calculateAmountForConsumedUnits(@ModelAttribute("person") Person person) {

		ModelAndView mav = null;
		Person person1 = null;
		try {
			if (person.getName() != null && person.getName() != "") {
				person1 = dao.getPersonsByName(person.getName());
			}
			if (person.getServiceNumber() != null && person.getServiceNumber() != "") {
				person1 = dao.getPersonsByServiceNumber(person.getServiceNumber());
			}
			mav = new ModelAndView("welcome");
			if (null != person1) {
				System.out.println(person1.getId() + "..." + person1.getName() + ".." + person1.getServiceNumber()
						+ "..consumed units.." + person1.getConsumedUnits());
				boolean isAvailable = false;
				int consumedUnits = person1.getConsumedUnits();
				int electricityChargesNeedToPay = 0;
				if (consumedUnits <= 100) {
					electricityChargesNeedToPay = consumedUnits * 10;
				}
				if (consumedUnits <= 200) {
					electricityChargesNeedToPay = (100 * 10) + (consumedUnits - 100) * 15;
				}
				if (consumedUnits <= 300) {
					electricityChargesNeedToPay = (100 * 10) + (100 * 15) + (consumedUnits - 200) * 20;
				}
				if (consumedUnits > 300) {
					electricityChargesNeedToPay = (100 * 10) + (100 * 15) + (100 * 20) + (consumedUnits - 300) * 25;
				}

				System.out.println("electricityChargesNeedToPay.." + electricityChargesNeedToPay);
				mav.addObject("Name", person1.getName());
				mav.addObject("ServiceNumber", person1.getServiceNumber());
				mav.addObject("ConsumedUnits", person1.getConsumedUnits());
				mav.addObject("Gender", person1.getGender());
				mav.addObject("ElectricityCharges", electricityChargesNeedToPay);

			} else {
				mav.addObject("firstname", person1.getName());
				mav.addObject("electricitycharges", "Not present in the database");

				// mav.addObject("location", person.getLocation());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mav;
	}

}