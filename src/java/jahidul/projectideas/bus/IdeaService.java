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

    public List<Idea> findAllIdeas() {
        return ideaFacade.findAll();
    }
    
    public void addIdea(Idea i, Person p) {
        i.setSubmitter(p);
        Date date = new Date();
        i.setDateUpdated(date);
        ideaFacade.create(i);
    }

    public Idea editIdea(Idea i) {
        Date date = new Date();
        i.setDateUpdated(date);
        return ideaFacade.edit(i);
    }

    public void deleteIdea(Idea i) {
        ideaFacade.remove(i);
    }
    
    public List<Idea> findApprovedButUnallocatedIdeas() {
        return ideaFacade.findApprovedButUnallocatedIdeas();
    }
    
    public List<Idea> findProvisionalIdeas() {
        return ideaFacade.findProvisionalIdeas();
    }
    
    public List<Idea> findIdeasBySearch(String search) {
        return ideaFacade.findIdeasBySearch(search);
    }
    
    public List<Idea> findApprovedButUnallocatedIdeasBySearch(String search) {
        return ideaFacade.findApprovedButUnallocatedIdeasBySearch(search);
    }
    
    public List<Idea> findProvisionalIdeasBySearch(String search) {
        return ideaFacade.findProvisionalIdeasBySearch(search);
    }
    
    public List<Idea> findApprovedIdeasBySearch(String search) {
        return ideaFacade.findApprovedIdeasBySearch(search);
    }
    
    public List<Idea> findApprovedIdeas() {
        return ideaFacade.findApprovedIdeas();
    }
    
    public List<Idea> findRejectedIdeasBySearch(String search) {
        return ideaFacade.findRejectedIdeasBySearch(search);
    }
    
    public List<Idea> findRejectedIdeas() {
        return ideaFacade.findRejectedIdeas();
    }
}
