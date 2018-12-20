angular.module('MainApp')
.controller('LoginController',['UserService',function(UserService){
  var self=this;
  self.userservice=UserService;
  self.tab='login';
  self.open=function(val){
    self.tab=val;
  };
  self.login=function(){
    UserService.login(self.user);
  };
  self.signup=function(){
    UserService.signup(self.user);
  };
}]);
