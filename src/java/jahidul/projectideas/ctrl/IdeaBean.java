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
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author up733474
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
     *
     */
    protected Idea idea = new Idea();

    /**
     *
     */
    protected List<Idea> ideasList = new ArrayList<Idea>();

    /**
     *
     */
    protected boolean apply = false;

    /**
     *
     */
    protected String search = "";

    /**
     *
     */
    protected String filter = "Approved";

    /**
     *
     */
    @PostConstruct
    public void init() {
        if (isUserStaff()) {
            filter = "All";
        }
        updateIdeasList();
    }

    /**
     *
     * @return
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
     * @return
     */
    public Idea getIdea() {
        return idea;
    }

    /**
     *
     * @param idea
     */
    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    /**
     *
     * @return
     */
    public List<Idea> getIdeasList() {
        return ideasList;
    }

    /**
     *
     * @param ideasList
     */
    public void setIdeasList(List<Idea> ideasList) {
        this.ideasList = ideasList;
    }

    /**
     *
     * @return
     */
    public boolean isApply() {
        return apply;
    }

    /**
     *
     * @param apply
     */
    public void setApply(boolean apply) {
        this.apply = apply;
    }

    /**
     *
     * @return
     */
    public String getSearch() {
        return search;
    }

    /**
     *
     * @param search
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     *
     * @return
     */
    public String getFilter() {
        return filter;
    }

    /**
     *
     * @param filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     *
     * @param p
     * @return
     */
    public String addIdea(Person p) {
        if (isUserApprovedOrganisation() || isUserStaff() || isUserStudent()) {
            if (apply && isUserStudent()) {
                idea.setAppliedStudent(p);
            }
            ideaService.addIdea(idea, p);
            ideasList = ideaService.findAll();
            return "Idea.xhtml";
        }
        return null;
    }

    /**
     *
     * @param idea
     * @return
     */
    public String viewIdea(Idea idea) {
        this.idea = idea;
        return "Idea";
    }

    /**
     *
     * @param idea
     * @return
     */
    public String setUpEditIdea(Idea idea) {
        this.idea = idea;
        if (isUserSubmitter() || isUserStaff()) {
            if (this.idea.getAppliedStudent() != null) {
                apply = true;
            }
            return "SubmitIdea";
        }
        return null;

    }

    /**
     *
     * @param idea
     * @param theUser
     * @return
     */
    public String applyForIdea(Idea idea, Person theUser) {
        if (this.idea.getAppliedStudent() == null && (isUserStudent())) {
            idea.setAppliedStudent(theUser);
            this.idea = ideaService.editIdea(idea);
            return "Idea";
        }
        return null;
    }

    /**
     *
     * @param idea
     * @param theUser
     * @return
     */
    public String unapplyForIdea(Idea idea, Person theUser) {
        if (Objects.equals(this.idea.getAppliedStudent().getId(), theUser.getId())) {
            idea.setAppliedStudent(null);
            this.idea = ideaService.editIdea(idea);
            return "Idea";
        }
        return "index";
    }

    /**
     *
     * @param idea
     * @param theUser
     * @return
     */
    public String editIdea(Idea idea, Person theUser) {
        if (isUserSubmitter() || isUserStaff()) {
            if (apply && isUserStudent() && this.idea.getAppliedStudent() == null) {
                idea.setAppliedStudent(theUser);
            } else if (this.idea.getAppliedStudent() == theUser && !apply){
                idea.setAppliedStudent(null);
            }
            this.idea = ideaService.editIdea(idea);
        }
        return "Idea";
    }

    /**
     *
     * @param idea
     * @return
     */
    public String deleteIdea(Idea idea) {
        this.idea = idea;
        if (isUserSubmitter() || isUserStaff()) {
            ideaService.deleteIdea(idea);
            ideasList = ideaService.findAll();
            this.idea = new Idea();
        }
        return "index";
    }

    /**
     *
     * @return
     */
    public String prepareCreate() {
        if (isUserApprovedOrganisation() || isUserStaff() || isUserStudent()) {
            idea = new Idea();
            idea.setStatus("Provisional");
            updatePersonsList();
            return "SubmitIdea";
        }
        return null;
    }

    /**
     *
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
                    ideasList = ideaService.findByStatusAndSearch(search, filter);
                } else {
                    ideasList = ideaService.findByStatus(filter);
                }
                break;
        }
    }
    
    /**
     *
     * @return
     */
    public String viewAllIdeas() {
        if (isUserStaff()) {
            filter = "All";
        } else {
            filter = "Approved";
        }
        search = "";
        updateIdeasList();
        return "index";
    }
    
    public PersonBean getPersonBean() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return (PersonBean) elContext.getELResolver().getValue(elContext, null, "personBean");
    }

    /**
     *
     */
    public void updatePersonsList() {
        getPersonBean().personsList = getPersonBean().getStudents();
    }

    /**
     *
     * @return
     */
    public boolean isUserSubmitter() {
        return Objects.equals(getPersonBean().theUser.getId(), idea.getSubmitter().getId());
    }

    /**
     *
     * @return
     */
    public boolean isUserStaff() {
        return getPersonBean().isUserStaff();
    }

    /**
     *
     * @return
     */
    public boolean isUserApprovedOrganisation() {
        return getPersonBean().isUserApprovedOrganisation();
    }

    /**
     *
     * @return
     */
    public boolean isUserStudent() {
        return getPersonBean().isUserStudent();
    }
}