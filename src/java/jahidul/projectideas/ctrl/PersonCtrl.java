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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author up733474
 */
@ManagedBean
@RequestScoped
public class PersonCtrl {

    /**
     * Creates a new instance of UserCtrl
     */
    public PersonCtrl() {
    }

    @EJB
    private PersonBean personBean;
    protected Person person = new Person();
    protected Person theUser = new Person();
    protected List<Person> personsList = new ArrayList<Person>();

    public PersonBean getPersonBean() {
        return personBean;
    }

    public void setPersonBean(PersonBean personBean) {
        this.personBean = personBean;
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

    private List<Person> results;

    public String logIn() {
        results = personBean.findPersonByUsernameAndPassword(theUser.getUsername(), theUser.getPassword());
        if (!results.isEmpty()) {
            theUser = results.get(0);
            return "index";
        }
        return "LogIn";
    }

    public String logOut() {
        theUser = new Person();
        return "index";
    }

    public String addUser() {
        personBean.addPerson(person);
        person = new Person();
        return "User";
    }
}