package Managed;

import Entidades.Factura;
import Entidades.Prenda;
import Entidades.Syslog;
import Sesiones.FacturaFacadeLocal;
import Sesiones.SyslogFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author pbals
 */
@Named
@ViewScoped
public class ReportesBean implements Serializable {

    private List<FacturasReportes> reporteFacturas;
    private List<VentasReportes> reporteVentas;
    private List<PrendasReportes> reporteVendidos;
    private List<PrendasReportes> reporteMasVendidos;
    private List<PrendasReportes> reporteMenosVendidos;
    private List<SyslogReportes> reporteSyslogs;

    @Inject
    private FacturaFacadeLocal facturaService;
    private List<Factura> facturas;

    @Inject
    private SyslogFacadeLocal syslogService;
    private List<Syslog> logs;

    public ReportesBean() {
    }

    @PostConstruct
    public void inicializar() {
        facturas = this.facturaService.findAll();
        logs = this.syslogService.findAll();
        generarReporteFacturas();
        generarReporteVentas();
        generarReporteVendidos();
        generarReporteSyslog();
    }

    private void generarReporteFacturas() {
        reporteFacturas = new ArrayList<>();
        for (Factura f : this.facturas) {
            int id_factura = f.getIdFactura();
            String nombre = f.getIdCliente().getIdPersona().getNombre() + " " + f.getIdCliente().getIdPersona().getApellido();
            String cedula = f.getIdCliente().getIdPersona().getCedula();
            Date fechaEmision = f.getFechaEmision();
            int numeroPrendas = f.getIdDetalle().getPrendaList().size();
            double valorTotal = f.getPrecioTotal().doubleValue();
            FacturasReportes fr = new FacturasReportes(id_factura, nombre, cedula, fechaEmision, numeroPrendas, valorTotal);
            reporteFacturas.add(fr);
        }
    }

    private void generarReporteVentas() {
        reporteVentas = new ArrayList<>();
        for (Factura f : this.facturas) {
            int buscar = f.getIdCliente().getIdCliente();
            int posicion = 0;
            if (encontrarCliente(buscar)) {
                posicion = indiceCambiar(buscar);
                VentasReportes aux1 = this.reporteVentas.get(posicion);
                this.reporteVentas.remove(aux1);
                aux1.setNumeroPrendas(aux1.getNumeroPrendas() + f.getIdDetalle().getCantidadPrenda().intValue());
                aux1.setGastado(aux1.getGastado() + f.getPrecioTotal().doubleValue());
                this.reporteVentas.add(aux1);
            } else {
                int idCliente = f.getIdCliente().getIdCliente();
                String nombre = f.getIdCliente().getIdPersona().getNombre() + " " + f.getIdCliente().getIdPersona().getApellido();
                String cedula = f.getIdCliente().getIdPersona().getCedula();
                int numeroPrendas = f.getIdDetalle().getCantidadPrenda().intValue();
                double valorTotal = f.getPrecioTotal().doubleValue();
                VentasReportes aux = new VentasReportes(idCliente, cedula, nombre, numeroPrendas, valorTotal);
                this.reporteVentas.add(aux);
            }
        }
    }

    private void generarReporteVendidos() {
        reporteMasVendidos = new ArrayList<>();
        reporteMenosVendidos = new ArrayList<>();
        reporteVendidos = new ArrayList<>();
        for (Factura f : this.facturas) {
            List<Prenda> prendas = f.getIdDetalle().getPrendaList();
            for (Prenda p : prendas) {
                int buscar = p.getIdPrenda();
                int posicion;
                if (encontrarPrenda(buscar)) {
                    posicion = encontrarPosicion(buscar);
                    PrendasReportes aux = this.reporteVendidos.get(posicion);
                    this.reporteVendidos.remove(aux);
                    aux.setNumeroVendidos(aux.getNumeroVendidos() + 1);
                    this.reporteVendidos.add(aux);
                } else {
                    int idPrenda = p.getIdPrenda();
                    String nombrePrenda = p.getNombre();
                    String categoria = p.getCategoria();
                    double precioPrenda = p.getPrecio().doubleValue();
                    int numeroVendidos = 1;
                    PrendasReportes aux = new PrendasReportes(idPrenda, nombrePrenda, categoria, precioPrenda, numeroVendidos);
                    this.reporteVendidos.add(aux);
                }
            }
        }
        generarReporteMenosVendidos();
        generarReporteMasVendidos();
    }

    private void generarReporteMenosVendidos() {
        for (PrendasReportes pr : this.reporteVendidos) {
            if (pr.getNumeroVendidos() < 4) {
                this.reporteMenosVendidos.add(pr);
            }
        }
    }

    private void generarReporteMasVendidos() {
        for (PrendasReportes pr : this.reporteVendidos) {
            if (pr.getNumeroVendidos() > 3) {
                this.reporteMasVendidos.add(pr);
            }
        }
    }

