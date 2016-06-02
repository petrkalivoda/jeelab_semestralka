route.controller("FacilityController", function($scope, $rootScope, addressStorage, rest, validation, errorRender, globalMessages, websockets) {
	
	$scope.facility = {
		datePicker: {},
		formSelector: "#facility-form",
		modalId: "facility-modal-form",
		modalSelector: "#facility-modal-form",
		modal: {},
	};
	
	$scope.reservation = {
		form: {}
	};
		
	var getFacilities = function() {
		addressStorage.get("facility", function(address) {
			rest.get(address, null, function(response) {
				$scope.facility.list = response.data;
			});
		});
	}
	
	var getFacilityReservation = function(address) {
		rest.get(address, null, function(response) {
			$scope.facility.reservations = response.data;
		});
	}
	
	getFacilities();
	
	/**
	 * Zavola se pri otevreni modalniho okna za ucelem vytvoreni rezervace
	 */
	$scope.facility.openReservationModal = function(facility) {
		getFacilityReservation(facility.reservationsUrl);
		errorRender.clear(angular.element($scope.facility.formSelector));
		$scope.reservation.form = {
			user: $rootScope.user.id,
			centreFacility: facility.id
		};
		$scope.facility.selected = {
			user: $rootScope.user.firstname + " " + $rootScope.user.firstname + " (" + $rootScope.user.email + ")",
			facility: facility.type.name
		};
		$scope.reservation.error = null;
		$scope.facility.modal = {
			btn: "Přidat",
			submit: function() {
				errorRender.clear(angular.element($scope.facility.formSelector));
				$scope.facility.createReservation();
			}
		};
	}
	
	/**
	 * Zavre modalni okno
	 */
	$scope.facility.closeFacilityModal = function() {
		angular.element($scope.facility.modalSelector).modal("hide");
	}
	
	/**
	 * Vytvori registraci
	 */
	$scope.facility.createReservation = function() {
		$scope.facility.applyValidation(function() {
//			console.log($scope.reservation.form);
			addressStorage.get("reservation", function(address) {
				rest.post(address, $scope.reservation.form, function(response) { // 2**
					websockets.notify(response.data.url);
					$scope.facility.closeFacilityModal();
					globalMessages.push("success", "Rezervace byla vytvorena");
				}, function(response) { // 3**, 4**, 5**
					if (response.status == 409)
						$scope.reservation.error = "Nelze rezervovat zadaný termín";
				});
			});
		});
	}
	
	/**
	 * otevre date picker
	 */
	$scope.facility.datePicker = function(name) {
		$scope.facility.datePicker[name] = true;
	}
	
	/**
	 * Validace typu
	 */
	$scope.facility.applyValidation = function(valid, invalid) {
		validation.input("date", $scope.reservation.form.date).required();
		validation.input("from", $scope.reservation.form.from).required();
		validation.input("to", $scope.reservation.form.to).required();
		
		if (validation.isValid()) {
			if (valid != null)
				valid();
		} else {
			errorRender.showErrors(angular.element($scope.facility.formSelector), validation);
			if (invalid != null)
				invalid()
		}
	}
	
});