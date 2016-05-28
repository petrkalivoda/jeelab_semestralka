var Input = function(name, value) {
	this.name = name;
	this.value = value;
	this.errors = [];
}

Input.prototype.required = function(msg) {
	if (msg == null)
		msg = "Povinné pole";
	if (this._isNullOrEmpty())
		this._pushError(msg);
	return this;
}

Input.prototype.match = function(otherValue, msg) {
	if (msg == null)
		msg = "validation.error.match";
	if (this.value != otherValue)
		this._pushError(msg);
	return this;
}

Input.prototype.email = function(msg) {
	if (this._isNullOrEmpty())
		return this;
	if (msg == null)
		msg = "E-mail není ve správném tvaru";
	var pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

	if(!pattern.test(this.value))
		this._pushError(msg);
	return this;
}

// TODO opravit zadavani desetinnnych cisel, nyni lez zadat pouze s teckou
Input.prototype.number = function() {
	if (this._isNullOrEmpty())
		return this;
	if (isNaN(this.value))
		this._pushError("validation.error.number");
	return this;
}

Input.prototype.integer = function() {
	if (this._isNullOrEmpty())
		return this;
	if (!/^\+?(0|[1-9]\d*)$/.test(this.value))
		this._pushError("validation.error.integer");
	return this;
}

Input.prototype.dateAfter = function(otherDate, msg) {
	if (otherDate == null || otherDate == "")
		return this;
	if (this._isNullOrEmpty())
		return this;
	if (msg == null)
		msg = "validation.error.dateAfter";
	if (this.value.getTime() <= otherDate.getTime())
		this._pushError(msg);
	return this;
}

Input.prototype.min = function(min) {
	if (this.value == null || this.value == "")
		return this;
	if (this.value < min)
		this._pushError("validation.error.min", min);
	return this;
}

Input.prototype.max = function(max) {
	if (this.value == null || this.value == "")
		return this;
	if (this.value > max)
		this._pushError("validation.error.max", max);
	return this;
}

Input.prototype.length = function(len) {
	if (this.value == null || this.value == "")
		return this;
	if (this.value.length > len)
		this._pushError("Maximální délka je " + len + " znaků");
	return this;
}

Input.prototype.notValue = function(val) {
	if (this.value == val)
		this._pushError("validation.error.value");
	return this;
}

Input.prototype.isValid = function() {
	return this.errors.length == 0;
}

Input.prototype.getName = function() {
	return this.name;
}

Input.prototype.getErrors = function() {
	return this.errors;
}

Input.prototype._isNullOrEmpty = function(min) {
	return this.value == null || this.value == "";
}

Input.prototype._pushError = function(code, value) {
	this.errors.push({code: code, value: value});
}



