route.controller("ReservationController", function($scope, addressStorage, rest, globalMessages, websockets) {
	
	$scope.reservation = {};
		
	var getReservations = function() {
		addressStorage.get("reservation", function(address) {
			rest.get(address, null, function(response) {
				$scope.reservation.list = response.data;
			});
		});
	}
	
	websockets.listen(function(address) {
		console.log("Received: " + address);
		globalMessages.push("info", "Byla vytvořena nová rezervace");
		rest.get(address, null, function(response) {
			$scope.reservation.list.count += 1;
			console.log(response.data);
			console.log($scope.reservation.list.list);
			$scope.reservation.list.list.push(response.data);
		});
	});		
	
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