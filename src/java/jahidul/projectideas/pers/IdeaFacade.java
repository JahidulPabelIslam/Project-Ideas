/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.pers;

import jahidul.projectideas.ents.Idea;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Facade to manage the persistence of any Idea objects
 *
 * @author Jahidul Pabel Islam, 733474
 */
@Stateless
public class IdeaFacade extends AbstractFacade<Idea> {

    @PersistenceContext(unitName = "Project-IdeasPU")
    private EntityManager em;

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
     *
     * @return the entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor for the Idea Facade
     */
    public IdeaFacade() {
        super(Idea.class);
    }

    /**
     *
     * @return a list of all ideas ordered by the date the ideas were updated
     */
    @Override
    public List<Idea> findAll() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }

    /**
     *
     * @param search the text to look for in ideas
     * @return a list of ideas that contain the search string, ordered by the
     * date the ideas were updated
     */
    public List<Idea> findAllBySearch(String search) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE lower(i.title) LIKE lower(:search) OR lower(i.aimsObjectives) LIKE lower(:search) OR lower(i.question) LIKE lower(:search) OR lower(i.deliverables) LIKE lower(:search) ORDER BY i.dateUpdated DESC", Idea.class);

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
     * @param status the status of ideas to look for
     * @return a list of ideas that are of the status provided, ordered by the
     * date the ideas were updated
     */
    public List<Idea> findByStatus(String status) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = :status ORDER BY i.dateUpdated DESC", Idea.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    /**
     *
     * @param status the status of ideas to look for
     * @param search the text to look for in ideas
     * @return a list of ideas that are of the status provided and contain the
     * search string, ordered by the date the ideas were updated
     */
    public List<Idea> findByStatusAndSearch(String status, String search) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = :status AND (lower(i.title) LIKE lower(:search) OR lower(i.aimsObjectives) LIKE lower(:search) OR lower(i.question) LIKE lower(:search) OR lower(i.deliverables) LIKE lower(:search)) ORDER BY i.dateUpdated DESC", Idea.class);
        query.setParameter("status", status);

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
     * @return a list of ideas that have the status as 'Approved' and doesn't
     * have a implementer, ordered by the date the ideas were updated
     */
    public List<Idea> findApprovedButUnallocatedIdeas() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Approved' AND i.implementer IS NULL ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }

    /**
     *
     * @param search the text to look for in ideas
     * @return a list of ideas that have the status as 'Approved' and doesn't
     * have a implementer and contain the search string, ordered by the date the
     * ideas were updated
     */
    public List<Idea> findApprovedButUnallocatedIdeasBySearch(String search) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Approved' AND i.implementer IS NULL AND (lower(i.title) LIKE lower(:search) OR lower(i.aimsObjectives) LIKE lower(:search) OR lower(i.question) LIKE lower(:search) OR lower(i.deliverables) LIKE lower(:search)) ORDER BY i.dateUpdated DESC", Idea.class);

        String finalSearch = "%";
        String[] strings = search.split(" ");
        for (String string : strings) {
            finalSearch += string + "%";
        }
        query.setParameter("search", finalSearch);

        return query.getResultList();
    }
}
