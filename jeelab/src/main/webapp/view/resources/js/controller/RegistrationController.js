route.controller("RegistrationController", function ($scope, $http, rest, globalMessages, validation, errorRender, addressStorage) {
	
	$scope.registration = {
		form: {},
		preset: {}
	};
	
	$scope.registration.submit = function() {
		errorRender.clear(angular.element("#registration-form"));
		$scope.registration.applyValidation(function() {
			addressStorage.get("registration", function(address) {
				rest.post(address, $scope.registration.form, function(response) {
					globalMessages.push("success", "Registrace byla dokončena");
				});
			});
		});
	};

	$scope.registration.applyValidation = function(valid, invalid) {
		validation.input("email", $scope.registration.form.email).required().email().length(125);
		validation.input("firstname", $scope.registration.form.firstname).required().length(125);
		validation.input("lastname", $scope.registration.form.lastname).required().length(125);
		validation.input("password", $scope.registration.form.password).required()
			.match($scope.registration.preset.password_check, "Hesla se neshodují");
		
		if (validation.isValid()) {
			if (valid != null)
				valid();
		} else {
			errorRender.showErrors(angular.element("#registration-form"), validation);
			if (invalid != null)
				invalid()
		}
	}
	
})