    private void generarReporteSyslog() {
        this.reporteSyslogs = new ArrayList<>();
        for (Syslog log : this.logs) {
            int buscar = log.getIdPersona().getIdPersona();
            int posicion;
            if (encontrarPersona(buscar)) {
                posicion = encontrarPosicionPersona(buscar);
                SyslogReportes aux = this.reporteSyslogs.get(posicion);
                this.reporteSyslogs.remove(aux);
                aux.setVecesIngresadas(aux.getVecesIngresadas() + 1);
                aux.setHorasLogeado(aux.getHorasLogeado() + calcularHorasLogeado(log));
                this.reporteSyslogs.add(aux);
            } else {
                int idPersona = log.getIdPersona().getIdPersona();
                String nombre = log.getIdPersona().getNombre() + " " + log.getIdPersona().getApellido();
                String cedula = log.getIdPersona().getCedula();
                int vecesLogeado = 1;
                double horasLogeado = calcularHorasLogeado(log);
                SyslogReportes aux = new SyslogReportes(idPersona, nombre, cedula, vecesLogeado, horasLogeado);
                this.reporteSyslogs.add(aux);
            }
        }
    }

    private double calcularHorasLogeado(Syslog log) {
        double horasT;
        double minutos;
        double horas;
        double aux;
        if (log.getIngreso().getDay() == log.getSalida().getDay()) {
            horas = 0;
        } else {
            horas = log.getSalida().getHours() - log.getIngreso().getHours();
        }
        if (log.getSalida().getMinutes() > log.getIngreso().getMinutes()) {
            aux = log.getSalida().getMinutes() - log.getIngreso().getMinutes();
        } else {
            aux = 60 - log.getIngreso().getMinutes() - log.getSalida().getMinutes();
        }
        minutos = aux / 60;
        horasT = horas = minutos;
        return horasT;
    }

    private boolean encontrarPersona(int id) {
        for (SyslogReportes sr : this.reporteSyslogs) {
            if (sr.getIdPersona() == id) {
                return true;
            }
        }
        return false;
    }

    private int encontrarPosicionPersona(int id) {
        int posicion = 0;
        for (SyslogReportes sr : this.reporteSyslogs) {
            if (sr.getIdPersona() == id) {
                return posicion;
            }
            posicion++;
        }
        return posicion;
    }

    private boolean encontrarPrenda(int id) {
        for (PrendasReportes pr : this.reporteVendidos) {
            if (pr.getIdPrenda() == id) {
                return true;
            }
        }
        return false;
    }

    private int encontrarPosicion(int id) {
        int posicion = 0;
        for (PrendasReportes pr : this.reporteVendidos) {
            if (pr.getIdPrenda() == id) {
                return posicion;
            }
            posicion++;
        }
        return posicion;
    }

    private boolean encontrarCliente(int id) {
        for (VentasReportes vr : this.reporteVentas) {
            if (vr.getIdCliente() == id) {
                return true;
            }
        }
        return false;
    }

    private int indiceCambiar(int id) {
        int posicion = 0;
        for (VentasReportes vr : this.reporteVentas) {
            if (vr.getIdCliente() == id) {
                return posicion;
            }
            posicion++;
        }
        return posicion;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<FacturasReportes> getReporteFacturas() {
        return reporteFacturas;
    }

    public void setReporteFacturas(List<FacturasReportes> reporteFacturas) {
        this.reporteFacturas = reporteFacturas;
    }

    public List<VentasReportes> getReporteVentas() {
        return reporteVentas;
    }

    public void setReporteVentas(List<VentasReportes> reporteVentas) {
        this.reporteVentas = reporteVentas;
    }

    public List<PrendasReportes> getReporteMasVendidos() {
        return reporteMasVendidos;
    }

    public void setReporteMasVendidos(List<PrendasReportes> reporteMasVendidos) {
        this.reporteMasVendidos = reporteMasVendidos;
    }

    public List<PrendasReportes> getReporteMenosVendidos() {
        return reporteMenosVendidos;
    }

    public void setReporteMenosVendidos(List<PrendasReportes> reporteMenosVendidos) {
        this.reporteMenosVendidos = reporteMenosVendidos;
    }

    public List<PrendasReportes> getReporteVendidos() {
        return reporteVendidos;
    }

    public void setReporteVendidos(List<PrendasReportes> reporteVendidos) {
        this.reporteVendidos = reporteVendidos;
    }

    public List<Syslog> getLogs() {
        return logs;
    }

    public void setLogs(List<Syslog> logs) {
        this.logs = logs;
    }

    public List<SyslogReportes> getReporteSyslogs() {
        return reporteSyslogs;
    }

    public void setReporteSyslogs(List<SyslogReportes> reporteSyslogs) {
        this.reporteSyslogs = reporteSyslogs;
    }

}
