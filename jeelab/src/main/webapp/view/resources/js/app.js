var entryPoint = "http://localhost:8080/jeelab/rest";

// seznam modulu
var route = angular.module("route", ['ngRoute']);
var uiBootstrap = angular.module("uiBootstrap", ['ui.bootstrap']);
var cookie = angular.module("cookie", ['ngCookies']);

// nastaveni
route.config(["$routeProvider", function($routeProvider) {	
	
	$routeProvider
	.when("/", {
		templateUrl: 'view/pages/content/home.html',
		controller: 'FacilityController'
	}).when("/centre", {
		templateUrl: 'view/pages/content/centre.html',
		controller: 'CentreController'
	}).when("/centre/:centreId", {
		templateUrl: 'view/pages/content/centre-detail.html',
		controller: 'CentreController'
	}).when("/registration", {
		templateUrl: 'view/pages/content/registration.html',
		controller: 'RegistrationController'
	}).when("/login", {
		templateUrl: 'view/pages/content/login.html',
		controller: 'AuthController'
	}).when("/facility/type", {
		templateUrl: 'view/pages/content/facility-type.html',
		controller: 'FacilityTypeController'
	}).when("/reservation", {
		templateUrl: 'view/pages/content/reservation.html',
		controller: 'ReservationController'
	}).otherwise({
		templateUrl: 'view/pages/content/home.html',
	});
	
}]);

var app = angular.module("app", ["route", "cookie", "uiBootstrap"]);

route.directive('hideAfterTimeout', function($timeout, globalMessages) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var time = attrs.hideAfterTimeout;
			$timeout(function() {
				var id = element.attr("data-id");
				element.remove();
				globalMessages.pop(id);
			}, time);
		}
	}
});

route.filter('day', function ($filter) {
	return function (input) {
		switch (input) {
			case 1:
				return "pondělí";
			case 2:
				return "úterý";
			case 3:
				return "středa";
			case 4:
				return "čtvrtek";
			case 5:
				return "pátek";
			case 6:
				return "sobota";
			case 7:
				return "neděle";
			default:
				return input;
		}
	};
});

// cdi
route.factory("errorRender", function($compile) {
	return new ErrorRender($compile);
});

route.factory("globalMessages", function($rootScope) {
	return new GlobalMessages($rootScope);
});

route.factory("validation", function() {
	return new Validation();
});

route.factory("addressStorage", function($cookies, $http, globalMessages) {
	return new AddressStorage(entryPoint, $cookies, $http, globalMessages);
});

route.factory("rest", function($http, globalMessages) {
	return new Model($http, globalMessages);
});

route.factory("base64", function() {
	return new Base64();
});

