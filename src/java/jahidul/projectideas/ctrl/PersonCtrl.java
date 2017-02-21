/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.PersonBean;
import jahidul.projectideas.ents.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author up733474
 */
@Named(value = "personCtrl")
@RequestScoped
public class PersonCtrl {

    /**
     * Creates a new instance of UserCtrl
     */
    public PersonCtrl() {
    }

    @EJB
    private PersonBean personBean;

    /**
     *
     */
    protected Person person = new Person();

    /**
     *
     */
    protected Person theUser = new Person();

    /**
     *
     */
    protected List<Person> personsList = new ArrayList<Person>();

    /**
     *
     */
    protected String search;
    
    /**
     *
     */
    @PostConstruct
    public void init() {
        personsList = personBean.findAllPersons();
        theUser = personsList.get(1);
        person.setType("Unapproved Organisation");
    }

    /**
     *
     * @return
     */
    public PersonBean getPersonBean() {
        return personBean;
    }

    /**
     *
     * @param personBean
     */
    public void setPersonBean(PersonBean personBean) {
        this.personBean = personBean;
    }

    /**
     *
     * @return
     */
    public Person getPerson() {
        return person;
    }

    /**
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     *
     * @return
     */
    public Person getTheUser() {
        return theUser;
    }

    /**
     *
     * @param theUser
     */
    public void setTheUser(Person theUser) {
        this.theUser = theUser;
    }

    /**
     *
     * @return
     */
    public List<Person> getPersonsList() {
        return personsList;
    }

    /**
     *
     * @param personsList
     */
    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }

    /**
     *
     * @return
     */
    public String getSearch() {
        return search;
    }

    /**
     *
     * @param search
     */
    public void setSearch(String search) {
        this.search = search;
    }

    private List<Person> results;

    /**
     *
     * @return
     */
    public String logIn() {
        results = personBean.logIn(theUser.getUsername(), theUser.getPassword());
        if (!results.isEmpty()) {
            theUser = results.get(0);
            return "index";
        }
        
        theUser = new Person();
        return "LogIn";
    }

    /**
     *
     * @return
     */
    public String logOut() {
        theUser = new Person();
        return "index";
    }

    /**
     *
     * @return
     */
    public String addUser() {
        personBean.addPerson(person);
        person = new Person();
        return "User";
    }
    
    /**
     *
     * @param person
     * @return
     */
    public String setUpEditPerson(Person person) {
        this.person = person;
        return "AddUser";
    }
    
    /**
     *
     * @param person
     * @return
     */
    public String updatePerson(Person person) {
        this.person = person;
        this.person = personBean.updatePerson(person);
        return "User";
    }
    
    /**
     *
     * @param person
     * @return
     */
    public String viewPerson(Person person) {
        this.person = person;
        return "User";
    }
    
    /**
     *
     * @param person
     * @return
     */
    public String deletePerson(Person person) {
        this.person = person;
        personBean.deletePerson(person);
        personsList = personBean.findAllPersons();
        return "Users";
    }
    
    /**
     *
     * @param person
     * @return
     */
    public String approveOrganisation(Person person) {
        this.person = person;
        this.person.setType("Organisation");
        this.person = personBean.updatePerson(person);
        return "User";
    }
    
    /**
     *
     * @param person
     * @return
     */
    public String unapproveOrganisation(Person person) {
        this.person = person;
        this.person.setType("Unapproved Organisation");
        this.person = personBean.updatePerson(person);
        return "User";
    }
  
    public void findAllPersons() {
        personsList = personBean.findAllPersons();
    }
    
    public void findStudents() {
        personsList = personBean.findStudents();
    }
    
    public void findStaff() {
        personsList = personBean.findStaff();
    }
    
    public void findOrganisations() {
        personsList = personBean.findOrganisations();
    }
    
    public void findUnapprovedOrganisations() {
        personsList = personBean.findUnapprovedOrganisations();
    }
}