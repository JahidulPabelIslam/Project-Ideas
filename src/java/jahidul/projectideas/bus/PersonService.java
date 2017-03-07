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

    public List<Person> findPersonsBySearch(String search) {
        return personFacade.findPersonsBySearch(search);
    }

    public List<Person> findStudentsBySearch(String search) {
        return personFacade.findStudentsBySearch(search);
    }

    public List<Person> findStaffBySearch(String search) {
        return personFacade.findStaffBySearch(search);
    }

    public List<Person> findOrganisationsBySearch(String search) {
        return personFacade.findOrganisationsBySearch(search);
    }

    public List<Person> findUnapprovedOrganisationsBySearch(String search) {
        return personFacade.findUnapprovedOrganisationsBySearch(search);
    }
    
    public List<Person> findAllPersonsForNonStaff() {
        return personFacade.findAllPersonsForNonStaff();
    }
    
    public List<Person> findPersonsBySearchForNonStaff(String search) {
        return personFacade.findPersonsBySearchForNonStaff(search);
    }
}
