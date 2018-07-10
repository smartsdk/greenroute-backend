/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    "tripId",
    "agency",
    "route",
    "from",
    "to",
    "startTime",
    "endTime"
})
public class SegmentPublic extends Segment{
    
    @JsonProperty("tripId")
    private String tripId;
    @JsonProperty("agency")
    private Agency agency;
    @JsonProperty("route")
    private Route route;
    @JsonProperty("from")
    private From from;
    @JsonProperty("to")
    private To to;
    @JsonProperty("startTime")
    private Integer startTime;
    @JsonProperty("endTime")
    private Integer endTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();    
    
    @JsonProperty("tripId")
    public String getTripId() {
        return tripId;
    }

    @JsonProperty("tripId")
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @JsonProperty("agency")
    public Agency getAgency() {
        return agency;
    }

    @JsonProperty("agency")
    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @JsonProperty("route")
    public Route getRoute() {
        return route;
    }

    @JsonProperty("route")
    public void setRoute(Route route) {
        this.route = route;
    }

    @JsonProperty("from")
    public From getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(From from) {
        this.from = from;
    }

    @JsonProperty("to")
    public To getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(To to) {
        this.to = to;
    }

    @JsonProperty("startTime")
    public Integer getStartTime() {
        return startTime;
    }

    @JsonProperty("startTime")
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("endTime")
    public Integer getEndTime() {
        return endTime;
    }

    @JsonProperty("endTime")
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
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
