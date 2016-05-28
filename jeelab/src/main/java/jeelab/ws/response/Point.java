package jeelab.ws.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * Reprezentuje entry point definovany unikatnim kodem a vlastni adresou
 * @author Vaclav Dobes
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class Point {

	private String code;
	private String url;
	
	public Point() {}

	public Point(String code, String url) {
		this.code = code;
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
