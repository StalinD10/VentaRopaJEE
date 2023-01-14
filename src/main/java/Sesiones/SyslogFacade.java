/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sesiones;

import Entidades.Syslog;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pbals
 */
@Stateless
public class SyslogFacade extends AbstractFacade<Syslog> implements SyslogFacadeLocal {

    @PersistenceContext(unitName = "VentaRopaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SyslogFacade() {
        super(Syslog.class);
    }
    
}
