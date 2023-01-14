/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author pbals
 */
@Entity
@Table(name = "genero_persona")
@NamedQueries({
    @NamedQuery(name = "GeneroPersona.findAll", query = "SELECT g FROM GeneroPersona g"),
    @NamedQuery(name = "GeneroPersona.findByIdGenero", query = "SELECT g FROM GeneroPersona g WHERE g.idGenero = :idGenero"),
    @NamedQuery(name = "GeneroPersona.findByNombreGenero", query = "SELECT g FROM GeneroPersona g WHERE g.nombreGenero = :nombreGenero")})
public class GeneroPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_genero")
    private Integer idGenero;
    @Size(max = 2147483647)
    @Column(name = "nombre_genero")
    private String nombreGenero;
    @JoinTable(name = "prenda_genero", joinColumns = {
        @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")}, inverseJoinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")})
    @ManyToMany
    private List<Prenda> prendaList;

    public GeneroPersona() {
    }

    public GeneroPersona(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public List<Prenda> getPrendaList() {
        return prendaList;
    }

    public void setPrendaList(List<Prenda> prendaList) {
        this.prendaList = prendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenero != null ? idGenero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneroPersona)) {
            return false;
        }
        GeneroPersona other = (GeneroPersona) object;
        if ((this.idGenero == null && other.idGenero != null) || (this.idGenero != null && !this.idGenero.equals(other.idGenero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nombreGenero;
    }
    
}
