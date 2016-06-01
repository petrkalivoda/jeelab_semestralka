route.controller("AuthController", function($scope, $rootScope, $http, $cookies, $window, rest, base64, addressStorage, globalMessages) {
	
	$scope.auth = {
		data: {}
	};
	
	$rootScope.user = $cookies.getObject("user");
	$rootScope.logged = $rootScope.user != null;
	if ($rootScope.logged) {
		$http.defaults.headers.common['Authorization'] = $rootScope.user.encoded;
	} else {
		$http.defaults.headers.common['Authorization'] = "Basic";
	}
	
	$rootScope.hasRole = function(role) {
		if ($rootScope.user == null)
			return false;
		var has = false;
		angular.forEach($rootScope.user.roles, function(userRole) {
			if (role == userRole) {
				has = true;
				return;
			}
		});
		return has;
	}
	
	$scope.auth.login = function() {
		if ($scope.auth.data.email == "" || $scope.auth.data.password == "") return;
		var encoded = "Basic " + base64.encode($scope.auth.data.email + ":" + $scope.auth.data.password);
		addressStorage.get("login", function(address) {
			var filters = {
				headers: {
					'Authorization': encoded,
					'X-Requested-With': 'XMLHttpRequest'
				}
			};
			rest.get(address, filters, function(response) {
				$rootScope.user = response.data;
				$rootScope.user.encoded = encoded;
				$rootScope.logged = $rootScope.user != null;
				$cookies.putObject("user", $rootScope.user);
				$http.defaults.headers.common['Authorization'] = encoded;
				globalMessages.push("success", "Přihlášení bylo úspěšné");
				$window.location.hash = "/";
			});
		});
	}
	
	$scope.auth.logout = function() {
		$http.defaults.headers.common['Authorization'] = "Basic";
		$cookies.remove("user");
		$rootScope.user = null;
		$rootScope.logged = false;
		$window.location.hash = "/";
		addressStorage.clear();
		globalMessages.push("success", "Odhlášení bylo úspěšné");
	}
	
});