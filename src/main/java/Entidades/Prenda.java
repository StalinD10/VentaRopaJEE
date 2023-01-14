/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "prenda")
@NamedQueries({
    @NamedQuery(name = "Prenda.findAll", query = "SELECT p FROM Prenda p"),
    @NamedQuery(name = "Prenda.findByIdPrenda", query = "SELECT p FROM Prenda p WHERE p.idPrenda = :idPrenda"),
    @NamedQuery(name = "Prenda.findByNombre", query = "SELECT p FROM Prenda p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Prenda.findByPrecio", query = "SELECT p FROM Prenda p WHERE p.precio = :precio"),
    @NamedQuery(name = "Prenda.findByDescripcion", query = "SELECT p FROM Prenda p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Prenda.findByRutaImagen", query = "SELECT p FROM Prenda p WHERE p.rutaImagen = :rutaImagen"),
    @NamedQuery(name = "Prenda.findByCantidad", query = "SELECT p FROM Prenda p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "Prenda.findByCategoria", query = "SELECT p FROM Prenda p WHERE p.categoria = :categoria")})
public class Prenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prenda")
    private Integer idPrenda;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private BigDecimal precio;
    @Size(max = 100)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 60)
    @Column(name = "ruta_imagen")
    private String rutaImagen;
    @Column(name = "cantidad")
    private BigInteger cantidad;
    @JoinTable(name = "prenda_descuentos", joinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")}, inverseJoinColumns = {
        @JoinColumn(name = "id_descuento", referencedColumnName = "id_descuento")})
    @ManyToMany
    private List<Descuentos> descuentosList;
    @JoinTable(name = "prenda_color", joinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")}, inverseJoinColumns = {
        @JoinColumn(name = "id_color", referencedColumnName = "id_color")})
    @ManyToMany
    private List<Color> colorList;
    @JoinTable(name = "prenda_talla", joinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")}, inverseJoinColumns = {
        @JoinColumn(name = "id_talla", referencedColumnName = "id_talla")})
    @ManyToMany
    private List<Talla> tallaList;
    @JoinTable(name = "prenda_impuesto", joinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")}, inverseJoinColumns = {
        @JoinColumn(name = "id_impuesto", referencedColumnName = "id_impuesto")})
    @ManyToMany
    private List<Impuesto> impuestoList;
    @JoinTable(name = "prenda_genero", joinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")}, inverseJoinColumns = {
        @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")})
    @ManyToMany
    private List<GeneroPersona> generoPersonaList;
    @JoinTable(name = "prenda_material", joinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")}, inverseJoinColumns = {
        @JoinColumn(name = "id_material", referencedColumnName = "id_material")})
    @ManyToMany
    private List<Material> materialList;
    @JoinTable(name = "detalle_factura_prenda", joinColumns = {
        @JoinColumn(name = "id_prenda", referencedColumnName = "id_prenda")}, inverseJoinColumns = {
        @JoinColumn(name = "id_detalle", referencedColumnName = "id_detalle")})
    @ManyToMany
    private List<DetalleFactura> detalleFacturaList;

    public Prenda() {
    }

    public Prenda(Integer idPrenda) {
        this.idPrenda = idPrenda;
    }

    public Integer getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(Integer idPrenda) {
        this.idPrenda = idPrenda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Descuentos> getDescuentosList() {
        return descuentosList;
    }

    public void setDescuentosList(List<Descuentos> descuentosList) {
        this.descuentosList = descuentosList;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public List<Talla> getTallaList() {
        return tallaList;
    }

    public void setTallaList(List<Talla> tallaList) {
        this.tallaList = tallaList;
    }

    public List<Impuesto> getImpuestoList() {
        return impuestoList;
    }

    public void setImpuestoList(List<Impuesto> impuestoList) {
        this.impuestoList = impuestoList;
    }

    public List<GeneroPersona> getGeneroPersonaList() {
        return generoPersonaList;
    }

    public void setGeneroPersonaList(List<GeneroPersona> generoPersonaList) {
        this.generoPersonaList = generoPersonaList;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    public List<DetalleFactura> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFactura> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrenda != null ? idPrenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prenda)) {
            return false;
        }
        Prenda other = (Prenda) object;
        if ((this.idPrenda == null && other.idPrenda != null) || (this.idPrenda != null && !this.idPrenda.equals(other.idPrenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nombre;
    }

}
