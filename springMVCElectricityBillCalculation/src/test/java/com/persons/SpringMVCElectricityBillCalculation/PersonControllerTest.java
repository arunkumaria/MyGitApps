package com.persons.SpringMVCElectricityBillCalculation;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.persons.beans.Person;
import com.persons.controllers.PersonController;
import com.persons.dao.PersonDao;

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PersonControllerTest {
  
    @InjectMocks
    private PersonController personController;
 
    private MockMvc mockMvc;

    @Autowired
    private PersonDao dao;
    
    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController).build(); 
    }
 
    @Test
    // 404 error thrown when coming from invalid resources
    public void testCreateSearchPersonsPageFormInvalidUser() throws Exception {
        this.mockMvc.perform(get("/"))
        .andExpect(status().isNotFound());
    }
   
      @Test 
      // positive checks for Name
      public void testCheckConsumedUnitsByName() throws Exception { 
          Person person = new Person(); 
          person.setName("personA"); 
          person = dao.getPersonsByName(person.getName());
          Assert.assertEquals(1, person.getId());
          Assert.assertEquals("123-456", person.getServiceNumber());
          Assert.assertEquals(250, person.getConsumedUnits());
      }
      
      @Test 
      // Negative checks for Name
      public void testCheckConsumedUnitsByNameForNegative() throws Exception { 
          Person person = new Person(); 
          person.setName("personA"); 
          person = dao.getPersonsByName(person.getName());
          Assert.assertNotEquals(2, person.getId());
          Assert.assertNotEquals("asd-123", person.getServiceNumber());
          Assert.assertNotEquals(1500, person.getConsumedUnits());
          Assert.assertNotEquals("Male", person.getGender());
      }
     
        @Test 
      // Negative checks + Positive
      public void testCheckConsumedUnits() throws Exception { 
          Person person = new Person(); 
          person.setConsumedUnits(250);
          person = dao.getPersonsByConsumedUnits(person.getConsumedUnits());
          Assert.assertNotEquals(10, person.getId());
          Assert.assertNotEquals("ST-123", person.getServiceNumber());
          Assert.assertEquals("Female", person.getGender());
      }

      @Test 
      // Positive  checks for consumedunits
      public void testCheckConsumedUnitsPositive() throws Exception { 
          Person person = new Person(); 
          person.setConsumedUnits(500);
          person = dao.getPersonsByConsumedUnits(person.getConsumedUnits());
          Assert.assertEquals(5, person.getId());
          Assert.assertEquals("146-189", person.getServiceNumber());
          Assert.assertEquals("personE", person.getName());
          Assert.assertEquals("female", person.getGender());
      }
      
      @Test 
      // Negative + Positive checks
      public void testCheckConsumedUnitsById() throws Exception { 
          Person person = new Person(); 
          person.setId(2); 
          person = dao.getPersonsById(person.getId());
          Assert.assertEquals(350, person.getConsumedUnits());
          Assert.assertNotEquals("personZ", person.getName());
          Assert.assertNotEquals("Gen-123", person.getServiceNumber());
      }
      
      @Test 
      // Positive checks for ID
      public void testCheckConsumedUnitsByIdPositiveCheck() throws Exception { 
          Person person = new Person(); 
          person.setId(5); 
          person = dao.getPersonsById(person.getId());
          Assert.assertEquals(500, person.getConsumedUnits());
          Assert.assertEquals("personE", person.getName());
          Assert.assertEquals("146-189", person.getServiceNumber());
          Assert.assertEquals("female", person.getGender());
      }     
        
}
