/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.pers;

import jahidul.projectideas.ents.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author up733474
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "Project-IdeasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }

    public List<Person> logIn(String username, String password) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.username = :username AND p.password = :password AND NOT p.type = 'Unapproved Organisation'", Person.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultList();
    }

    public List<Person> findStudents() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Student'", Person.class);
        return query.getResultList();
    }

    public List<Person> findStaff() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Staff'", Person.class);
        return query.getResultList();
    }

    public List<Person> findOrganisations() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Organisation'", Person.class);
        return query.getResultList();
    }

    public List<Person> findUnapprovedOrganisations() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Unapproved Organisation'", Person.class);
        return query.getResultList();
    }

    public List<Person> findPersonsBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE lower(p.firstName) LIKE lower(:search) OR lower(p.surname) LIKE lower(:search) OR lower(p.organisationName) LIKE lower(:search)", Person.class);
        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);
        return query.getResultList();
    }

    public List<Person> findStudentsBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Student' AND ( lower(p.firstName) LIKE lower(:search) OR lower(p.surname) LIKE lower(:search) )", Person.class);
        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);
        return query.getResultList();
    }

    public List<Person> findStaffBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Staff' AND ( lower(p.firstName) LIKE lower(:search) OR lower(p.surname) LIKE lower(:search) OR lower(p.staffRole) LIKE lower(:search) )", Person.class);
        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);
        return query.getResultList();
    }

    public List<Person> findOrganisationsBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Organisation' AND ( lower(p.organisationName) LIKE lower(:search) )", Person.class);
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }

    public List<Person> findUnapprovedOrganisationsBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Unapproved Organisation' AND ( lower(p.organisationName) LIKE lower(:search) )", Person.class);
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }

    public List<Person> findAllPersonsForNonStaff() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE NOT p.type = 'Unapproved Organisation'", Person.class);
        return query.getResultList();
    }

    public List<Person> findPersonsBySearchForNonStaff(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE NOT p.type = 'Unapproved Organisation' AND ( lower(p.firstName) LIKE lower(:search) OR lower(p.surname) LIKE lower(:search) OR lower(p.organisationName) LIKE lower(:search) )", Person.class);
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }
}
