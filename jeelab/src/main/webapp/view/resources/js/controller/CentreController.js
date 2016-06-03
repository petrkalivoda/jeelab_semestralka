route.controller("CentreController", function($scope, $location, $routeParams, addressStorage, rest, globalMessages, validation, errorRender) {
	
	$scope.centre = {
		formSelector: "#centre-type-form",
		form: {
			hours: [],
		},
		modalId: "centre-modal-form",
		modalSelector: "#centre-modal-form",
		modal: {},
		facility: {
			formSelector: "#facility-form",
			form: {
				hours: [],
			},
			modalId: "facility-modal-form",
			modalSelector: "#facility-modal-form",
			modal: {},
		},
		select: {
			country: [
			         {value: "CZ", name: "Česká republika"},
			         {value: "DE", name: "Německo"},
			         {value: "UK", name: "Velká británie"}
			         ],
			day: [
			      {value: 2, name: "pondělí"},
			      {value: 3, name: "úterý"},
			      {value: 4, name: "středa"},
			      {value: 5, name: "čtvrtek"},
			      {value: 6, name: "pátek"},
			      {value: 7, name: "sobota"},
			      {value: 1, name: "neděle"},
			      ]
		}
	};
	
	/**
	 * Zobrazi detail centra 
	 */
	var showCentre = function(address) {
		rest.get(address, null, function(response) {
			$scope.centre.detail = response.data;
		});
	}
			
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
	
	/**
	 * Vraci seznam typu zarizeni
	 */
	var getTypes = function() {
		addressStorage.get("facilityType", function(address) {
			rest.get(address, null, function(response) {
//				console.log(response.data);	
				$scope.centre.facility.type = response.data;
			});
		});
	}
	
	var centreId = $routeParams.centreId;
//	console.log($routeParams.centreId);
	if (centreId != null) {
		addressStorage.get("centre", function(address) {
			showCentre(address + centreId);
		});
	} else {
		getCentres();
	}
	
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
	 * Zavola se pri otevreni modalniho okna za ucelem vytvoreni noveho zarizeni
	 */
	$scope.centre.openFacilityAddModal = function(address) {
		errorRender.clear(angular.element($scope.centre.facility.formSelector));
		getTypes();
		$scope.centre.facility.form = {
			hours: []
		};
		$scope.centre.facility.modal = {
			btn: "Přidat",
			submit: function() {
				errorRender.clear(angular.element($scope.centre.facility.formSelector));
				$scope.centre.addFacility(address);
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
	 * Zavola se pri otevreni modalniho okna za ucelem upraveni zarizeni
	 */
	$scope.centre.openFacilityUpdateModal = function(index, centre) {
		errorRender.clear(angular.element($scope.centre.facility.formSelector));
		getTypes();
		$scope.centre.facility.form = {
			type: centre.type.id,
			hours: []
		};
		angular.forEach(centre.hours, function(h) {
			$scope.centre.facility.form.hours.push({
				day: h.day,
				open: h.open,
				close: h.close
			});	
		});
//			console.log(response.data);
		
		$scope.centre.facility.modal = {
			btn: "Upravit",
			submit: function() {
				errorRender.clear(angular.element($scope.centre.facility.formSelector));
				$scope.centre.updateFacility(index, centre.url);
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
	 * Zavre modalni okno
	 */
	$scope.centre.closeFacilityModal = function() {
		angular.element($scope.centre.facility.modalSelector).modal("hide");
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
	 * Prida zarizeni
	 */
	$scope.centre.addFacility = function(address) {
		$scope.centre.facility.applyValidation(function() {
			console.log($scope.centre.facility.form);
			rest.post(address, $scope.centre.facility.form, function(response) {
				$scope.centre.closeFacilityModal();
				$scope.centre.detail.facilities.push(response.data);
				globalMessages.push("success", "Zařízení bylo přidáno");
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
	
	var getTypeName = function(id) {
		if($scope.centre.facility.type == null) return null;
		var name = null;
		angular.forEach($scope.centre.facility.type.list, function(item) {
			if (item.id == id) {
				name = item.name;
				return ;
			}
		});
		return name
	}
	
	/**
	 * Upravi sportoviste
	 */
	$scope.centre.updateFacility = function(index, address) {
		$scope.centre.facility.applyValidation(function() {
			rest.put(address, $scope.centre.facility.form, function(response) {
				$scope.centre.closeFacilityModal();
				console.log(index);
				console.log($scope.centre.detail.facilities);
				$scope.centre.detail.facilities[index].hours = $scope.centre.facility.form.hours; 
				$scope.centre.detail.facilities[index].type.name = getTypeName($scope.centre.facility.form.type);
				$scope.centre.detail.facilities[index].type.id = $scope.centre.facility.form.type;
				globalMessages.push("success", "Zařízení bylo upraveno");
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
	 * Smazat sportoviste
	 */
	$scope.centre.deleteFacility = function(index, address) {
		if (confirm("Opravdu smazat ?")) {
			rest.delete(address, function(response) {
				globalMessages.push("success", "Zařízení bylo odebráno");
				$scope.centre.detail.facilities.splice(index, 1);
			});
		}
	}
	
	/**
	 * Prida hodiny do formulare sportoviste
	 */
	$scope.centre.addHours = function() {
		if ($scope.centre.form.hours.length < 7)
			$scope.centre.form.hours.push({});
//		console.log($scope.centre.form.hours);
	}
	 
	/**
	 * Prida hodiny do formulare zarizeni
	 */
	$scope.centre.facility.addHours = function() {
		if ($scope.centre.facility.form.hours.length < 7)
			$scope.centre.facility.form.hours.push({});
//		console.log($scope.centre.facility.form.hours);
	}
	
	/**
	 * Odebere hodiny z formulare sportoviste
	 */
	$scope.centre.removeHours = function(index) {
		if ($scope.centre.form.hours != null) {
			$scope.centre.form.hours.splice(index, 1);
//			console.log($scope.centre.form.hours);
		}
	}
	
	/**
	 * Odebere hodiny z formulare zarizeni
	 */
	$scope.centre.facility.removeHours = function(index) {
		if ($scope.centre.facility.form.hours != null) {
			$scope.centre.facility.form.hours.splice(index, 1);
//			console.log($scope.centre.facility.form.hours);
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

	/**
	 * Validace sportoviste
	 */
	$scope.centre.facility.applyValidation = function(valid, invalid) {
		validation.input("type", $scope.centre.facility.form.type).notValue(null);
		
		if (validation.isValid()) {
			if (valid != null)
				valid();
		} else {
			errorRender.showErrors(angular.element($scope.centre.facility.formSelector), validation);
			if (invalid != null)
				invalid()
		}
	}
	
});