/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * A extendable controller bean, includes the basics needed for a controller
 * bean
 *
 * @author Jahidul Pabel Islam!
 */
@Named(value = "abstractBean")
@SessionScoped
public class AbstractBean implements Serializable {

    /**
     * Creates a new instance of AbstractBean
     */
    public AbstractBean() {
    }

    /**
     * a search string which will be changed to users search input to get list
     * via Ajax
     */
    protected String search = "";

    /**
     * a string of what types of ideas to show which will be changed to users
     * drop down input which is used to get list via Ajax
     */
    protected String filter = "All";

    /**
     * display a error message and refresh the current page
     *
     * @param message the message to display
     * @return null, to refresh the current page
     */
    public String AddErrorMessage(String message) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * display a success/confirmation message and refresh the current page
     *
     * @param message the message to display
     * @return null, to refresh the current page
     */
    public String AddSuccessMessage(String message) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
    }

    /**
     * return the current search string
     *
     * @return the current search string
     */
    public String getSearch() {
        return search;
    }

    /**
     * set a new search string
     *
     * @param search the search string to set as the current search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * return the current filter
     *
     * @return the current filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * set a new filter
     *
     * @param filter the filter to set as the current filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

}
