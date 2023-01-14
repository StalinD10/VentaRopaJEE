package Managed;

import Entidades.Administrador;
import Entidades.Cliente;
import Entidades.Syslog;
import Sesiones.AdministradorFacadeLocal;
import Sesiones.ClienteFacadeLocal;
import Sesiones.SyslogFacadeLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author david
 */
@Named
@RequestScoped
public class ManagedSesiones implements Serializable {

    private String usuario, contraseña, msj;

    @Inject
    private ClienteFacadeLocal manejadorPersona;
    private Cliente cli;

    @Inject
    private AdministradorFacadeLocal manejadorAdmin;

    @Inject
    private SyslogFacadeLocal manejadorSyslog;
    private Syslog log;

    public ManagedSesiones() {
    }

    @PostConstruct
    public void inicializar() {
        this.usuario = null;
        this.contraseña = null;
        this.cli = null;
    }

    public String iniciarSesion() {
        List<Cliente> clientesRecuperados = manejadorPersona.findAll();
        List<Administrador> admins = manejadorAdmin.findAll();
        Administrador admin = manejadorAdmin.validarAdmin(admins, usuario, contraseña);
        String redireccion = "";
        try {
            if (admin != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("admin", admin);
                this.msj = "Cuenta detectada como Administrador";
                redireccion = "indexAdmin.xhtml";
            } else {
                cli = manejadorPersona.validarCliente(clientesRecuperados, this.getUsuario(), this.getContraseña());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", cli);
                if (cli != null) {
                    registroSyslog();
                    this.msj = "Bienvenido " + this.getCli().getUsuario();
                    redireccion = "indexUsuario.xhtml";
                } else {
                    this.msj = "Datos Incorrectos";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.msj = "Error" + e.getMessage();
        }
        FacesMessage mensaje = new FacesMessage(this.msj);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        return redireccion;
    }

    private void registroSyslog() {
        try {
            this.log = new Syslog();
            this.log.setIdPersona(this.cli.getIdPersona());
            Date fechaActual = new Date();
            this.log.setIngreso(fechaActual);
            this.manejadorSyslog.create(log);
        } catch (Exception e) {
            System.out.println("No registrado syslog");
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public Syslog getLog() {
        return log;
    }

    public void setLog(Syslog log) {
        this.log = log;
    }

}
