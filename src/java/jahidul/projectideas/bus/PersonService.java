/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.bus;

import jahidul.projectideas.ents.Person;
import jahidul.projectideas.pers.PersonFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author up733474
 */
@Stateless
public class PersonService {

    @EJB
    private PersonFacade personFacade;

    public List<Person> findAllPersons() {
        return personFacade.findAll();
    }

    public void addPerson(Person p) {
        personFacade.create(p);
    }

    public Person updatePerson(Person p) {
        return personFacade.edit(p);
    }

    public void deletePerson(Person p) {
        personFacade.remove(p);
    }

    public List<Person> logIn(String username, String password) {
        return personFacade.logIn(username, password);
    }
    
    public List<Person> findStudents() {
        return personFacade.findStudents();
    }
    
    public List<Person> findStaff() {
        return personFacade.findStaff();
    }
    
    public List<Person> findOrganisations() {
        return personFacade.findOrganisations();
    }
    
    public List<Person> findUnapprovedOrganisations() {
        return personFacade.findUnapprovedOrganisations();
    }
    
    public List<Person> findPersonsByText(String search) {
        return personFacade.findPersonsByText(search);
    }
}