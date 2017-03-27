/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.bus;

import jahidul.projectideas.ents.Idea;
import jahidul.projectideas.pers.IdeaFacade;
import jahidul.projectideas.pers.PersonFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Holds all the business logic for the Idea entity
 *
 * @author Jahidul Pabel Islam, 733474
 */
@Stateless
public class IdeaService {

    @EJB
    private IdeaFacade ideaFacade;

    @EJB
    private PersonFacade personFacade;

    /**
     *
     * @return the current IdeaFacade
     */
    public IdeaFacade getIdeaFacade() {
        return ideaFacade;
    }

    /**
     *
     * @param ideaFacade the new IdeaFacade to set as the ideaFacade
     */
    public void setIdeaFacade(IdeaFacade ideaFacade) {
        this.ideaFacade = ideaFacade;
    }

    /**
     *
     * @return the current PersonFacade
     */
    public PersonFacade getPersonFacade() {
        return personFacade;
    }

    /**
     *
     * @param personFacade the new PersonFacade to set as the personFacade
     */
    public void setPersonFacade(PersonFacade personFacade) {
        this.personFacade = personFacade;
    }

    /**
     * gets a list of all ideas
     *
     * @return a list of all ideas
     */
    public List<Idea> findAll() {
        return ideaFacade.findAll();
    }

    /**
     * creates a new idea
     *
     * @param i the idea to add
     */
    public void addIdea(Idea i) {
        Date date = new Date();
        i.setDateUpdated(date);
        ideaFacade.create(i);
    }

    /**
     * update a idea
     *
     * @param i the idea to update
     * @return the updated idea
     */
    public Idea updateIdea(Idea i) {
        Date date = new Date();
        i.setDateUpdated(date);
        Idea oldIdea = ideaFacade.find(i.getId());
        if (oldIdea.getImplementer() != null && oldIdea.getImplementer() != i.getImplementer()) {
            oldIdea.getImplementer().setImplementingIdea(null);
            personFacade.edit(oldIdea.getImplementer());
        }
        return ideaFacade.edit(i);
    }

    /**
     * delete a idea
     *
     * @param i the idea to delete
     */
    public void deleteIdea(Idea i) {
        if (i.getImplementer() != null) {
            i.getImplementer().setImplementingIdea(null);
            personFacade.edit(i.getImplementer());
        }
        i.getSubmitter().getIdeas().remove(i);
        personFacade.edit(i.getSubmitter());
        ideaFacade.remove(i);
    }

    /**
     * gets a list of ideas that have the status as 'Approved' and doesn't have
     * a implementer
     *
     * @return a list of ideas that have the status as 'Approved' and doesn't
     * have a implementer
     */
    public List<Idea> findApprovedButUnallocatedIdeas() {
        return ideaFacade.findApprovedButUnallocatedIdeas();
    }

    /**
     * gets a list of ideas that are of the status provided
     *
     * @param status the status of ideas to look for
     * @return a list of ideas that are of the status provided
     */
    public List<Idea> findByStatus(String status) {
        return ideaFacade.findByStatus(status);
    }

    /**
     * gets a list of ideas that are of the status provided and contain the
     * search string
     *
     * @param status the status of ideas to look for
     * @param search the text to look for in ideas
     * @return a list of ideas that are of the status provided and contain the
     * search string
     */
    public List<Idea> findByStatusAndSearch(String status, String search) {
        return ideaFacade.findByStatusAndSearch(status, search);
    }

    /**
     * gets a list of ideas that contain the search string
     *
     * @param search the text to look for in ideas
     * @return a list of ideas that contain the search string
     */
    public List<Idea> findAllBySearch(String search) {
        return ideaFacade.findAllBySearch(search);
    }

    /**
     * gets a list of ideas that have the status as 'Approved' and doesn't have
     * a implementer and contain the search string
     *
     * @param search the text to look for in ideas
     * @return a list of ideas that have the status as 'Approved' and doesn't
     * have a implementer and contain the search string
     */
    public List<Idea> findApprovedButUnallocatedIdeasBySearch(String search) {
        return ideaFacade.findApprovedButUnallocatedIdeasBySearch(search);
    }

}
