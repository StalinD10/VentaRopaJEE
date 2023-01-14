/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente> implements ClienteFacadeLocal {

    @PersistenceContext(unitName = "VentaRopaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
    @Override
    public Persona encontrarId() {
        Persona persona;
        Query sql = em.createNamedQuery("Persona.findIDPersona");
        persona = (Persona) sql.getSingleResult();
        return persona;
    }

    @Override
    public Cliente validarCliente(List<Cliente> clientes, String usuario, String contraseña) {
        Cliente cli = null;
        for (Cliente c : clientes) {
            if (c.getUsuario().equals(usuario) && c.getContrasena().equals(contraseña)) {
                cli = c;
                continue;
            }
        }
        return cli;
    }
    
}
