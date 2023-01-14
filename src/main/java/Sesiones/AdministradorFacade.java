/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Administrador;
import Entidades.Persona;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author pbals
 */
@Stateless
public class AdministradorFacade extends AbstractFacade<Administrador> implements AdministradorFacadeLocal {

    @PersistenceContext(unitName = "VentaRopaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministradorFacade() {
        super(Administrador.class);
    }
    
    @Override
    public Persona obtenerID() {
        Persona persona;
        Query sql = em.createNamedQuery("Persona.findIDPersona");
        persona = (Persona) sql.getSingleResult();
        return persona;
    }

    @Override
    public Administrador validarAdmin(List<Administrador> admins, String usuario, String contraseña) {
        Administrador admin = null;
        for (Administrador a : admins) {
            if (a.getUsuarioAdmin().equals(usuario) && a.getContrasenaAdmin().equals(contraseña)) {
                admin = a;
            }
        }
        return admin;
    }
    
}
