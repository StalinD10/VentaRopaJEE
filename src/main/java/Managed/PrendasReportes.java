package Managed;

/**
 *
 * @author pbals
 */
public class PrendasReportes {

    private int idPrenda;
    private String nombrePrenda;
    private String categoria;
    private double precioPrenda;
    private int numeroVendidos;

    public PrendasReportes(int idPrenda, String nombrePrenda, String categoria, double precioPrenda, int numeroVendidos) {
        this.idPrenda = idPrenda;
        this.nombrePrenda = nombrePrenda;
        this.categoria = categoria;
        this.precioPrenda = precioPrenda;
        this.numeroVendidos = numeroVendidos;
    }

    public int getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(int idPrenda) {
        this.idPrenda = idPrenda;
    }

    public String getNombrePrenda() {
        return nombrePrenda;
    }

    public void setNombrePrenda(String nombrePrenda) {
        this.nombrePrenda = nombrePrenda;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioPrenda() {
        return precioPrenda;
    }

    public void setPrecioPrenda(double precioPrenda) {
        this.precioPrenda = precioPrenda;
    }

    public int getNumeroVendidos() {
        return numeroVendidos;
    }

    public void setNumeroVendidos(int numeroVendidos) {
        this.numeroVendidos = numeroVendidos;
    }

}
