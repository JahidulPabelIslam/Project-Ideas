/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.PersonService;
import jahidul.projectideas.ents.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author up733474
 */
@Named(value = "personBean")
@SessionScoped
public class PersonBean implements Serializable {

    public PersonBean() {
    }

    @EJB
    private PersonService personService;

    protected Person person = new Person();

    protected Person theUser = new Person();

    protected List<Person> personsList = new ArrayList<Person>();

    protected String search;

    @PostConstruct
    public void init() {
        personsList = personService.findAllPersons();
    }

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getTheUser() {
        return theUser;
    }

    public void setTheUser(Person theUser) {
        this.theUser = theUser;
    }

    public List<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    private List<Person> results;

    public String logIn() {
        results = personService.logIn(theUser.getUsername(), theUser.getPassword());
        if (!results.isEmpty()) {
            theUser = results.get(0);
            return "index";
        }
        theUser = new Person();
        return "LogIn";
    }

    public String logOut() {
        theUser = new Person();
        return "index";
    }

    public String addUser() {
        personService.addPerson(person);
        return "User";
    }

    public String setUpEditPerson(Person person) {
        this.person = person;
        return "AddUser";
    }

    public String updatePerson(Person person) {
        this.person = person;
        this.person = personService.updatePerson(person);
        return "User";
    }

    public String viewPerson(Person person) {
        this.person = person;
        return "User";
    }

    public String deletePerson(Person person) {
        this.person = person;
        personService.deletePerson(person);
        personsList = personService.findAllPersons();
        return "Users";
    }

    public String approveOrganisation(Person person) {
        this.person = person;
        this.person.setType("Organisation");
        this.person = personService.updatePerson(person);
        return "User";
    }

    public String unapproveOrganisation(Person person) {
        this.person = person;
        this.person.setType("Unapproved Organisation");
        this.person = personService.updatePerson(person);
        return "User";
    }

    public void findAllPersons() {
        personsList = personService.findAllPersons();
    }

    public void findStudents() {
        personsList = personService.findStudents();
    }

    public void findStaff() {
        personsList = personService.findStaff();
    }

    public void findOrganisations() {
        personsList = personService.findOrganisations();
    }

    public void findUnapprovedOrganisations() {
        personsList = personService.findUnapprovedOrganisations();
    }

    public String prepareCreate() {
        person = new Person();
        person.setType("Unapproved Organisation");
        return "AddUser";
    }
}
