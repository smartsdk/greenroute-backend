
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

/**
 *
 * @author yolanda.baca
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "distance",
    "path",
    "mode",
    "instructions",
    "tripId",
    "agency",
    "route",
    "from",
    "to",
    "startTime",
    "endTime"
})
public class Segment {

    @JsonProperty("type")
    private String type;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("path")
    private String path;
    @JsonProperty("instructions")
    private List<String> instructions = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("distance")
    public Double getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("instructions")
    public List<String> getInstructions() {
        return instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
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
