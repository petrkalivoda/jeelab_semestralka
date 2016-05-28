var Model = function(http, globalMessages) {
	this.globalMessages = globalMessages;
	this.http = http;
}

Model.prototype.get = function(address, filters, successCall, errorCall) {
	var params = "";
	if(filters == null) {
		filters = {};
	} else { 
		params = this.parameterParser.processGetData(filters.parameters);
	}
	this.http({
		method: 'GET',
		url: address+params,
		headers: filters.headers,
	}).then(function success(response) {
		if (successCall != null)
			successCall(response);
	}, function error(response) {
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
	
}

Model.prototype.post = function(address, data, successCall, errorCall) {
	this.http({
		method: 'POST',
		data: data,
		url: address,
	}).then(function success(response) {
		if (successCall != null)
			successCall(response);
	}, function error(response) {
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
}

Model.prototype.put = function(address, data, form, successCall, errorCall) {
	this.http({
		method: 'PUT',
		data: data,
		url: address,
	}).then(function success(response) {
		if (successCall != null)
			successCall(response);
	}, function error(response) {
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
}

Model.prototype.delete = function(address, successCall, errorCall) {
	this.http({
		method: 'DELETE',
		url: address,
	}).then(function success(response) {
		if (successCall != null)
			successCall(response);
	}, function error(response) {
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
}