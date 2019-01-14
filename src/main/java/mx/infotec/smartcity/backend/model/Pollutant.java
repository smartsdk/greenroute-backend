package mx.infotec.smartcity.backend.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "value",
    "distanceTravelled",
    "unit"
})
public class Pollutant {
	
	@Id
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("value")
	private Double value;
	@JsonProperty("distanceTravelled")
	private Double distanceTravelled;
	@JsonProperty("unit")
	private String unit;
	
	public Pollutant() {}

	public Pollutant(String id, String name, Double value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public Pollutant(String id, String name, Double value, Double distanceTravelled, String unit) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.distanceTravelled = distanceTravelled;
		this.unit = unit;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getDistanceTravelled() {
		return distanceTravelled;
	}
	public void setDistanceTravelled(Double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
