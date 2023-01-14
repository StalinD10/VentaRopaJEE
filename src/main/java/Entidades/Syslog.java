/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pbals
 */
@Entity
@Table(name = "syslog")
@NamedQueries({
    @NamedQuery(name = "Syslog.findAll", query = "SELECT s FROM Syslog s"),
    @NamedQuery(name = "Syslog.findByIdSyslog", query = "SELECT s FROM Syslog s WHERE s.idSyslog = :idSyslog"),
    @NamedQuery(name = "Syslog.findByIngreso", query = "SELECT s FROM Syslog s WHERE s.ingreso = :ingreso"),
    @NamedQuery(name = "Syslog.findBySalida", query = "SELECT s FROM Syslog s WHERE s.salida = :salida")})
public class Syslog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_syslog")
    private Integer idSyslog;
    @Column(name = "ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ingreso;
    @Column(name = "salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne
    private Persona idPersona;

    public Syslog() {
    }

    public Syslog(Integer idSyslog) {
        this.idSyslog = idSyslog;
    }

    public Integer getIdSyslog() {
        return idSyslog;
    }

    public void setIdSyslog(Integer idSyslog) {
        this.idSyslog = idSyslog;
    }

    public Date getIngreso() {
        return ingreso;
    }

    public void setIngreso(Date ingreso) {
        this.ingreso = ingreso;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSyslog != null ? idSyslog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Syslog)) {
            return false;
        }
        Syslog other = (Syslog) object;
        if ((this.idSyslog == null && other.idSyslog != null) || (this.idSyslog != null && !this.idSyslog.equals(other.idSyslog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Syslog[ idSyslog=" + idSyslog + " ]";
    }
    
}
