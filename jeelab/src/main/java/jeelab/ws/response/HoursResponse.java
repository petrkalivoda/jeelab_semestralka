package jeelab.ws.response;

public class HoursResponse {

	private Long id;
	private String url;
	private Integer day;
	private Float open;
	private Float close;
	
	public HoursResponse id(Long id) {
		this.id = id;
		return this;
	}
	
	public HoursResponse url(String url) {
		this.url = url;
		return this;
	}
	
	public HoursResponse day(Integer day) {
		this.day = day;
		return this;
	}
	
	public HoursResponse open(Float open) {
		this.open = open;
		return this;
	}
	
	public HoursResponse close(Float close) {
		this.close = close;
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
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Float getOpen() {
		return open;
	}
	public void setOpen(Float open) {
		this.open = open;
	}
	public Float getClose() {
		return close;
	}
	public void setClose(Float close) {
		this.close = close;
	}
		
}
