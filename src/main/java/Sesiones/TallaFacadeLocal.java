/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Talla;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pbals
 */
@Local
public interface TallaFacadeLocal {

    void create(Talla talla);

    void edit(Talla talla);

    void remove(Talla talla);

    Talla find(Object id);

    List<Talla> findAll();

    List<Talla> findRange(int[] range);

    int count();
    
}
