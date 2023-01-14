package Managed;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named
@ViewScoped
public class MapaBean implements Serializable {

    private MapModel simpleModel;

    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();
        LatLng coord1 = new LatLng(-0.22612303723190288, -78.5112420995193);
        simpleModel.addOverlay(new Marker(coord1, "YPS Fashion Store"));
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

}
