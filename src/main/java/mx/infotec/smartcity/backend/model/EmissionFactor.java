package mx.infotec.smartcity.backend.model;

public class EmissionFactor {
	
	private String id;
	private String name;
	private Double value;
	private String transportMode;
	private String unit;
	private String source;
	
	public EmissionFactor(String id, String name, Double value, String unit) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

}
