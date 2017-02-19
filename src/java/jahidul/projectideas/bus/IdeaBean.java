/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.bus;

import jahidul.projectideas.ents.Idea;
import jahidul.projectideas.pers.IdeaFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author up733474
 */
@Stateless
public class IdeaBean {

    @EJB
    private IdeaFacade ideaFacade;

    public void addIdea(Idea i) {
        ideaFacade.create(i);
    }
}
