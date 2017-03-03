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
import javax.enterprise.context.SessionScoped;
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

    protected String search;

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
        if (apply) {
            idea.setAppliedStudent(p);
        }
        ideaService.addIdea(idea, p);
        ideasList = ideaService.findAllIdeas();
        return "Idea.xhtml";
    }

    public String viewIdea(Idea idea) {
        this.idea = idea;
        return "Idea";
    }

    public String setUpEditIdea(Idea idea) {
        this.idea = idea;
        if (this.idea.getAppliedStudent() != null) {
            apply = true;
        }
        return "SubmitIdea.xhtml";
    }

    public String editIdea(Idea idea) {
        this.idea = ideaService.editIdea(idea);
        ideasList = ideaService.findAllIdeas();
        return "Idea.xhtml";
    }

    public String deleteIdea(Idea idea) {
        ideaService.deleteIdea(idea);
        ideasList = ideaService.findAllIdeas();
        return "index.xhtml";
    }

    public void findAllIdeas() {
        ideasList = ideaService.findAllIdeas();
    }

    public void findApprovedButUnallocatedIdeas() {
        ideasList = ideaService.findApprovedButUnallocatedIdeas();
    }

    public void findProvisionalIdeas() {
        ideasList = ideaService.findProvisionalIdeas();
    }

    public String prepareCreate() {
        idea = new Idea();
        idea.setStatus("Provisional");
        return "SubmitIdea";
    }

    public void findIdeasByText() {
        ideasList = ideaService.findIdeasByText(search);
    }

    public void getFilteredIdeas() {
        if ("Approved But Unallocated".equals(filter)) {
            ideasList = ideaService.findApprovedButUnallocatedIdeas();
        } else if ("Provisional".equals(filter)) {
            ideasList = ideaService.findProvisionalIdeas();
        } else {
            ideasList = ideaService.findAllIdeas();
        }
    }

    public String viewAllIdeas() {
        ideasList = ideaService.findAllIdeas();
        return "index";
    }
}