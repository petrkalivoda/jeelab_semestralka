/**
 * rozhrani pro websockets
 */
var WebSockets = function(address, timeout) {
	this.timeout = timeout;
	this.ws = new WebSocket(address);
	this.ws.onopen = function() {
		console.log("websockets open");
//		this._listen();
	}.bind(this);
}

/**
 * Nastavi funkci ktera se ma vykonat v pripade prijeti zpravy
 */
WebSockets.prototype.callback = function(callback) {
	this.callback = callback;
}

/**
 * Odesle zpravu
 */
WebSockets.prototype.notify = function(message) {
//	console.log("Send message");
	this.ws.send(message);
}

/**
 * V pripade prijeti zpravy vykona callback metodu 
 */
WebSockets.prototype.listen = function(callback) {
	this.timeout(function() {
		console.log("listen");
		this.ws.onmessage = function(env) {
			console.log("New message");
			if (callback != null)
				callback(env.data);
		}.bind(this);
	}.bind(this), 500);
}

WebSockets.prototype._listen = function() {
//	console.log("listen");
	this.ws.onmessage = function(env) {
		console.log("New message");
//		console.log(this.callback)
		if (this.callback != null)
			this.callback(env.data);
	}.bind(this);
}
