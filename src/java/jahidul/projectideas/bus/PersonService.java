/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.bus;

import jahidul.projectideas.ents.Person;
import jahidul.projectideas.pers.IdeaFacade;
import jahidul.projectideas.pers.PersonFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Holds all the business logic for the Person entity
 *
 * @author Jahidul Pabel Islam, 733474
 */
@Stateless
public class PersonService {

    @EJB
    private PersonFacade personFacade;

    @EJB
    private IdeaFacade ideaFacade;

    /**
     * return the current PersonFacade
     *
     * @return the personFacade
     */
    public PersonFacade getPersonFacade() {
        return personFacade;
    }

    /**
     * set a new PersonFacade
     *
     * @param personFacade the new personFacade to set as personFacade
     */
    public void setPersonFacade(PersonFacade personFacade) {
        this.personFacade = personFacade;
    }

    /**
     * get the current IdeaFacade
     *
     * @return the current IdeaFacade
     */
    public IdeaFacade getIdeaFacade() {
        return ideaFacade;
    }

    /**
     * set a new IdeaFacade
     *
     * @param ideaFacade the new IdeaFacade to set as the ideaFacade
     */
    public void setIdeaFacade(IdeaFacade ideaFacade) {
        this.ideaFacade = ideaFacade;
    }

    /**
     * gets all persons
     *
     * @return a list of all persons
     */
    public List<Person> findAllPersons() {
        return personFacade.findAll();
    }

    /**
     * try to add a new person
     *
     * @param p the person to add
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
     * update a persons details
     *
     * @param p the person to update
     * @return the updated person
     */
    public Person updatePerson(Person p) {
        return personFacade.edit(p);
    }

    /**
     * deletes a person
     *
     * @param p the person to delete
     */
    public void deletePerson(Person p) {
        if (p.getImplementingIdea() != null) {
            p.getImplementingIdea().setImplementer(null);
            ideaFacade.edit(p.getImplementingIdea());
        }

        personFacade.remove(p);
    }

    /**
     * find all persons that have the username and password provided
     *
     * @param username the username of user to look for
     * @param password the password of user to look for
     * @return the user with the username and password provided should be null
     * or a user
     */
    public List<Person> findPersonByUsernamePassword(String username, String password) {
        return personFacade.findPersonByUsernamePassword(username, password);
    }

    /**
     * find the person with the username provided
     *
     * @param username
     * @return the user with the username provided should be null or a user
     */
    public List<Person> findPersonByUsername(String username) {
        return personFacade.findPersonByUsername(username);
    }

    /**
     * find all user that are students
     *
     * @return a list of users that are students
     */
    public List<Person> findStudents() {
        return personFacade.findStudents();
    }

    /**
     * find all user that are staff
     *
     * @return a list of users that are staff
     */
    public List<Person> findStaff() {
        return personFacade.findStaff();
    }

    /**
     * find all user that are Organisations
     *
     * @return a list of users that are Organisations
     */
    public List<Person> findOrganisations() {
        return personFacade.findOrganisations();
    }

    /**
     * find all user that are Unapproved Organisations
     *
     * @return a list of users that are Unapproved Organisations
     */
    public List<Person> findUnapprovedOrganisations() {
        return personFacade.findUnapprovedOrganisations();
    }

    /**
     * find persons by a string
     *
     * @param search the text to look for in users
     * @return a list of users that contain the search string provided
     */
    public List<Person> findPersonsBySearch(String search) {
        return personFacade.findPersonsBySearch(search);
    }

    /**
     * find students that include a string search
     *
     * @param search the text to look for in users
     * @return a list of users that are students and contain the search string
     * provided
     */
    public List<Person> findStudentsBySearch(String search) {
        return personFacade.findStudentsBySearch(search);
    }

    /**
     * find staff that include a string search
     *
     * @param search the text to look for in users
     * @return a list of users that are staff and contain the search string
     * provided
     */
    public List<Person> findStaffBySearch(String search) {
        return personFacade.findStaffBySearch(search);
    }

    /**
     * find Organisations that include a string search
     *
     * @param search search the text to look for in users
     * @return a list of users that are Organisations and contain the search
     * string provided
     */
    public List<Person> findOrganisationsBySearch(String search) {
        return personFacade.findOrganisationsBySearch(search);
    }

    /**
     * find Unapproved Organisations that include a string search
     *
     * @param search search the text to look for in users
     * @return a list of users that are Unapproved Organisations and contain the
     * search string provided
     */
    public List<Person> findUnapprovedOrganisationsBySearch(String search) {
        return personFacade.findUnapprovedOrganisationsBySearch(search);
    }

    /**
     * find all users that aren't Unapproved Organisation
     *
     * @return a list of users that aren't Unapproved Organisation
     */
    public List<Person> findAllPersonsForNonStaff() {
        return personFacade.findAllPersonsForNonStaff();
    }

    /**
     * find all users that aren't Unapproved Organisation and contain the search
     * string provided
     *
     * @param search search the text to look for in users
     * @return a list of users that aren't Unapproved Organisation and contain
     * the search string provided
     */
    public List<Person> findPersonsBySearchForNonStaff(String search) {
        return personFacade.findPersonsBySearchForNonStaff(search);
    }
}
