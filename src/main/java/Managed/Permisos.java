package Managed;

import Entidades.Administrador;
import Entidades.Cliente;
import Entidades.Syslog;
import Sesiones.SyslogFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class Permisos implements Serializable {

    @Inject
    private SyslogFacadeLocal syslogManager;
    private List<Syslog> logs;
    private Syslog log;

    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Cliente cli = (Cliente) context.getExternalContext().getSessionMap().get("cliente");
            if (cli == null) {
                context.getExternalContext().redirect("pruebasSesiones.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cerrarSesion() {
        try {
            this.logs = new ArrayList<>();
            this.logs = syslogManager.findAll();
            Date fechaSalida = new Date();
            int last = this.logs.size();
            this.log = this.logs.get(last - 1);
            this.log.setSalida(fechaSalida);
            this.syslogManager.edit(log);
        } catch (Exception e) {
            System.out.println("Error guardando fecha salida");
        }
    }

    public void verificarSesionAdmin() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Administrador admin = (Administrador) context.getExternalContext().getSessionMap().get("admin");
            if (admin == null) {
                context.getExternalContext().redirect("pruebasSesiones.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Syslog> getLogs() {
        return logs;
    }

    public void setLogs(List<Syslog> logs) {
        this.logs = logs;
    }

    public Syslog getLog() {
        return log;
    }

    public void setLog(Syslog log) {
        this.log = log;
    }

}
