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
 *
 * @author up733474
 */
@Stateless
public class IdeaFacade extends AbstractFacade<Idea> {

    @PersistenceContext(unitName = "Project-IdeasPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public IdeaFacade() {
        super(Idea.class);
    }
    
    /**
     *
     * @return
     */
    public List<Idea> findAllIdeas() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }
    
    /**
     *
     * @return
     */
    public List<Idea> findProvisionalIdeas() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Provisional' ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public List<Idea> findApprovedButUnallocatedIdeas() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Approved' AND i.appliedStudent IS NULL ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }

    /**
     *
     * @param search
     * @return
     */
    public List<Idea> findIdeasBySearch(String search) {
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
     * @param search
     * @return
     */
    public List<Idea> findProvisionalIdeasBySearch(String search) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Provisional' AND (lower(i.title) LIKE lower(:search) OR lower(i.aimsObjectives) LIKE lower(:search) OR lower(i.question) LIKE lower(:search) OR lower(i.deliverables) LIKE lower(:search)) ORDER BY i.dateUpdated DESC", Idea.class);
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
     * @param search
     * @return
     */
    public List<Idea> findApprovedButUnallocatedIdeasBySearch(String search) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Approved' AND i.appliedStudent IS NULL AND (lower(i.title) LIKE lower(:search) OR lower(i.aimsObjectives) LIKE lower(:search) OR lower(i.question) LIKE lower(:search) OR lower(i.deliverables) LIKE lower(:search)) ORDER BY i.dateUpdated DESC", Idea.class);
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
     * @param search
     * @return
     */
    public List<Idea> findApprovedIdeasBySearch(String search) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Approved' AND (lower(i.title) LIKE lower(:search) OR lower(i.aimsObjectives) LIKE lower(:search) OR lower(i.question) LIKE lower(:search) OR lower(i.deliverables) LIKE lower(:search)) ORDER BY i.dateUpdated DESC", Idea.class);
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
     * @return
     */
    public List<Idea> findApprovedIdeas() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Approved' ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }

    /**
     *
     * @param search
     * @return
     */
    public List<Idea> findRejectedIdeasBySearch(String search) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Rejected' AND (lower(i.title) LIKE lower(:search) OR lower(i.aimsObjectives) LIKE lower(:search) OR lower(i.question) LIKE lower(:search) OR lower(i.deliverables) LIKE lower(:search)) ORDER BY i.dateUpdated DESC", Idea.class);
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
     * @return
     */
    public List<Idea> findRejectedIdeas() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Rejected' ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }
}
