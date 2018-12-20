angular.module('MainApp')
.controller('BusController',['BusTypeService','$http',function(BusTypeService,$http){
  var self=this;
  var api='http://localhost:8080/BusReservationSystem/api/';
  self.list=[];
  self.bustypelist=BusTypeService.getall();
  var init=function(){
    self.bus={};
    $http.get(api+'controllers/bus').then(function(response){
      self.list=response.data;
      for(var i=0;i<self.list.length;i++){
        for(var j=0;j<self.bustypelist.length;j++){
          if(self.list[i].bus.busType==self.bustypelist[j].busTypeID){
            self.list[i].busTypeName=self.bustypelist[j].busTypeName;
            break;
          }
        }
      }
    });
  };
  init();
  self.add=function(){
    self.bus.busType=self.bustype.busTypeID;
    $http.post(api+'controllers/bus',self.bus).then(function(response){
      console.log('Bus added!');
      init();
    },function(error){
      console.log('Error in adding Bus');
    });
  };
  self.remove=function(idx){
    $http.delete(api+'controllers/bus/'+self.list[idx].bus.busID).then(function(response){
      console.log('Bus Removed');
      init();
    },function(error){
      console.log('Error in removing Bus');
    });
  };
}]);
