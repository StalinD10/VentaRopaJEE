/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "impuesto")
@NamedQueries({
    @NamedQuery(name = "Impuesto.findAll", query = "SELECT i FROM Impuesto i"),
    @NamedQuery(name = "Impuesto.findByIdImpuesto", query = "SELECT i FROM Impuesto i WHERE i.idImpuesto = :idImpuesto"),
    @NamedQuery(name = "Impuesto.findByNombreImpuesto", query = "SELECT i FROM Impuesto i WHERE i.nombreImpuesto = :nombreImpuesto"),
    @NamedQuery(name = "Impuesto.findByValorImpuesto", query = "SELECT i FROM Impuesto i WHERE i.valorImpuesto = :valorImpuesto")})
public class Impuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_impuesto")
    private Integer idImpuesto;
    @Size(max = 2147483647)
    @Column(name = "nombre_impuesto")
    private String nombreImpuesto;
    @Column(name = "valor_impuesto")
    private BigInteger valorImpuesto;
    @JoinTable(name = "prenda_impuesto", joinColumns = {
        @JoinColumn(name = "id_impuesto", referencedColumnName = "id_impuesto")}, inverseJoinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")})
    @ManyToMany
    private List<Prenda> prendaList;

    public Impuesto() {
    }

    public Impuesto(Integer idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(Integer idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public String getNombreImpuesto() {
        return nombreImpuesto;
    }

    public void setNombreImpuesto(String nombreImpuesto) {
        this.nombreImpuesto = nombreImpuesto;
    }

    public BigInteger getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(BigInteger valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
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
        hash += (idImpuesto != null ? idImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Impuesto)) {
            return false;
        }
        Impuesto other = (Impuesto) object;
        if ((this.idImpuesto == null && other.idImpuesto != null) || (this.idImpuesto != null && !this.idImpuesto.equals(other.idImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nombreImpuesto;
    }
    
}
