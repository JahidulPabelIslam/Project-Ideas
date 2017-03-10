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

    public PersonFacade getPersonFacade() {
        return personFacade;
    }

    public void setPersonFacade(PersonFacade personFacade) {
        this.personFacade = personFacade;
    }

    /**
     *
     * @return
     */
    public List<Person> findAllPersons() {
        return personFacade.findAll();
    }

    /**
     *
     * @param p
     * @throws java.lang.Exception
     */
    public void addPerson(Person p) throws Exception {
        if (findPersonByUsername(p.getUsername()).isEmpty()) {
            personFacade.create(p);
        } else {
            throw new Exception("Username already exists.");
        }
    }

    /**
     *
     * @param p
     * @return
     */
    public Person updatePerson(Person p) {
        return personFacade.edit(p);
    }

    /**
     *
     * @param p
     */
    public void deletePerson(Person p) {
        personFacade.remove(p);
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public List<Person> findPersonByUsernamePassword(String username, String password) {
        return personFacade.findPersonByUsernamePassword(username, password);
    }
    
    /**
     *
     * @param username
     * @return
     */
    public List<Person> findPersonByUsername(String username) {
        return personFacade.findPersonByUsername(username);
    }

    /**
     *
     * @return
     */
    public List<Person> findStudents() {
        return personFacade.findStudents();
    }

    /**
     *
     * @return
     */
    public List<Person> findStaff() {
        return personFacade.findStaff();
    }

    /**
     *
     * @return
     */
    public List<Person> findOrganisations() {
        return personFacade.findOrganisations();
    }

    /**
     *
     * @return
     */
    public List<Person> findUnapprovedOrganisations() {
        return personFacade.findUnapprovedOrganisations();
    }

    /**
     *
     * @param search
     * @return
     */
    public List<Person> findPersonsBySearch(String search) {
        return personFacade.findPersonsBySearch(search);
    }

    /**
     *
     * @param search
     * @return
     */
    public List<Person> findStudentsBySearch(String search) {
        return personFacade.findStudentsBySearch(search);
    }

    /**
     *
     * @param search
     * @return
     */
    public List<Person> findStaffBySearch(String search) {
        return personFacade.findStaffBySearch(search);
    }

    /**
     *
     * @param search
     * @return
     */
    public List<Person> findOrganisationsBySearch(String search) {
        return personFacade.findOrganisationsBySearch(search);
    }

    /**
     *
     * @param search
     * @return
     */
    public List<Person> findUnapprovedOrganisationsBySearch(String search) {
        return personFacade.findUnapprovedOrganisationsBySearch(search);
    }
    
    /**
     *
     * @return
     */
    public List<Person> findAllPersonsForNonStaff() {
        return personFacade.findAllPersonsForNonStaff();
    }
    
    /**
     *
     * @param search
     * @return
     */
    public List<Person> findPersonsBySearchForNonStaff(String search) {
        return personFacade.findPersonsBySearchForNonStaff(search);
    }
}
