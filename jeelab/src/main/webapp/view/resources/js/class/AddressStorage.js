var AddressStorage = function(address, cookies, http, globalMessages) {
	
	this.globalMessages = globalMessages;
	this.cookies = cookies;
	this.http = http;
	
	this.address = address;
	this.addresses = [];
	
}

AddressStorage.prototype.loadResources = function(code, callback) {
	this.http({
		method: 'GET',
		url: this.address,
	}).then(function success(response) {
		this.addresses = [];
		angular.forEach(response.data, function(item) {
			this.addresses[item.code] = item.url;
		}.bind(this));
//		console.log("resources loaded");
		callback(this.addresses[code]);
	}.bind(this), function error(response) {
		this.globalMessages.push("danger", "Chyba: " + response.status);
		console.log(response.body);		
	}.bind(this));
}

AddressStorage.prototype.get = function(code, callback) {
	if (this.addresses[code] == null) {
		this.loadResources(code, callback);
	} else {
		callback(this.addresses[code]);
	}
}

AddressStorage.prototype.clear = function() {
	this.addresses = [];
}