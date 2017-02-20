/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.IdeaBean;
import jahidul.projectideas.ents.Idea;
import jahidul.projectideas.ents.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author up733474
 */
@ManagedBean
@RequestScoped
public class IdeaCtrl {

    /**
     * Creates a new instance of IdeaBean
     */
    public IdeaCtrl() {
    }

    @EJB
    private IdeaBean ideaBean;

    protected Idea idea = new Idea();
    protected List<Idea> ideasList = new ArrayList<Idea>();

    @PostConstruct
    public void init() {
        ideasList = ideaBean.findAllIdeas();
    }

    public IdeaBean getIdeaBean() {
        return ideaBean;
    }

    public void setIdeaBean(IdeaBean ideaBean) {
        this.ideaBean = ideaBean;
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

    public String addIdea(Person p) {
        ideaBean.addIdea(idea, p);
        return "Idea";
    }

    public String viewIdea(Idea idea) {
        this.idea = idea;
        return "Idea";
    }

    public String setUpEditIdea(Idea idea) {
        this.idea = idea;
        return "SubmitIdea";
    }

    public String editIdea() {
        ideaBean.editIdea(idea);
        return "Idea";
    }

    public String deleteIdea(Idea idea) {
        ideaBean.deleteIdea(idea);
        ideasList = ideaBean.findAllIdeas();
        return "index";
    }
}
