route.controller("ReservationController", function($scope, addressStorage, rest, globalMessages) {
	
	$scope.reservation = {};

	var getReservations = function() {
		addressStorage.get("reservation", function(address) {
			rest.get(address, null, function(response) {
				$scope.reservation.list = response.data;
			});
		});
	}
	
	getReservations();
	
	$scope.reservation.deleteReservation = function(address) {
		if(confirm("Opravdu smazat ?")) {
			rest.delete(address, function(response) {
				getReservations();
				globalMessages.push("success", "Rezervace byla smazána");
			});
		}
	}
	
});