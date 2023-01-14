/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Descuentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pbals
 */
@Local
public interface DescuentosFacadeLocal {

    void create(Descuentos descuentos);

    void edit(Descuentos descuentos);

    void remove(Descuentos descuentos);

    Descuentos find(Object id);

    List<Descuentos> findAll();

    List<Descuentos> findRange(int[] range);

    int count();
    
}
