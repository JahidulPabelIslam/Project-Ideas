/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.bus;

import jahidul.projectideas.ents.Idea;
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
public class PersonBean {

    @EJB
    private PersonFacade personFacade;
    
    public List<Person> findAllPersons() {
        return personFacade.findAll();
    }

    public void addPerson(Person p) {
        personFacade.create(p);
    }
    
    public List findPersonByUsernameAndPassword(String username, String password) {
        return personFacade.findPersonByUsernameAndPassword(username, password);
    }
}