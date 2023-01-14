package Managed;

import Entidades.GeneroPersona;
import Entidades.Prenda;
import Sesiones.PrendaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CatalogoBean implements Serializable {

    private List<Prenda> catalogoGeneral;
    private List<Prenda> catalogoDama;
    private List<Prenda> catalogoDamaCalzado;
    private List<Prenda> catalogoDamaInteriorPijama;
    private List<Prenda> catalogoDamaCamisaFormal;
    private List<Prenda> catalogoDamaPantalon;
    private List<Prenda> catalogoDamaAbrigoCalentador;
    private List<Prenda> catalogoDamaBlusa;
    private List<Prenda> catalogoDamaVestido;
    private List<Prenda> catalogoCaballero;
    private List<Prenda> catalogoCaballeroCalzado;
    private List<Prenda> catalogoCaballeroInteriorPijama;
    private List<Prenda> catalogoCaballeroCamisaFormal;
    private List<Prenda> catalogoCaballeroPantalonBermuda;
    private List<Prenda> catalogoCaballeroAbrigo;
    private List<Prenda> catalogoCaballeroDeportivaCalentador;
    private List<Prenda> catalogoCaballeroCamiseta;
    private List<Prenda> catalogoInfantil;
    private List<Prenda> catalogoNiño;
    private List<Prenda> catalogoNiña;

    @Inject
    private PrendaFacadeLocal catalogoService;

    public CatalogoBean() {
    }

    @PostConstruct
    public void inicializar() {
        this.catalogoGeneral = this.catalogoService.findAll();
        this.catalogoDama = new ArrayList<>();
        this.catalogoDamaCalzado = new ArrayList<>();
        this.catalogoDamaInteriorPijama = new ArrayList<>();
        this.catalogoDamaCamisaFormal = new ArrayList<>();
        this.catalogoDamaPantalon = new ArrayList<>();
        this.catalogoDamaAbrigoCalentador = new ArrayList<>();
        this.catalogoDamaBlusa = new ArrayList<>();
        this.catalogoDamaVestido = new ArrayList<>();
        this.catalogoCaballero = new ArrayList<>();
        this.catalogoCaballeroCalzado = new ArrayList<>();
        this.catalogoCaballeroInteriorPijama = new ArrayList<>();
        this.catalogoCaballeroCamisaFormal = new ArrayList<>();
        this.catalogoCaballeroPantalonBermuda = new ArrayList<>();
        this.catalogoCaballeroAbrigo = new ArrayList<>();
        this.catalogoCaballeroDeportivaCalentador = new ArrayList<>();
        this.catalogoCaballeroCamiseta = new ArrayList<>();
        this.catalogoInfantil = new ArrayList<>();
        this.catalogoNiño = new ArrayList<>();
        this.catalogoNiña = new ArrayList<>();
    }

    //************************************************
    //METODOS PARA CATALOGO CABALLERO
    //************************************************
    public void generarCatalogoCaballero() {
        for (Prenda p : this.catalogoGeneral) {
            for (GeneroPersona gp : p.getGeneroPersonaList()) {
                if (gp.getIdGenero() == 2) {
                    this.catalogoCaballero.add(p);
                }
            }
        }
    }

    public void generarCaballeroCalzado() {
        for (Prenda p : this.catalogoCaballero) {
            if (p.getCategoria().equals("Calzado")) {
                this.catalogoCaballeroCalzado.add(p);
            }
        }
    }

    public void generarCaballeroInteriorPijama() {
        for (Prenda p : this.catalogoCaballero) {
            if (p.getCategoria().equals("Ropa Interior") || p.getCategoria().equals("Pijamas")) {
                this.catalogoCaballeroInteriorPijama.add(p);
            }
        }
    }

    public void generarCaballeroCamisaFormal() {
        for (Prenda p : this.catalogoCaballero) {
            if (p.getCategoria().equals("Camisa") || p.getCategoria().equals("Formal")) {
                this.catalogoCaballeroCamisaFormal.add(p);
            }
        }
    }

    public void generarCaballeroPantalonBermuda() {
        for (Prenda p : this.catalogoCaballero) {
            if (p.getCategoria().equals("Pantalon") || p.getCategoria().equals("Bermudas")) {
                this.catalogoCaballeroPantalonBermuda.add(p);
            }
        }
    }

    public void generarCaballeroAbrigo() {
        for (Prenda p : this.catalogoCaballero) {
            if (p.getCategoria().equals("Abrigo")) {
                this.catalogoCaballeroAbrigo.add(p);
            }
        }
    }

    public void generarCaballeroDeportivaCalentador() {
        for (Prenda p : this.catalogoCaballero) {
            if (p.getCategoria().equals("Deportiva") || p.getCategoria().equals("Calentador")) {
                this.catalogoCaballeroDeportivaCalentador.add(p);
            }
        }
    }

    public void generarCaballeroCamiseta() {
        for (Prenda p : this.catalogoCaballero) {
            if (p.getCategoria().equals("Camiseta")) {
                this.catalogoCaballeroCamiseta.add(p);
            }
        }
    }
    //************************************************
    //FIN METODOS PARA CATALOGO CABALLERO
    //************************************************

    //************************************************
    //METODOS PARA CATALOGO DAMA
    //************************************************
    public void generarCatalogoDama() {
        for (Prenda p : this.catalogoGeneral) {
            for (GeneroPersona gp : p.getGeneroPersonaList()) {
                if (gp.getIdGenero() == 1) {
                    this.catalogoDama.add(p);
                }
            }
        }
    }

    public void generarDamaCalzado() {
        for (Prenda p : this.catalogoDama) {
            if (p.getCategoria().equals("Calzado")) {
                this.catalogoDamaCalzado.add(p);
            }
        }
    }

    public void generarDamaInteriorPijama() {
        for (Prenda p : this.catalogoDama) {
            if (p.getCategoria().equals("Ropa Interior") || p.getCategoria().equals("Pijamas")) {
                this.catalogoDamaInteriorPijama.add(p);
            }
        }
    }

    public void generarDamaCamisaFormal() {
        for (Prenda p : this.catalogoDama) {
            if (p.getCategoria().equals("Camisa") || p.getCategoria().equals("Formal")) {
                this.catalogoDamaCamisaFormal.add(p);
            }
        }
    }

    public void generarDamaPantalon() {
        for (Prenda p : this.catalogoDama) {
            if (p.getCategoria().equals("Pantalon")) {
                this.catalogoDamaPantalon.add(p);
            }
        }
    }

    public void generarDamaAbrigoCalentador() {
        for (Prenda p : this.catalogoDama) {
            if (p.getCategoria().equals("Abrigo") || p.getCategoria().equals("Calentador")) {
                this.catalogoDamaAbrigoCalentador.add(p);
            }
        }
    }

    public void generarDamaBlusa() {
        for (Prenda p : this.catalogoDama) {
            if (p.getCategoria().equals("Blusa")) {
                this.catalogoDamaBlusa.add(p);
            }
        }
    }

    public void generarDamaVestido() {
        for (Prenda p : this.catalogoDama) {
            if (p.getCategoria().equals("Vestido")) {
                this.catalogoDamaVestido.add(p);
            }
        }
    }
    //************************************************
    //FIN METODOS PARA CATALOGO DAMA
    //************************************************

    //************************************************
    //METODOS PARA CATALOGO INFANTIL
    //************************************************
    public void generarCatalogoInfantil() {
        for (Prenda p : this.catalogoGeneral) {
            for (GeneroPersona gp : p.getGeneroPersonaList()) {
                if (gp.getIdGenero() == 3 || gp.getIdGenero() == 4) {
                    this.catalogoInfantil.add(p);
                }
            }
        }
    }

    public void generarCatalogoNiño() {
        for (Prenda p : this.catalogoInfantil) {
            for (GeneroPersona gp : p.getGeneroPersonaList()) {
                if (gp.getIdGenero() == 3) {
                    this.catalogoNiño.add(p);
                }
            }
        }
    }

    public void generarCatalogoNiña() {
        for (Prenda p : this.catalogoInfantil) {
            for (GeneroPersona gp : p.getGeneroPersonaList()) {
                if (gp.getIdGenero() == 4) {
                    this.catalogoNiña.add(p);
                }
            }
        }
    }
    //************************************************
    //FIN METODOS PARA CATALOGO INFANTIL
    //************************************************

    public List<Prenda> getCatalogoGeneral() {
        return catalogoGeneral;
    }

    public void setCatalogoGeneral(List<Prenda> catalogoGeneral) {
        this.catalogoGeneral = catalogoGeneral;
    }

    public List<Prenda> getCatalogoDama() {
        return catalogoDama;
    }

    public void setCatalogoDama(List<Prenda> catalogoDama) {
        this.catalogoDama = catalogoDama;
    }

    public List<Prenda> getCatalogoCaballero() {
        return catalogoCaballero;
    }

    public void setCatalogoCaballero(List<Prenda> catalogoCaballero) {
        this.catalogoCaballero = catalogoCaballero;
    }

    public List<Prenda> getCatalogoInfantil() {
        return catalogoInfantil;
    }

    public void setCatalogoInfantil(List<Prenda> catalogoInfantil) {
        this.catalogoInfantil = catalogoInfantil;
    }

    public List<Prenda> getCatalogoNiño() {
        return catalogoNiño;
    }

    public void setCatalogoNiño(List<Prenda> catalogoNiño) {
        this.catalogoNiño = catalogoNiño;
    }

    public List<Prenda> getCatalogoNiña() {
        return catalogoNiña;
    }

    public void setCatalogoNiña(List<Prenda> catalogoNiña) {
        this.catalogoNiña = catalogoNiña;
    }

    public List<Prenda> getCatalogoDamaCalzado() {
        return catalogoDamaCalzado;
    }

    public void setCatalogoDamaCalzado(List<Prenda> catalogoDamaCalzado) {
        this.catalogoDamaCalzado = catalogoDamaCalzado;
    }

    public List<Prenda> getCatalogoDamaInteriorPijama() {
        return catalogoDamaInteriorPijama;
    }

    public void setCatalogoDamaInteriorPijama(List<Prenda> catalogoDamaInteriorPijama) {
        this.catalogoDamaInteriorPijama = catalogoDamaInteriorPijama;
    }

    public List<Prenda> getCatalogoDamaCamisaFormal() {
        return catalogoDamaCamisaFormal;
    }

    public void setCatalogoDamaCamisaFormal(List<Prenda> catalogoDamaCamisaFormal) {
        this.catalogoDamaCamisaFormal = catalogoDamaCamisaFormal;
    }

    public List<Prenda> getCatalogoDamaPantalon() {
        return catalogoDamaPantalon;
    }

    public void setCatalogoDamaPantalon(List<Prenda> catalogoDamaPantalon) {
        this.catalogoDamaPantalon = catalogoDamaPantalon;
    }

    public List<Prenda> getCatalogoDamaAbrigoCalentador() {
        return catalogoDamaAbrigoCalentador;
    }

    public void setCatalogoDamaAbrigoCalentador(List<Prenda> catalogoDamaAbrigoCalentador) {
        this.catalogoDamaAbrigoCalentador = catalogoDamaAbrigoCalentador;
    }

    public List<Prenda> getCatalogoDamaBlusa() {
        return catalogoDamaBlusa;
    }

    public void setCatalogoDamaBlusa(List<Prenda> catalogoDamaBlusa) {
        this.catalogoDamaBlusa = catalogoDamaBlusa;
    }

    public List<Prenda> getCatalogoDamaVestido() {
        return catalogoDamaVestido;
    }

    public void setCatalogoDamaVestido(List<Prenda> catalogoDamaVestido) {
        this.catalogoDamaVestido = catalogoDamaVestido;
    }

    public List<Prenda> getCatalogoCaballeroCalzado() {
        return catalogoCaballeroCalzado;
    }

    public void setCatalogoCaballeroCalzado(List<Prenda> catalogoCaballeroCalzado) {
        this.catalogoCaballeroCalzado = catalogoCaballeroCalzado;
    }

    public List<Prenda> getCatalogoCaballeroInteriorPijama() {
        return catalogoCaballeroInteriorPijama;
    }

    public void setCatalogoCaballeroInteriorPijama(List<Prenda> catalogoCaballeroInteriorPijama) {
        this.catalogoCaballeroInteriorPijama = catalogoCaballeroInteriorPijama;
    }

    public List<Prenda> getCatalogoCaballeroCamisaFormal() {
        return catalogoCaballeroCamisaFormal;
    }

    public void setCatalogoCaballeroCamisaFormal(List<Prenda> catalogoCaballeroCamisaFormal) {
        this.catalogoCaballeroCamisaFormal = catalogoCaballeroCamisaFormal;
    }

    public List<Prenda> getCatalogoCaballeroPantalonBermuda() {
        return catalogoCaballeroPantalonBermuda;
    }

    public void setCatalogoCaballeroPantalonBermuda(List<Prenda> catalogoCaballeroPantalonBermuda) {
        this.catalogoCaballeroPantalonBermuda = catalogoCaballeroPantalonBermuda;
    }

    public List<Prenda> getCatalogoCaballeroAbrigo() {
        return catalogoCaballeroAbrigo;
    }

    public void setCatalogoCaballeroAbrigo(List<Prenda> catalogoCaballeroAbrigo) {
        this.catalogoCaballeroAbrigo = catalogoCaballeroAbrigo;
    }

    public List<Prenda> getCatalogoCaballeroDeportivaCalentador() {
        return catalogoCaballeroDeportivaCalentador;
    }

    public void setCatalogoCaballeroDeportivaCalentador(List<Prenda> catalogoCaballeroDeportivaCalentador) {
        this.catalogoCaballeroDeportivaCalentador = catalogoCaballeroDeportivaCalentador;
    }

    public List<Prenda> getCatalogoCaballeroCamiseta() {
        return catalogoCaballeroCamiseta;
    }

    public void setCatalogoCaballeroCamiseta(List<Prenda> catalogoCaballeroCamiseta) {
        this.catalogoCaballeroCamiseta = catalogoCaballeroCamiseta;
    }

}
