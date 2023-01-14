/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Sesiones;

import Entidades.Syslog;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pbals
 */
@Local
public interface SyslogFacadeLocal {

    void create(Syslog syslog);

    void edit(Syslog syslog);

    void remove(Syslog syslog);

    Syslog find(Object id);

    List<Syslog> findAll();

    List<Syslog> findRange(int[] range);

    int count();
    
}
