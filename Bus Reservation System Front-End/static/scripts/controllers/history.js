angular.module('MainApp')
.controller('BookedController',['UserService','BusService','RouteService','$http',function(UserService,BusService,RouteService,$http){
  var self=this;
  var api='http://localhost:8080/BusReservationSystem/api/';
  self.routeservice=RouteService;
  var user=UserService.getuser();
  self.list=[];
  self.open=-1;
  var normalize=function(a){
    if(a<10){
      a='0'+a;
    }
    a=a+'';
    return a;
  };
  var init=function(){
    $http.get(api+'controllers/history/'+user.userID).then(function(response){
      self.list=response.data;
      for(var i=0;i<self.list.length;i++)
      {
        var today=new Date();
        self.list[i].cancan=false;
        var tdate=new Date(self.list[i].ticket.depDate.substring(4,8),self.list[i].ticket.depDate.substring(2,4),self.list[i].ticket.depDate.substring(0,2));
        if(today<tdate){
          self.list[i].cancan=true;
        }
        self.list[i].seatnums='';
        for(var j=0;j<self.list[i].ticket.seatNums.length;j++)
        {
          self.list[i].seatnums=self.list[i].seatnums+' '+self.list[i].ticket.seatNums[j];
        }
        var dur=self.list[i].duration;
        var dep=self.list[i].ticket.depTime;
        var hh=parseInt(dep.substring(0,2));
        var min=parseInt(dep.substring(3,5));
        var m=normalize(parseInt((min+dur)%60));
        var h=normalize(parseInt((hh+(min+dur)/60)%24));
        self.list[i].arrTime=h+':'+m;
        self.list[i].duration=normalize(parseInt(dur/60))+':'+normalize(parseInt(dur%60));
        self.list[i].ticket.depTime=dep.substring(0,2)+':'+dep.substring(3,5);
        self.list[i].ticket.depDate=self.list[i].ticket.depDate.substring(0,2)+'-'+self.list[i].ticket.depDate.substring(2,4)+'-'+self.list[i].ticket.depDate.substring(6,8);
      }
    });
  };
  init();
  self.cancel=function(idx){
    $http.delete(api+'controllers/history/'+self.list[idx].ticket.pnr).then(function(resp){
      console.log('Ticket cancelled');
      init();
    });
  };
}]);
