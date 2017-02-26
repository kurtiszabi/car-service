angular.module('mainApp', [])
  .controller('CarServiceController', function($scope, $http) {
	  $scope.pageTitle = "Car Service - Szabolcs Kürti";	  
	  var service = this;
	  service.cars = [];
	  service.selected = null;
	  service.loadCars = function() {
		  console.log('Loading cars available');
		  $http({
			  method: 'GET',
			  url: '/cars'
			}).then(function successCallback(response) {
				service.cars = response.data;				
			}, function errorCallback(response) {
				  alert(response.statusText);
			});		  		  
	  }
	  service.loadCars();
	  service.load = function(id) {
		  console.log('Loading car id='+id);
		  $http({
			  method: 'GET',
			  url: '/cars/'+id
			}).then(function successCallback(response) {
				service.selected = response.data;				
			}, function errorCallback(response) {
				  alert(response.statusText);
			});
	  }
	  service.makeReservation = function() {
		  console.log('Making a reservation');
	  }
  });