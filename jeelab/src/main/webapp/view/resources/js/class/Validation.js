var Validation = function() {
	this.inputs = {};
}

Validation.prototype.input = function(name, value) {
	var input = new Input(name, value);
	this.inputs[name] = input;
	return input;
}

Validation.prototype.isValid = function() {
	var err = true;
	angular.forEach(this.inputs, function(input, key){
		if (!input.isValid()) {
			err = false;
			return;
		}
	});
	return err;
}

Validation.prototype.errors = function(field) {
	if (this.inputs[field] == null)
		return null;
	return this.inputs[field].getErrors();
}

Validation.prototype.fields = function(field) {
	return this.inputs;
}
