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
    protected List<Person> personsList = new ArrayList<Person>();
    
    @PostConstruct
    public void init() {
        personsList = personBean.findAllPersons();
        if (personsList.size() > 0) {
             person = personsList.get(0);
        }
       
    }

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
    
    public String logIn() {
        personBean.addPerson(person);
        person = new Person();
        return "index";
    }
}