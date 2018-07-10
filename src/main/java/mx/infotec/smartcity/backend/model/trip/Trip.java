
package mx.infotec.smartcity.backend.model.trip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;

/**
 *
 * @author yolanda.baca
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "wheelchair",
    "arriveBy",
    "toPlace",
    "fromPlace",
    "mode",
    "time",
    "maxWalkDistance",
    "segments"
})
public class Trip {
    
    @Id
    private String id;
    @JsonProperty("wheelchair")
    private String wheelchair;
    @JsonProperty("arriveBy")
    private String arriveBy;
    @JsonProperty("toPlace")
    private ToPlace toPlace;
    @JsonProperty("fromPlace")
    private FromPlace fromPlace;
    @JsonProperty("mode")
    private String mode;
    @JsonProperty("time")
    private String time;
    @JsonProperty("maxWalkDistance")
    private String maxWalkDistance;
    @JsonProperty("segments")
    private List<Segment> segments = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    
    
    
    @JsonProperty("wheelchair")
    public String getWheelchair() {
        return wheelchair;
    }

    @JsonProperty("wheelchair")
    public void setWheelchair(String wheelchair) {
        this.wheelchair = wheelchair;
    }

    @JsonProperty("arriveBy")
    public String getArriveBy() {
        return arriveBy;
    }

    @JsonProperty("arriveBy")
    public void setArriveBy(String arriveBy) {
        this.arriveBy = arriveBy;
    }

    @JsonProperty("toPlace")
    public ToPlace getToPlace() {
        return toPlace;
    }

    @JsonProperty("toPlace")
    public void setToPlace(ToPlace toPlace) {
        this.toPlace = toPlace;
    }

    @JsonProperty("fromPlace")
    public FromPlace getFromPlace() {
        return fromPlace;
    }

    @JsonProperty("fromPlace")
    public void setFromPlace(FromPlace fromPlace) {
        this.fromPlace = fromPlace;
    }

    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("maxWalkDistance")
    public String getMaxWalkDistance() {
        return maxWalkDistance;
    }

    @JsonProperty("maxWalkDistance")
    public void setMaxWalkDistance(String maxWalkDistance) {
        this.maxWalkDistance = maxWalkDistance;
    }

    @JsonProperty("segments")
    public List<Segment> getSegments() {
        return segments;
    }

    @JsonProperty("segments")
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
