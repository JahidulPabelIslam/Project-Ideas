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
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author up733474
 */
@Named(value = "personBean")
@SessionScoped
public class PersonBean implements Serializable {

    /**
     *
     */
    public PersonBean() {
    }

    @EJB
    private PersonService personService;

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
    protected String search = "";

    /**
     *
     */
    protected String filter = "All";

    /**
     *
     */
    @PostConstruct
    public void init() {
        if (isUserStaff()) {
            personsList = personService.findAllPersons();
        } else {
            personsList = personService.findAllPersonsForNonStaff();
        }
    }

    /**
     *
     * @return
     */
    public PersonService getPersonService() {
        return personService;
    }

    /**
     *
     * @param personService
     */
    public void setPersonService(PersonService personService) {
        this.personService = personService;
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

    /**
     *
     * @return
     */
    public String getFilter() {
        return filter;
    }

    /**
     *
     * @param filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     *
     * @return
     */
    public String logIn() {
        List<Person> results = personService.findPersonByUsernamePassword(theUser.getUsername(), theUser.getPassword());
        if (!results.isEmpty()) {
            theUser = results.get(0);
            return "index";
        }
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username and/or password not recognised.", "Sign In Error");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
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
    public String prepareCreate() {
        person = new Person();
        person.setType("Unapproved Organisation");
        return "AddUser";
    }

    /**
     *
     * @return
     */
    public String addUser() {
        try {
            personService.addPerson(person);
            if (!isUserStaff()) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully registered, An administrator will review your details and enable\n"
                        + "                           your account soon, come back later and try to log in.", "Successfully registered.");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                person = new Person();
                return null;
            }
            return "User";
        } catch (Exception be) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, be.getMessage(), be.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return null;
        }
    }

    /**
     *
     * @param person
     * @return
     */
    public String setUpEditPerson(Person person) {
        this.person = person;
        if (isUserStaff() || isPersonTheUser()) {
            return "AddUser";
        }
        return "";
    }

    /**
     *
     * @param person
     * @return
     */
    public String updatePerson(Person person) {
        this.person = person;
        if (isUserStaff() || isPersonTheUser()) {
            this.person = personService.updatePerson(person);
        }
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
        if (isUserStaff() && !isPersonTheUser()) {
            personService.deletePerson(this.person);
            personsList = personService.findAllPersons();
            return "Users";
        } else if (isPersonTheUser()) {
            personService.deletePerson(this.person);
            personsList = personService.findAllPersons();
            theUser = new Person();
            return "Users";
        }
        return null;
    }

    /**
     *
     * @param person
     * @return
     */
    public String approveOrganisation(Person person) {
        this.person = person;
        if (isUserStaff()) {
            this.person.setType("Organisation");
            this.person = personService.updatePerson(person);
        }
        return "User";
    }

    /**
     *
     * @param person
     * @return
     */
    public String unapproveOrganisation(Person person) {
        this.person = person;
        if (isUserStaff()) {
            this.person.setType("Unapproved Organisation");
            this.person = personService.updatePerson(person);
        }
        return "User";
    }

    /**
     *
     * @return
     */
    public String viewAllPersons() {
        if (isUserStaff()) {
            personsList = personService.findAllPersons();
        } else {
            personsList = personService.findAllPersonsForNonStaff();
        }
        filter = "All";
        search = "";
        return "Users";
    }

    /**
     *
     * @return
     */
    public List<Person> getStudents() {
        return personService.findStudents();
    }

    /**
     *
     */
    public void updatePersonsList() {
        switch (filter) {
            case "Students":
                if (!"".equals(search)) {
                    personsList = personService.findStudentsBySearch(search);
                } else {
                    personsList = getStudents();
                }
                break;
            case "Staff":
                if (!"".equals(search)) {
                    personsList = personService.findStaffBySearch(search);
                } else {
                    personsList = personService.findStaff();
                }
                break;
            case "Organisations":
                if (!"".equals(search)) {
                    personsList = personService.findOrganisationsBySearch(search);
                } else {
                    personsList = personService.findOrganisations();
                }
                break;
            case "Unapproved Organisations":
                if (isUserStaff() && !"".equals(search)) {
                    personsList = personService.findUnapprovedOrganisationsBySearch(search);
                } else if (isUserStaff()) {
                    personsList = personService.findUnapprovedOrganisations();
                } else {
                    filter = "All";
                    personsList = personService.findAllPersonsForNonStaff();
                }
                break;
            default:
                if (isUserStaff() && !"".equals(search)) {
                    personsList = personService.findPersonsBySearch(search);
                } else if (!"".equals(search)) {
                    personsList = personService.findPersonsBySearchForNonStaff(search);
                } else if (isUserStaff()) {
                    personsList = personService.findAllPersons();
                } else {
                    personsList = personService.findAllPersonsForNonStaff();
                }
                break;
        }
    }

    /**
     *
     * @return
     */
    public boolean isUserStaff() {
        return "Staff".equals(theUser.getType());
    }

    /**
     *
     * @return
     */
    public boolean isUserStudent() {
        return "Student".equals(theUser.getType());
    }

    /**
     *
     * @return
     */
    public boolean isUserApprovedOrganisation() {
        return "Organisation".equals(theUser.getType());
    }

    /**
     *
     * @return
     */
    public boolean isPersonStaff() {
        return "Staff".equals(person.getType());
    }

    /**
     *
     * @return
     */
    public boolean isPersonStudent() {
        return "Student".equals(person.getType());
    }

    /**
     *
     * @return
     */
    public boolean isPersonAnyOrganisation() {
        return "Organisation".equals(person.getType()) || "Unapproved Organisation".equals(person.getType());
    }

    /**
     *
     * @return
     */
    public boolean isPersonApprovedOrganisation() {
        return "Organisation".equals(person.getType());
    }

    /**
     *
     * @return
     */
    public boolean isPersonUnapprovedOrganisation() {
        return "Unapproved Organisation".equals(person.getType());
    }

    /**
     *
     * @return
     */
    public boolean isPersonTheUser() {
        return Objects.equals(theUser.getId(), person.getId());
    }

}
