/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Prenda;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pbals
 */
@Stateless
public class PrendaFacade extends AbstractFacade<Prenda> implements PrendaFacadeLocal {

    @PersistenceContext(unitName = "VentaRopaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrendaFacade() {
        super(Prenda.class);
    }
    
}
