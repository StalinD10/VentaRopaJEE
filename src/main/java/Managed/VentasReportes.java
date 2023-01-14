package Managed;

/**
 *
 * @author pbals
 */
public class VentasReportes {

    private int idCliente;
    private String cedula;
    private String nombre;
    private int numeroPrendas;
    private double gastado;

    public VentasReportes(int idCliente, String cedula, String nombre, int numeroPrendas, double gastado) {
        this.idCliente = idCliente;
        this.cedula = cedula;
        this.nombre = nombre;
        this.numeroPrendas = numeroPrendas;
        this.gastado = gastado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroPrendas() {
        return numeroPrendas;
    }

    public void setNumeroPrendas(int numeroPrendas) {
        this.numeroPrendas = numeroPrendas;
    }

    public double getGastado() {
        return gastado;
    }

    public void setGastado(double gastado) {
        this.gastado = gastado;
    }

}
