angular.module('MainApp')
.controller('HeaderController',['UserService','$location',function(UserService,$location){
  var self=this;
  self.userservice=UserService;
  self.isadmin=function(){
    if(UserService.getuserstatus()==2)
    {
      return true;
    }
    else {
      return false;
    }
  };
  self.book=function(){
    $location.path('/');
  };
  self.cancel=function(){
    if(UserService.getuserstatus()!=0)
    {
      $location.path('/bookinghistory');
    }
    else{
      console.log('User not logged in');
      UserService.turnonoverlay();
    }
  };
  self.history=function(){
    if(UserService.getuserstatus()!=0)
    {
      $location.path('/bookinghistory');
    }
    else{
      console.log('User not logged in');
      UserService.turnonoverlay();
    }
  };
  self.route=function(){
    if(UserService.getuserstatus()==2)
    {
      $location.path('/route');
    }
    else{
      console.log('Admin not logged in');
      UserService.turnonoverlay();
    }
  };
  self.bus=function(){
    if(UserService.getuserstatus()==2)
    {
      $location.path('/bus');
    }
    else{
      console.log('Admin not logged in');
      UserService.turnonoverlay();
    }
  };
  self.bustype=function(){
    if(UserService.getuserstatus()==2)
    {
      $location.path('/bustype');
    }
    else{
      console.log('Admin not logged in');
      UserService.turnonoverlay();
    }
  };
  self.schedule=function(){
    if(UserService.getuserstatus()==2)
    {
      $location.path('/schedule');
    }
    else{
      console.log('Admin not logged in');
      UserService.turnonoverlay();
    }
  };
}]);
