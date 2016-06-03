var url = window.location.href;
var arr = url.split("/");
var host = arr[0] + "//" + arr[2]
var wsHost = host.replace("http://", "");
wsHost = wsHost.replace("https://", "");

var entryPoint = host + "/jeelab/rest";
var wsEntryPoint = "ws://"  + wsHost +  "/jeelab/websockets/reservation";

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
	}).when("/my-reservation", {
		templateUrl: 'view/pages/content/my-reservation.html',
		controller: 'ReservationController'
	}).otherwise({
		templateUrl: 'view/pages/content/home.html',
		controller: 'FacilityController'
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
				return "neděle";
			case 2:
				return "pondělí";
			case 3:
				return "úterý";
			case 4:
				return "středa";
			case 5:
				return "čtvrtek";
			case 6:
				return "pátek";
			case 7:
				return "sobota";
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

route.factory("rest", function($http, $rootScope, globalMessages) {
	return new Model($http, $rootScope, globalMessages);
});

route.factory("base64", function() {
	return new Base64();
});

route.factory("websockets", function($timeout) {
	return new WebSockets(wsEntryPoint, $timeout);
});

