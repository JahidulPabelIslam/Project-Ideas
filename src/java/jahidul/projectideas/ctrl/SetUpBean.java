/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.PersonService;
import jahidul.projectideas.ents.Person;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author up733474
 */
@Named(value = "setUpBean")
@RequestScoped
public class SetUpBean {

    /**
     * Creates a new instance of SetUpBean
     */
    public SetUpBean() {
    }

    @EJB
    private PersonService personService;

    public String setTestData() {
        Person person = new Person();
        person.setEmail("jim@port.ac.uk");
        person.setFirstName("Jim");
        person.setPassword("Briggs123");
        person.setPhoneNumber("02012345678");
        person.setStaffRole("Admin");
        person.setSurname("Briggs");
        person.setType("Staff");
        person.setUsername("JB");
        
        try {
            personService.addPerson(person);
        } catch (Exception be) {
            
        }

        return "/start.xhtml";

    }

}
