/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Impuesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pbals
 */
@Local
public interface ImpuestoFacadeLocal {

    void create(Impuesto impuesto);

    void edit(Impuesto impuesto);

    void remove(Impuesto impuesto);

    Impuesto find(Object id);

    List<Impuesto> findAll();

    List<Impuesto> findRange(int[] range);

    int count();
    
}
