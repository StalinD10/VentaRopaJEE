/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entidades.GeneroPersona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pbals
 */
@Local
public interface GeneroPersonaFacadeLocal {

    void create(GeneroPersona generoPersona);

    void edit(GeneroPersona generoPersona);

    void remove(GeneroPersona generoPersona);

    GeneroPersona find(Object id);

    List<GeneroPersona> findAll();

    List<GeneroPersona> findRange(int[] range);

    int count();
    
}
