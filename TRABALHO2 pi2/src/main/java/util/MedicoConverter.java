package util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.MedicoDAO;
import entidades.Medico;

@FacesConverter("medicoConverter")
public class MedicoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long id = Long.parseLong(value);
            MedicoDAO medicoDao = new MedicoDAO(JPAUtil.getEntityManager());
            
            return medicoDao.buscarPorId(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor não é um ID válido de médico: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (!(value instanceof Medico)) {
            throw new IllegalArgumentException("O objeto não é uma instância válida de Medico: " + value);
        }

        Medico medico = (Medico) value;
        return String.valueOf(medico.getId());
    }

}
