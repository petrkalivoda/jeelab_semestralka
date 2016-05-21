package jeelab.ws.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"count", "totalCount", "list"})
@JsonInclude(Include.NON_NULL)
public class ListWrapper {

	private List<?> list;
	private int count;
	private Integer totalCount;
	
	public ListWrapper(List<?> list) {
		if (list == null) return;
		this.list = list;
		this.count = list.size();
	}
	
	public ListWrapper(List<?> list, int totalCount) {
		if (list == null) return;
		this.list = list;
		this.count = list.size();
		this.totalCount = totalCount;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
		
}
