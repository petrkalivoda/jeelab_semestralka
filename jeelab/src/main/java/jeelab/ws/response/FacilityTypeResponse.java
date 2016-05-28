package jeelab.ws.response;

public class FacilityTypeResponse {

	private Long id;
	private String url;
	private String name;
	
	public FacilityTypeResponse id(long id) {
		this.id = id;
		return this;
	}
	
	public FacilityTypeResponse url(String url) {
		this.url = url;
		return this;
	}
	
	public FacilityTypeResponse name(String name) {
		this.name = name;
		return this;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
}
