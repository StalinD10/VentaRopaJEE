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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author pbals
 */
@Entity
@Table(name = "detalle_factura")
@NamedQueries({
    @NamedQuery(name = "DetalleFactura.findAll", query = "SELECT d FROM DetalleFactura d"),
    @NamedQuery(name = "DetalleFactura.findByIdDetalle", query = "SELECT d FROM DetalleFactura d WHERE d.idDetalle = :idDetalle"),
    @NamedQuery(name = "DetalleFactura.findIDDetalle", query = "SELECT d FROM DetalleFactura d WHERE d.idDetalle IN (SELECT MAX(d1.idDetalle) FROM DetalleFactura d1)"),
    @NamedQuery(name = "DetalleFactura.findByCantidadPrenda", query = "SELECT d FROM DetalleFactura d WHERE d.cantidadPrenda = :cantidadPrenda")})
public class DetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @Column(name = "cantidad_prenda")
    private BigInteger cantidadPrenda;
    @JoinTable(name = "detalle_factura_prenda", joinColumns = {
        @JoinColumn(name = "id_detalle", referencedColumnName = "id_detalle")}, inverseJoinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")})
    @ManyToMany
    private List<Prenda> prendaList;
    @OneToMany(mappedBy = "idDetalle")
    private List<Factura> facturaList;

    public DetalleFactura() {
    }

    public DetalleFactura(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public BigInteger getCantidadPrenda() {
        return cantidadPrenda;
    }

    public void setCantidadPrenda(BigInteger cantidadPrenda) {
        this.cantidadPrenda = cantidadPrenda;
    }

    public List<Prenda> getPrendaList() {
        return prendaList;
    }

    public void setPrendaList(List<Prenda> prendaList) {
        this.prendaList = prendaList;
    }

    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFactura)) {
            return false;
        }
        DetalleFactura other = (DetalleFactura) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DetalleFactura[ idDetalle=" + idDetalle + " ]";
    }
    
}
