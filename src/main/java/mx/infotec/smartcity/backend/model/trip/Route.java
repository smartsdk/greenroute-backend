
package mx.infotec.smartcity.backend.model.trip;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "route_id",
    "short_name",
    "long_name"
})
public class Route {

    @JsonProperty("route_id")
    private String routeId;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("long_name")
    private String longName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("route_id")
    public String getRouteId() {
        return routeId;
    }

    @JsonProperty("route_id")
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @JsonProperty("short_name")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("short_name")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("long_name")
    public String getLongName() {
        return longName;
    }

    @JsonProperty("long_name")
    public void setLongName(String longName) {
        this.longName = longName;
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
