var GlobalMessages = function(rootScope) {
	this.rootScope = rootScope;
	this.rootScope.messages = [];
	this.index = 0;
	this.msgId = 0;
}

GlobalMessages.prototype.push = function(status, text) {
	var m = {text: text, status: status, id: this.msgId++};
	this.rootScope.messages[this.index++] = m;
}

GlobalMessages.prototype.pop = function(msgId) {
	var index = this._getMessageIndex(msgId);
	if (index == null) return null;
	this.rootScope.messages.splice(index, 1);
	this.index = this.rootScope.messages.length;
//	console.log(this.rootScope.messages);
}

GlobalMessages.prototype._getMessageIndex = function(msgId) {
	if (this.rootScope.messages == null) return null;
	for (var i = 0; i < this.rootScope.messages.length; i++) {
		if (this.rootScope.messages[i].id == msgId)
			return i;
	}
	return null;
}