/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.Prenda;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pbals
 */
@Local
public interface PrendaFacadeLocal {

    void create(Prenda prenda);

    void edit(Prenda prenda);

    void remove(Prenda prenda);

    Prenda find(Object id);

    List<Prenda> findAll();

    List<Prenda> findRange(int[] range);

    int count();
    
}
