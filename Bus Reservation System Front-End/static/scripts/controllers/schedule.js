angular.module('MainApp')
.controller('ScheduleController',['BusTypeService','RouteService','BusService','InitService','$http',function(BusTypeService,RouteService,BusService,InitService,$http){
  var self=this;
  var api='http://localhost:8080/BusReservationSystem/api/';
  self.days=['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
  self.bustypelist=[];
  self.buslist=[];
  self.fromlist=[];
  self.tolist=[];
  var normalize=function(a){
    if(a<10){
      a='0'+a;
    }
    a=a+'';
    return a;
  };
  RouteService.getunique().then(function(resp){
    self.fromlist=resp.data;
    self.tolist=resp.data;
    console.log('Myre:'+self.fromlist);
  });
  self.changetolist=function(){
    RouteService.getto(self.query.from).then(function(resp){
      self.tolist=resp.data;
    });
  };
  BusService.getall().then(function(resp){
    self.buslist=resp.data;
    for(var i=0;i<self.buslist.length;i++){
      self.buslist[i].dispname=self.buslist[i].busNum+' - '+self.buslist[i].busName;
    }
  });
  self.bustypelist=BusTypeService.getall();
  self.action=false;
  self.trigger=function(){
    self.schedule={};
    self.query={};
    if(self.action){
      self.assign();
    }
    else{
      self.view();
    }
  };
  self.view=function(){
    $http.get(api+'controllers/schedule/end/'+self.bussel.busID).then(function(response){
      self.schedule=response.data;
      return RouteService.getid(self.schedule.routeID)
    }).then(function(response){
      self.schedule.route=response.data;
      console.log('from'+response.data.fromLocation+'  to'+response.data.toLocation);
    });
  };
  self.assign=function(){
    self.schedule={};
    self.schedule.route={};
    self.schedule.depTimings=[[],[],[],[],[],[],[]];
  };
  self.calculate=function(){
    if(self.query.from&&self.query.to){
      self.schedule.route={};
      self.schedule.route.fromLocation=self.query.from;
      self.schedule.route.toLocation=self.query.to;
      RouteService.get(self.query.from,self.query.to).then(function(response){
        //self.schedule.route={};
        self.schedule.route.routeID=response.data;
        self.schedule.route.fromLocation=self.query.from;
        self.schedule.route.toLocation=self.query.to;
        var calcdata={};
        calcdata.busID=self.bussel.busID;
        calcdata.routeID=response.data;
        return $http.post(api+'controllers/schedule/calculate',calcdata);
      }).then(function(resp){
        self.schedule.duration=resp.data.duration;
        self.schedule.fare=resp.data.fare;
      });
    }
  };
  self.updatearrtime=function(){
    var dur=self.schedule.duration;
    var hh=self.query.depTime.getHours();
    var min=self.query.depTime.getMinutes();
    var m=normalize(parseInt((min+dur)%60));
    var h=normalize(parseInt((hh+(min+dur)/60)%24));
    self.query.arrTime=h+':'+m;
  };
  self.addtime=function(){
    var i=0;
    for(;i<self.days.length;i++){
      if(self.days[i]==self.query.day){
        break;
      }
    }
    var hh=normalize(self.query.depTime.getHours());
    var mm=normalize(self.query.depTime.getMinutes());
    var timestr=hh+mm;
    var j=0;
    for(;j<self.schedule.depTimings[i].length;j++){
      if(timestr==self.schedule.depTimings[i][j]){
        break;
      }
    }
    if(j!=self.schedule.depTimings[i].length){
      console.log('Duplicate entry!')
    }
    else{
      self.schedule.depTimings[i].push(timestr);
    }
  };
  self.add=function(){
    self.shtemp={};
    self.shtemp.busID=self.bussel.busID;
    self.shtemp.routeID=self.schedule.route.routeID;
    self.shtemp.duration=self.schedule.duration;
    self.shtemp.fare=self.schedule.fare;
    self.shtemp.depTimings=self.schedule.depTimings;
    $http.post(api+'controllers/schedule/end',self.shtemp).then(function(response){
      console.log('Schedule added!');
      self.action=false;
      self.view();
      InitService.forceinit();
    }).then(function(response){
      console.log('Seat Tracker updated!');
    },function(error){
      console.log('Seat Tracker failed to update!');
    });
  };
}]);
