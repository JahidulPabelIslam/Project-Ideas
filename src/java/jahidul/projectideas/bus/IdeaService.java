/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.bus;

import jahidul.projectideas.ents.Idea;
import jahidul.projectideas.ents.Person;
import jahidul.projectideas.pers.IdeaFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author up733474
 */
@Stateless
public class IdeaService {

    @EJB
    private IdeaFacade ideaFacade;

    /**
     *
     * @return
     */
    public List<Idea> findAllIdeas() {
        return ideaFacade.findAllIdeas();
    }
    
    /**
     *
     * @param i
     * @param p
     */
    public void addIdea(Idea i, Person p) {
        i.setSubmitter(p);
        Date date = new Date();
        i.setDateUpdated(date);
        ideaFacade.create(i);
    }

    /**
     *
     * @param i
     * @return
     */
    public Idea editIdea(Idea i) {
        Date date = new Date();
        i.setDateUpdated(date);
        return ideaFacade.edit(i);
    }

    /**
     *
     * @param i
     */
    public void deleteIdea(Idea i) {
        ideaFacade.remove(i);
    }
    
    /**
     *
     * @return
     */
    public List<Idea> findApprovedButUnallocatedIdeas() {
        return ideaFacade.findApprovedButUnallocatedIdeas();
    }
    
    /**
     *
     * @return
     */
    public List<Idea> findProvisionalIdeas() {
        return ideaFacade.findProvisionalIdeas();
    }
    
    /**
     *
     * @param search
     * @return
     */
    public List<Idea> findIdeasBySearch(String search) {
        return ideaFacade.findIdeasBySearch(search);
    }
    
    /**
     *
     * @param search
     * @return
     */
    public List<Idea> findApprovedButUnallocatedIdeasBySearch(String search) {
        return ideaFacade.findApprovedButUnallocatedIdeasBySearch(search);
    }
    
    /**
     *
     * @param search
     * @return
     */
    public List<Idea> findProvisionalIdeasBySearch(String search) {
        return ideaFacade.findProvisionalIdeasBySearch(search);
    }
    
    /**
     *
     * @param search
     * @return
     */
    public List<Idea> findApprovedIdeasBySearch(String search) {
        return ideaFacade.findApprovedIdeasBySearch(search);
    }
    
    /**
     *
     * @return
     */
    public List<Idea> findApprovedIdeas() {
        return ideaFacade.findApprovedIdeas();
    }
    
    /**
     *
     * @param search
     * @return
     */
    public List<Idea> findRejectedIdeasBySearch(String search) {
        return ideaFacade.findRejectedIdeasBySearch(search);
    }
    
    /**
     *
     * @return
     */
    public List<Idea> findRejectedIdeas() {
        return ideaFacade.findRejectedIdeas();
    }
}
