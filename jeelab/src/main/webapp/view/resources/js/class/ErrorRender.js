var ErrorRender = function (compile) {
	this.compile = compile;
	this.errorClass = "error";
}

ErrorRender.prototype.showErrors = function(form, validation) {
	var inputs = form.find("input, textarea, select");
	angular.forEach(inputs, (function(input, index) {
		var name = input.name;
		var errors = validation.errors(name);
		if (errors != null && errors.length > 0) {
			var inputSelector = angular.element(input);
			inputSelector.parent().addClass(this.errorClass);
			var parent = inputSelector.parent();
			if (inputSelector.hasClass("error-in-parent"))
				parent = parent.parent();
			parent.append(this._buildValidationContainer(form, name, errors, true));
		}
	}).bind(this));
}

ErrorRender.prototype._buildValidationContainer = function(element, namespace, errors, withValues) {
	var scope = element.scope();
	if (scope.errors == null)
		scope.errors = {};
	scope.errors[namespace] = errors;
	var errStr = "e";
	var translateValues = "";
	if (withValues) {
		errStr = "e.code";
		translateValues = " translate-values='{value: e.value}' ";
	}
	var container = this.compile("<div class='error-container'><ul>" +
		"<li ng-repeat='e in errors." + namespace + "' translate='{{" + errStr + "}}'" + translateValues + ">{{" + errStr + "}}</li>" +
		"</ul></div>")(scope);
	return container;
}

ErrorRender.prototype.clear = function(form) {
	form.find(".error-container").remove();
	var inputs = form.find("input, textarea, select");
	inputs.parent().removeClass(this.errorClass);
}
