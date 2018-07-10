/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    "mode",
})
public class SegmentPrivate extends Segment{
    
    @JsonProperty("mode")
    private String mode;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }
        
    @JsonAnyGetter
    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    @Override
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
