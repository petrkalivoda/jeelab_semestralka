route.controller("CentreController", function($scope, addressStorage, rest, globalMessages, validation, errorRender) {
	
	$scope.centre = {
		formSelector: "#centre-type-form",
		form: {
			hours: [],
		},
		modalId: "centre-modal-form",
		modalSelector: "#centre-modal-form",
		modal: {},
		select: {
			country: [
			         {value: "CZ", name: "Česká republika"},
			         {value: "DE", name: "Německo"},
			         {value: "UK", name: "Velká británie"}
			         ],
			day: [
			      {value: 1, name: "pondělí"},
			      {value: 2, name: "úterý"},
			      {value: 3, name: "středa"},
			      {value: 4, name: "čtvrtek"},
			      {value: 5, name: "pátek"},
			      {value: 6, name: "sobota"},
			      {value: 7, name: "neděle"},
			      ]
		}
	};
	
	/**
	 * Vraci seznam sportovist
	 */
	var getCentres = function() {
		addressStorage.get("centre", function(address) {
			rest.get(address, null, function(response) {
//				console.log(response.data);	
				$scope.centre.list = response.data;
			});
		});
	}
	getCentres();
	
	/**
	 * Zavola se pri otevreni modalniho okna za ucelem vytvoreni sportoviste
	 */
	$scope.centre.openCentreAddModal = function() {
		errorRender.clear(angular.element($scope.centre.formSelector));
		$scope.centre.form = {
			country: "CZ",
			hours: []
		};
		$scope.centre.modal = {
			btn: "Přidat",
			submit: function() {
				errorRender.clear(angular.element($scope.centre.formSelector));
				$scope.centre.addCentre();
			}
		};
	}
	
	/**
	 * Zavola se pri otevreni modalniho okna za ucelem upraveni sportoviste
	 */
	$scope.centre.openCentreUpdateModal = function(centre) {
		errorRender.clear(angular.element($scope.centre.formSelector));
		rest.get(centre.url, null, function(response) {
			$scope.centre.form = {
				street: response.data.street,
				city: response.data.city,
				country: response.data.country,
				buildingNumber: response.data.building,
				phone: response.data.phone,
				hours: []
			};
			angular.forEach(response.data.hours, function(h) {
				$scope.centre.form.hours.push({
					day: h.day,
					open: h.open,
					close: h.close
				});	
			});
//			console.log(response.data);
		});
		
		$scope.centre.modal = {
			btn: "Upravit",
			submit: function() {
				errorRender.clear(angular.element($scope.centre.formSelector));
				$scope.centre.updateCentre(centre.url);
			}
		};
	}
	
	/**
	 * Zavre modalni okno
	 */
	$scope.centre.closeCentreModal = function() {
		angular.element($scope.centre.modalSelector).modal("hide");
	}
	
	
	/**
	 * Prida sportoviste
	 */
	$scope.centre.addCentre = function() {
		$scope.centre.applyValidation(function () {
//			console.log($scope.centre.form);
			addressStorage.get("centre", function(address) {
				rest.post(address, $scope.centre.form, function(response) {
					$scope.centre.closeCentreModal();
					globalMessages.push("success", "Sportoviště bylo přidáno");
					getCentres();
				});
			});
		});
	}
	
	/**
	 * Upravi sportoviste
	 */
	$scope.centre.updateCentre = function(address) {
//		console.log($scope.centre.form);
		$scope.centre.applyValidation(function () {
			rest.put(address, $scope.centre.form, function(response) {
				$scope.centre.closeCentreModal();
				globalMessages.push("success", "Sportoviště bylo upraveno");
				getCentres();
			});
		});
	}
	
	/**
	 * Smazat sportoviste
	 */
	$scope.centre.deleteCentre = function(address) {
		if (confirm("Opravdu smazat ?")) {
			rest.delete(address, function(response) {
				globalMessages.push("success", "Sportoviště bylo odebráno");
				getCentres();
			});
		}
	}
	
	/**
	 * Prida hodiny do formulare
	 */
	$scope.centre.addHours = function() {
		if ($scope.centre.form.hours.length < 7)
			$scope.centre.form.hours.push({});
//		console.log($scope.centre.form.hours);
	}
	
	/**
	 * Odebere hodiny z formulare
	 */
	$scope.centre.removeHours = function(index) {
		if ($scope.centre.form.hours != null) {
			$scope.centre.form.hours.splice(index, 1);
//			console.log($scope.centre.form.hours);
		}
	}
	
	/**
	 * Validace sportoviste
	 */
	$scope.centre.applyValidation = function(valid, invalid) {
		validation.input("street", $scope.centre.form.street).required().length(125);
		validation.input("city", $scope.centre.form.city).required().length(125);
		validation.input("buildingNumber", $scope.centre.form.buildingNumber).required().length(125);
		validation.input("phone", $scope.centre.form.phone).required().length(125);
		
		if (validation.isValid()) {
			if (valid != null)
				valid();
		} else {
			errorRender.showErrors(angular.element($scope.centre.formSelector), validation);
			if (invalid != null)
				invalid()
		}
	}
	
});