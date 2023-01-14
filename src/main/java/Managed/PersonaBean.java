package Managed;

import Entidades.Administrador;
import Entidades.Cliente;
import Entidades.Persona;
import Sesiones.AdministradorFacadeLocal;
import Sesiones.ClienteFacadeLocal;
import Sesiones.PersonaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class PersonaBean implements Serializable {

    private String correoVerificar;
    private static final String USUARIO = "yspfashionstore@gmail.com";
    private static final String PASS = "yspFashionStore";
    private int codigoVerificacion;
    private int codigoTemporal;

    @Inject
    //Variables tabla persona
    private PersonaFacadeLocal personaService;
    private List<Persona> personasSeleccionadas;
    private List<Persona> personas;
    private Persona personaSeleccionada;

    @Inject
    //Variables tabla cliente
    private ClienteFacadeLocal clienteService;
    private List<Cliente> clientes;
    private List<Cliente> clientesSeleccionados;
    private Cliente clienteSeleccionado;

    @Inject
    //Variables tabla administrador
    private AdministradorFacadeLocal adminService;
    private List<Administrador> admins;
    private List<Administrador> adminsSeleccionados;
    private Administrador adminSeleccionado;

    public PersonaBean() {
    }

    @PostConstruct
    public void inicializar() {
        personas = personaService.findAll();
        clientes = clienteService.findAll();
        admins = adminService.findAll();
        personaSeleccionada = new Persona();
        clienteSeleccionado = new Cliente();
        adminSeleccionado = new Administrador();
    }

    //************************************************
    //METODOS USADOS EN TABLA PERSONA
    //************************************************
    private void enviarMensaje(String rutaArchivo, String correoReceptor, String asu, String msg) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            session.setDebug(false);
            MimeMultipart multiParte = new MimeMultipart();
            BodyPart texto = new MimeBodyPart();
            texto.setText(msg);
            multiParte.addBodyPart(texto);
            if (!rutaArchivo.equals("")) {
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(rutaArchivo)));
                adjunto.setFileName("bienvenida.jpg.");
                multiParte.addBodyPart(adjunto);
            }
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USUARIO));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asu);
            message.setContent(multiParte);
            try ( Transport t = session.getTransport("smtp")) {
                t.connect(USUARIO, PASS);
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje Enviado"));
        } catch (AddressException ex) {
            java.util.logging.Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            java.util.logging.Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean tienePersonasSeleccionadas() {
        return this.personasSeleccionadas != null && !this.personasSeleccionadas.isEmpty();
    }

    public void modificarPersona() {
        try {
            this.personaService.edit(personaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Persona modificada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editPersonDialog').hide()");
        PrimeFaces.current().ajax().update("form_person:messages_person", "form_person:dt-people");
    }

    public void verificarCorreo() {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String mail = this.personaSeleccionada.getCorreo();
        Matcher mather = pattern.matcher(mail);
        if (mather.find()) {
            this.codigoTemporal = (int) Math.floor(Math.random() * (900000) + 100000);
            String asu = "Te enviamos el código de seguridad";
            String msg = "Este dato es exclusivo para que puedas ingresar a tu cuenta, "
                    + "\nnadie de YPS Fashion Store te lo va pedir en ninguna instancia."
                    + "\n¡No compartas este código!"
                    + "\n" + this.codigoTemporal
                    + "\nTen en cuenta que vence en 30 minutos.";
            enviarMensaje("", this.personaSeleccionada.getCorreo(), asu, msg);
        } else {
            limpiarValores();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Correo no valido"));
        }
        PrimeFaces.current().ajax().update("form_client:messages_client");
    }

    public void verificarCodigo() {
        if (this.codigoTemporal == this.codigoVerificacion) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo Verificado"));
            agregarPersona();
            reiniciarPersonaSeleccionada();
            agregarCliente();
            reiniciarClienteSeleccionada();
            limpiarValores();
        } else {
            limpiarValores();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Codigo Incorrecto"));
        }
        PrimeFaces.current().ajax().update("form_client:messages_client");
    }

    public void limpiarValores() {
        this.personaSeleccionada = new Persona();
        this.clienteSeleccionado = new Cliente();
        this.codigoTemporal = 0;
        this.codigoVerificacion = 0;
        PrimeFaces.current().ajax().update("form_client");
    }

    public void agregarPersona() {
        try {
            this.personaService.create(personaSeleccionada);
            this.personas.add(personaSeleccionada);
            String rutaRegistroLogo = "C:\\WorkSpace\\Java\\VentaRopaJEE\\src\\main\\webapp\\resources\\images\\ExitoRegistro1.png";
            String asu = "Registro YPS Fashion Store";
            String msg = "Has sido registrado exitosamente, te saluda YPS Fashion Store, "
                    + "\ndisfruta de nuestras ofertas y recuerda, todo en tus manos.";
            enviarMensaje(rutaRegistroLogo, this.personaSeleccionada.getCorreo(), asu, msg);
            this.personaSeleccionada = new Persona();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Persona creada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('managePersonDialog').hide()");
        PrimeFaces.current().ajax().update("form_person:messages_person", "form_person:dt-people");
    }

    public void eliminarPersonas() {
        try {
            for (Persona p : this.personasSeleccionadas) {
                this.personaService.remove(p);
                this.personas.remove(p);
            }
            this.personasSeleccionadas = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Personas eliminadas"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_person:messages_person", "form_person:dt-people");
        PrimeFaces.current().executeScript("PF('dtPeople').clearFilters()");
    }

    public void eliminarPersona() {
        try {
            this.personaService.remove(personaSeleccionada);
            this.personas.remove(personaSeleccionada);
            this.personaSeleccionada = new Persona();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Persona eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_person:messages_person", "form_person:dt-people");
        PrimeFaces.current().executeScript("PF('dtPeople').clearFilters()");
    }

    public String getDeleteButtonMessagePersona() {
        if (tienePersonasSeleccionadas()) {
            int size = this.personasSeleccionadas.size();
            return size > 1 ? size + " Personas Seleccionadas" : "1 Persona Seleccionada";
        }
        return "Eliminar";
    }

    public void reiniciarPersonaSeleccionada() {
        this.personaSeleccionada = new Persona();
    }

    public List<Persona> getPersonasSeleccionadas() {
        return personasSeleccionadas;
    }

    public void setPersonasSeleccionadas(List<Persona> personasSeleccionadas) {
        this.personasSeleccionadas = personasSeleccionadas;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }
    //************************************************
    //FIN METODOS USADOS EN TABLA PERSONA
    //************************************************

    //************************************************
    //METODOS USADOS EN TABLA CLIENTE
    //************************************************
    public void ubicarCliente() {
        this.inicializar();
        FacesContext context = FacesContext.getCurrentInstance();
        this.clienteSeleccionado = (Cliente) context.getExternalContext().getSessionMap().get("cliente");
    }

    public boolean tieneClientesSeleccionados() {
        return this.clientesSeleccionados != null && !this.clientesSeleccionados.isEmpty();
    }

    public void modificarCliente() {
        try {
            this.personaSeleccionada = this.clienteSeleccionado.getIdPersona();
            this.modificarPersona();
            this.personaSeleccionada = new Persona();
            clienteService.edit(clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Actualizado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editClientDialog').hide()");
        PrimeFaces.current().ajax().update("form_client:messages_client", "form_client:dt-clients");
    }

    public void agregarCliente() {
        try {
            this.clienteSeleccionado.setIdPersona(clienteService.encontrarId());
            this.clienteService.create(clienteSeleccionado);
            this.clientes.add(clienteSeleccionado);
            this.clienteSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Agregado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageClientDialog').hide()");
        PrimeFaces.current().ajax().update("form_client:messages_client", "form_client:dt-clients");
    }

    public void eliminarClientes() {
        try {
            for (Cliente c : this.clientesSeleccionados) {
                this.clienteService.remove(c);
                this.clientes.remove(c);
            }
            this.clientesSeleccionados = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_client:messages_client", "form_client:dt-clients");
        PrimeFaces.current().executeScript("PF('dtClients').clearFilters()");
    }

    public void eliminarCliente() {
        try {
            this.clienteService.remove(this.clienteSeleccionado);
            this.clientes.remove(this.clienteSeleccionado);
            this.clienteSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_client:messages_client", "form_client:dt-clients");
        PrimeFaces.current().executeScript("PF('dtClients').clearFilters()");
    }

    public String getDeleteButtonMessage() {
        if (tieneClientesSeleccionados()) {
            int size = this.clientesSeleccionados.size();
            return size > 1 ? size + " Clientes seleccionados" : "1 Cliente seleccionado";
        }
        return "Eliminar";
    }

    public void reiniciarClienteSeleccionada() {
        this.clienteSeleccionado = new Cliente();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientesSeleccionados() {
        return clientesSeleccionados;
    }

    public void setClientesSeleccionados(List<Cliente> clientesSeleccionados) {
        this.clientesSeleccionados = clientesSeleccionados;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }
    //************************************************
    //FIN METODOS USADOS EN TABLA CLIENTE
    //************************************************

    //************************************************
    //METODOS USADOS EN TABLA ADMINISTRADOR
    //************************************************
    public boolean tieneAdminsSeleccionados() {
        return this.adminsSeleccionados != null && this.adminsSeleccionados.isEmpty();
    }

    public void modificarAdmin() {
        try {
            this.adminService.edit(adminSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Administrador modificado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editAdminDialog').hide()");
        PrimeFaces.current().ajax().update("form_admin:messages_admin", "form_admin:dt-admins");
    }

    public void agregarAdmin() {
        try {
            this.adminSeleccionado.setIdPersona(this.adminService.obtenerID());
            this.adminService.create(adminSeleccionado);
            this.admins.add(adminSeleccionado);
            this.adminSeleccionado = new Administrador();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Administrador registrado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageAdminDialog').hide()");
        PrimeFaces.current().ajax().update("form_admin:messages_admin", "form_admin:dt-admins");
    }

    public void eliminarAdmins() {
        try {
            for (Administrador a : this.adminsSeleccionados) {
                this.adminService.remove(a);
                this.admins.remove(a);
            }
            this.adminsSeleccionados = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Administradores Eliminados"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_admin:messages_admin", "form_admin:dt-admins");
        PrimeFaces.current().executeScript("PF('dtAdmins').clearFilters()");
    }

    public void eliminarAdmin() {
        try {
            this.adminService.remove(adminSeleccionado);
            this.admins.remove(adminSeleccionado);
            this.adminSeleccionado = new Administrador();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Administrador eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_admin:messages_admin", "form_admin:dt-admins");
        PrimeFaces.current().executeScript("PF('dtAdmins').clearFilters()");
    }

    public String getDeleteButtonMessageAdmin() {
        if (tienePersonasSeleccionadas()) {
            int size = this.personasSeleccionadas.size();
            return size > 1 ? size + " Administradores seleccionados" : "1 Administrador Seleccionado";
        }
        return "Eliminar";
    }

    public void reiniciarAdminSeleccionado() {
        this.adminSeleccionado = new Administrador();
    }

    public List<Administrador> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Administrador> admins) {
        this.admins = admins;
    }

    public List<Administrador> getAdminsSeleccionados() {
        return adminsSeleccionados;
    }

    public void setAdminsSeleccionados(List<Administrador> adminsSeleccionados) {
        this.adminsSeleccionados = adminsSeleccionados;
    }

    public Administrador getAdminSeleccionado() {
        return adminSeleccionado;
    }

    public void setAdminSeleccionado(Administrador adminSeleccionado) {
        this.adminSeleccionado = adminSeleccionado;
    }
    //************************************************
    //FIN METODOS USADOS EN TABLA ADMINISTRADOR
    //************************************************

    public String getCorreoVerificar() {
        return correoVerificar;
    }

    public void setCorreoVerificar(String correoVerificar) {
        this.correoVerificar = correoVerificar;
    }

    public int getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(int codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public int getCodigoTemporal() {
        return codigoTemporal;
    }

    public void setCodigoTemporal(int codigoTemporal) {
        this.codigoTemporal = codigoTemporal;
    }

}
