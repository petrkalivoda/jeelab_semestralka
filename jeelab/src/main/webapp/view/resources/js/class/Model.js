var Model = function(http, rootScope, globalMessages) {
	this.globalMessages = globalMessages;
	this.http = http;
	this.rootScope = rootScope;
	this.rootScope.loading = false;
}

Model.prototype.get = function(address, filters, successCall, errorCall) {
	this.rootScope.loading = true;
	if(filters == null)
		filters = {};
	this.http({
		method: 'GET',
		url: address,
		params: filters.params,
		headers: filters.headers,
	}).then(function success(response) {
		this.rootScope.loading = false;
		if (successCall != null)
			successCall(response);
	}.bind(this), function error(response) {
		this.rootScope.loading = false;
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
}

Model.prototype.post = function(address, data, successCall, errorCall) {
	this.rootScope.loading = true;
	this.http({
		method: 'POST',
		data: data,
		url: address,
	}).then(function success(response) {
		this.rootScope.loading = false;
		if (successCall != null)
			successCall(response);
	}.bind(this), function error(response) {
		this.rootScope.loading = false;
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
}

Model.prototype.put = function(address, data, successCall, errorCall) {
	this.rootScope.loading = true;
	this.http({
		method: 'PUT',
		data: data,
		url: address,
	}).then(function success(response) {
		this.rootScope.loading = false;
		if (successCall != null)
			successCall(response);
	}.bind(this), function error(response) {
		this.rootScope.loading = false;
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
}

Model.prototype.delete = function(address, successCall, errorCall) {
	this.rootScope.loading = true;
	this.http({
		method: 'DELETE',
		url: address,
	}).then(function success(response) {
		this.rootScope.loading = false;
		if (successCall != null)
			successCall(response);
	}.bind(this), function error(response) {
		this.rootScope.loading = false;
		if (errorCall != null)
			errorCall(response);
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);
	}.bind(this));
}