
package mx.infotec.smartcity.backend.model.trip;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author yolanda.baca
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "arrival",
    "name",
    "stopSequence",
    "location",
    "departure",
    "stopId"
})
public class From {

    @JsonProperty("arrival")
    private Integer arrival;
    @JsonProperty("name")
    private String name;
    @JsonProperty("stopSequence")
    private Integer stopSequence;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("departure")
    private Integer departure;
    @JsonProperty("stopId")
    private String stopId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("arrival")
    public Integer getArrival() {
        return arrival;
    }

    @JsonProperty("arrival")
    public void setArrival(Integer arrival) {
        this.arrival = arrival;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("stopSequence")
    public Integer getStopSequence() {
        return stopSequence;
    }

    @JsonProperty("stopSequence")
    public void setStopSequence(Integer stopSequence) {
        this.stopSequence = stopSequence;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("departure")
    public Integer getDeparture() {
        return departure;
    }

    @JsonProperty("departure")
    public void setDeparture(Integer departure) {
        this.departure = departure;
    }

    @JsonProperty("stopId")
    public String getStopId() {
        return stopId;
    }

    @JsonProperty("stopId")
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
