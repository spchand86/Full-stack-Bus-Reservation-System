angular.module('MainApp')
.controller('BusTypeController',['$http',function($http){
  var self=this;
  var api='http://localhost:8080/BusReservationSystem/api/';
  self.list=[];
  var init=function(){
    self.bustype={};
    $http.get(api+'controllers/bustype').then(function(response){
      self.list=response.data;
      for(var i=0;i<self.list.length;i++){
        if(self.list[i].ac==true){
          self.list[i].acstr='YES';
        }
        else{
          self.list[i].acstr='NO';
        }
      }
    });
  };
  init();
  self.add=function(){
    $http.post(api+'controllers/bustype',self.bustype).then(function(response){
      console.log('Bus Type added!');
      init();
    },function(error){
      console.log('Error in adding bus type');
    });
  };
  self.remove=function(idx){
    $http.delete(api+'controllers/bustype/'+self.list[idx].busTypeID).then(function(response){
      console.log('Route Removed');
      init();
    },function(error){
      console.log('Error in removing route');
    });
  };
}]);
