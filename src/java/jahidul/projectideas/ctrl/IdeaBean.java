/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.IdeaService;
import jahidul.projectideas.ents.Idea;
import jahidul.projectideas.ents.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Jahidul Pabel Islam, 733474
 */
@Named(value = "ideaBean")
@SessionScoped
public class IdeaBean implements Serializable {

    /**
     *
     */
    public IdeaBean() {
    }

    @EJB
    private IdeaService ideaService;

    /**
     * the current idea
     */
    protected Idea idea = new Idea();

    /**
     * a list of ideas
     */
    protected List<Idea> ideasList = new ArrayList<Idea>();

    /**
     * whether a user want to apply for idea (used for adding/updating a idea by
     * a student)
     */
    protected boolean apply = false;

    /**
     * a search string which will be changed to users search input to get list
     * of ideas via Ajax
     */
    protected String search = "";

    /**
     * a string of what types of ideas to show which will be changed to users
     * drop down input which is used to get list of ideas via Ajax
     */
    protected String filter = "Approved";

    /**
     *
     */
    @PostConstruct
    public void init() {
        //if user is staff display all ideas, otherwise only display "Approved" ideas
        if (isUserStaff()) {
            filter = "All";
            ideasList = ideaService.findAll();
        } else {
            ideasList = ideaService.findByStatus(filter);
        }
    }

    /**
     *
     * @return ideaService
     */
    public IdeaService getIdeaService() {
        return ideaService;
    }

    /**
     *
     * @param ideaService
     */
    public void setIdeaService(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    /**
     *
     * @return the current idea selected
     */
    public Idea getIdea() {
        return idea;
    }

    /**
     *
     * @param idea the idea to set as the current idea
     */
    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    /**
     *
     * @return the current list of ideas
     */
    public List<Idea> getIdeasList() {
        return ideasList;
    }

    /**
     *
     * @param ideasList the list of ideas to set as the current set of ideas
     */
    public void setIdeasList(List<Idea> ideasList) {
        this.ideasList = ideasList;
    }

    /**
     *
     * @return apply, boolean, whether or not the user has selected to apply for
     * idea
     */
    public boolean isApply() {
        return apply;
    }

    /**
     *
     * @param apply, boolean, whether or not the user has selected to apply for
     * idea
     */
    public void setApply(boolean apply) {
        this.apply = apply;
    }

    /**
     *
     * @return the current search string
     */
    public String getSearch() {
        return search;
    }

    /**
     *
     * @param search the search string to set as the current search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     *
     * @return the current filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     *
     * @param filter the filter to set as the current filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * try to add a idea
     *
     * @param theUser the person that is adding the idea
     * @return the page to go to, Idea if adding successful, same page if failed
     */
    public String addIdea(Person theUser) {
        //check if the user is logged in
        if (isUserApprovedOrganisation() || isUserStaff() || isUserStudent()) {

            //check if the user is student has tried to apply for idea but already has applied for another idea
            if (apply && isUserStudent() && theUser.getImplementingIdea() != null && !theUser.getImplementingIdea().equals(idea)) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You can't apply for more than one idea, unapply from other idea first.", "Error.");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                return null;
            } //check if user is staff and student selected to undertake idea already has applied for a idea
            else if (isUserStaff() && idea.getImplementer() != null && idea.getImplementer().getImplementingIdea() != null) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Student selected can't apply for more than one idea, Student needs to unapply from other idea first.", "Error.");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                return null;
            } //else everything is fine
            else {
                //if the user is student and has selcted to apply for idea, set the idea to the user and vise versa
                if (apply && isUserStudent() && theUser.getImplementingIdea() == null) {
                    idea.setImplementer(theUser);
                    theUser.setImplementingIdea(idea);
                }
                ideaService.addIdea(idea, theUser);
                theUser.getIdeas().add(idea);
                getPersonBean().getPersonService().updatePerson(theUser);
                ideasList = ideaService.findAll();
                return "Idea";
            }

        }
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not authorised to add a idea.", "Not authorised.");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * set up variables to view details of a idea
     *
     * @param idea the idea to view
     * @return the idea page
     */
    public String viewIdea(Idea idea) {
        this.idea = idea;
        return "Idea";
    }

    /**
     * set up to edit a idea detail
     *
     * @param idea the idea to edit
     * @return the page to go to, SubmitIdea if authorised, same page if failed
     */
    public String setUpEditIdea(Idea idea) {
        this.idea = idea;

        if (isUserSubmitter() || isUserStaff()) {
            if (this.idea.getImplementer() != null) {
                apply = true;
            } else {
                apply = false;
            }
            updatePersonsListToStudents();
            return "SubmitIdea";
        }

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not authorised to edit the idea.", "Not authorised.");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;

    }

