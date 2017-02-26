angular.module('mainApp', [])
  .controller('CarServiceController', function($scope, $http) {
	  $scope.pageTitle = "Car Service - Szabolcs Kürti";	  
	  $scope.years = range(2017, 2019);
	  $scope.months = range(1, 13);
	  $scope.days = range(1, 32);
	  $scope.hours = range(0, 25);
	  $scope.minutes = range(0, 60);
	  
	  var service = this;
	  service.cars = [];
	  service.selected = null;
	  service.reservation = {
			  username: "Guest 1",
			  fromYear: 2017,
			  fromMonth: 3,
			  fromDay: 8,
			  fromHour: 12,
			  fromMinute: 0,
			  toYear: 2017,
			  toMonth: 3,
			  toDay: 15,
			  toHour: 12,
			  toMinute: 0,
			  country: "SK"
	  };
	  
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
		  var res = service.createReservationObj();		  
		  $http({
			  method: 'POST',
			  url: '/cars/'+res.car.id+'/reservations',
			  data: JSON.stringify(res),
			  headers: {
		            'Content-Type': 'application/json;'
		      }
			}).then(function successCallback(response) {
				alert('Your reservation has been book successfully');				
			}, function errorCallback(response) {
				alert(response.data.message);
			});
	  }
	  service.createReservationObj = function() {
		  var o = {
	        car: service.selected,
	        user: service.reservation.username,
	        country: service.reservation.country,
	        from: [service.reservation.fromYear, service.reservation.fromMonth-1, service.reservation.fromDay, service.reservation.fromHour, service.reservation.fromMinute],
	        to: [service.reservation.toYear, service.reservation.toMonth-1, service.reservation.toDay, service.reservation.toHour, service.reservation.toMinute]				  
		  }
		  return o;
	  }	  
  });

function range(start, end) {
  var range = [];
  while(start != end) {
	range.push(start++);
  }
  return range;
}