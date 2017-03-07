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
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author up733474
 */
@Named(value = "ideaBean")
@SessionScoped
public class IdeaBean implements Serializable {

    public IdeaBean() {
    }

    @EJB
    private IdeaService ideaService;

    protected Idea idea = new Idea();

    protected List<Idea> ideasList = new ArrayList<Idea>();

    protected boolean apply = false;

    protected String search = "";

    protected String filter = "All";

    @PostConstruct
    public void init() {
        ideasList = ideaService.findAllIdeas();
    }

    public IdeaService getIdeaService() {
        return ideaService;
    }

    public void setIdeaService(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public List<Idea> getIdeasList() {
        return ideasList;
    }

    public void setIdeasList(List<Idea> ideasList) {
        this.ideasList = ideasList;
    }

    public boolean isApply() {
        return apply;
    }

    public void setApply(boolean apply) {
        this.apply = apply;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String addIdea(Person p) {
        if (isTheUserApprovedOrganisation() || isUserTheStaff() || isUserStudent()) {
            if (apply && isUserStudent()) {
                idea.setAppliedStudent(p);
            }
            ideaService.addIdea(idea, p);
            ideasList = ideaService.findAllIdeas();
            return "Idea.xhtml";
        }
        return null;
    }

    public String viewIdea(Idea idea) {
        this.idea = idea;
        return "Idea";
    }

    public String setUpEditIdea(Idea idea) {
        this.idea = idea;
        if (isUserTheSubmitter() || isUserTheStaff()) {
            if (this.idea.getAppliedStudent() != null) {
                apply = true;
            }
            return "SubmitIdea";
        }
        return null;

    }

    public String editIdea(Idea idea) {
        this.idea = idea;
        if (isUserTheSubmitter() || isUserTheStaff()) {
            this.idea = ideaService.editIdea(idea);
        }
        return "Idea";
    }

    public String deleteIdea(Idea idea) {
        this.idea = idea;
        if (isUserTheSubmitter() || isUserTheStaff()) {
            ideaService.deleteIdea(idea);
            ideasList = ideaService.findAllIdeas();
            this.idea = new Idea();
        }
        return "index";
    }

    public String prepareCreate() {
        if (isTheUserApprovedOrganisation() || isUserTheStaff() || isUserStudent()) {
            idea = new Idea();
            idea.setStatus("Provisional");
            updatePersonsList();
            return "SubmitIdea";
        }
        return null;
    }

    public void updateIdeasList() {
        switch (filter) {
            case "Approved But Unallocated":
                if (!"".equals(search)) {
                    ideasList = ideaService.findApprovedButUnallocatedIdeasBySearch(search);
                } else {
                    ideasList = ideaService.findApprovedButUnallocatedIdeas();
                }
                break;
            case "Provisional":
                if (!"".equals(search)) {
                    ideasList = ideaService.findProvisionalIdeasBySearch(search);
                } else {
                    ideasList = ideaService.findProvisionalIdeas();
                }
                break;
            case "Approved":
                if (!"".equals(search)) {
                    ideasList = ideaService.findApprovedIdeasBySearch(search);
                } else {
                    ideasList = ideaService.findApprovedIdeas();
                }
                break;
            case "Rejected":
                if (!"".equals(search)) {
                    ideasList = ideaService.findRejectedIdeasBySearch(search);
                } else {
                    ideasList = ideaService.findRejectedIdeas();
                }
                break;
            default:
                if (!"".equals(search)) {
                    ideasList = ideaService.findIdeasBySearch(search);
                } else {
                    ideasList = ideaService.findAllIdeas();
                }
                break;
        }
    }

    public void updatePersonsList() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        PersonBean personBean = (PersonBean) elContext.getELResolver().getValue(elContext, null, "personBean");
        personBean.personsList = personBean.getStudents();
    }

    public String viewAllIdeas() {
        ideasList = ideaService.findAllIdeas();
        filter = "All";
        search = "";
        return "index";
    }

    public boolean isUserTheSubmitter() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        PersonBean personBean = (PersonBean) elContext.getELResolver().getValue(elContext, null, "personBean");
        return idea.getSubmitter() == personBean.theUser;
    }

    public boolean isUserTheStaff() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        PersonBean personBean = (PersonBean) elContext.getELResolver().getValue(elContext, null, "personBean");
        return personBean.isUserStaff();
    }

    public boolean isTheUserApprovedOrganisation() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        PersonBean personBean = (PersonBean) elContext.getELResolver().getValue(elContext, null, "personBean");
        return personBean.isTheUserApprovedOrganisation();
    }

    public boolean isUserStudent() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        PersonBean personBean = (PersonBean) elContext.getELResolver().getValue(elContext, null, "personBean");
        return personBean.isUserStudent();
    }
}
