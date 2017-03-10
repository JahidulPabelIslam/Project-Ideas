/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ctrl;

import jahidul.projectideas.bus.PersonService;
import jahidul.projectideas.ents.Person;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author up733474
 */
@FacesConverter(forClass = Person.class)
public class PersonConverter implements Converter {

    @EJB
    private PersonService personService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Long idLong = Long.decode(value);
        return personService.find(idLong);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();

    }

}
