package Managed;

import Entidades.Color;
import Entidades.Descuentos;
import Entidades.GeneroPersona;
import Entidades.Impuesto;
import Entidades.Material;
import Entidades.Prenda;
import Entidades.Talla;
import Sesiones.ColorFacadeLocal;
import Sesiones.DescuentosFacadeLocal;
import Sesiones.GeneroPersonaFacadeLocal;
import Sesiones.ImpuestoFacadeLocal;
import Sesiones.MaterialFacadeLocal;
import Sesiones.PrendaFacadeLocal;
import Sesiones.TallaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@Named
@ViewScoped
public class PrendaBean implements Serializable {

    //Variables para cargar la imagen
    private UploadedFile imagenOriginal;

    @Inject
    //Variables Tabla Prenda
    private PrendaFacadeLocal prendaService;
    private List<Prenda> prendas;
    private List<Prenda> prendasSeleccionadas;
    private Prenda prendaSeleccionada;

    @Inject
    //Variables Tabla Color
    private ColorFacadeLocal colorService;
    private List<Color> colores;
    private List<Color> coloresSeleccionados;
    private Color colorSeleccionado;

    @Inject
    //Variables Tabla Genero
    private GeneroPersonaFacadeLocal generoService;
    private List<GeneroPersona> generos;
    private List<GeneroPersona> generosSeleccionados;
    private GeneroPersona generoSeleccionado;

    @Inject
    //Variables Tabla Descuento
    private DescuentosFacadeLocal descuentoService;
    private List<Descuentos> descuentos;
    private List<Descuentos> descuentosSeleccionados;
    private Descuentos descuentoSeleccionado;

    @Inject
    //Variables Tabla Material
    private MaterialFacadeLocal materialService;
    private List<Material> materiales;
    private List<Material> materialesSeleccionados;
    private Material materialSeleccionado;

    @Inject
    //Variables Tabla Impuesto
    private ImpuestoFacadeLocal impuestoService;
    private List<Impuesto> impuestos;
    private List<Impuesto> impuestosSeleccionados;
    private Impuesto impuestoSeleccionado;

    @Inject
    //Variables Tabla Talla
    private TallaFacadeLocal tallaService;
    private List<Talla> tallas;
    private List<Talla> tallasSeleccionado;
    private Talla tallaSeleccionado;

    public PrendaBean() {
    }

    @PostConstruct
    public void inicializar() {
        prendas = prendaService.findAll();
        colores = colorService.findAll();
        generos = generoService.findAll();
        descuentos = descuentoService.findAll();
        materiales = materialService.findAll();
        impuestos = impuestoService.findAll();
        tallas = tallaService.findAll();
        prendaSeleccionada = new Prenda();
        prendaSeleccionada.setRutaImagen("null.png");
        generoSeleccionado = new GeneroPersona();
        colorSeleccionado = new Color();
        descuentoSeleccionado = new Descuentos();
        materialSeleccionado = new Material();
        impuestoSeleccionado = new Impuesto();
        tallaSeleccionado = new Talla();
    }

