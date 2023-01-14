package Managed;

import java.util.Date;

/**
 *
 * @author pbals
 */
public class FacturasReportes {

    private int idFactura;
    private String nombre;
    private String cedula;
    private Date fechaEmision;
    private int numeroPrendas;
    private double valorTotal;

    public FacturasReportes(int idFactura, String nombre, String cedula, Date fechaEmision, int numeroPrendas, double valorTotal) {
        this.idFactura = idFactura;
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaEmision = fechaEmision;
        this.numeroPrendas = numeroPrendas;
        this.valorTotal = valorTotal;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getNumeroPrendas() {
        return numeroPrendas;
    }

    public void setNumeroPrendas(int numeroPrendas) {
        this.numeroPrendas = numeroPrendas;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

}
