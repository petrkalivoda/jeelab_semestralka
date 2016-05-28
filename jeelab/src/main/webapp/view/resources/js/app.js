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
	}).when("/registration", {
		templateUrl: 'view/pages/content/registration.html',
		controller: 'RegistrationController'
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

