angular.module('MainApp',['ngRoute'])
.config(function($routeProvider,$locationProvider){
  $locationProvider.hashPrefix('');
  $routeProvider.when('/',{
    templateUrl:'static/views/search.html',
    controller:'SearchController as srctrl',
    resolve:{
      auth:['BusTypeService',function(BusTypeService){
        return BusTypeService.initlist();
      }],
      auth2:['InitService',function(InitService){
        return InitService.init();
      }]
    }
  })
  .when('/bookinghistory',{
    templateUrl:'static/views/history.html',
    controller:'BookedController as bkctrl',
    resolve:{
      auth: ['$location', 'UserService',
        function($location, UserService) {
          if(UserService.getuserstatus()==0)
          {
            $location.path('/');
          }
          else {
            return true;
          }
      }]
    }
  })
  .when('/bus',{
    templateUrl:'static/views/bus.html',
    controller:'BusController as busctrl',
    resolve:{
      auth: ['$location', 'UserService',
        function($location, UserService) {
          if(UserService.getuserstatus()==0)
          {
            $location.path('/');
          }
          else {
            return true;
          }
      }]
    }
  })
  .when('/route',{
    templateUrl:'static/views/route.html',
    controller:'RouteController as rtctrl',
    resolve:{
      auth: ['$location', 'UserService',
        function($location, UserService) {
          if(UserService.getuserstatus()==0)
          {
            $location.path('/');
          }
          else {
            return true;
          }
      }]
    }
  })
  .when('/bustype',{
    templateUrl:'static/views/bustype.html',
    controller:'BusTypeController as btctrl',
    resolve:{
      auth: ['$location', 'UserService',
        function($location, UserService) {
          if(UserService.getuserstatus()==0)
          {
            $location.path('/');
          }
          else {
            return true;
          }
      }]
    }
  })
  .when('/schedule',{
    templateUrl:'static/views/schedule.html',
    controller:'ScheduleController as shctrl',
    resolve:{
      auth: ['$location', 'UserService',
        function($location, UserService) {
          if(UserService.getuserstatus()==0)
          {
            $location.path('/');
          }
          else {
            return true;
          }
      }]
    }
  });
  $routeProvider.otherwise({
    redirectTo:'/'
  });
});
