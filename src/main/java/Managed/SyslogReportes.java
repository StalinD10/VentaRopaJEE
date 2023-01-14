package Managed;

/**
 *
 * @author pbals
 */
public class SyslogReportes {

    private int idPersona;
    private String nombre;
    private String cedula;
    private int vecesIngresadas;
    private double horasLogeado;

    public SyslogReportes(int idPersona, String nombre, String cedula, int vecesIngresadas, double horasLogeado) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.cedula = cedula;
        this.vecesIngresadas = vecesIngresadas;
        this.horasLogeado = horasLogeado;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
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

    public int getVecesIngresadas() {
        return vecesIngresadas;
    }

    public void setVecesIngresadas(int vecesIngresadas) {
        this.vecesIngresadas = vecesIngresadas;
    }

    public double getHorasLogeado() {
        return horasLogeado;
    }

    public void setHorasLogeado(double horasLogeado) {
        this.horasLogeado = horasLogeado;
    }

}
