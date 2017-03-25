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
 * Facade to manage the persistence of any Person objects
 *
 * @author Jahidul Pabel Islam, 733474
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "Project-IdeasPU")
    private EntityManager em;

    /**
     *
     * @return the entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @return the current EntityManager
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     *
     * @param em the EntityManager to set as the current EntityManager
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Constructor for the Person Facade
     */
    public PersonFacade() {
        super(Person.class);
    }

    /**
     *
     * @param username the username of a user looking for
     * @param password the password of a user looking for
     * @return the user with the username and password provided should be null
     * or a user
     */
    public List<Person> findPersonByUsernamePassword(String username, String password) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.username = :username AND p.password = :password AND NOT p.type = 'Unapproved Organisation'", Person.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultList();
    }

    /**
     *
     * @param username the username of a user looking for
     * @return the user with the username provided should be null or a user
     */
    public List<Person> findPersonByUsername(String username) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.username = :username", Person.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    /**
     *
     * @return a list of users that are students, ordered by their surname
     */
    public List<Person> findStudents() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Student' ORDER BY p.firstName ASC", Person.class);
        return query.getResultList();
    }

    /**
     *
     * @return a list of users that are staff, ordered by their surname
     */
    public List<Person> findStaff() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Staff' ORDER BY p.firstName ASC", Person.class);
        return query.getResultList();
    }

    /**
     *
     * @return a list of users that are Organisations, ordered by their
     * Organisation name
     */
    public List<Person> findOrganisations() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Organisation' ORDER BY p.organisationName ASC", Person.class);
        return query.getResultList();
    }

    /**
     *
     * @return a list of users that are Unapproved Organisations, ordered by
     * their Organisation name
     */
    public List<Person> findUnapprovedOrganisations() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Unapproved Organisation' ORDER BY p.organisationName ASC", Person.class);
        return query.getResultList();
    }

    /**
     *
     * @param search the text to look for in users
     * @return a list of users that contain the search string provided
     */
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

    /**
     *
     * @param search the text to look for in users
     * @return a list of users that are students and contain the search string
     * provided, ordered by their surname
     */
    public List<Person> findStudentsBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Student' AND ( lower(p.firstName) LIKE lower(:search) OR lower(p.surname) LIKE lower(:search) ) ORDER BY p.firstName ASC", Person.class);

        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);

        return query.getResultList();
    }

    /**
     *
     * @param search the text to look for in users
     * @return a list of users that are staff and contain the search string
     * provided, ordered by their surname
     */
    public List<Person> findStaffBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Staff' AND ( lower(p.firstName) LIKE lower(:search) OR lower(p.surname) LIKE lower(:search) OR lower(p.staffRole) LIKE lower(:search) ) ORDER BY p.firstName ASC", Person.class);

        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);

        return query.getResultList();
    }

    /**
     *
     * @param search search the text to look for in users
     * @return a list of users that are Organisations and contain the search
     * string provided, ordered by their Organisation name
     */
    public List<Person> findOrganisationsBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Organisation' AND ( lower(p.organisationName) LIKE lower(:search) ) ORDER BY p.organisationName ASC", Person.class);

        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);

        return query.getResultList();
    }

    /**
     *
     * @param search search the text to look for in users
     * @return a list of users that are Unapproved Organisations and contain the
     * search string provided, ordered by their Organisation name
     */
    public List<Person> findUnapprovedOrganisationsBySearch(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.type = 'Unapproved Organisation' AND ( lower(p.organisationName) LIKE lower(:search) ) ORDER BY p.organisationName ASC", Person.class);

        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);

        return query.getResultList();
    }

    /**
     *
     * @return a list of users that aren't Unapproved Organisation
     */
    public List<Person> findAllPersonsForNonStaff() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE NOT p.type = 'Unapproved Organisation'", Person.class);
        return query.getResultList();
    }

    /**
     *
     * @param search search the text to look for in users
     * @return a list of users that aren't Unapproved Organisation and contain
     * the search string provided
     */
    public List<Person> findPersonsBySearchForNonStaff(String search) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE NOT p.type = 'Unapproved Organisation' AND ( lower(p.firstName) LIKE lower(:search) OR lower(p.surname) LIKE lower(:search) OR lower(p.organisationName) LIKE lower(:search) )", Person.class);

        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);

        return query.getResultList();
    }
}
