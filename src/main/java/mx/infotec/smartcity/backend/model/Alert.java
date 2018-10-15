package mx.infotec.smartcity.backend.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import mx.infotec.smartcity.backend.utils.OrionMapper;
import org.springframework.data.annotation.Id;
import mx.infotec.smartcity.backend.utils.AlertCatalog;
import static mx.infotec.smartcity.backend.utils.AlertCatalog.setSubCategoryAlert;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Adrian Molina
 */
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String type;
    private String alertType;
    private String eventObserved;
    private String locationDescription;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date dateTime;
    private String description;
    private String refUser;
    private String refDevice;
    private ArrayList location;
    private boolean found;
    private HashMap address;
    private String dataSource;
    private String severity;
        
    
    public Alert (){
        
    }
    
    public Alert(Data data, String elemento, String valor){
        /* Se inicializa la varialbe found en false */
        setFound(false);
        if(data.getType().equals("AirQualityObserved")){
            this.description = OrionMapper.extractProperty(data, elemento);
            /* Verificamos que el resultado de la propiedad consultada, sea diferente al código 102 */

            if(!this.description.split(" ")[0].equals("102")){
                /* Indicamos que el elemento fue encontrado */
                setFound(true);               
                this.type = "AirQualityObserved";
                this.alertType = valor;
                this.eventObserved = firstLetterCaps(elemento);
                this.refUser = "refUser";
                this.refDevice = "refDevice";
                this.dateTime = OrionMapper.extractTimeProperty(data, "dateObserved");
                this.location = OrionMapper.extractCoordinateProperty(data);
                this.address = OrionMapper.extractMapProperty(data, "address");
                this.locationDescription = OrionMapper.extractFromMapProperty(address, "streetAddress") + ", " + OrionMapper.extractFromMapProperty(address, "addressLocality");
                this.dataSource = OrionMapper.extractProperty(data, "source");
                                
                if(elemento.equals("temperature")){
                    if(Float.parseFloat(description)>=14&&Float.parseFloat(description)<=26){
                        setFound(false);
                    }else{
                        this.eventObserved = AlertCatalog.seteventObservedTemperature(description);
                        this.description = AlertCatalog.setAlertTemperature(description);                     
                    }
                   
                }else if(elemento.equals("relativeHumidity")){
                    this.eventObserved = "Relative humidity";
                    this.description = AlertCatalog.setAlertHumidity(description);
                    
                }else if(valor.equals("Environment")){
                    if(Float.parseFloat(description)<=100){
                        setFound(false);
                    }else{
                    this.description = AlertCatalog.AlertPollution(description, elemento);
                    }
                }
            }
        }else if(data.getType().equals("Alert")){
            /* Indicamos que el elemento fue encontrado */
            setFound(true);
            this.type = data.getType();
            this.alertType = OrionMapper.extractProperty(data, "category");
            this.eventObserved = firstLetterCaps(setSubCategoryAlert(OrionMapper.extractProperty(data, "subCategory")));
            this.refUser = OrionMapper.extractProperty(data, "alertSource");
            this.refDevice = OrionMapper.extractProperty(data, "refDevice");
            this.description = OrionMapper.extractProperty(data, "description");
            this.dateTime = OrionMapper.extractTimeProperty(data, "dateObserved");
            this.location = OrionMapper.extractCoordinateProperty(data);
            this.address = OrionMapper.extractMapProperty(data, "address");
            this.locationDescription = OrionMapper.extractFromMapProperty(address, "streetAddress") + ", " + OrionMapper.extractFromMapProperty(address, "addressLocality");
            this.dataSource = OrionMapper.extractProperty(data, "dataSource");
            this.severity = OrionMapper.extractProperty(data, "severity");
        }
    }
    
    public boolean getFound(){
        return found;
    }
    
    public void setFound(boolean found){
        this.found = found;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefUser() {
        return refUser;
    }

    public void setRefUser(String refUser) {
        this.refUser = refUser;
    }

    public String getRefDevice() {
        return refDevice;
    }

    public void setRefDevice(String refDevice) {
        this.refDevice = refDevice;
    }

    public String getEventObserved() {
        return eventObserved;
    }

    public void setEventObserved(String eventObserved) {
        this.eventObserved = eventObserved;
    }
    
    public String getdataSource(){
        return dataSource;
    }
    
    public void setdataSource(String dataSource){
        this.dataSource = dataSource;
    }
    
    public ArrayList getLocation(){
        return location;
    }
    
    public void setLocation(ArrayList location){
        this.location = location;
    }
    
    public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}
    
    /* Función que convierte a mayúsculas la primera letra de un String */
    public final String firstLetterCaps ( String data ) {
      String firstLetter = data.substring(0,1).toUpperCase();
      String restLetters = data.substring(1);
      return firstLetter + restLetters;
    }
    
    
}
