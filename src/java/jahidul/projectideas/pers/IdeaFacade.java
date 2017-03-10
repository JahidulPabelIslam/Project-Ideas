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
    @Override
    public List<Idea> findAll() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }

    /**
     *
     * @param search
     * @return
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
     * @param status
     * @return
     */
    public List<Idea> findByStatus(String status) {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = :status ORDER BY i.dateUpdated DESC", Idea.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    /**
     *
     * @param status
     * @param search
     * @return
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
     * @return
     */
    public List<Idea> findApprovedButUnallocatedIdeas() {
        TypedQuery<Idea> query = em.createQuery("SELECT i FROM Idea i WHERE i.status = 'Approved' AND i.implementer IS NULL ORDER BY i.dateUpdated DESC", Idea.class);
        return query.getResultList();
    }

    /**
     *
     * @param search
     * @return
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
