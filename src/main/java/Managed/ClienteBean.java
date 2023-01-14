package Managed;

import Entidades.Cliente;
import Entidades.Persona;
import Sesiones.ClienteFacadeLocal;
import Sesiones.PersonaFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

    @Inject
    private PersonaFacadeLocal personaService;
    private Persona persona = null;

    @Inject
    private ClienteFacadeLocal clienteService;
    private Cliente clienteSeleccionado;

    public ClienteBean() {
    }

    @PostConstruct
    public void inicializar() {
        clienteSeleccionado = (Cliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
    }

    public void modificarCliente() {
        try {
            persona = this.clienteSeleccionado.getIdPersona();
            personaService.edit(persona);
            clienteService.edit(clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Actualizado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
    }

    public void reiniciarClienteSeleccionada() {
        this.clienteSeleccionado = new Cliente();
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
