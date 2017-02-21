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
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author up733474
 */
@Named(value = "ideaCtrl")
@RequestScoped
public class IdeaCtrl {

    /**
     * Creates a new instance of IdeaCtrl
     */
    public IdeaCtrl() {
    }

    @EJB
    private IdeaBean ideaBean;

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
    protected String search;

    /**
     *
     */
    @PostConstruct
    public void init() {
        ideasList = ideaBean.findAllIdeas();
    }

    /**
     *
     * @return
     */
    public IdeaBean getIdeaBean() {
        return ideaBean;
    }

    /**
     *
     * @param ideaBean
     */
    public void setIdeaBean(IdeaBean ideaBean) {
        this.ideaBean = ideaBean;
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
     * @param p
     * @return
     */
    public String addIdea(Person p) {
        if (apply) {
            idea.setAppliedStudent(p);
        }
        ideaBean.addIdea(idea, p);
        return "Idea.xhtml";
    }

    /**
     *
     * @param idea
     * @return
     */
    public String viewIdea(Idea idea) {
        this.idea = idea;
        return "Idea.xhtml";
    }

    /**
     *
     * @param idea
     * @return
     */
    public String setUpEditIdea(Idea idea) {
        this.idea = idea;
        if (this.idea.getAppliedStudent() != null) {
            apply = true;
        }
        return "SubmitIdea.xhtml";
    }

    /**
     *
     * @return
     */
    public String editIdea() {
        this.idea = ideaBean.editIdea(idea);
        return "Idea.xhtml";
    }

    /**
     *
     * @param idea
     * @return
     */
    public String deleteIdea(Idea idea) {
        ideaBean.deleteIdea(idea);
        ideasList = ideaBean.findAllIdeas();
        return "index.xhtml";
    }
}