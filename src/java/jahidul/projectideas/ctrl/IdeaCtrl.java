/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.IdeaBean;
import jahidul.projectideas.ents.Idea;
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
    
    public String addIdea() {
        ideaBean.addIdea(idea);
        return "index";
    }
}
