/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author pbals
 */
@Entity
@Table(name = "descuentos")
@NamedQueries({
    @NamedQuery(name = "Descuentos.findAll", query = "SELECT d FROM Descuentos d"),
    @NamedQuery(name = "Descuentos.findByIdDescuento", query = "SELECT d FROM Descuentos d WHERE d.idDescuento = :idDescuento"),
    @NamedQuery(name = "Descuentos.findByFechaInicio", query = "SELECT d FROM Descuentos d WHERE d.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Descuentos.findByFechaFinal", query = "SELECT d FROM Descuentos d WHERE d.fechaFinal = :fechaFinal"),
    @NamedQuery(name = "Descuentos.findByPorcentajeDescuento", query = "SELECT d FROM Descuentos d WHERE d.porcentajeDescuento = :porcentajeDescuento"),
    @NamedQuery(name = "Descuentos.findByNombreDescuento", query = "SELECT d FROM Descuentos d WHERE d.nombreDescuento = :nombreDescuento")})
public class Descuentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_descuento")
    private Integer idDescuento;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @Column(name = "porcentaje_descuento")
    private BigInteger porcentajeDescuento;
    @Size(max = 50)
    @Column(name = "nombre_descuento")
    private String nombreDescuento;
    @JoinTable(name = "prenda_descuentos", joinColumns = {
        @JoinColumn(name = "id_descuento", referencedColumnName = "id_descuento")}, inverseJoinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")})
    @ManyToMany
    private List<Prenda> prendaList;

    public Descuentos() {
    }

    public Descuentos(Integer idDescuento) {
        this.idDescuento = idDescuento;
    }

    public Integer getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(Integer idDescuento) {
        this.idDescuento = idDescuento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigInteger getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigInteger porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getNombreDescuento() {
        return nombreDescuento;
    }

    public void setNombreDescuento(String nombreDescuento) {
        this.nombreDescuento = nombreDescuento;
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
        hash += (idDescuento != null ? idDescuento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descuentos)) {
            return false;
        }
        Descuentos other = (Descuentos) object;
        if ((this.idDescuento == null && other.idDescuento != null) || (this.idDescuento != null && !this.idDescuento.equals(other.idDescuento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nombreDescuento;
    }
    
}
