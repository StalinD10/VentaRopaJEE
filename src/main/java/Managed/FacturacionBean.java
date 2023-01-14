package Managed;

import Entidades.Factura;
import Sesiones.FacturaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class FacturacionBean implements Serializable {

    @Inject
    private FacturaFacadeLocal facturaService;
    private List<Factura> facturas;
    private List<Factura> facturasSeleccionadas;
    private Factura facturaSeleccionada;

    public FacturacionBean() {
    }

    @PostConstruct
    public void inicializar() {
        this.facturas = this.facturaService.findAll();
        this.facturasSeleccionadas = new ArrayList<>();
        this.facturaSeleccionada = new Factura();
    }

    //************************************************
    //METODOS TABLA FACTURACION
    //************************************************
    public boolean tieneFacturasSeleccionadas() {
        return this.facturasSeleccionadas != null && !this.facturasSeleccionadas.isEmpty();
    }

    public void eliminarFacturas() {
        try {
            for (Factura f : this.facturasSeleccionadas) {
                this.facturaService.remove(f);
                this.facturas.remove(f);
            }
            this.facturasSeleccionadas = new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Facturas eliminadas"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_factura:messages_factura", "form_factura:dt-facturas");
        PrimeFaces.current().executeScript("PF('dtFacturas').clearFilters()");
    }

    public void eliminarFactura() {
        try {
            this.facturaService.remove(this.facturaSeleccionada);
            this.facturas.remove(this.facturaSeleccionada);
            reiniciarFacturaSeleccionada();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Factura eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_factura:messages_factura", "form_factura:dt-facturas");
        PrimeFaces.current().executeScript("PF('dtFacturas').clearFilters()");
    }

    public String getDeleteButtonMessageFactura() {
        if (tieneFacturasSeleccionadas()) {
            int size = this.facturasSeleccionadas.size();
            return size > 1 ? size + " Facturas Seleccionadas" : "1 Factura Seleccionada";
        }
        return "Eliminar";
    }

    public void reiniciarFacturaSeleccionada() {
        this.facturaSeleccionada = new Factura();
    }
    //************************************************
    //FIN METODOS TABLA FACTURACION
    //************************************************

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<Factura> getFacturasSeleccionadas() {
        return facturasSeleccionadas;
    }

    public void setFacturasSeleccionadas(List<Factura> facturasSeleccionadas) {
        this.facturasSeleccionadas = facturasSeleccionadas;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

}
