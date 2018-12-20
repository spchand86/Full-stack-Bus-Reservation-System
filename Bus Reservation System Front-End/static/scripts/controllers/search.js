angular.module('MainApp')
.controller('SearchController',['UserService','BusTypeService','RouteService','$http','$location','$scope',function(UserService,BusTypeService,RouteService,$http,$location,$scope){
  var self=this;
  var api='http://localhost:8080/BusReservationSystem/api/';
  self.bustypeservice=BusTypeService;
  self.routeservice=RouteService;
  self.showright=false;
  self.numres=0;
  self.bustype='Any';
  self.fromlist=[];
  self.tolist=[];
  self.bustypelist=[];
  self.date=new Date();
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1;
  var yyyy = today.getFullYear();
  if(dd<10) {
    dd = '0'+dd;
  }
  if(mm<10) {
    mm = '0'+mm;
  }
  self.mindate=yyyy+'-'+mm+'-'+dd;
  today.setDate(today+29);
  dd = today.getDate();
  mm = today.getMonth()+1;
  yyyy = today.getFullYear();
  if(dd<10) {
    dd = '0'+dd;
  }
  if(mm<10) {
    mm = '0'+mm;
  }
  console.log('IN');
  self.maxdate=yyyy+'-'+mm+'-'+dd;
  RouteService.getunique().then(function(resp){
    self.fromlist=resp.data;
    self.tolist=resp.data;
    console.log('Myre:'+self.fromlist);
  });
  self.bustypelist=BusTypeService.getall();
  /*.then(function(resp){
    self.bustypelist=resp.data;
    console.log('Poore:'+self.bustypelist[0].busTypeName);
  });*/
  console.log('fromlist:'+self.fromlist);
  self.changetolist=function(){
    RouteService.getto(self.from).then(function(resp){
      self.tolist=resp.data;
    });
  };
  var normalize=function(a){
    if(a<10){
      a='0'+a;
    }
    a=a+'';
    return a;
  };
  self.search=function(){
    self.dd = self.date.getDate();
    self.mm = self.date.getMonth()+1;
    self.yyyy = self.date.getFullYear();
    var dummy;
    self.dd=normalize(self.dd);
    self.mm=normalize(self.mm);
    if(self.bustype=='Any'){
      dummy='';
    }
    else {
      dummy='/'+self.bustype.busTypeID;
    }
    $http.get(api+'controllers/search/get/'+self.from+'/'+self.to+'/'+self.dd+self.mm+self.date.getFullYear()+dummy)
    .then(function(response){
      self.list=response.data;
      for(var i=0;i<self.list.length;i++)
      {
        var dur=self.list[i].duration;
        var dep=self.list[i].depTime;
        var hh=parseInt(dep.substring(0,2));
        var min=parseInt(dep.substring(2,4));
        var m=normalize(parseInt((min+dur)%60));
        var h=normalize(parseInt((hh+(min+dur)/60)%24));
        self.list[i].arrTime=h+':'+m;
        self.list[i].duration=normalize(parseInt(dur/60))+':'+normalize(parseInt(dur%60));
        self.list[i].depTime=dep.substring(0,2)+':'+dep.substring(2,4);
        for(var j=0;j<self.bustypelist.length;j++){
          if(self.list[i].bus.busType==self.bustypelist[j].busTypeID){
            self.list[i].bus.busTypeName=self.bustypelist[j].busTypeName;
            break;
          }
        }
        self.list[i].fare=self.list[i].fare*self.numseats;
      }
      self.showright=true;
      self.numres=self.list.length;
    },function(error){
      console.log('Error searching for tickets');
      self.showright=false;
      self.numres=0;
    });
  };
  self.book=function(idx){
    console.log('ID:'+idx);
    if(UserService.getuserstatus()!=0){
      var ticket={};
      ticket.busID=self.list[idx].bus.busID;
      ticket.depDate=self.dd+''+self.mm+self.yyyy;
      ticket.depTime=self.list[idx].depTime;
      ticket.numSeats=self.numseats;
      ticket.fare=self.list[idx].fare;
      ticket.userID=UserService.getuser().userID;
      RouteService.get(self.from,self.to).then(function(resp){
        ticket.routeID=resp.data;
        $http.post(api+'controllers/search/book',ticket)
        .then(function(response){
          console.log('Ticket booked with PNR:'+response.data.pnr);
          $location.path('/bookinghistory');
        },function(error){
          console.log('Error in booking');
        });
      });
    }
    else {
      UserService.turnonoverlay();
    }
  };
}]);
