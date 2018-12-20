angular.module('MainApp')
.controller('RouteController',['$http',function($http){
  var self=this;
  var api='http://localhost:8080/BusReservationSystem/api/';
  self.list=[];
  var init=function(){
    self.route={};
    $http.get(api+'services/route/end').then(function(response){
      self.list=response.data;
    });
  };
  init();
  self.add=function(){
    $http.post(api+'services/route/end',self.route).then(function(response){
      console.log('Route added!');
      init();
    },function(error){
      console.log('Error in adding route');
    });
  };
  self.remove=function(idx){
    $http.delete(api+'services/route/end/'+self.list[idx].routeID).then(function(response){
      console.log('Route Removed');
      init();
    },function(error){
      console.log('Error in removing route');
    });
  };
}]);
