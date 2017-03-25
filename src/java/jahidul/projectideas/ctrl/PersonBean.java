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
 * @author Jahidul Pabel Islam, 733474
 */
@Named(value = "personBean")
@SessionScoped
public class PersonBean extends AbstractBean implements Serializable {

    /**
     *
     */
    public PersonBean() {
    }

    @EJB
    private PersonService personService;

    /**
     * the current person
     */
    protected Person person = new Person();

    /**
     * the current logged in user
     */
    protected Person user = new Person();

    /**
     * a list of ideas used to display
     */
    protected List<Person> personsList = new ArrayList<Person>();

    /**
     *
     */
    @PostConstruct
    public void init() {
        if (user.getId() != null && isUserStaff()) {
            personsList = personService.findAllPersons();
        } else {
            personsList = personService.findAllPersonsForNonStaff();
        }
    }

    /**
     *
     * @return personService
     */
    public PersonService getPersonService() {
        return personService;
    }

    /**
     *
     * @param personService the personService to set as the current
     * personService
     */
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    /**
     *
     * @return the current person
     */
    public Person getPerson() {
        return person;
    }

    /**
     *
     * @param person the person to set as the current person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     *
     * @return the current logged in user
     */
    public Person getUser() {
        return user;
    }

    /**
     *
     * @param user the person to set as the current logged in user
     */
    public void setUser(Person user) {
        this.user = user;
    }

    /**
     *
     * @return the current persons List
     */
    public List<Person> getPersonsList() {
        return personsList;
    }

    /**
     *
     * @param personsList the persons List to set as the current persons List
     */
    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }

    /**
     * try to log in using users provided credentials
     *
     * @return the view to display, index page is correct credentials, same page
     * if failed
     */
    public String logIn() {
        //try to validate credentials
        List<Person> results = personService.findPersonByUsernamePassword(user.getUsername(), user.getPassword());
        if (!results.isEmpty()) {
            user = results.get(0);
            return "index";
        }

        user = new Person();
        return AddErrorMessage("Username and/or password not recognised.");
    }

    /**
     * set the current user to a null/new person
     *
     * @return the index view
     */
    public String logOut() {
        user = new Person();
        return "index";
    }

    /**
     * used to prepare the variables to allow the user to create a new person
     *
     * @return the AddUser view
     */
    public String prepareCreate() {
        person = new Person();
        person.setType("Unapproved Organisation");
        return "SubmitUser";
    }

    /**
     * try to add a new user
     *
     * @return the view to display, User details view if the user is staff and
     * successful or same page if correct successful or failed
     */
    public String addUser() {
        try {
            
            personService.addPerson(person);
            
            if (!isUserStaff()) {
                person = new Person();
                return AddSuccessMessage("Successfully registered, An administrator will review your details and enable\n"
                        + "your account soon, come back later and try to log in.");
            }
            
            return "User";
            
        } catch (Exception be) {
            return AddErrorMessage(be.getMessage());
        }
    }

    /**
     * set up variables to try and edit the person details
     *
     * @param person the person to try to edit
     * @return the view to display, AddUser page if authorised, the same page if
     * not
     */
    public String setUpEditPerson(Person person) {
        this.person = person;
        
        if (isUserStaff() || isPersonTheUser()) {
            return "SubmitUser";
        }
        
        return AddErrorMessage("You are not authorised to edit the user.");
    }

    /**
     * update the details of the person
     *
     * @param person the person to update
     * @return the view to display, User details page if authorised, the same
     * page if not
     */
    public String updatePerson(Person person) {
        this.person = person;
        
        if (isUserStaff() || isPersonTheUser()) {
            this.person = personService.updatePerson(person);
            return "User";
        }
        
        return AddErrorMessage("You are not authorised to edit the user.");
    }

    /**
     * set up variables to view a person's details
     *
     * @param person the person to view details of
     * @return the User details view
     */
    public String viewPerson(Person person) {
        this.person = person;
        return "User";
    }

    /**
     * try to delete a person
     *
     * @param person the person to delete
     * @return the view to display, Users List view if authorised, the same page
     * if not
     */
    public String deletePerson(Person person) {
        this.person = person;
        
        //if the user is staff but isn't the person, just delete
        if (isUserStaff() && !isPersonTheUser()) {
            personService.deletePerson(this.person);
            personsList = personService.findAllPersons();
            return "Users";
        } 

        //else if the user if the person, log out and delete
        else if (isPersonTheUser()) {
            personService.deletePerson(this.person);
            personsList = personService.findAllPersons();
            user = new Person();
            return "Users";
        }
        
        return AddErrorMessage("You are not authorised to delete the user.");
    }

    /**
     * try to approve a organisation
     *
     * @param person the person(organisation) to approve
     * @return the view to display, User details view if authorised, the same
     * page if not
     */
    public String approveOrganisation(Person person) {
        this.person = person;
        
        if (isUserStaff() && isPersonUnapprovedOrganisation()) {
            this.person.setType("Organisation");
            this.person = personService.updatePerson(person);
            return "User";
        }
        
        return AddErrorMessage("You are not authorised to approve the organisation.");
    }

    /**
     * try to unapprove a organisation
     *
     * @param person the person(organisation) to unapprove
     * @return the view to display, User details view if authorised, the same
     * page if not
     */
    public String unapproveOrganisation(Person person) {
        this.person = person;
        
        if (isUserStaff() && isPersonUnapprovedOrganisation()) {
            this.person.setType("Unapproved Organisation");
            this.person = personService.updatePerson(person);
            return "User";
        }
        
        return AddErrorMessage("You are not authorised to unapprove the organisation.");
    }

    /**
     * updates list to get appropriate list of persons and return the Users List
     * page
     *
     * @return Users list view page which displays a list of persons
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
     * gets a list of students
     *
     * @return a list of just students
     */
    public List<Person> getStudents() {
        return personService.findStudents();
    }

    /**
     * updates the personsList based on the current filter and search string
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
     * @return whether or not the user is Staff
     */
    public boolean isUserStaff() {
        return "Staff".equals(user.getType());
    }

    /**
     *
     * @return whether or not the user is a Student
     */
    public boolean isUserStudent() {
        return "Student".equals(user.getType());
    }

    /**
     *
     * @return whether or not the user is a Approved Organisation
     */
    public boolean isUserApprovedOrganisation() {
        return "Organisation".equals(user.getType());
    }

    /**
     *
     * @return whether or not the current person is staff
     */
    public boolean isPersonStaff() {
        return "Staff".equals(person.getType());
    }

    /**
     *
     * @return whether or not the current person is a student
     */
    public boolean isPersonStudent() {
        return "Student".equals(person.getType());
    }

    /**
     *
     * @return whether or not the current person is any type of Organisation
     * (Approved Organisation or Unapproved Organisation)
     */
    public boolean isPersonAnyOrganisation() {
        return isPersonUnapprovedOrganisation() || isPersonApprovedOrganisation();
    }

    /**
     *
     * @return whether or not the current person is a Approved Organisation
     */
    public boolean isPersonApprovedOrganisation() {
        return "Organisation".equals(person.getType());
    }

    /**
     *
     * @return whether or not the current person is a Unapproved Organisation
     */
    public boolean isPersonUnapprovedOrganisation() {
        return "Unapproved Organisation".equals(person.getType());
    }

    /**
     *
     * @return whether or not the logged in user is the current person
     */
    public boolean isPersonTheUser() {
        return user.equals(person);
    }

}
