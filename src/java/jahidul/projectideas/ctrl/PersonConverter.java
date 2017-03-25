/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.PersonService;
import jahidul.projectideas.ents.Person;
import jahidul.projectideas.pers.PersonFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Converter for the Person Entity, used for drop down menu.
 *
 * @author Jahidul Pabel Islam, 733474
 */
@FacesConverter(forClass = Person.class)
public class PersonConverter implements Converter {

    /**
     *
     * @param context
     * @param component
     * @param value the string value of a object
     * @return the string provided as a Person object
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();

        PersonBean personBean = (PersonBean) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "personBean");

        PersonService ps = (PersonService) personBean.getPersonService();

        PersonFacade pf = (PersonFacade) ps.getPersonFacade();

        Long id = Long.decode(value);
        Person p = pf.find(id);

        return p;
    }

    /**
     *
     * @param context
     * @param component
     * @param object
     * @return the Person Object as a string
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {

        if (object == null) {
            return "";
        }

        if (object instanceof Person) {
            return ((Person) object).getId().toString();
        } else {
            throw new Error("object is not of type Person");
        }
    }
}
