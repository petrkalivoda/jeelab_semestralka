route.controller("FacilityTypeController", function($scope, addressStorage, rest, globalMessages, validation, errorRender) {
	
	$scope.facility = {
		type: {
			formSelector: "#facility-type-form",
			form: {},
			modalId: "type-modal-form",
			modalSelector: "#type-modal-form",
			modal: {}
		}
	};	
	
	/**
	 * Vraci seznam typu zarizeni
	 */
	var getTypes = function() {
		addressStorage.get("facilityType", function(address) {
			rest.get(address, null, function(response) {
//				console.log(response.data);	
				$scope.facility.type.list = response.data;
			});
		});
	}
	getTypes();
	
	/**
	 * Zavola se pri otevreni modalniho okna za ucelem pridani typu
	 */
	$scope.facility.openFacilityTypeAddModal = function() {
		errorRender.clear(angular.element($scope.facility.type.formSelector));
		$scope.facility.type.form = {};
		$scope.facility.type.modal = {
			btn: "Přidat",
			submit: function() {
				errorRender.clear(angular.element($scope.facility.type.formSelector));
				$scope.facility.addType();
			}
		};
	}
	
	/**
	 * Zavola se pri otevreni modalniho okna za ucelem upraveni typu
	 */
	$scope.facility.openFacilityTypeUpdateModal = function(type) {
		errorRender.clear(angular.element($scope.facility.type.formSelector));
		$scope.facility.type.form = {
			name: type.name
		};
		$scope.facility.type.modal = {
			btn: "Upravit",
			submit: function() {
				errorRender.clear(angular.element($scope.facility.type.formSelector));
				$scope.facility.updateType(type.url);
			}
		};
	}
	
	/**
	 * Zavre modalni okno
	 */
	$scope.facility.type.closeFacilityTypeModal = function(facility) {
		angular.element($scope.facility.type.modalSelector).modal("hide");
	}

	/**
	 * Prida typ zarizeni
	 */
	$scope.facility.addType = function() {
		addressStorage.get("facilityType", function(address) {
			$scope.facility.type.applyValidation(function() {
				rest.post(address, $scope.facility.type.form, function(response) {
					$scope.facility.type.closeFacilityTypeModal();
					globalMessages.push("success", "Typ zařízení byl přidán");
					getTypes();
				});
			});
		});
	}
	
	/**
	 * Upravi typ zarizeni
	 */
	$scope.facility.updateType = function(address) {
		$scope.facility.type.applyValidation(function() {
			rest.put(address, $scope.facility.type.form, function(response) {
				$scope.facility.type.closeFacilityTypeModal();
				globalMessages.push("success", "Typ zařízení byl upraven");
				getTypes();
			});
		});
	}
	
	/**
	 * Vola rest pro mazani typu, chyba 400 znamena, ze typ je prirazen k zarizeni anelze smazat.
	 */
	$scope.facility.deleteType = function(address) {
		if (confirm("Opravdu smazat ?")) {
			rest.delete(address, function(response) {
				globalMessages.push("success", "Typ zařízení byl smazán");
				getTypes();
			});
		}
	}
	
	/**
	 * Validace typu
	 */
	$scope.facility.type.applyValidation = function(valid, invalid) {
		validation.input("name", $scope.facility.type.form.name).required().length(125);
		
		if (validation.isValid()) {
			if (valid != null)
				valid();
		} else {
			errorRender.showErrors(angular.element($scope.facility.type.formSelector), validation);
			if (invalid != null)
				invalid()
		}
	}
	
});