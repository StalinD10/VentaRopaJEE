/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Tarjeta;
import java.util.ArrayList;
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
public class TarjetaFacade extends AbstractFacade<Tarjeta> implements TarjetaFacadeLocal {

    @PersistenceContext(unitName = "VentaRopaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarjetaFacade() {
        super(Tarjeta.class);
    }

    @Override
    public Tarjeta getTarjeta() {
        Tarjeta tarjeta;
        Query sql = em.createNamedQuery("Tarjeta.findIDTarjeta");
        tarjeta = (Tarjeta) sql.getSingleResult();
        return tarjeta;
    }

    @Override
    public List<Tarjeta> getTarjetasClientes(int idCliente) {
        List<Tarjeta> tarjetas = new ArrayList<>();
        try {
            Query sql = em.createNamedQuery("Tarjeta.findAll");
            tarjetas = sql.getResultList();
            List<Tarjeta> aux = new ArrayList<>();
            if (!tarjetas.isEmpty()) {
                for (Tarjeta t : tarjetas) {
                    if (t.getIdCliente().getIdCliente() == idCliente) {
                        aux.add(t);
                    }
                }
            }
            tarjetas = aux;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tarjetas;
    }

}