    //************************************************
    //METODOS EXTRAS
    //************************************************
    public void cargarImagenPrenda(FileUploadEvent event) {
        this.imagenOriginal = null;
        UploadedFile archivo = event.getFile();
        if (archivo != null && archivo.getContent() != null && archivo.getContent().length > 0 && archivo.getFileName() != null) {
            this.imagenOriginal = archivo;
            this.prendaSeleccionada.setRutaImagen(this.imagenOriginal.getFileName());
            PrimeFaces.current().ajax().update("form_prenda:manage-prenda-content");
            FacesMessage msg = new FacesMessage("Exitoso", this.imagenOriginal.getFileName() + " cargado.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void reiniciarImagen() {
        this.imagenOriginal = null;
    }
    //************************************************
    //FIN METODOS EXTRAS
    //************************************************

    //************************************************
    //METODOS USADOS EN TABLA PRENDA
    //************************************************
    public boolean tienePrendasSeleccionadas() {
        return this.prendasSeleccionadas != null && !this.prendasSeleccionadas.isEmpty();
    }

    public void modificarPrenda() {
        try {
            this.prendaService.edit(prendaSeleccionada);
            this.prendaSeleccionada = new Prenda();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Prenda actualizada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editPrendaDialog').hide()");
        PrimeFaces.current().ajax().update("form_prenda:messages_prenda", "form_prenda:dt-prendas");
    }

    public void agregarPrenda() {
        try {
            this.prendaService.create(prendaSeleccionada);
            this.prendas.add(prendaSeleccionada);
            this.prendaSeleccionada = new Prenda();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Prenda agregada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('managePrendaDialog').hide()");
        PrimeFaces.current().ajax().update("form_prenda:messages_prenda", "form_prenda:dt-prendas");
    }

    public void eliminarPrendas() {
        try {
            for (Prenda p : this.prendasSeleccionadas) {
                this.prendaService.remove(p);
                this.prendas.remove(p);
            }
            this.prendasSeleccionadas = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Prendas eliminadas"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_prenda:messages_prenda", "form_prenda:dt-prendas");
        PrimeFaces.current().executeScript("PF('dtPrendas').clearFilters()");
    }

    public void eliminarPrenda() {
        try {
            this.prendaService.remove(prendaSeleccionada);
            this.prendas.remove(prendaSeleccionada);
            this.prendaSeleccionada = new Prenda();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Prenda eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_prenda:messages_prenda", "form_prenda:dt-prendas");
        PrimeFaces.current().executeScript("PF('dtPrendas').clearFilters()");
    }

    public String getDeleteButtonMessagePrenda() {
        if (tienePrendasSeleccionadas()) {
            int size = this.prendasSeleccionadas.size();
            return size > 1 ? size + " Prendas seleccioandas" : "1 Prenda Seleccionada";
        }
        return "Eliminar";
    }

    public void reiniciarPrendaSeleccionada() {
        this.prendaSeleccionada = new Prenda();
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public List<Prenda> getPrendasSeleccionadas() {
        return prendasSeleccionadas;
    }

    public void setPrendasSeleccionadas(List<Prenda> prendasSeleccionadas) {
        this.prendasSeleccionadas = prendasSeleccionadas;
    }

    public Prenda getPrendaSeleccionada() {
        return prendaSeleccionada;
    }

    public void setPrendaSeleccionada(Prenda prendaSeleccionada) {
        this.prendaSeleccionada = prendaSeleccionada;
    }
    //************************************************
    //FIN METODOS USADOS EN TABLA PRENDA
    //************************************************

    //************************************************
    //METODOS USADOS EN TABLA COLOR
    //************************************************
    public boolean tieneColoresSeleccionados() {
        return this.coloresSeleccionados != null && !this.coloresSeleccionados.isEmpty();
    }

    public void modificarColor() {
        try {
            this.colorService.edit(this.colorSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Color actualizado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editColorDialog').hide()");
        PrimeFaces.current().ajax().update("form_color:messages_color", "form_color:dt-colors");
    }

    public void agregarColor() {
        try {
            this.colorService.create(this.colorSeleccionado);
            this.colores.add(this.colorSeleccionado);
            this.colorSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Color agregado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageColorDialog').hide()");
        PrimeFaces.current().ajax().update("form_color:messages_color", "form_color:dt-colors");
    }

    public void eliminarColores() {
        try {
            for (Color c : colores) {
                this.colorService.remove(c);
                this.colores.remove(c);
            }
            this.coloresSeleccionados = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Colores borrados"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_color:messages_color", "form_color:dt-colors");
        PrimeFaces.current().executeScript("PF('dtColors').clearFilters()");
    }

    public void eliminarColor() {
        try {
            this.colorService.remove(this.colorSeleccionado);
            this.colores.remove(this.colorSeleccionado);
            this.colorSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Color borrado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_color:messages_color", "form_color:dt-colors");
        PrimeFaces.current().executeScript("PF('dtColors').clearFilters()");
    }

    public String getDeleteButtonMessageColor() {
        if (tieneColoresSeleccionados()) {
            int size = this.coloresSeleccionados.size();
            return size > 1 ? size + " Colores seleccionados" : "1 Color seleccionado";
        }
        return "Eliminar";
    }

    public void reiniciarColorSeleccionado() {
        this.colorSeleccionado = new Color();
    }

    public List<Color> getColores() {
        return colores;
    }

    public void setColores(List<Color> colores) {
        this.colores = colores;
    }

    public List<Color> getColoresSeleccionados() {
        return coloresSeleccionados;
    }

    public void setColoresSeleccionados(List<Color> coloresSeleccionados) {
        this.coloresSeleccionados = coloresSeleccionados;
    }

    public Color getColorSeleccionado() {
        return colorSeleccionado;
    }

    public void setColorSeleccionado(Color colorSeleccionado) {
        this.colorSeleccionado = colorSeleccionado;
    }
    //************************************************
    //FIN DE METODOS TABLA COLOR
    //************************************************

    //************************************************
    //METODOS USADOS EN TABLA GENERO
    //************************************************
    public boolean tieneGenerosSeleccionados() {
        return this.generosSeleccionados != null && !this.generosSeleccionados.isEmpty();
    }

    public void modificarGenero() {
        try {
            this.generoService.edit(this.generoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Genero modificado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editGenderDialog').hide()");
        PrimeFaces.current().ajax().update("form_gender:messages_gender", "form_gender:dt-genders");
    }

    public void agregarGenero() {
        try {
            this.generoService.create(this.generoSeleccionado);
            this.generos.add(this.generoSeleccionado);
            this.generoSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Genero ingresado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageGenderDialog').hide()");
        PrimeFaces.current().ajax().update("form_gender:messages_gender", "form_gender:dt-genders");
    }

    public void eliminarGeneros() {
        try {
            for (GeneroPersona gp : generosSeleccionados) {
                this.generoService.remove(gp);
                this.generos.remove(gp);
            }
            this.generosSeleccionados = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Generos Eliminados"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_gender:messages_gender", "form_gender:dt-genders");
        PrimeFaces.current().executeScript("PF('dtGenders').clearFilters()");
    }

    public void eliminarGenero() {
        try {
            this.generoService.remove(this.generoSeleccionado);
            this.generos.remove(this.generoSeleccionado);
            this.generoSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Genero Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_gender:messages_gender", "form_gender:dt-genders");
        PrimeFaces.current().executeScript("PF('dtGenders').clearFilters()");
    }

    public String getDeleteButtonMessageGenero() {
        if (tieneGenerosSeleccionados()) {
            int size = this.generosSeleccionados.size();
            return size > 1 ? size + " Generos seleccionados" : "1 Genero seleccionado";
        }
        return "Eliminar";
    }

    public void reiniciarGeneroSeleccionado() {
        this.generoSeleccionado = new GeneroPersona();
    }

    public List<GeneroPersona> getGeneros() {
        return generos;
    }

    public void setGeneros(List<GeneroPersona> generos) {
        this.generos = generos;
    }

    public List<GeneroPersona> getGenerosSeleccionados() {
        return generosSeleccionados;
    }

    public void setGenerosSeleccionados(List<GeneroPersona> generosSeleccionados) {
        this.generosSeleccionados = generosSeleccionados;
    }

    public GeneroPersona getGeneroSeleccionado() {
        return generoSeleccionado;
    }

    public void setGeneroSeleccionado(GeneroPersona generoSeleccionado) {
        this.generoSeleccionado = generoSeleccionado;
    }
    //************************************************
    //FIN DE METODOS USADOS EN TABLA GENERO
    //************************************************

    //************************************************
    //METODOS USADOS EN TABLA DESCUENTOS
    //************************************************
    public boolean tieneDescuentosSeleccionados() {
        return this.descuentosSeleccionados != null && !this.descuentosSeleccionados.isEmpty();
    }

    public void modificarDescuento() {
        try {
            this.descuentoService.edit(this.descuentoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Descuento modificado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editDescountDialog').hide()");
        PrimeFaces.current().ajax().update("form_descount:messages_descount", "form_descount:dt-descounts");
    }

    public void agregarDescuento() {
        try {
            this.descuentoService.create(this.descuentoSeleccionado);
            this.descuentos.add(this.descuentoSeleccionado);
            this.descuentoSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Descuento agregado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageDescountDialog').hide()");
        PrimeFaces.current().ajax().update("form_descount:messages_descount", "form_descount:dt-descounts");
    }

    public void eliminarDescuentos() {
        try {
            for (Descuentos d : this.descuentosSeleccionados) {
                this.descuentoService.remove(d);
                this.descuentos.remove(d);
            }
            this.descuentosSeleccionados = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Descuentos eliminados"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_descount:messages_descount", "form_descount:dt-descounts");
        PrimeFaces.current().executeScript("PF('dtDescounts').clearFilters()");
    }

    public void eliminarDescuento() {
        try {
            this.descuentoService.remove(this.descuentoSeleccionado);
            this.descuentos.remove(this.descuentoSeleccionado);
            this.descuentoSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Descuento Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_descount:messages_descount", "form_descount:dt-descounts");
        PrimeFaces.current().executeScript("PF('dtDescounds').clearFilters()");
    }

    public String getDeleteButtonMessageDescuento() {
        if (tieneDescuentosSeleccionados()) {
            int size = this.descuentosSeleccionados.size();
            return size > 1 ? size + " Descuentos seleccionados" : "1 Descuento seleccionado";
        }
        return "Eliminar";
    }

    public void reiniciarDescuentoSeleccionado() {
        this.descuentoSeleccionado = new Descuentos();
    }

    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<Descuentos> descuentos) {
        this.descuentos = descuentos;
    }

    public List<Descuentos> getDescuentosSeleccionados() {
        return descuentosSeleccionados;
    }

    public void setDescuentosSeleccionados(List<Descuentos> descuentosSeleccionados) {
        this.descuentosSeleccionados = descuentosSeleccionados;
    }

    public Descuentos getDescuentoSeleccionado() {
        return descuentoSeleccionado;
    }

    public void setDescuentoSeleccionado(Descuentos descuentoSeleccionado) {
        this.descuentoSeleccionado = descuentoSeleccionado;
    }
    //************************************************
    //FIN METODOS USADOS EN TABLA DESCUENTOS
    //************************************************

    //************************************************
    //METODOS USADOS EN TABLA MATERIAL
    //************************************************
    public boolean tieneMaterialSeleccionados() {
        return this.materialesSeleccionados != null && !this.materialesSeleccionados.isEmpty();
    }

    public void modificarMaterial() {
        try {
            materialService.edit(materialSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Material modificado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editMaterialDialog').hide()");
        PrimeFaces.current().ajax().update("form_material:messages_material", "form_material:dt-materials");
    }

    public void agregarMaterial() {
        try {
            this.materialService.create(materialSeleccionado);
            this.materiales.add(materialSeleccionado);
            this.materialSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Material agregado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageMaterialDialog').hide()");
        PrimeFaces.current().ajax().update("form_material:messages_material", "form_material:dt-materials");
    }

    public void eliminarMateriales() {
        try {
            for (Material m : this.materialesSeleccionados) {
                this.materialService.remove(m);
                this.materiales.remove(m);
            }
            this.materialesSeleccionados = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Materiales eliminados"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_material:messages_material", "form_material:dt-materials");
        PrimeFaces.current().executeScript("PF('dtMaterials').clearFilters()");
    }

    public void eliminarMaterial() {
        try {
            this.materialService.remove(this.materialSeleccionado);
            this.materiales.remove(this.materialSeleccionado);
            this.materialSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Material Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_material:messages_material", "form_material:dt-materials");
        PrimeFaces.current().executeScript("PF('dtMaterials').clearFilters()");
    }

    public String getDeleteButtonMessageMaterial() {
        if (tieneMaterialSeleccionados()) {
            int size = this.materialesSeleccionados.size();
            return size > 1 ? size + " Materiales seleccionados" : "1 Material seleccionado";
        }
        return "Eliminar";
    }

    public void reiniciarMaterialSeleccionado() {
        this.materialSeleccionado = new Material();
    }

    public List<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<Material> materiales) {
        this.materiales = materiales;
    }

    public List<Material> getMaterialesSeleccionados() {
        return materialesSeleccionados;
    }

    public void setMaterialesSeleccionados(List<Material> materialesSeleccionados) {
        this.materialesSeleccionados = materialesSeleccionados;
    }

    public Material getMaterialSeleccionado() {
        return materialSeleccionado;
    }

    public void setMaterialSeleccionado(Material materialSeleccionado) {
        this.materialSeleccionado = materialSeleccionado;
    }
    //************************************************
    //FIN METODOS USADOS EN TABLA MATERIAL
    //************************************************

    //************************************************
    // METODOS USADOS EN TABLA IMPUESTO
    //************************************************
    public boolean tieneImpuestoSeleccionados() {
        return this.impuestosSeleccionados != null && !this.impuestosSeleccionados.isEmpty();
    }

    public void modificarImpuesto() {
        try {
            this.impuestoService.edit(this.impuestoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impuesto modificado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editTaxeDialog').hide()");
        PrimeFaces.current().ajax().update("form_taxe:messages_taxe", "form_taxe:dt-taxes");
    }

    public void agregarImpuesto() {
        try {
            this.impuestoService.create(this.impuestoSeleccionado);
            this.impuestos.add(this.impuestoSeleccionado);
            this.impuestoSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impuesto agregado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageTaxeDialog').hide()");
        PrimeFaces.current().ajax().update("form_taxe:messages_taxe", "form_taxe:dt-taxes");
    }

    public void eliminarImpuestos() {
        try {
            for (Impuesto i : this.impuestosSeleccionados) {
                this.impuestoService.remove(i);
                this.impuestos.remove(i);
            }
            this.impuestosSeleccionados = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impuestos Eliminados"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_taxe:messages_taxe", "form_taxe:dt-taxes");
        PrimeFaces.current().executeScript("PF('dtTaxes').clearFilters()");
    }

    public void eliminarImpuesto() {
        try {
            this.impuestoService.remove(this.impuestoSeleccionado);
            this.impuestos.remove(this.impuestoSeleccionado);
            this.impuestoSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impuesto Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_taxe:messages_taxe", "form_taxe:dt-taxes");
        PrimeFaces.current().executeScript("PF('dtTaxes').clearFilters()");
    }

    public String getDeleteButtonMessageImpuesto() {
        if (tieneImpuestoSeleccionados()) {
            int size = this.impuestosSeleccionados.size();
            return size > 1 ? size + " Impuestos seleccionados" : "1 Impuesto seleccionado";
        }
        return "Eliminar";
    }

    public void reiniciarImpuestosSeleccionados() {
        this.impuestoSeleccionado = new Impuesto();
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public List<Impuesto> getImpuestosSeleccionados() {
        return impuestosSeleccionados;
    }

    public void setImpuestosSeleccionados(List<Impuesto> impuestosSeleccionados) {
        this.impuestosSeleccionados = impuestosSeleccionados;
    }

    public Impuesto getImpuestoSeleccionado() {
        return impuestoSeleccionado;
    }

    public void setImpuestoSeleccionado(Impuesto impuestoSeleccionado) {
        this.impuestoSeleccionado = impuestoSeleccionado;
    }
    //************************************************
    // FIN METODOS USADOS EN TABLA IMPUESTO
    //************************************************

    //************************************************
    // METODOS USADOS EN TABLA TALLA
    //************************************************
    public boolean tieneTallasSeleccionados() {
        return this.tallasSeleccionado != null && !this.tallasSeleccionado.isEmpty();
    }

    public void modificarTalla() {
        try {
            this.tallaService.edit(this.tallaSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Talla modificada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('editSizeDialog').hide()");
        PrimeFaces.current().ajax().update("form_size:messages_size", "form_size:dt-sizes");
    }

    public void agregarTalla() {
        try {
            this.tallaService.create(this.tallaSeleccionado);
            this.tallas.add(this.tallaSeleccionado);
            this.tallaSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Talla agregada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('manageSizeDialog').hide()");
        PrimeFaces.current().ajax().update("form_size:messages_size", "form_size:dt-sizes");
    }

    public void eliminarTallas() {
        try {
            for (Talla t : this.tallasSeleccionado) {
                this.tallaService.remove(t);
                this.tallas.remove(t);
            }
            this.tallasSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tallas Eliminadas"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_size:messages_size", "form_size:dt-sizes");
        PrimeFaces.current().executeScript("PF('dtSizes').clearFilters()");
    }

    public void eliminarTalla() {
        try {
            this.tallaService.remove(this.tallaSeleccionado);
            this.tallas.remove(this.tallaSeleccionado);
            this.tallaSeleccionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Talla Eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error " + e.getMessage()));
        }
        PrimeFaces.current().ajax().update("form_size:messages_size", "form_size:dt-sizes");
        PrimeFaces.current().executeScript("PF('dtSize').clearFilters()");
    }

    public String getDeleteButtonMessageTalla() {
        if (tieneTallasSeleccionados()) {
            int size = this.tallasSeleccionado.size();
            return size > 1 ? size + " Tallas seleccionadoa" : "1 Talla seleccionada";
        }
        return "Eliminar";
    }

    public void reiniciarTallaSeleccionada() {
        this.tallaSeleccionado = new Talla();
    }

    public List<Talla> getTallas() {
        return tallas;
    }

    public void setTallas(List<Talla> tallas) {
        this.tallas = tallas;
    }

    public List<Talla> getTallasSeleccionado() {
        return tallasSeleccionado;
    }

    public void setTallasSeleccionado(List<Talla> tallasSeleccionado) {
        this.tallasSeleccionado = tallasSeleccionado;
    }

    public Talla getTallaSeleccionado() {
        return tallaSeleccionado;
    }

    public void setTallaSeleccionado(Talla tallaSeleccionado) {
        this.tallaSeleccionado = tallaSeleccionado;
    }
    //************************************************
    // FIN METODOS USADOS EN TABLA TALLA
    //************************************************

}