    /**
     * try to apply for a idea
     *
     * @param idea the idea the user wants to apply for
     * @param theUser the user trying to apply for the idea
     * @return the page to go to, Idea if authorised, same page if failed
     */
    public String applyForIdea(Idea idea, Person theUser) {
        //check if user is a student, idea isn'r already taken
        if (this.idea.getImplementer() == null && isUserStudent()) {

            //checks if user hasn't already applied for another idea
            if (theUser.getImplementingIdea() == null) {
                idea.setImplementer(theUser);
                theUser.setImplementingIdea(idea);
                getPersonBean().getPersonService().updatePerson(theUser);
                this.idea = ideaService.updateIdea(idea);
                return "Idea";
            }

            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are can't apply for more than one idea, unapply from other idea first.", "Error.");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return null;
        }

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not authorised to apply for the idea.", "Not authorised.");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * try to unapply for a idea
     *
     * @param idea the idea to unapply from
     * @param theUser the user trying to unapply
     * @return the page to go to, Idea if authorised, same page if failed
     */
    public String unapplyForIdea(Idea idea, Person theUser) {
        //check if user has actually applied for the idea
        if (idea.getImplementer().equals(theUser)) {
            idea.setImplementer(null);
            theUser.setImplementingIdea(null);
            getPersonBean().getPersonService().updatePerson(theUser);
            this.idea = ideaService.updateIdea(idea);
            return "Idea";
        }

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not authorised to unapply for the idea.", "Not authorised.");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * update the current idea with the new details
     *
     * @param theUser the user trying to update the idea
     * @return the page to go to, Idea if authorised, same page if failed
     */
    public String updateIdea(Person theUser) {
        if (isUserSubmitter() || isUserStaff()) {
            if (apply && isUserStudent() && theUser.getImplementingIdea() != null && !theUser.getImplementingIdea().equals(idea)) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You can't apply for more than one idea, unapply from other idea first.", "Error.");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                return null;
            } else if (isUserStaff() && idea.getImplementer() != null && idea.getImplementer().getImplementingIdea() != null) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Student selected can't apply for more than one idea, Student needs to unapply from other idea first.", "Error.");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                return null;
            } else {

                if (apply && isUserStudent() && theUser.getImplementingIdea() == null) {
                    idea.setImplementer(theUser);
                    theUser.setImplementingIdea(idea);
                    getPersonBean().getPersonService().updatePerson(theUser);
                } else if (idea.getImplementer() != null && idea.getImplementer().equals(theUser)) {
                    if (!apply) {
                        idea.setImplementer(null);
                        theUser.setImplementingIdea(null);
                        getPersonBean().getPersonService().updatePerson(theUser);
                    }
                }

                idea = ideaService.updateIdea(idea);
                return "Idea";
            }
        }

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not authorised to edit the idea.", "Not authorised.");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * try to delete a idea
     *
     * @param idea the idea to delete
     * @return the page to go to, index if authorised, same page if failed
     */
    public String deleteIdea(Idea idea) {
        this.idea = idea;
        if (isUserSubmitter() || isUserStaff()) {
            ideaService.deleteIdea(idea);
            Person theUser = getPersonBean().getUser();
            theUser.getIdeas().remove(idea);
            getPersonBean().getPersonService().updatePerson(theUser);
            ideasList = ideaService.findAll();
            this.idea = new Idea();
            return "index";
        }
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not authorised to delete the idea.", "Not authorised.");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * used to prepare the variables to allow the user to create a new idea
     *
     * @return the page to go to, SubmitIdea if authorised, same page if failed
     */
    public String prepareCreate() {
        //check if user is logged in
        if (isUserApprovedOrganisation() || isUserStaff() || isUserStudent()) {
            idea = new Idea();
            idea.setStatus("Provisional");
            updatePersonsListToStudents();
            return "SubmitIdea";
        }
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not authorised to add a idea.", "Not authorised.");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * updates the ideasList based on the current filter and search string
     */
    public void updateIdeasList() {
        switch (filter) {
            case "Approved But Unallocated":
                if (!"".equals(search)) {
                    ideasList = ideaService.findApprovedButUnallocatedIdeasBySearch(search);
                } else {
                    ideasList = ideaService.findApprovedButUnallocatedIdeas();
                }
                break;
            case "All":
                if (!"".equals(search)) {
                    ideasList = ideaService.findAllBySearch(search);
                } else {
                    ideasList = ideaService.findAll();
                }
                break;
            default:
                if (!"".equals(search)) {
                    ideasList = ideaService.findByStatusAndSearch(filter, search);
                } else {
                    ideasList = ideaService.findByStatus(filter);
                }
                break;
        }
    }

    /**
     * updates list to get appropriate list of ideas and return the index page
     * and reset the search input
     *
     * @return index page which displays a list of ideas
     */
    public String viewIdeas() {
        //check if user is staff, then get all ideas/
        if (isUserStaff()) {
            filter = "All";
        } //otherwise only get approved ideas
        else {
            filter = "Approved";
        }
        search = "";
        updateIdeasList();
        return "index";
    }

    /**
     * get the personBean controller
     *
     * @return the personBean
     */
    public PersonBean getPersonBean() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return (PersonBean) elContext.getELResolver().getValue(elContext, null, "personBean");
    }

    /**
     * updates personsList in the personBean to only Students
     */
    public void updatePersonsListToStudents() {
        getPersonBean().personsList = getPersonBean().getStudents();
    }

    /**
     *
     * @return whether or not the user is the submitter of a idea
     */
    public boolean isUserSubmitter() {
        return getPersonBean().getUser().equals(idea.getSubmitter());
    }

    /**
     *
     * @return whether or not the user is Staff
     */
    public boolean isUserStaff() {
        return getPersonBean().isUserStaff();
    }

    /**
     *
     * @return whether or not the user is a Approved Organisation
     */
    public boolean isUserApprovedOrganisation() {
        return getPersonBean().isUserApprovedOrganisation();
    }

    /**
     *
     * @return whether or not the user is a Student
     */
    public boolean isUserStudent() {
        return getPersonBean().isUserStudent();
    }
}
