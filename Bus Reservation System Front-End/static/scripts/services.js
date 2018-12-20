angular.module('MainApp')
.factory('UserService',['$http',function($http){
  var userstatus=0;
  var api='http://localhost:8080/BusReservationSystem/api/';
  var user={userID:-1,userName:'user123',name:'user',password:''};
  var service={
    overlaystatus:false,
    turnonoverlay:function(){
      service.overlaystatus=true;
    },
    turnoffoverlay:function(){
      service.overlaystatus=false;
    },
    getuserstatus:function(){
      return userstatus;
    },
    getuser:function(){
      return user;
    },
    login:function(uservar)
    {
      return $http.post(api+'controllers/user/login',uservar)
      .then(function(response){
        if(response.data.userName=='admin')
        {
          userstatus=2;
        }
        else {
          userstatus=1;
        }
        user=response.data;
        service.overlaystatus=false;
        console.log('Logged in with:'+response.data);
      },function(error){
        console.log('Invalid Username or password');
      });
    },
    logout:function()
    {
      userstatus=0;
      user={userID:-1,userName:'user123',name:'user',password:''};
    },
    signup:function(uservar){
      return $http.post(api+'controllers/user/signup',uservar)
      .then(function(response){
        userstatus=1;
        user=response.data;
        service.overlaystatus=false;
        console.log('Signup success with:'+response.data);
      },function(error){
        console.log('Signup failed');
      });
    }
  };
  return service;
}])
.factory('BusTypeService',['$http',function($http){
  var api='http://localhost:8080/BusReservationSystem/api/';
  var busTypeList=[];
  /*initlist=function()
  {
    return $http.get(api+'services/bustype').then(function(response){
      busTypeList=response.data;
      console.log('sdfsdfdf');
    },function(error){
      console.log("Error fetching all bus types in service init");
    });
  };
  initlist();*/
  return {
    getall:function(){
      return busTypeList;
    },
    get:function(id){
      return $http.get(api+'services/bustype/'+id);
    },
    initlist:function(){
      return $http.get(api+'services/bustype').then(function(response){
        busTypeList=response.data;
        console.log('sdfsdfdf');
        return response;
      },function(error){
        console.log("Error fetching all bus types in service init");
        return error;
      });
    }
  };
}])
.factory('RouteService',['$http',function($http){
  var api='http://localhost:8080/BusReservationSystem/api/';
  return {
    getunique:function(){
      return $http.get(api+'services/route/getunq');
    },
    getto:function(from){
      return $http.get(api+'services/route/getunq/'+from);
    },
    get:function(from,to){
      return $http.get(api+'services/route/getunq/'+from+'/'+to);
    },
    getid:function(routeID){
      return $http.get(api+'services/route/end/'+routeID);
    }
  };
}])
.factory('BusService',['$http',function($http){
  var api='http://localhost:8080/BusReservationSystem/api/';
  return {
    get:function(busID){
      return $http.get(api+'services/bus/'+busID);
    },
    getall:function(){
      return $http.get(api+'services/bus');
    }
  };
}])
.factory('InitService',['$http',function($http){
  var api='http://localhost:8080/BusReservationSystem/api/';
  return {
    init:function(){
      return $http.get(api+'services/init').then(function(response){
        console.log('Successfully initialized Seat Tracker');
        return response;
      },function(error){
        console.log("Error initializing Seat Tracker");
        return error;
      });
    },
    forceinit:function(){
      return $http.get(api+'services/init/force');
    }
  };
}]);
