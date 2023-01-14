package Managed;

import Entidades.Cliente;
import Entidades.Descuentos;
import Entidades.DetalleFactura;
import Entidades.Factura;
import Entidades.Impuesto;
import Entidades.Prenda;
import Entidades.Tarjeta;
import Sesiones.ClienteFacadeLocal;
import Sesiones.DetalleFacturaFacadeLocal;
import Sesiones.FacturaFacadeLocal;
import Sesiones.PrendaFacadeLocal;
import Sesiones.TarjetaFacadeLocal;
import com.itextpdf.text.Chunk;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class PagoBean implements Serializable {

    private static final String USUARIO = "yspfashionstore@gmail.com";
    private static final String PASS = "yspFashionStore";
    
    @Inject
    private PrendaFacadeLocal prendaService;
    private List<Prenda> carrito;
    private Prenda prendaAgregar;
    private Prenda productoSeleccionado;
    private Prenda productoDuplicado;

    @Inject
    private DetalleFacturaFacadeLocal detalleService;
    private DetalleFactura detalle;

    @Inject
    private FacturaFacadeLocal facturaService;
    private List<Factura> facturas;
    private List<Factura> facturasCliente;
    private List<Factura> facturasSeleccionadas;
    private Factura facturaSeleccionada;
    private Factura factura;

    @Inject
    private ClienteFacadeLocal clienteService;
    private Cliente cliente;

    @Inject
    private TarjetaFacadeLocal tarjetaService;
    private List<Tarjeta> tarjetasSeleccionadas;
    private List<Tarjeta> tarjetas;
    private Tarjeta tarjetaSeleccionada;
    private List<Tarjeta> tarjetasCliente;
    private Tarjeta tarjetaSeleccionadaC;

    public PagoBean() {
    }

    @PostConstruct
    public void inicializar() {
        cliente = (Cliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
        tarjetas = tarjetaService.findAll();
        facturas = facturaService.findAll();
        facturasCliente = new ArrayList<>();
        tarjetasCliente = new ArrayList<>();
        listarTarjetasCliente();
        listarFacturasCliente();
        actualizarBotonEscoger();
        carrito = new ArrayList<>();
        facturasSeleccionadas = new ArrayList<>();
        facturaSeleccionada = new Factura();
        tarjetaSeleccionada = new Tarjeta();
        detalle = new DetalleFactura();
        factura = new Factura();
        tarjetaSeleccionadaC = new Tarjeta();
        productoSeleccionado = new Prenda();
        productoSeleccionado.setRutaImagen("null.png");
        productoDuplicado = new Prenda();
        prendaAgregar = new Prenda();
        this.prendaAgregar.setCantidad(BigInteger.ZERO);
    }

    //************************************************
    //METODOS PARA ACTUALIZACIÓN DE LA INTERFAZ
    //************************************************
    public void listarTarjetasCliente() {
        for (Tarjeta tarjeta : this.tarjetas) {
            if (tarjeta.getIdCliente().getIdCliente().equals(this.cliente.getIdCliente())) {
                this.tarjetasCliente.add(tarjeta);
            }
        }
    }

    public void listarFacturasCliente() {
        for (Factura f : this.facturas) {
            if (f.getIdCliente().getIdCliente().equals(cliente.getIdCliente())) {
                this.facturasCliente.add(f);
            }
        }
    }

    public boolean tienePrendasCarrito() {
        return this.carrito != null && !this.carrito.isEmpty();
    }

    public String numeroPrendas() {
        if (tienePrendasCarrito()) {
            int size = carrito.size();
            return size >= 1 ? size + " Prendas" : "1 Prenda";
        }
        return "Sin elementos";
    }

    public void actualizarBotonCarrito() {
        numeroPrendas();
        PrimeFaces.current().ajax().update("form:btn_carrito");
    }

    public void actualizarTablaTarjetas() {
        PrimeFaces.current().ajax().update("form:dt-tarpago");
    }

    public void actualizarBotonEscoger() {
        PrimeFaces.current().ajax().update("form:pago_confirmar", "form:over_tar");
    }

    public void actualizarTablaCarrito() {
        PrimeFaces.current().ajax().update("form:dt-carritos");
    }
    
    public void actualizarTablaFacturas() {
        PrimeFaces.current().ajax().update("form:dt-facturas");
    }

    public boolean tieneTarjetaCliente() {
        return this.tarjetaSeleccionadaC != null;
    }

    public boolean tieneTarjetasCliente() {
        listarTarjetasCliente();
        return this.tarjetasCliente != null && !this.tarjetasCliente.isEmpty();
    }

    public void tarjetaEnSeleccion(SelectEvent<Tarjeta> event) {
        this.tarjetaSeleccionada = event.getObject();
        FacesMessage msg = new FacesMessage("Tarjeta Seleccionada", event.getObject().getNombreTitular());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    //************************************************
    //FIN METODOS PARA ACTUALIZACIÓN DE LA INTERFAZ
    //************************************************

    //************************************************
    //METODOS PARA GENERAR FACTURA
    //************************************************
    public List<Prenda> actualizarCarrito() {
        List<Prenda> prendaR = new ArrayList<>();
        for (Prenda prenda : this.carrito) {
            if (!prendaR.contains(prenda)) {
                prenda.setCantidad(BigInteger.valueOf(1));
                prendaR.add(prenda);
            } else {
                int index = 0;
                for (Prenda p : prendaR) {
                    if (!p.equals(prenda)) {
                        index++;
                    }
                }
                prendaR.get(index).setCantidad(BigInteger.valueOf(prendaR.get(index).getCantidad().longValue() + 1));
            }
        }
        return prendaR;
    }

    public void generarDetalle(List<Prenda> prendaR) {
        try {
            this.detalle.setCantidadPrenda(BigInteger.valueOf(carrito.size()));
            this.detalle.setPrendaList(prendaR);
            this.detalleService.create(detalle);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error" + e.getMessage()));
        }
    }

    public void generarFactura(List<Prenda> p) {
        try {
            generarDetalle(p);
            this.factura.setFechaEmision(new Date());
            this.factura.setIdCliente(this.cliente);
            this.factura.setIdDetalle(this.detalleService.getID());
            double total;
            double antesP = 0;
            double vDes = 0;
            double vImp = 0;
            for (Prenda pr : p) {
                antesP = antesP + (pr.getPrecio().doubleValue() * pr.getCantidad().doubleValue());
                if (!pr.getImpuestoList().isEmpty() && pr.getImpuestoList() != null) {
                    for (Impuesto i : pr.getImpuestoList()) {
                        vImp = vImp + (pr.getPrecio().doubleValue() * (i.getValorImpuesto().doubleValue() / 100));
                    }
                }
                if (!pr.getDescuentosList().isEmpty() && pr.getDescuentosList() != null) {
                    Date fechaActual = new Date();
                    for (Descuentos d : pr.getDescuentosList()) {
                        if (fechaActual.before(d.getFechaFinal()) && fechaActual.after(d.getFechaInicio())) {
                            vDes = vDes + (pr.getPrecio().doubleValue() * (d.getPorcentajeDescuento().doubleValue() / 100));
                        }
                    }
                }
            }
            total = antesP + vImp - vDes;
            this.factura.setPrecioTotal(BigDecimal.valueOf(total));
            this.facturaService.create(factura);
            this.facturasCliente.add(factura);
            actualizarStock(p);
            generarFactura(p, this.factura, vImp, vDes, antesP);
            FacesMessage msg = new FacesMessage("Compra realizada con exito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
    }

    public void actualizarStock(List<Prenda> p) {
        List<Prenda> aux = this.prendaService.findAll();
        for (Prenda prenda : p) {
            for (Prenda auxp : aux) {
                if (prenda.getIdPrenda().equals(auxp.getIdPrenda())) {
                    int nuevaC = auxp.getCantidad().intValue() - prenda.getCantidad().intValue();
                    auxp.setCantidad(BigInteger.valueOf(nuevaC));
                    this.prendaService.edit(auxp);
                }
            }
        }
    }

    public boolean suficienteStock(List<Prenda> p) {
        List<Prenda> aux = this.prendaService.findAll();
        for (Prenda prenda : p) {
            for (Prenda auxp : aux) {
                int operacion = auxp.getCantidad().intValue() - prenda.getCantidad().intValue();
                if (operacion <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void realizarPago() {
        if (this.tarjetaSeleccionada != null && this.tarjetaSeleccionada.getNombreTitular() != null) {
            List<Prenda> prendaA = actualizarCarrito();
            if (suficienteStock(prendaA)) {
                generarFactura(prendaA);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay suficiente stock para las prendas seleccionadas"));
            }
            this.carrito = new ArrayList<>();
            PrimeFaces.current().ajax().update("form:dt-carritos", "form:btn_carrito", "form:dt-facturas");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Escoger o ingresar tarjeta para realizar el pago"));
        }
        PrimeFaces.current().ajax().update("form:messages_pago");
    }

    public void generarFactura(List<Prenda> pr, Factura f, double vImp, double vDes, double sImp) {
        Document facturaG = new Document();
        try {
            Date fecha = new Date();
            String numero = fecha.getHours() + "_" + fecha.getMinutes() + "_" + fecha.getSeconds();
            String r = "C:\\WorkSpace\\Java\\VentaRopaJEE\\src\\main\\webapp\\resources\\documents\\factura" + numero + ".pdf";
            PdfWriter.getInstance(facturaG, new FileOutputStream(r));
            facturaG.open();
            //Inicio factura
            Image header = Image.getInstance("C:\\WorkSpace\\Java\\VentaRopaJEE\\src\\main\\webapp\\resources\\images\\logofactura.png");
            header.setAlignment(Chunk.ALIGN_CENTER);
            facturaG.add(header);
            Paragraph parrafo2 = new Paragraph("YPS FASION STORE");
            parrafo2.setAlignment(1);
            facturaG.add(parrafo2);
            facturaG.add(new Paragraph(""));
            Paragraph parrafo = new Paragraph("Datos Cliente");
            parrafo.setAlignment(1);
            facturaG.add(parrafo);
            facturaG.add(new Paragraph(""));
            //Datos Cliente
            facturaG.add(new Paragraph("Nombre: " + f.getIdCliente().getIdPersona().getNombre()
                    + " " + f.getIdCliente().getIdPersona().getApellido()));
            facturaG.add(new Paragraph("Correo: " + f.getIdCliente().getIdPersona().getCorreo()));
            facturaG.add(new Paragraph("Cédula: " + f.getIdCliente().getIdPersona().getCedula()));
            facturaG.add(new Paragraph("Dirección: " + f.getIdCliente().getIdPersona().getDireccion()));
            facturaG.add(new Paragraph("Telefono: " + f.getIdCliente().getIdPersona().getTelefono()));
            facturaG.add(new Paragraph("Fecha Emisión: " + f.getFechaEmision().toString()));
            facturaG.add(new Paragraph(""));
            //Index tabla
            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("Nombre Prenda");
            tabla.addCell("Categoría");
            tabla.addCell("Descripcion");
            tabla.addCell("Precio");
            //Contenido tabla
            for (Prenda p : pr) {
                tabla.addCell(p.getNombre());
                tabla.addCell(p.getCategoria());
                tabla.addCell(p.getDescripcion());
                tabla.addCell(p.getPrecio().toString());
            }
            tabla.addCell("");
            tabla.addCell("");
            tabla.addCell("Sin Impuestos: ");
            tabla.addCell("" + sImp);
            tabla.addCell("");
            tabla.addCell("");
            tabla.addCell("Impuestos: ");
            tabla.addCell("" + vImp);
            tabla.addCell("");
            tabla.addCell("");
            tabla.addCell("Descuentos: ");
            tabla.addCell("" + vDes);
            tabla.addCell("");
            tabla.addCell("");
            tabla.addCell("Precio Total: ");
            tabla.addCell(f.getPrecioTotal().toString());
            facturaG.add(tabla);
            facturaG.close();
            enviarFactura(r);
            FacesMessage msg = new FacesMessage("Factura generada con exito");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarFactura(String rutaArchivo) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            String correoReceptor = cliente.getIdPersona().getCorreo();
            String asu = "Factura Electrónica YPS Fashion Store";
            String msg = "Gracias por preferirnos 7w7";
            Session session = Session.getDefaultInstance(props);
            session.setDebug(false);
            BodyPart texto = new MimeBodyPart();
            texto.setText(msg);
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(rutaArchivo)));
            adjunto.setFileName("factura.pdf");
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USUARIO));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asu);
            message.setContent(multiParte);
            try (Transport t = session.getTransport("smtp")) {
                t.connect(USUARIO, PASS);
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            }
            FacesMessage msge = new FacesMessage("Factura enviada con exito");
            FacesContext.getCurrentInstance().addMessage(null, msge);
        } catch (AddressException ex) {
            java.util.logging.Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            java.util.logging.Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //************************************************
    //FIN METODOS PARA GENERAR FACTURA
    //************************************************

    //************************************************
    //METODOS TABLA TARJETA
    //************************************************
    public boolean tieneTarjetasSeleccionadas() {
        return this.tarjetasSeleccionadas != null && !this.tarjetasSeleccionadas.isEmpty();
    }

    public void agregarTarjeta() {
        try {
            this.tarjetaSeleccionada.setIdCliente(cliente);
            this.tarjetaService.create(tarjetaSeleccionada);
            List<Tarjeta> aux = cliente.getTarjetaList();
            aux.add(this.tarjetaService.getTarjeta());
            this.cliente.setTarjetaList(aux);
            this.clienteService.edit(cliente);
            this.tarjetas.add(tarjetaSeleccionada);
            this.tarjetasCliente.add(tarjetaSeleccionada);
            this.tarjetaSeleccionada = new Tarjeta();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tarjeta agregada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageTarjetaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages_tarjeta", "form:dt-tarjetas");
    }

    public void modificarTarjeta() {
        try {
            this.tarjetaService.edit(tarjetaSeleccionada);
            this.tarjetaSeleccionada = new Tarjeta();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tarjeta modificada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editTarjetaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages_tarjeta", "form:dt-tarjetas");
    }

    public void eliminarTarjetas() {
        try {
            for (Tarjeta t : this.tarjetasSeleccionadas) {
                this.tarjetaService.remove(t);
                this.tarjetas.remove(t);
            }
            this.tarjetasSeleccionadas = new ArrayList<>();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form:messages_tarjeta", "form:dt-tarjetas");
        PrimeFaces.current().executeScript("PF('dtTarjetas').clearFilters()");
    }

    public void eliminarTarjeta() {
        try {
            this.tarjetaService.remove(tarjetaSeleccionada);
            this.tarjetas.remove(tarjetaSeleccionada);
            this.tarjetaSeleccionada = new Tarjeta();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form:messages_tarjeta", "form:dt-tarjetas");
        PrimeFaces.current().executeScript("PF('dtTarjetas').clearFilters()");
    }

    public String getDeleteButtonMessageTarjeta() {
        if (tieneTarjetasSeleccionadas()) {
            int size = this.tarjetasSeleccionadas.size();
            return size > 1 ? size + " Tarjetas Seleccioanadas" : "1 Tarjeta Seleccionada";
        }
        return "Eliminar";
    }

    public void reiniciarTarjetaSeleccionada() {
        this.tarjetaSeleccionada = new Tarjeta();
    }
    //************************************************
    //FIN METODOS TABLA TARJETA
    //************************************************

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

    public void editarCarrito(Prenda prendaEditar) {
        for (Prenda p : this.carrito) {
            if (p.getIdPrenda().equals(prendaEditar.getIdPrenda())) {
                this.carrito.remove(p);
                this.carrito.add(prendaEditar);
            }
        }
    }

    public void eliminarPrendaCarrito(Prenda prendaEliminar) {
        this.carrito.remove(prendaEliminar);
        PrimeFaces.current().ajax().update("form:dt-carritos");
    }

    public Prenda getPrendaAgregar() {
        return prendaAgregar;
    }
    
    public void editarPrenda(Prenda p) {
        for (Prenda pr : this.prendaService.findAll()) {
            if (pr.getIdPrenda().equals(p.getIdPrenda())) {
                this.productoDuplicado = pr;
            }
        }
    }

    public void agregarPrendaCarrito(Prenda p) {
        p.setTallaList(this.prendaAgregar.getTallaList());
        p.setColorList(this.prendaAgregar.getColorList());
        this.carrito.add(p);
        this.prendaAgregar = new Prenda();
    }
    
    public void setPrendaAgregar(Prenda prendaAgregar) {
        this.prendaAgregar = prendaAgregar;
    }

    public List<Prenda> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<Prenda> carrito) {
        this.carrito = carrito;
    }

    public List<Tarjeta> getTarjetasSeleccionadas() {
        return tarjetasSeleccionadas;
    }

    public void setTarjetasSeleccionadas(List<Tarjeta> tarjetasSeleccionadas) {
        this.tarjetasSeleccionadas = tarjetasSeleccionadas;
    }

    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public Tarjeta getTarjetaSeleccionada() {
        return tarjetaSeleccionada;
    }

    public void setTarjetaSeleccionada(Tarjeta tarjetaSeleccionada) {
        this.tarjetaSeleccionada = tarjetaSeleccionada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Prenda getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Prenda productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public List<Tarjeta> getTarjetasCliente() {
        return tarjetasCliente;
    }

    public void setTarjetasCliente(List<Tarjeta> tarjetasCliente) {
        this.tarjetasCliente = tarjetasCliente;
    }

    public Prenda getProductoDuplicado() {
        return productoDuplicado;
    }

    public void setProductoDuplicado(Prenda productoDuplicado) {
        this.productoDuplicado = productoDuplicado;
    }

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

    public DetalleFactura getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleFactura detalle) {
        this.detalle = detalle;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Tarjeta getTarjetaSeleccionadaC() {
        return tarjetaSeleccionadaC;
    }

    public void setTarjetaSeleccionadaC(Tarjeta tarjetaSeleccionadaC) {
        this.tarjetaSeleccionadaC = tarjetaSeleccionadaC;
    }

    public List<Factura> getFacturasCliente() {
        return facturasCliente;
    }

    public void setFacturasCliente(List<Factura> facturasCliente) {
        this.facturasCliente = facturasCliente;
    }

}
