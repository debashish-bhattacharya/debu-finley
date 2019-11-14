
/*****************************************************constants***************************************************/


//Color codes for nodes



//layers defined over map for holding nodes and events
var ugsLayer 				= null;
var ugsLayer_day_2 			= null;
var ugsLayer_day_3 			= null;

var trglLayer 				= null;
var trglLayer_day_2 		= null;
var trglLayer_day_3 		= null;

var tmdasLayer 				= null;
var tmdasLayer_day_2 		= null;
var tmdasLayer_day_3 		= null;

var ugsEventLayer 			= null;
var trglEventLayer 			= null;
var tmdasEventLayer 		= null;
var towerLayer 				= null;

var heighightCircleLayer 	= null;

//holds the current inventory add in cms
var inventory 			= {"UGS":"","TRGL":"","TMDAS":""};

//converge units is meter
var tmdasCoverageRadius = 2000;
var trglCoverageRadius 	= 2000;
var ugsCoverage  		= 5;

//Holds the current day of events 0 stantds for current day and so on
var currentEventOffset 	= 0;

var marker_width = 22.5;
var marker_height = 30;

var eventsData = [];
var historyEventWR = null

var alarmMarkers = [];

var UGSCurrentAlarm = 
{
	currentMarker:"",
	currentTimeOut:"",
	previousTimeOut:""
}


var eventsMap = 
{
		"UGS":
		{
			"1":"Detected",
			"2":"Node Up",
			"3":"Node Down",
			"4":"Node Added",
			"5":"Node Removed",
			"7":"Alarm"
		},
		"TRGL":
		{
			"1":"Intiated from HUMMER",
			"2":"Node Up",
			"3":"Node Down",
			"4":"Node Added",
			"5":"Node Removed",
			"6":"Intiated from OXFAM",
			"7":"Alarm"
		},
		"TMDAS":
		{
			"1":"Intiated from FALCON",
			"2":"Node Up",
			"3":"Node Down",
			"4":"Node Added",
			"5":"Node Removed",
			"6":"Intiated from HUMMER",
			"7":"Alarm"
		}
		
}



/*****************************************************constants***************************************************/

/*****************************************************MAP Section************************************************/

function loadLeafMap()
{
	map = new L.Map('map_leaf', {fullscreenControl: true,rotate: true,center: new L.LatLng(28.4867, 77.0753),  zoom: 14, minZoom: 12});
    
	map.zoomControl.setPosition('bottomright');
	
	var googleMaplayer = L.gridLayer.googleMutant({
			type: 'roadmap' // valid values are 'roadmap', 'satellite', 'terrain' and 'hybrid'
		}).addTo(map);

     
	//var openstreatMapLayer = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	  var openstreatMapLayer = L.tileLayer('http://'+mapServerIp+'/hot/{z}/{x}/{y}.png', {
		  //var openstreatMapLayer = L.tileLayer('http://192.168.3.3/hot/{z}/{x}/{y}.png', {
		attribution: 'Imagery © <a href="http://mapbox.com">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(map);
	
	
	  towerMarkerLayer = L.layerGroup().addTo(map);
	  
	  ugsLayer = L.layerGroup().addTo(map);
	  ugsLayer_day_2 = L.layerGroup().addTo(map);
	  ugsLayer_day_3 = L.layerGroup().addTo(map);
	  
	  trglLayer = L.layerGroup().addTo(map);
	  trglLayer_day_2 = L.layerGroup().addTo(map);
	  trglLayer_day_3 = L.layerGroup().addTo(map);
	  
	  tmdasLayer = L.layerGroup().addTo(map);
	  tmdasLayer_day_2 = L.layerGroup().addTo(map);
	  tmdasLayer_day_3 = L.layerGroup().addTo(map);
	  
	  ugsEventLayer = L.layerGroup().addTo(map);
	  tmdasEventLayer = L.layerGroup().addTo(map);
	  trglEventLayer = L.layerGroup().addTo(map);
	  
	  towerLayer= L.layerGroup().addTo(map);
	  
	  heighightCircleLayer = L.layerGroup().addTo(map);
	  
	  var toggleOfflineOnlineMap =  L.Control.extend({

		  options: {
			position: 'bottomright'
		  },

		  onAdd: function (map) 
		  {
			var container = L.DomUtil.create('img');
			container.id="map_type";
			container.title="Offline";
            container.src=images.MAP.offline;  
			container.style.backgroundColor = 'white';
			$(container).addClass("offline");			
			container.style.width = '33px';
			container.style.height = '33px';
			
			container.onclick = function(){
			  console.log($(container));
			  if($(container).hasClass('offline')){
				 map.removeLayer(openstreatMapLayer);
				 container.title="Online";
				 $(container).removeClass("offline");	
				 $(container).addClass("online");	
				 container.src=images.MAP.online;  
			}
			else {
				//map.removeLayer(googleMapLayer);
				map.addLayer(openstreatMapLayer);
				container.title="Offline";
				$(container).addClass("offline");	
				 $(container).removeClass("online");
				 container.src=images.MAP.offline;  
			}
			
			}
			return container;
		  }
		});
	
	
	var compasImg =  L.Control.extend({

		  options: {
			position: 'bottomright'
		  },

		  onAdd: function (map) 
		  {
			var container = L.DomUtil.create('img');
			container.id="map_compass";
			container.title="Up(North)-Down(South)";
			container.src=images.MAP.compass;  
			//container.style.backgroundColor = 'white';
			//$(container).addClass("offline");			
			container.style.width = '33px';
			container.style.height = '33px';
			container.style.transform = "rotate(-42deg)";
			
			return container;
		  }
		});
	
	var currentAngle =  L.Control.extend({

		  options: {
			position: 'bottomleft'
		  },

		  onAdd: function (map) 
		  {
			var container = L.DomUtil.create('div');
			container.id="angle_map";
			container.title="Device current angle";
			container.style.backgroundColor = 'white';
			//$(container).addClass("offline");			
			container.style.width = '45px';
			container.style.height = '35px';
			container.style["font-size"] = '13px';
			container.style["font-weight"] = '900';
			container.style.padding = '7PX';
			
			return container;
		  }
		});
	
	
	
/*----------------------------SquareBox Image ----------------------------------------*/
	
	var SquareBoxImage =  L.Control.extend({

		  options: {
			position: 'bottomright'
		  },

		  onAdd: function (map) 
		  {
			var container = L.DomUtil.create('square');
			container.id="rotator_id";
			container.title="Rotator";
			container.style.backgroundColor = 'white';
			//$(container).addClass("offline");			
			container.style.width = '60px';
			container.style.height = '60px';
			//container.style["font-size"] = '13px';
			//container.style["font-weight"] = '900';
			container.style.padding = '7PX';
			return container;
		  }
		});
/*-------------------------------------------------------------------------------------------------*/
	
	
	var currentData =  L.Control.extend({

		  options: {
			position: 'topright'
		  },

		  onAdd: function (map) 
		  {
			var container = L.DomUtil.create('select');
			container.id="data_type";
			container.title="Device current data";
			container.style.backgroundColor = 'white';
			//$(container).addClass("offline");			
			//container.style.width = '45px';
			//container.style.height = '35px';
			container.style["font-size"] = '13px';
			container.style["font-weight"] = '900';
			container.style.padding = '7PX';
			
			return container;
		  }
		});
	
	 
	 map.addControl(new toggleOfflineOnlineMap());
	 map.addControl(new compasImg());
	 map.addControl(new SquareBoxImage());
	 //map.addControl(new currentAngle());
	 
	 map.addControl(new currentData());
	 
	// $("#data_type").html("<option value=0>Current day</option><option value=1>From yesterday</option><option value=2>From last two Day</option>");
	 $("#data_type").html("<option value=0>Today</option><option value=1>From yesterday</option><option value=2>From Last 2 days</option>");
	 var offset = $("#data_type").val();

/**********************************CREATE ROTATOR BUTTON **********************************************************************/	 
	 $("#rotator_id").html("<div class='row h-33 cont_row'>" +
		 		"<div class='col-4'></div>" +
		 		"<div class='col-4'><i class='fa fa-arrow-circle-up rotator-pointer' aria-hidden='true' title='Up' onclick=mapRotator(270)></i></div>" +
		 		"<div class ='col-4'></div>" +
		 		"</div>" +
		 		"<div class='row h-34 cont_row'>" +
		 		"<div class='col-4'><i class='fa fa-arrow-circle-left rotator-pointer' aria-hidden='true' title='Left' onclick=mapRotator(180)></i></div>" +
		 		"<div class='col-4'><i class='fa fa-refresh rotator-pointer' aria-hidden='true' title='Reset' onclick=mapRotator(0)></i></div>" +
		 		"<div class ='col-4'><i class='fa fa-arrow-circle-right rotator-pointer' aria-hidden='true' title='Right' onclick=mapRotator(360)></i></div>" +
		 		"</div>" +
		 		"<div class='row h-33 cont_row'>" +
		 		"<div class='col-4'></div>" +
		 		"<div class='col-4'><i class='fa fa-arrow-circle-down rotator-pointer' aria-hidden='true' title='Down' onclick=mapRotator(90)></i></div>" +
		 		"<div class ='col-4'></div>" +
		 		"</div>");		
/**********************************CREATE ROTATOR BUTTON *********************************************************************/
	 
	 $("#data_type").change(function(){
		 //resetLayers();
		 //getEventsData( $("#data_type").val());
		 $.cookie('offset', $("#data_type").val());
		 location.reload();
	  });
	 
	 if($.cookie('offset') != undefined)
	 {
		 $("#data_type").val($.cookie('offset'));
	 }
	 //historyEventWorker();
	 getEventsData( $("#data_type").val());
	 
}

var addPolyLine = function(start,end,lineColor,layer)
{
	
	// create a red polyline from an array of LatLng points
	var latlngs = [
		start,
		end
	];
	var polyline = L.polyline(latlngs, {color: lineColor}).addTo(layer);
	return polyline;
}


var calulateLatLongAtGivenAngleAndDistance = function(lat,lng,angle,distance)
{
	
	return L.GeometryUtil.destination(L.latLng(lat,lng),angle,distance);
}

var createArcFromGivenAngle = function(center,angle)
{
	var truf_center = turf.point(center);//([-75, 40]);
	
	var radius = 100;
	
	//var bearing1 = angle/2;
	
	var bearing1 = (angle - 30)<0?360+(angle-30):(angle-30);
	
	var bearing2 = (angle + 30)>360?(angle + 30)-360:(angle + 30);

	var arc = turf.lineArc(truf_center, radius, bearing1, bearing2);
	
	
	var latlngs = [];
	latlngs.push(center);
	var len = arc.geometry.coordinates.length
	for(i=0;i<arc.geometry.coordinates.length;i++)
	{
		//latlngs.push(arc.geometry.coordinates[i]);
	}
	latlngs.push(arc.geometry.coordinates[0],arc.geometry.coordinates[len-1]);
	
	
	var polygon = L.polygon(latlngs, {color: 'red'}).addTo(map);
	//map.fitBounds(polygon.getBounds());
	return polygon;
}

var createCircle = function(radius,color,location,layer)
{
	return  L.circle(location, {radius: radius,fill:color,color:color}).addTo(layer);
}

var createMarkerLableForDay = function(location,layer)
{
	if(currentEventOffset != 0 )
	{
		
	
		var color = "red";
		if(currentEventOffset==1)
		{
			color = yesterdayColor;
		}
		else if(currentEventOffset ==2)
		{
			color = dayBeforeYestrdayColor;
		}
		return createCircle(radiusBelowMarker,color,location,layer)
	}
	else
	{
		return "";
	}
}



var resetLayers = function()
{
	  towerMarkerLayer.clearLayers();
	  ugsLayer.clearLayers();
	  trglLayer.clearLayers();
	  tmdasLayer.clearLayers();
	  ugsEventLayer.clearLayers();
	  tmdasEventLayer.clearLayers();
	  trglEventLayer.clearLayers();
}



var rotateMap = function(angle)
{
	//map.setBearing(angle-2*angle);
	if(ROTATE_MAP)
	{
		var rotationAngle = mapRotationOffset+(angle-2*angle);
		setMapDirection(rotationAngle);
	}
	
}

var setMapDirection = function(angle)
{
	map.setBearing(angle);
	$('#map_compass').css("transform","rotate("+(angle-42)+"deg)");
}

/****************************SET CURRENT ANGLE *************************************/

var setCurrentAngle = function()
{
	if(ROTATE_MAP)
	{
		$.ajax(
				{
					url:serviceUrls.getCurrentAngle,
					type: 'GET',
					data:{},
					success:function(data)
					{
						$('angle').val(data);
						var rotationAngle=data;
						setMapDirection(rotationAngle);
					}
				});
	}
}
/****************************SET CURRENT ANGLE *************************************/

/****************************ROTATE MAP ********************************************/
var mapRotator = function(angle)
{
	var a=getSystemLocation();
	if(angle==0){
		angle=a.OFFSET;
	}
	
	if(ROTATE_MAP)
	{
		var rotationAngle=angle - a.OFFSET;
		setMapDirection(rotationAngle);
		updateAngle(rotationAngle);
	}
}
/****************************ROTATE MAP ********************************************/

/****************************UPDATE CURRENT MAP ANGLE ******************************/ 
var updateAngle = function(angle)
{
	$.ajax(
			{
				url:serviceUrls.updateAngle,
				data:{"angle":angle},
				success:function(data)
				{
					//location.reload();
				}
			});
}
/****************************UPDATE CURRENT MAP ANGLE ******************************/ 

/*************************** MAP Section **********************************/
/*****************************validation***********************************/

var validateAddNodeForm = function()
{
	var emptyCheck = false;
	$("#add_node_form input").each(function(){
		if(!checkIfTextBoxIsEmpty($(this)))
		{
			
			emptyCheck = true;
			//break;
		}
	});
	if(emptyCheck)
	{
		alert("Please Fill All the details");
		return false;
	}
	
	if(!ValidateIPaddress($("#node_ip").val()))
	{
				return false;
	}
	
	return true;	
}


var validateDefForm = function()
{
	var emptyCheck = false;
	$("#def_form input").each(function(){
		if(!checkIfTextBoxIsEmpty($(this)))
		{
			
			emptyCheck = true;
			//break;
		}
	});
	if(emptyCheck)
	{
		alert("Please Fill All the details");
		return false;
	}
	return true;	
}

var checkIfTextBoxIsEmpty = function(element)
{
	if(element.val().trim().length <= 0)
	{	
		return false;
	}
	else
	{
		return true;
	}
}

function ValidateIPaddress(ipaddress) 
{
 if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ipaddress))
  {
    return (true)
  }
alert("You have entered an invalid IP address!")
return (false)
}
/*****************************validation***********************************/


/***************************************CMS*****************************************************************/

var initData = function()
{
	loadLeafMap();
	
	//setInterval(Time, 500);
	Time();
	contextMenu();
	
	$("#UGS_legend").css("background",ugsColorCode);
	$("#TMDAS_legend").css("background",tmdasColorCode);
	$("#TRGL_legend").css("background",trglColorCode);
	$("#ADU_legend").css("background",aduColorCode);
	
	$("#UGS_box").css("background",ugsColorCode);
	$("#TMDAS_box").css("background",tmdasColorCode);
	$("#TRGL_box").css("background",trglColorCode);
	$("#ADU_box").css("background",aduColorCode);
	
	
	$(".dashboard").addClass("active");
	
	setLegends();
	
	//var fullscreen = new fullscreen(".fullscreen");
	
	
	var option =
	{
			"width": "95%",
			"height": "95%",
			"top": "2.5%",
			"left": "2.5%",
			"right": "2.5%",
			"bottom": "2.5%",
			"background": "white"
	}
	
	$(".fullscreen").fullscreen(option,function(element){
		cosole.log(element);
	});
	
	getCells();
	getCuesData();
	
}


var getInventory = function()
{
	$.ajax({
		url:serviceUrls.inventory,
		dataType:"json",
		success:function(data)
		{
			inventory = data;
			placeUGSOnMap(inventory);
			//placeTRGLOnMap(inventory);
			//placeTMDASOnMap(inventory);
			addFinleyOnMap(getSystemLocation());
		}
	});
}

var placeUGSOnMap = function(data)
{
	ugsLayer.clearLayers();
	ugsData = data.UGS;
	for(var i in ugsData)
	{
		for(var j in ugsData[i])
		{
			try
			{
				inventory.UGS[i][j].marker = addUGSNodeOnMap(ugsData[i][j]);
			}
			catch(err)
			{
				
			}
		}
	}
}

var placeTRGLOnMap = function(data,url)
{
	
	trglLayer.clearLayers();
	deviceData = data.TRGL;
	for(var i in deviceData)
	{
		for(var j in deviceData[i])
		{
			try
			{
				inventory.TRGL[i][j].marker = addTRGLNodeOnMap(deviceData[i][j]);
			}
			catch(err)
			{
				
			}
		}
	}
}

var placeTMDASOnMap = function(data,url)
{
	tmdasLayer.clearLayers();
	deviceData = data.TMDAS;
	for(var i in deviceData)
	{
		for(var j in deviceData[i])
		{
			try
			{
				inventory.TMDAS[i][j].marker = addTMDASnodeOnMap(deviceData[i][j]);
			}
			catch(err)
			{
				
			}
		}
	}
}


var addUGSNodeOnMap = function(data)
{
	
	
	var myIcon = null;
	
	if(data.STATE == 1)
		myIcon = createIconObject(images.UGS.ugs_up,marker_width,marker_height);	
	else
		myIcon = createIconObject(images.UGS.ugs_down,marker_width,marker_height);
	
	var circle = L.circle([data.LATITUDE,data.LONGITUDE], {
	    color: ugsCoverageCircleColor,
	    fillColor: ugsCoverageCircleColor,
	    fillOpacity: 0.5,
	    radius: ugsCoverage
	}).addTo(ugsLayer);
	
	var towerMarker = L.marker([data.LATITUDE,data.LONGITUDE],{icon:myIcon,opacity:getMarkerOpecity()}).addTo(ugsLayer);
	towerMarker.coverage = [circle];
	return towerMarker;
}


var addTRGLNodeOnMap = function(data)
{
	var myIcon = null;
	
	if(data.STATE == 1)
		myIcon = createIconObject(images.TRGL.trgl_up,marker_width,marker_height);	
	else
		myIcon = createIconObject(images.TRGL.trgl_down,marker_width,marker_height);
	
	
	var towerMarker = L.marker([data.LATITUDE,data.LONGITUDE],{icon:myIcon,opacity:getMarkerOpecity()}).addTo(trglLayer);
	
	//towerMarker.coverage = [createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET,trglCoverageRadius,trglLayer)];
	//towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+60,trglCoverageRadius,trglLayer));
	//towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+120,trglCoverageRadius,trglLayer));
	//towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+180,trglCoverageRadius,trglLayer));
	
	return towerMarker;
}

var addTMDASnodeOnMap = function(data)
{
	if(data.STATE == 1)
		myIcon = createIconObject(images.TMDAS.tmdas_up,marker_width,marker_height);	
	else
		myIcon = createIconObject(images.TMDAS.tmdas_down,marker_width,marker_height);
	var towerMarker = L.marker([data.LATITUDE,data.LONGITUDE],{icon:myIcon,opacity:getMarkerOpecity()}).addTo(tmdasLayer);
	
	map.flyTo([data.LATITUDE,data.LONGITUDE]);
	$("#sys_location").html(parseFloat(data.LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data.LONGITUDE).toFixed(latLonUptoPlaces));
	
	towerMarker.coverage = [createSemiCircleOverToMap([data.LATITUDE,data.LONGITUDE],tmdasCoverageRadius,data.OFFSET,(data.OFFSET+180),tmdasLayer)];
	
	towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET,tmdasCoverageRadius,tmdasLayer));
	towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+60,tmdasCoverageRadius,tmdasLayer));
	towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+120,tmdasCoverageRadius,tmdasLayer));
	towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+180,tmdasCoverageRadius,tmdasLayer));
	
	
	return towerMarker;
}

var addFinleyOnMap = function(data)
{	
	try
	{
		var myIcon = createIconObject(images.SYSTEM.center,marker_width,marker_height,13,26);
		var towerMarker = L.marker([data.LATITUDE,data.LONGITUDE],{icon:myIcon,opacity:getMarkerOpecity()}).addTo(tmdasLayer);
		towerMarker.coverage = [createSemiCircleOverToMap([data.LATITUDE,data.LONGITUDE],tmdasCoverageRadius,data.OFFSET,(data.OFFSET+180),tmdasLayer)];
		towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET,tmdasCoverageRadius,tmdasLayer));
		towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+60,tmdasCoverageRadius,tmdasLayer));
		towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+120,tmdasCoverageRadius,tmdasLayer));
		towerMarker.coverage.push(createLineAtGivenAngle(data.LATITUDE,data.LONGITUDE,data.OFFSET+180,tmdasCoverageRadius,tmdasLayer));
		map.flyTo([data.LATITUDE,data.LONGITUDE]);
		//rotateMap(data.OFFSET);
		setCurrentAngle();
		$("#sys_location").html(parseFloat(data.LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data.LONGITUDE).toFixed(latLonUptoPlaces));
	}
	catch(err)
	{
		
	}
	
}

var placeTowers = function(data)
{
	
	
	var myIcon = null;
	myIcon = createIconObject(images.CELL.tower,marker_width,marker_height);
	
	var towerMarker = L.marker([data.lat,data.lon],{icon:myIcon}).addTo(towerLayer);
	
	var html = createPoupHtmlForCell(data);
	
	towerMarker.bindPopup(html);
	
	return towerMarker;
}

var getMarkerOpecity = function()
{
	return currentEventOffset == 0?1:currentEventOffset==1?0.6:0.3;
}

var getLayerAccordingToDayOfEvents = function(type)
{
	
	var layer = null;
	switch(type)
	{
		case "UGS":
			if(currentEventOffset == 0)
				layer = ugsLayer;
			if(currentEventOffset == 1)
				layer = ugsLayer_day_2;
			if(currentEventOffset == 2)
				layer = ugsLayer_day_3;
			break;
		case "TMDAS":
			if(currentEventOffset == 0)
				layer = tmdasLayer;
			if(currentEventOffset == 1)
				layer = tmdasLayer_day_2;
			if(currentEventOffset == 2)
				layer = tmdasLayer_day_3;
			
			break;
		case "TRGL":
			if(currentEventOffset == 0)
				layer = trglLayer;
			if(currentEventOffset == 1)
				layer = trglLayer_day_2;
			if(currentEventOffset == 2)
				layer = trglLayer_day_3;
			break;
		
	
	}
	return layer;
}

var checkAndAddCorospondingTypeNode = function(type,ip,id,data)
{
	switch(type)
	{
		case "UGS":
			var marker = addUGSNodeOnMap(data);
			inventory.UGS[ip][ip+"-"+id].marker = marker;
			break;
		case "TMDAS":
			var marker = addTMDASnodeOnMap(data);
			inventory.TMDAS[ip][ip+"-"+id].marker = marker;
			break;
		case "TRGL":
			var marker = addTRGLNodeOnMap(data);
			inventory.TRGL[ip][ip+"-"+id].marker = marker;
			break;
	}
}


var createIconObject = function(url,width,height,margin_left=4,margin_top=29)
{
	
	
	return L.icon
	({
			iconUrl: url,
			iconSize: [width, height],
			iconAnchor: [margin_left, margin_top]
	}); 
}




var fillNodeDataInForm = function()
{
	$.ajax({
		url:serviceUrls.nodes,
		dataType:"json",
		success:function(data)
		{
			console.log(data);
			$("#ugs_node_ip").val(data.nodes.UGS);
			$("#trgl_node_ip").val(data.nodes.TRGL);
			$("#tmdas_node_ip").val(data.nodes.TMDAS);
			
		}
	});
}

var updateNodeList = function()
{
	$.ajax({
		url:serviceUrls.updateNodes,
		dataType:"json",
		type:"post",
		data: 
		{
				"nodes":
				{
					"UGS":$("#ugs_node_ip").val(),
					"TRGL":$("#trgl_node_ip").val(),
					"TMDAS":$("#tmdas_node_ip").val(),
				}
		},
		
		success:function(data)
		{
			console.log(data);
			$("#ugs_node_ip").val(data.nodes.UGS);
			$("#trgl_node_ip").val(data.nodes.TRGL);
			$("#tmdas_node_ip").val(data.nodes.TMDAS);
			
		}
	});
}


var createSemiCircleOverToMap = function(possition,radius,startAngle,endAngle,layer)
{
	return L.semiCircle(possition, {
	    radius: radius,
		startAngle: startAngle,
		stopAngle: endAngle
	}).addTo(layer);
	
}

var createLineAtGivenAngle = function(lat,lon,offset,distance,layer)
{
	lineEndPoint = calulateLatLongAtGivenAngleAndDistance(lat,lon,offset,distance);
	var line = addPolyLine([lat,lon],lineEndPoint,'#a5a2a2',layer);
	return line;	
}

var registeredEvents = function()
{
	$("#udpate_node").click(function(){
		updateNodeList();
	});
	
	
	$(".e_tab").on("dblclick","tr",function(){
		
		var lat = $(this).data("lat");
		var lon = $(this).data("lon");
		
		
		
		
		
		if($(this).parent().parent().attr("id") == "bl_table" || $(this).parent().parent().attr("id") == "cue_table")		
		{
			/*var type = $(this).data("type");
			var id = $(this).data("id");
			var ip = $(this).data("ip");
			var index = $(this).data("index");
			var marker = inventory[type][ip][ip+"-"+id].EVENT.data[index];
			openAssociatedPopup(marker);
			//map.setZoom(21);
			map.flyTo([lat,lon]);*/
			for(var i in alarmMarkers)
			{
				if(alarmMarkers[i].cueId == $(this).data("cueid"))
				{
					//openAssociatedPopup(alarmMarkers[i]);
					//map.flyTo(alarmMarkers[i].getLatLng());
					$('*[data-cueid="'+alarmMarkers[i].cueId+'"]').each(function(index, value){
						$(value).addClass("heighlightalarm");
						$('*[data-cueid="'+alarmMarkers[i].cueId+'"]')[index].scrollIntoView();
					});
					heighLightMarker(alarmMarkers[i].cueId);
				}
			}
			
		}
		if(lat != undefined && lat != null && lat != "" && lon != undefined && lon != null && lon != "" && parseFloat(lat) != -1.1 && parseFloat(lon) != -1.1)
		{
			
			
		}
			
	});
	
	
	
	$(".e_tab").on("click","tr",function()
	{
		if($(this).parent().parent().attr("id") == "bl_table" || $(this).parent().parent().attr("id") == "cue_table")		
		{
			/*for(var i in alarmMarkers)
			{
				$('*[data-cueid="'+alarmMarkers[i].cueId+'"]').each(function(){
					$(this).removeClass("heighlightalarm");
				});
				$('*[data-cueid="'+alarmMarkers[i].cueId+'"]').each(function(index, value){
					$(value).removeClass("heighlightalarm");
					//$('*[data-cueid="'+alarmMarkers[i].cueId+'"]')[index].scrollIntoView();
				});
				heighightCircleLayer.clearLayers();
			}*/
			unselectHeighlighted();
		}	
	});
	
	
	
	
	$(".fullscreen").click(function()
	{
		if($(this).find('i').hasClass("fa-window-maximize"))
		{
			$(this).find('i').removeClass("fa-window-maximize");
			$(this).find('i').addClass('fa-window-minimize');
		}
		else
		{
			$(this).find('i').removeClass("fa-window-minimize");
			$(this).find('i').addClass('fa-window-maximize');
		}
	});
	
		
	$('.connect_c2').click(function(){
		
		 var url = $(this).data("url");
		 window.open(url, '_blank');
		});
	
	$(".legend_block").click(function(){
		$("#status_detail").modal("show");
	});
	
}

 var errorCalBackSocket = function(error)
 {
	 console.log('*********************************error****************************');
	 console.log(error)
	 console.log('*********************************error****************************');
 }
var connectSocket = function()
{
	
	var stompClient = null;


	var socket = new SockJS(endPoint.socket);
	
	stompClient = Stomp.over(socket);
	
	stompClient.reconnect_delay = 2000;
	
	stompClient.connect({}, function (frame) 
    {
    		console.log('Connected: ' + frame);
    		stompClient.subscribe(topics.event, function (data) 
    		{
    			if(data.body != "")
    			{
    				var data = data.body.split(";");
    				if(data.length > 0)
    				{
    					var type = data[0];
            			var event = jQuery.parseJSON(data[1]);
            			event.eventId = data[2];
            			incomingEventHandler(type,event,"socket");
            			resizeAllTable();
    				}	
    			}
    		});
    		stompClient.subscribe(topics.status, function (data) 
    	    		{
    	    			if(data.body != "")
    	    			{
    	    				var data = jQuery.parseJSON(data.body);
    	    				var statusColor = (data.status=="Down")?nodeDownColor:nodeUpColor;
    	    				$("#"+data.type+"_state").css("background",statusColor);
    	    				setBoxValue(data);
    	    			}
    	    		});
    		stompClient.subscribe(topics.reload, function (data) 
    	    		{
    	    			if(data.body != "")
    	    			{
    	    				var data = jQuery.parseJSON(data.body);
    	    				if(data.status=="reload")
    	    				{
    	    					location.reload();
    	    				}
    	    			}
    	    		});
    		stompClient.subscribe(topics.cue, function (data) 
    	    		{
    	    			if(data.body != "")
    	    			{
    	    				var data = jQuery.parseJSON(data.body);
    	    				updateCueTable(data,'new');
    	    				resizeTable("cue_table");
    	    			}
    	    		});
    		/*stompClient.subscribe(topics.cell, function (data) 
    	    		{
    	    			if(data.body != "")
    	    			{
    	    				var data = jQuery.parseJSON(data.body);
    	    				placeTowers(data);
    	    			}
    	    		});*/
    },errorCalBackSocket);
}



var setBoxValue = function(data)
{
	
	$("#"+data.type+"_box .statusClass").text(data.status);
	$("#"+data.type+"_box .ip").text(data.ip);
	$("#"+data.type+"_box .mode").text(data.mode);
	$("#"+data.type+"_box .space").text(data.space);
	if(data.freeze == "1")
	{
		$("#"+data.type+"_box").addClass("blinking");
		$("#"+data.type+"_box .statusClass").text("Freezed");
		$("#"+data.type+"_state").addClass("blinking");
	}
	else
	{
		$("#"+data.type+"_box").removeClass("blinking");
		$("#"+data.type+"_state").removeClass("blinking");
	}
}


/*
 * Handles the incoming events it decides what is the type of the event and call the corosponding handler
 * */
var incomingEventHandler = function(type,data,eventType)
{
	switch(type)
	{
		case "UGS":
			ugsEventHadler(data,eventType);
			break;
		case "TRGL":
			trglEventHadler(data,eventType);
			break;
		case "TMDAS":
			tmdasEventHadler(data,eventType)
			break;
	}
}

var ugsEventHadler = function(data,eventType)
{
	
	var detectedData = Object.assign({}, data);
	switch(parseInt(data.EVENT_TYPE))
	{
		case 1:
			//ugsObjectDetectionEvent(data);
			break;
		case 2:
			updateUgsStatus(data);
			break;
		case 3:
			updateUgsStatus(data);
			break;
		case 4:
			addNewNode("UGS",data);
			break;
		case 5:
			deleteNode(data,"UGS");
			break;
		case 7:
			detectedData = ugsObjectDetectionEvent(detectedData);
			break;
	}
	
	if(currentEventOffset == 0)
	{
		updateOXFAMEvent(data,eventType);			
		if(data.EVENT_TYPE == 7)
		{
			updateAlarmTableUGSdata(detectedData,eventType);
		}
		else
		{
			updateEventTableUGSdata(data,eventType);
			
		}
		
	}
	
}

var updateUgsStatus = function(data)
{
	
	var icon = null;
	if(data.EVENT_TYPE == 2)
		icon = createIconObject(images.UGS.ugs_up,marker_width,marker_height);	
	else
		icon = createIconObject(images.UGS.ugs_down,marker_width,marker_height);
	inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].marker.setIcon(icon);
}

var ugsObjectDetectionEvent = function(data)
{
	
	removeLastDetected("UGS",data);
	var transictionId = data.TRANS_ID;
	
	var image = images.UGS.ugs_detect;
	if(data.ack != undefined && data.ack == true)
	{
		//updateAlarmTableUGSdata(data);
		image = images.UGS.ugs_normal;
			
	}
	
	
	var myIcon = createIconObject(images.UGS.ugs_detect,marker_width,marker_height);
	
	
	var layer = getLayerAccordingToDayOfEvents("UGS");
	
	var marker = L.marker([data.LATITUDE,data.LONGITUDE],{icon:myIcon,opacity:getMarkerOpecity()}).addTo(layer);
	marker.transid = data.TRANS_ID;
	marker.cueId = data.TRANS_ID;
	
	
	marker.bindPopup(getPopUpHtml(data,"UGS"));
	
	
	
	marker.on("click",function()
	{	
		//var rowId = this.type+"_"+this.ip+"_"+this.id+"_"+this.index;
		//$(document.getElementById(rowId)).addClass("heighlightalarm");
		
		$('*[data-cueid="'+marker.cueId+'"]').each(function(index, value){
			$(value).addClass("heighlightalarm");
			$('*[data-cueid="'+marker.cueId+'"]')[index].scrollIntoView();
		});
		heighLightMarker(marker.cueId);
	});
	
	marker.on("popupclose",function()
	{	
		//var rowId = this.type+"_"+this.ip+"_"+this.id+"_"+this.index;
		//$(document.getElementById(rowId)).removeClass("heighlightalarm");
		
		/*$('*[data-cueid="'+marker.cueId+'"]').each(function(){
			$(this).removeClass("heighlightalarm");
		});
		heighightCircleLayer.clearLayers();*/
		
		unselectHeighlighted();
		
	});
	
	data.marker = marker;
	marker.type="UGS";
	alarmMarkers.push(marker);
	
	
	blinkImage(null,blinkTime.ugs,createIconObject(images.UGS.ugs_normal,marker_width,marker_height),marker);
	marker.dayLabel =  createMarkerLableForDay([data.LATITUDE,data.LONGITUDE],layer);
	try
	{
		if(inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT == undefined)
		{
			inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT ={};
		}
		
		if(inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data == undefined)
		{
			//var eventData={};
			//eventData[transictionId] = marker;
			//inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data = eventData;
			inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data = [marker];
		}
		else
		{
			//inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data[transictionId] = marker;
			inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data.push(marker);
			
		}
		
		
		var index = (inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data.length -1);
		data.index = index;
		data.type = "UGS";
		
		inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data[index].index = index;
		inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data[index].type = "UGS";
		inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data[index].ip = data.ip;
		inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data[index].id = data.DEVICE_ID;
		
		
		
		//inventory.UGS[data.ip].CURRENT_DETECT= {"ip":data.ip,"id":data.DEVICE_ID,"trans_id":transictionId};
		inventory.UGS[data.ip].CURRENT_DETECT= marker;
		//inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data.TRANS_ID = marker;
	}
	catch(err)
	{
		
	}
	
	
	return data;
	
}

var removeLastDetected = function(type,data)
{
	
	try
	{
		if(type=="UGS" && inventory.UGS != undefined && !inventory.UGS[data.ip] != undefined && inventory.UGS[data.ip].CURRENT_DETECT != undefined)
		{
			var icon = createIconObject(images.UGS.ugs_normal,marker_width,marker_height)
			inventory.UGS[data.ip].CURRENT_DETECT.setIcon(icon);
			//inventory.UGS[data.ip].CURRENT_DETECT.remove();	
		}
		
		
		if(type=="TRGL" && inventory.TRGL != undefined && !inventory.TRGL[data.DEVICE_IP] != undefined && inventory.TRGL[data.DEVICE_IP].CURRENT_DETECT != undefined)
		{
			var icon = createIconObject(images.TRGL.trgl_normal,marker_width,marker_height)
			inventory.TRGL[data.DEVICE_IP].CURRENT_DETECT.setIcon(icon);
		}
		
		if(type=="TMDAS" && inventory.TMDAS != undefined && !inventory.TMDAS[data.DEVICE_IP] != undefined && inventory.TMDAS[data.DEVICE_IP].CURRENT_DETECT != undefined)
		{
			var icon = createIconObject(images.TMDAS.tmdas_normal,marker_width,marker_height)
			inventory.TMDAS[data.DEVICE_IP].CURRENT_DETECT.setIcon(icon);	
		}
		/*
		if(type=="TRGL" && inventory.TRGL[data.DEVICE_IP] != undefined && inventory.TRGL[data.DEVICE_IP].CURRENT_DETECT != undefined)
		{
			var last_detected_transiction_details = inventory[type][data.DEVICE_IP].CURRENT_DETECT;
			var myIcon = createIconObject(images.TRGL.trgl_normal,marker_width,marker_height);
			inventory.TRGL[data.DEVICE_IP][last_detected_transiction_details.ip+"-"+last_detected_transiction_details.id].EVENT.data[last_detected_transiction_details.trans_id].setIcon(myIcon);	
		}
		
		if(type=="TMDAS"  && inventory.TMDAS[data.DEVICE_IP] != undefined && inventory.TMDAS[data.DEVICE_IP].CURRENT_DETECT != undefined)
		{
			var last_detected_transiction_details = inventory[type][data.DEVICE_IP].CURRENT_DETECT;
			var myIcon = createIconObject(images.TMDAS.tmdas_normal,15,15);
			inventory.TMDAS[data.DEVICE_IP][last_detected_transiction_details.ip+"-"+last_detected_transiction_details.id].EVENT.data[last_detected_transiction_details.trans_id].setIcon(myIcon);	
		}*/
	}
	catch(err)
	{
		
	}
}

var trglEventHadler = function(data,eventType)
{
	
	//var detectedData = data;
	var detectedData = Object.assign({}, data);
	
	switch(parseInt(data.EVENT_TYPE))
	{
		case 1:
			//trglObjectDetectionEvent(data);
			break;
		case 2:
			//updateTrglStatus(data);
			break;
		case 3:
			//updateTrglStatus(data);
			break;
		case 4:
			//addNewNode("TRGL",data);
			break;
		case 5:
			//deleteNode(data,"TRGL");
			break;
		case 6:
			//trglObjectDetectionEvent(data);
			break;
		case 7:
			//location.reload();
			//alarm handing will come here
			detectedData = trglObjectDetectionEvent(data);
			
			break;
	}
	
	if(currentEventOffset ==0)
	{
		
		if(data.EVENT_TYPE == 7)
		{
			updateAlarmTRGLdata(detectedData,eventType);
		}
		else
		{
			updateEventTRGLdata(data,eventType);
		}
		
	}
}


var updateTrglStatus = function(data)
{
	
	var icon = null;
	if(data.EVENT_TYPE == 2)
		icon = createIconObject(images.TRGL.trgl_up,marker_width,marker_height);	
	else
		icon = createIconObject(images.TRGL.trgl_down,marker_width,marker_height);
	inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].marker.setIcon(icon);
}

var updateTmdasStatus = function(data)
{
	
	var icon = null;
	if(data.EVENT_TYPE == 2)
		icon = createIconObject(images.TMDAS.tmdas_up,marker_width,marker_height);	
	else
		icon = createIconObject(images.TMDAS.tmdas_down,marker_width,marker_height);
	inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].marker.setIcon(icon);
}

var trglObjectDetectionEvent = function(data)
{
	
	try
	{
		removeLastDetected("TRGL",data);
		
		var image = images.TRGL.trgl_detect
		
		if(data.ack != undefined && data.ack == true)
		{
			image = images.TRGL.trgl_normal;
		}
		
		var myIcon = createIconObject(image,marker_width,marker_height);
		
		
		
		var layer = getLayerAccordingToDayOfEvents("TRGL");
		
		var marker = L.marker([data.LATITUDE,data.LONGITUDE],{icon:myIcon,opacity:getMarkerOpecity()}).addTo(layer);
		
		var transictionId = data.TRANS_ID;
		marker.transid = transictionId;
		marker.cueId = data.CUE_ID;
		
		marker.bindPopup(getPopUpHtml(data,"TRGL"));
		
		
		marker.on("click",function()
		{	
			//var rowId = this.type+"_"+this.ip+"_"+this.id+"_"+this.index;
			//$(document.getElementById(rowId)).addClass("heighlightalarm");
			
			$('*[data-cueid="'+marker.cueId+'"]').each(function(index, value){
				$(value).addClass("heighlightalarm");
				$('*[data-cueid="'+marker.cueId+'"]')[index].scrollIntoView();
			});
			heighLightMarker(marker.cueId);
			
		});
				
		marker.on("popupclose",function()
		{	
			//var rowId = this.type+"_"+this.ip+"_"+this.id+"_"+this.index;
			//$(document.getElementById(rowId)).removeClass("heighlightalarm");
			/*$('*[data-cueid="'+marker.cueId+'"]').each(function(){
				$(this).removeClass("heighlightalarm");
			});
			heighightCircleLayer.clearLayers();*/
			unselectHeighlighted();
		});
		
		blinkImage(null,blinkTime.trgl,createIconObject(images.TRGL.trgl_normal,marker_width,marker_height),marker);
		marker.dayLabel =  createMarkerLableForDay([data.LATITUDE,data.LONGITUDE],layer);
		
		
		data.marker = marker;
		marker.type="TRGL";
		alarmMarkers.push(marker);
		
		if(inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT == undefined)
		{
			inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT ={};
		}
		
		if(inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data == undefined)
		{
			//var eventData={};
			//eventData[transictionId] = marker;
			//inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data = eventData;
			inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data = [marker];
		}
		else
		{
			//inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[transictionId] = marker;
			inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data.push(marker);
		}
		
		//inventory.TRGL[data.DEVICE_IP].CURRENT_DETECT= {"ip":data.DEVICE_IP,"id":data.DEVICE_IP,"trans_id":transictionId};
		
		
		
		var index = (inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data.length -1);
		data.index = index;
		data.type = "TRGL";
		
		inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].index = index;
		inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].type = "TRGL";
		inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].ip = data.DEVICE_IP;
		inventory.TRGL[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].id = data.DEVICE_ID;
		
		
		
		//inventory.UGS[data.ip].CURRENT_DETECT= {"ip":data.ip,"id":data.DEVICE_ID,"trans_id":transictionId};
		inventory.TRGL[data.DEVICE_IP].CURRENT_DETECT= marker;
		//inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data.TRANS_ID = marker;
		
		
		
		
		
	}
	catch(err)
	{
		
	}
	
	
	
	return data;
	
	
	
	//inventory.TRGL[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data.TRANS_ID = marker;
}

var tmdasObjectDetectionEvent = function(data)
{
	
	try
	{
			removeLastDetected("TMDAS",data);		
		
			var image = images.TMDAS.tmdas_detect
			
			if(data.ack != undefined && data.ack == true)
			{
				image =images.TMDAS.tmdas_normal;
			}
			
			var myIcon = createIconObject(image,marker_width,marker_height);
			
			
			//var myIcon = createIconObject(images.TMDAS.tmdas_detect);
			var layer = getLayerAccordingToDayOfEvents("TMDAS");
			
			
			
			data = calculateLatLonFromTa(data);
			
			
			var marker = L.marker([data.LATITUDE,data.LONGITUDE],{icon:myIcon,opacity:getMarkerOpecity()}).addTo(layer);

			marker.dayLabel =  createMarkerLableForDay([data.LATITUDE,data.LONGITUDE],layer);
			
			marker.bindPopup(getPopUpHtml(data,"TMDAS"));
			
			/*if(inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT == undefined)
			{
				inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT ={};
			}
			
			if(inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data == undefined)
			{
				var eventData={};
				eventData[transictionId] = marker;
				inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data = eventData;
			}
			else
			{
				inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[transictionId] = marker;	
			}
			inventory.TMDAS[data.DEVICE_IP].CURRENT_DETECT= {"ip":data.DEVICE_IP,"id":data.DEVICE_IP,"trans_id":transictionId};*/
			
			var transictionId = data.TRANS_ID;
			marker.transid = transictionId;
			marker.cueId = data.CUE_ID;
			
			marker.on("click",function()
			{	
				//var rowId = this.type+"_"+this.ip+"_"+this.id+"_"+this.index;
				//$(document.getElementById(rowId)).addClass("heighlightalarm");
				$('*[data-cueId="'+marker.cueId+'"]').each(function(index, value){
					$(value).addClass("heighlightalarm");
				});
				heighLightMarker(marker.cueId);
			});
					
			marker.on("popupclose",function()
			{	
				//var rowId = this.type+"_"+this.ip+"_"+this.id+"_"+this.index;
				//$(document.getElementById(rowId)).removeClass("heighlightalarm");
				/*$('*[data-cueId="'+marker.cueId+'"]').each(function(){
					$(this).removeClass("heighlightalarm");
				});
				heighightCircleLayer.clearLayers();*/
				unselectHeighlighted();
			});
			
			blinkImage(null,blinkTime.tmdas,createIconObject(images.TMDAS.tmdas_normal,marker_width,marker_height),marker);	
			
			
			data.marker = marker;
			marker.type="TMDAS";
			alarmMarkers.push(marker);
			
			if(inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT == undefined)
			{
				inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT ={};
			}
			
			if(inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data == undefined)
			{
				//var eventData={};
				//eventData[transictionId] = marker;
				//inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data = eventData;
				inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data = [marker];
			}
			else
			{
				//inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[transictionId] = marker;
				inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data.push(marker);
			}
			
			//inventory.TMDAS[data.DEVICE_IP].CURRENT_DETECT= {"ip":data.DEVICE_IP,"id":data.DEVICE_IP,"trans_id":transictionId};
			
			
			
			var index = (inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data.length -1);
			data.index = index;
			data.type = "TMDAS";
			
			inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].index = index;
			inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].type = "TMDAS";
			inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].ip = data.DEVICE_IP;
			inventory.TMDAS[data.DEVICE_IP][data.DEVICE_IP+"-"+data.DEVICE_IP].EVENT.data[index].id = data.DEVICE_ID;
			
			
			
			//inventory.UGS[data.ip].CURRENT_DETECT= {"ip":data.ip,"id":data.DEVICE_ID,"trans_id":transictionId};
			inventory.TMDAS[data.DEVICE_IP].CURRENT_DETECT= marker;
			//inventory.UGS[data.ip][data.ip+"-"+data.DEVICE_ID].EVENT.data.TRANS_ID = marker;			
			
	}
	catch(err)
	{
		
	}
	
	return data;
	
}

var addNewNode = function(type,data)
{
	var DEVICE_ID = (type=="UGS")?data.DEVICE_ID:data.DEVICE_IP;
	//var dd = inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID];
	if(inventory[type][data.DEVICE_IP] != undefined && inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID] != undefined && inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID] != null)
	{
		inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID].marker.setLatLng([data.LATITUDE,data.LONGITUDE]);
		//var stateEvent = {"EVENT_TYPE":2}
		data.EVENT_TYPE = 2
		incomingEventHandler(type,data);
	}
	else
	{
		data.STATE=1;
		if(inventory[type][data.DEVICE_IP] != undefined)
			inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID] = data;
		else
		{
			inventory[type][data.DEVICE_IP]=new Object();
			var key = data.DEVICE_IP+"-"+DEVICE_ID;
			inventory[type][data.DEVICE_IP][key] = data;
		}
		checkAndAddCorospondingTypeNode(type,data.DEVICE_IP,DEVICE_ID,data);
	}
}


var deleteNode = function(data,type)
{
	try
	{
		var DEVICE_ID = (type=="UGS")?data.DEVICE_ID:data.DEVICE_IP;
		for(var i in inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID].marker.coverage)
		{
			inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID].marker.coverage[i].remove();
		}
		inventory[type][data.DEVICE_IP][data.DEVICE_IP+"-"+DEVICE_ID].marker.remove();
		var array = inventory[type][data.DEVICE_IP];
		var index = delete array[data.DEVICE_IP+"-"+DEVICE_ID];
	}
	catch(err)
	{
		console.log(err);
	}
	
	/*if (index > -1) 
	{
	  array.splice(index, 1);
	}*/
}




var tmdasEventHadler = function(data,eventType)
{
	
	var detectedData = Object.assign({}, data);
	removeLastTransIdObjects(data.TRANS_ID);
	switch(parseInt(data.EVENT_TYPE))
	{	
		case 1:
			//tmdasObjectDetectionEvent(data);
			break;
		case 2:
			//updateTmdasStatus(data);
			break;
		case 3:
			//updateTmdasStatus(data);
			break;
		case 4:
			//addNewNode("TMDAS",data);
			//checkAndAddCorospondingTypeNode("TMDAS",data.DEVICE_IP,data.DEVICE_IP,data);
			break;
		case 5:
			//deleteNode(data,"TMDAS");
			break;
		case 6:
			//tmdasObjectDetectionEvent(data)
			break;
		case 7:
			detectedData = tmdasObjectDetectionEvent(data);
			
			break;
	}
	
	if(currentEventOffset == 0)
	{
		updateFalconEvent(data,eventType);
		
		
		
		if(data.EVENT_TYPE == 7)
		{
			updateAlarmTMDASdata(detectedData,eventType);
		}
		else
		{
			updateEventTMDASdata(data,eventType);
		}
	}
}

var getEventsData = function(offset)
{
	
	var date = getDateForEvent(offset);
	var type = (offset ==0)?1:2;
	if(offset <=-1)
	{
		//placeEvent();
		var postData=
		{
			"eventsData":eventsData,
			"finley":getSystemLocation()
		}
		//historyEventWR.postMessage(postData);
		resizeAllTable();
	}
	else
	{	
		try
		{
			$.ajax({
				url:serviceUrls.event,
				dataType:"json",
				data:{date:date,type:type,offset:offset},
				type:"post",
				success:function(data)
				{
					/*var aa = data.split("\n");
					events = events.concat(aa);
					getEventsData(offset-1);*/
					currentEventOffset = offset;
					console.log(JSON.stringify(data));
					placeEvent(data,offset);
					eventsData[offset]=data;
					getEventsData(offset-1);
					
				}
			});
		}
		catch(err)
		{
			getEvents(offset-1);
		}
	}
}

var getDateForEvent = function(offset)
{

	var today = new Date();
	today.setDate( today.getDate() - offset );
	var dd = today.getDate();
	
	var mm = today.getMonth()+1; 
	var yyyy = today.getFullYear();
	if(dd<10) 
	{
	    dd='0'+dd;
	} 
	
	if(mm<10) 
	{
	    mm='0'+mm;
	} 
	today = yyyy+"-"+mm+'-'+dd;
	return today;
	console.log(today);
}

/*var placeEvent = function()
{
	for(var i in events)
	{
		var data = events[i].split(";");
		
		if(data.length > 0)
		{
			var type = data[0];
			var event = jQuery.parseJSON(data[1]);
			incomingEventHandler(type,event);
		}
		
	}
}*/



var placeEvent = function(data,offset)
{
	
	for(var i in data)
	//for(var i=data.length-1;i>=0;i--)
	{
		var type = data[i].nodeType;
		var event = jQuery.parseJSON(data[i].eventData);
		//if(parseInt(event.EVENT_TYPE)==7)
		//{
			event.eventId = data[i].eventId
			event.ack = data[i].acknowledged
			incomingEventHandler(type,event,"old");
		//}
	}
}


var updateAlarmTableUGSdata = function(data,eventType)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.UGS+"</td>";
	var id = "<td>"+data.TRANS_ID+"</td>";
	if(eventType != 'old')
	{
		$("#bl_table tbody").prepend("<tr class='rightClick' data-devicetype=UGS data-dataid="+data.eventId+" data-cueid="+data.TRANS_ID+" data-transId="+data.TRANS_ID+" data-type="+data.type+" data-index="+data.index+" data-ip="+data.ip+" data-id="+data.DEVICE_ID+" data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" " +
				" id='"+data.type+"_"+data.ip+"_"+data.DEVICE_ID+"_"+data.index+"' style='background:"+ugsColorCode+"' >"+date+time+node+id+"</tr>");
	}
	else
	{
		$("#bl_table tbody").append("<tr class='rightClick' data-devicetype=UGS data-dataid="+data.eventId+" data-cueid="+data.TRANS_ID+" data-transId="+data.TRANS_ID+" data-type="+data.type+" data-index="+data.index+" data-ip="+data.ip+" data-id="+data.DEVICE_ID+" data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" " +
				" id='"+data.type+"_"+data.ip+"_"+data.DEVICE_ID+"_"+data.index+"' style='background:"+ugsColorCode+"' >"+date+time+node+id+"</tr>");
	}
		
	limitNumberOfRows($("#bl_table tbody"));
	//resizeTable("bl_table");
}


var updateAlarmTMDASdata = function(data,eventType)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.TMDAS+"</td>";
	var id = "<td>"+data.CUE_ID+"</td>";
	if(eventType != 'old')
		$("#bl_table tbody").prepend("<tr  class='rightClick'   data-devicetype=TMDAS data-dataid="+data.eventId+" data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+" data-type="+data.type+" data-index="+data.index+" data-ip="+data.DEVICE_IP+" data-id="+data.DEVICE_IP+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+"  style='background:"+tmdasColorCode+"' >"+date+time+node+id+"</tr>");
	else
		$("#bl_table tbody").append("<tr  class='rightClick'   data-devicetype=TMDAS data-dataid="+data.eventId+" data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+" data-type="+data.type+" data-index="+data.index+" data-ip="+data.DEVICE_IP+" data-id="+data.DEVICE_IP+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+"  style='background:"+tmdasColorCode+"' >"+date+time+node+id+"</tr>");
	limitNumberOfRows($("#bl_table tbody"));
	//resizeTable("bl_table");
}


var updateAlarmTRGLdata = function(data,eventType)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.TRGL+"</td>";
	var id = "<td>"+data.CUE_ID+"</td>";
	if(eventType != 'old')
		$("#bl_table tbody").prepend("<tr   class='rightClick'  data-devicetype=TRGL data-dataid="+data.eventId+" data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+" data-type="+data.type+" data-index="+data.index+" data-ip="+data.DEVICE_IP+" data-id="+data.DEVICE_IP+" data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+"  style='background:"+trglColorCode+"' >"+date+time+node+id+"</tr>");
	else
		$("#bl_table tbody").append("<tr   class='rightClick'  data-devicetype=TRGL data-dataid="+data.eventId+" data-cueid="+data.CUE_ID+"  data-transId="+data.TRANS_ID+" data-type="+data.type+" data-index="+data.index+" data-ip="+data.DEVICE_IP+" data-id="+data.DEVICE_IP+" data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+"  style='background:"+trglColorCode+"' >"+date+time+node+id+"</tr>");
	limitNumberOfRows($("#bl_table tbody"));
	//resizeTable("bl_table");
}


var updateEventTableUGSdata = function(data,eventType)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.UGS+"</td>";
	
	var event = "<td><a onclick='showDetailsOfEvent("+JSON.stringify(data)+")'>"+getEventType("UGS",data.EVENT_TYPE)+"</a></td>";
	if(eventType != 'old')
		$("#el_table tbody").prepend("<tr  data-cueid="+data.TRANS_ID+"  data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+ugsColorCode+"' >"+date+time+node+event+"</tr>");
	else
		$("#el_table tbody").append("<tr  data-cueid="+data.TRANS_ID+"  data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+ugsColorCode+"' >"+date+time+node+event+"</tr>");
	limitNumberOfRows($("#el_table tbody"));
	//resizeTable("el_table");
}


var updateEventTMDASdata = function(data,eventType)
{
	
	
	data = calculateLatLonFromTa(data);
	
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.TMDAS+"</td>";
	var event = "<td><a onclick='showDetailsOfEvent("+JSON.stringify(data)+")'>"+getEventType("TMDAS",data.EVENT_TYPE)+"</a></td>";
	if(eventType != 'old')
		$("#el_table tbody").prepend("<tr   data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+tmdasColorCode+"' >"+date+time+node+event+"</tr>");
	else
		$("#el_table tbody").append("<tr   data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+tmdasColorCode+"' >"+date+time+node+event+"</tr>");
	limitNumberOfRows($("#el_table tbody"));
	//resizeTable("el_table");
}


var updateEventTRGLdata = function(data,eventType)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.TRGL+"</td>";
	var event = "<td><a onclick='showDetailsOfEvent("+JSON.stringify(data)+")'>"+getEventType("TRGL",data.EVENT_TYPE)+"</a></td>";
	if(eventType != 'old')
		$("#el_table tbody").prepend("<tr   data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+trglColorCode+"' >"+date+time+node+event+"</tr>");
	else
		$("#el_table tbody").append("<tr   data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+trglColorCode+"' >"+date+time+node+event+"</tr>");
	limitNumberOfRows($("#el_table tbody"));
	//resizeTable("el_table");
}


var updateFalconEvent = function(data,eventType)
{
	data = calculateLatLonFromTa(data);
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var IMEI = "<td>"+data.IMEI+"</td>";
	var IMSI = "<td>"+data.IMSI+"</td>";
	var TA = "<td>"+data.TA+"</td>";
	var FREQ = "<td>"+data.FREQ+"</td>";
	var RXL = "<td>"+data.RXL+"</td>";
	var LATLONG = "<td>"+parseFloat(data.LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data.LONGITUDE).toFixed(latLonUptoPlaces)+"</td>";
	var dis= "<td>"+data.PROBABLE_DISTANCE+"</td>";
	var sector= "<td>"+data.SECTOR+"</td>";
	if(eventType != 'old')
		$("#falcon_table tbody").prepend("<tr   data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" >"+date+time+IMEI+IMSI+TA+FREQ+RXL+sector+dis+LATLONG+"</tr>");
	else
		$("#falcon_table tbody").append("<tr   data-cueid="+data.CUE_ID+" data-transId="+data.TRANS_ID+" data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" >"+date+time+IMEI+IMSI+TA+FREQ+RXL+sector+dis+LATLONG+"</tr>");
	limitNumberOfRows($("#falcon_table tbody"));
	//resizeTable("falcon_table");
}


var updateOXFAMEvent = function(data,eventType)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var description = "<td>"+data.CLASIFICATION+"</td>";
	var LATLONG = "<td>"+parseFloat(data.LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data.LONGITUDE).toFixed(latLonUptoPlaces)+"</td>";
	if(eventType != 'old')
		$("#oxfam_table tbody").prepend("<tr data-cueid="+data.TRANS_ID+" data-transId="+data.TRANS_ID+"  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" >"+date+time+LATLONG+description+"</tr>");
	else
		$("#oxfam_table tbody").append("<tr   data-cueid="+data.TRANS_ID+" data-transId="+data.TRANS_ID+" data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" >"+date+time+LATLONG+description+"</tr>");
	limitNumberOfRows($("#oxfam_table tbody"));
	//resizeTable("oxfam_table");
}


var getPopUpHtml = function(eventData,type)
{
	var html = "<div style='width:500px'><table class='table e_tab alarm_popup'><thead></thead><tbody>";	
	html +="<tr><td>DEVICE IP</td><td>"+((type=="UGS")?eventData.ip:eventData.DEVICE_IP)+"</td></tr>";
	html +="<tr><td>TIME STAMP</td><td>"+eventData.TIME_STAMP+"</td></tr>";
	html +="<tr><td>LATITUDE</td><td>"+eventData.LATITUDE+"</td></tr>";
	html +="<tr><td>LONGITUDE</td><td>"+eventData.LONGITUDE+"</td></tr>";
	
	
	if(type=="UGS")
	{
		html +="<tr><td>DEVICE_ID</td><td>"+eventData.DEVICE_ID+"</td></tr>";
		html +="<tr><td>SIGNAL_STRENGTH</td><td>"+eventData.SIGNAL_STRENGTH+"</td></tr>";
		html +="<tr><td>CLASIFICATION</td><td>"+eventData.CLASIFICATION+"</td></tr>";
	}
	
	
	if(type=="TRGL")
	{
		html +="<tr><td>FREQ</td><td>"+eventData.FREQ+"</td></tr>";
		html +="<tr><td>ANGLE</td><td>"+eventData.ANGLE+"</td></tr>";
		html +="<tr><td>POWER</td><td>"+eventData.POWER+"</td></tr>";
		
		html +="<tr><td>PROBABLE_DISTANCE</td><td>"+eventData.PROBABLE_DISTANCE+"</td></tr>";
	}
	
	
	if(type=="TMDAS")
	{
		html +="<tr><td>TA</td><td>"+eventData.TA+"</td></tr>";
		html +="<tr><td>PLMN</td><td>"+eventData.PLMN+"</td></tr>";
		html +="<tr><td>OPERATOR</td><td>"+eventData.OPERATOR+"</td></tr>";
		html +="<tr><td>IMSI</td><td>"+eventData.IMSI+"</td></tr>";
		html +="<tr><td>IMEI</td><td>"+eventData.IMEI+"</td></tr>";
		html +="<tr><td>FREQ</td><td>"+eventData.FREQ+"</td></tr>";
		html +="<tr><td>RxL</td><td>"+eventData.RXL+"</td></tr>";
		html +="<tr><td>SECTOR</td><td>"+eventData.SECTOR+"</td></tr>";
	}
	
	
	html += "</tbody></table></div>";
	
	return html;
}

var openAssociatedPopup = function(object)
{
	
	object.openPopup();
}

var getEventType = function(type,eventType)
{
	
	return eventsMap[type][eventType];
}


var limitNumberOfRows = function(element)
{
	if(element.children().length >500)
	{
		element.children().last().remove();
	}
}




function Time()
{
	var date=new Date();
	var time=date.toLocaleTimeString();
	$.ajax({
		url:serviceUrls.startupTime,
		success:function(data)
		{
			$("#current_time").html(data);
		}
	});
	
	
}

var contextMenu = function()
{

	$.contextMenu({
        selector: '.rightClick', 
        callback: function(key, options) {
            var m = "clicked: " + key;
            console.log(options.$trigger.data("transid"));
            alarmData = 
            {
            		"ID": options.$trigger.data("dataid"),
            		"DEVICE_ID":options.$trigger.data("id"),
            		"TRANS_ID": options.$trigger.data("transid"),
            		"ACTION": "1",
            		"NODE":options.$trigger.data("devicetype")
            }
            
            $.ajax({
    	        url: serviceUrls.ackAlarm,
    	        type:"post",
    	        data:JSON.stringify(alarmData),
    	        contentType: 'application/json',
    	        xhrFields: {
    	            responseType: 'blob'
    	        },
    	        success: function (data) 
    	        {
    	        	if(data == 1)
    	        	{
    	        		location.reaload();
    	        	}
    	        	else
    	        	{
    	        		alert("Unable to acknowledge");
    	        	}
    	        }
    	    });
            
            
        },
        items: {
            "Acknowledge": {name: "Acknowledge"}
            /*"cut": {name: "Cut", icon: "cut"},
           copy: {name: "Copy", icon: "copy"},
            "paste": {name: "Paste", icon: "paste"},
            "delete": {name: "Delete", icon: "delete"},
            "sep1": "---------",
            "quit": {name: "Quit", icon: function(){
                return 'context-menu-icon context-menu-icon-quit';
            }}*/
        }
    });
}

var showDetailsOfEvent = function(data)
{
	console.log(data);
	$("#event_detail .modal-body").html("");
	var html="<table><tbody>";
	
	for(var i in data)
	{
		html += ("<tr><td>"+i+"</td><td>"+data[i]+"</td></tr>");
	}
	
	html += ("</tbody></tale>");
	$("#event_detail .modal-body").append(html);
	//$("#event_detail .modal-body").html(data);
	$("#event_detail").modal("show");
	
}

var setLegends = function()
{
	$("#legend_ugs_image").attr({"src":images.UGS.ugs_up});
	$("#legend_trgl_image").attr({"src":images.TRGL.trgl_up});
	$("#legend_tmdas_image").attr({"src":images.TMDAS.tmdas_up});
}

var getCells = function()
{
	$.ajax({
		url:serviceUrls.cells,
		dataType:"json",
		success:function(data)
		{
			towerLayer.clearLayers();
			for(var i in data)
			{
				placeTowers(data[i]);
			}
		}
	});
}

var getCuesData = function()
{
	$.ajax({
		url:serviceUrls.cues,
		dataType:"json",
		success:function(data)
		{
			
			for(var i in data)
			{
				/*var today =  new Date(data[i].DATE);
				var date = today.getDate()<10?'0'+today.getDate():today.getDate();
				var month = today.getMonth()<9?'0'+(today.getMonth()+1):(today.getMonth()+1);
				var dateTime =today.toLocaleString("en-GB").split(" ");
				var date = today.getFullYear()+"-"+month+"-"+date;
				var time = dateTime[1];
				data[i].DATE= date+" "+time;*/
				updateCueTable(data[i],'old');
				
				
			}
			resizeTable("cue_table");
		}
	});
}

var resizeTable = function(id)
{
	
    
	$("#"+id).width($("#"+id+" tbody").width());
	$("#"+id+" thead").width($("#"+id+" tbody").width());
	$bodyCells = $("#"+id).find('tbody tr:first').children();
	
	// Get the tbody columns width array
    var colWidth = $bodyCells.map(function() {
        return $(this).width();
    }).get();
    
    // Set the width of thead columns
    $("#"+id).find('thead tr').children().each(function(i, v) {
        $(v).width(colWidth[i]);
    });   
}

var resizeTable1 = function(id)
{
	
    
	$i = 0;
	$("#"+id+" tbody tr:first-of-type td").each(function()
  {
    $i++;
    width = $(this).outerWidth();
    $("#"+id+" thead th:nth-of-type(" +$i+")").css({"min-width": width+"px"});
  });      
}

var createPoupHtmlForCell = function(data)
{
	var html = "<table class='table e_tab'><tbody>";
			html += "<tr><td>Country</td><td>"+data.country+"</td></tr>";
			html += "<tr><td>Operator</td><td>"+data.operators+"</td></tr>";
			html += "<tr><td>PLMN</td><td>"+data.mcc+data.mnc+"</td></tr>";
			html += "<tr><td>LAC</td><td>"+data.lac+"</td></tr>";
			html += "<tr><td>CELL</td><td>"+data.cell+"</td></tr>";
			html += "<tr><td>RXL</td><td>"+data.rssi+"</td></tr>";
			html += "<tr><td>Location</td><td>"+data.lat+","+data.lon+"</td></tr>";
	html+= "</tbody></table>";
	return html;
}

var createPoupHtmlForNode = function(data)
{
	var html = "<table class='table e_tab'><tbody>";
			html += "<tr><td>Country</td><td>"+data.country+"</td></tr>";
			html += "<tr><td>Operator</td><td>"+data.operators+"</td></tr>";
			html += "<tr><td>PLMN</td><td>"+data.mcc+data.mnc+"</td></tr>";
			html += "<tr><td>LAC</td><td>"+data.lac+"</td></tr>";
			html += "<tr><td>CELL</td><td>"+data.cell+"</td></tr>";
			html += "<tr><td>RXL</td><td>"+data.rssi+"</td></tr>";
			html += "<tr><td>Location</td><td>"+data.lat+","+data.lon+"</td></tr>";
	html+= "</tbody></table>";
	return html;
}	

var calulateLatLongAtGivenAngleAndDistance_TA = function(lat,lng,angle,distance)
{
	
	return L.GeometryUtil.destination(L.latLng(lat,lng),angle,distance);
}

var getSystemLocation = function()
{
	var centerFlag = false;
	
	/*
	 * 
	 * Changed As per disscusion with sir tomake trgl master @1-8-19
	 *
	 * if(inventory.TMDAS != undefined && Object.keys(inventory.TMDAS).length>0)
	{
		for(var i in inventory.TMDAS)
		{
			if(Object.keys(inventory.TMDAS[i]).length >0)
			{
				for(var j in inventory.TMDAS[i])
				{
					if(parseInt(inventory.TMDAS[i][j].LATITUDE) != 0 && parseInt(inventory.TMDAS[i][j].LONGITUDE) !=0)
						return {"LATITUDE":inventory.TMDAS[i][j].LATITUDE,"LONGITUDE":inventory.TMDAS[i][j].LONGITUDE,"OFFSET":inventory.TMDAS[i][j].OFFSET}
				}	
			}
		}
	}*/
	
	
	if(inventory.TRGL != undefined && Object.keys(inventory.TRGL).length>0)
	{
		for(var i in inventory.TRGL)
		{
			if(Object.keys(inventory.TRGL[i]).length >0)
			{
				for(var j in inventory.TRGL[i])
				{
					if(parseInt(inventory.TRGL[i][j].LATITUDE) != 0 && parseInt(inventory.TRGL[i][j].LONGITUDE) !=0)
						return {"LATITUDE":inventory.TRGL[i][j].LATITUDE,"LONGITUDE":inventory.TRGL[i][j].LONGITUDE,"OFFSET":inventory.TRGL[i][j].OFFSET}
				}	
			}
		}
	}
	 
	
	
	
	
	if(!centerFlag)
	//else
	{
	/*
	 * 
	 * Changed As per disscusion with sir tomake trgl master @1-8-19
	 *
	 *if(inventory.TRGL != undefined && Object.keys(inventory.TRGL).length>0)
		{
			for(var i in inventory.TRGL)
			{
				if(Object.keys(inventory.TRGL[i]).length >0)
				{
					for(var j in inventory.TRGL[i])
					{
						if(parseInt(inventory.TRGL[i][j].LATITUDE) != 0 && parseInt(inventory.TRGL[i][j].LONGITUDE) !=0)
							return {"LATITUDE":inventory.TRGL[i][j].LATITUDE,"LONGITUDE":inventory.TRGL[i][j].LONGITUDE,"OFFSET":inventory.TRGL[i][j].OFFSET}
					}	
				}
			}
		}*/
		if(inventory.TMDAS != undefined && Object.keys(inventory.TMDAS).length>0)
		{
			for(var i in inventory.TMDAS)
			{
				if(Object.keys(inventory.TMDAS[i]).length >0)
				{
					for(var j in inventory.TMDAS[i])
					{
						if(parseInt(inventory.TMDAS[i][j].LATITUDE) != 0 && parseInt(inventory.TMDAS[i][j].LONGITUDE) !=0)
							return {"LATITUDE":inventory.TMDAS[i][j].LATITUDE,"LONGITUDE":inventory.TMDAS[i][j].LONGITUDE,"OFFSET":inventory.TMDAS[i][j].OFFSET}
					}	
				}
			}
		}
		
		if(!centerFlag)
		{
			for(var i in inventory.UGS)
			{
				if(Object.keys(inventory.UGS[i]).length >0)
				{
					for(var j in inventory.UGS[i])
					{
						return {"LATITUDE":inventory.UGS[i][j].LATITUDE,"LONGITUDE":inventory.UGS[i][j].LONGITUDE,"OFFSET":inventory.UGS[i][j].OFFSET}
					}	
				}
			}
		}
	}
	return {"LATITUDE":0.0,"LONGITUDE":0.0,"OFFSET":0}
	
}

var calculateLatLonFromTa = function(data)
{
	
	try
	{
		var a = getSystemLocation();
		if(parseFloat(data.LATITUDE) == -1.1 || parseFloat(data.LONGITUDE) == -1.1 )
		{
			var distance = data.PROBABLE_DISTANCE;
			/*if(data.TECH != undefined)			
			{
				if(data.TECH == '3G')
				{
					distance=555;
				}
			}*/
			var b = calulateLatLongAtGivenAngleAndDistance_TA(a.LATITUDE,a.LONGITUDE,(a.OFFSET+data.ANGLE),distance);
			data.LATITUDE=b.lat;
			data.LONGITUDE=b.lng;
		}

	}
	catch(err)
	{
		
	}
		
	return data;
}

var placeHistoryTableData = function(dd)
{
	$("#el_table tbody").append(dd.eventTable);
	$("#falcon_table tbody").append(dd.falconTable);
	$("#oxfam_table tbody").append(dd.oxfamTable);
	resizeAllTable();
}


var historyEventWorker = function()
{
	historyEventWR = new Worker('js/startup.js');
	
	// listen to message event of worker
	historyEventWR.addEventListener('message', function(event) 
	{
	    var dd = jQuery.parseJSON(event.data);
	    //do your chart work here
	    placeHistoryTableData(dd);
	});
	// listen to error event of worker
	historyEventWR.addEventListener('error', function(event) {
	    console.error('error received from workerFor => ', event);
	});
}

var resizeAllTable =function()
{
	resizeTable("bl_table");
	resizeTable("el_table");
	resizeTable("falcon_table");
	resizeTable("oxfam_table");
	resizeTable("cue_table");
	//resizeTable1("cue_table");
}

var updateCueTable = function(data,eventType)
{
	
	var today =  new Date(data.DATE);
	var date = today.getDate()<10?'0'+today.getDate():today.getDate();
	var month = today.getMonth()<9?'0'+(today.getMonth()+1):(today.getMonth()+1);
	var dateTime =today.toLocaleString("en-GB").split(" ");
	var date = today.getFullYear()+"-"+month+"-"+date;
	var time = dateTime[1];
	data.DATE= date+" "+time;
	
	var dateTime = data.DATE.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+data.NODE+"</td>";
	var source = "<td>"+data.SOURCE+"</td>";
	var detail = "<td>"+data.DETAIL+"</td>";
	//var detail = "<td data-detail="+data.DETAIL+"><a onclick='showCueDetail(this)'>Detail</a></td>";
	var cueId = "<td>"+data.CUE_ID+"</td>";
	color="";
	switch(data.nodeType)
	{
		case "UGS":
			color=ugsColorCode;
		break;
		case "TMDAS":
			color=tmdasColorCode;
		break;
		case "TRGL":
			color=trglColorCode;
		break;
	}
	
	var tr = "<tr  data-cueid="+data.CUE_ID+"  style='background:"+color+"' >"+date+time+node+cueId+source+detail+"</tr>";
	if(eventType != 'old')
		$("#cue_table tbody").prepend(tr);
	else
		$("#cue_table tbody").append(tr);
	
	//resizeTable1("cue_table");
}

var heighLightMarker = function(cueId)
{
	heighightCircleLayer.clearLayers();
	for(var i in alarmMarkers)
	{
		if(alarmMarkers[i].cueId == cueId)
		{
			createCircle(radiusBelowMarker,heightLightColor,alarmMarkers[i].getLatLng(),heighightCircleLayer);
			alarmMarkers[i].setIcon(createIconObject(images[alarmMarkers[i].type].spotlight,marker_width,marker_height));
		}
	}
}



var unselectHeighlighted = function()
{
	for(var i in alarmMarkers)
	{
		$('*[data-cueid="'+alarmMarkers[i].cueId+'"]').each(function(){
			$(this).removeClass("heighlightalarm");
		});
		$('*[data-cueid="'+alarmMarkers[i].cueId+'"]').each(function(index, value){
			$(value).removeClass("heighlightalarm");
			//$('*[data-cueid="'+alarmMarkers[i].cueId+'"]')[index].scrollIntoView();
		});
		alarmMarkers[i].setIcon(createIconObject(images[alarmMarkers[i].type].normal,marker_width,marker_height));
		heighightCircleLayer.clearLayers();
	}
}

var blinkImage = function(peivousTimeOutObj,timeOut,icon,marker)
{
	//clearTimeout(peivousTimeOutObj);
	myVar = setTimeout(function(){
		marker.setIcon(icon)
	}, timeOut);
} 


var removeLastTransIdObjects = function(id)
{
	$('*[data-transid="'+id+'"]').remove();
	for(var i in alarmMarkers)
	{
		if(alarmMarkers[i].transid == id)
		{
			alarmMarkers[i].remove();
		}
	}
	
}

var showCueDetail = function(element)
{
	var data = $(element).data("detail");
	$.alert({
	    title: '',
	    content: data,
	});
}



/***************************************CMS*****************************************************************/
$(document).ready(function()
{	
	getInventory();
	fillNodeDataInForm()
	registeredEvents();
	//getEventsData();
	connectSocket();
	initData();
	/*for(var i=0;i<=10;i++){
	var tt = "<tr>"+
								  	  "<td scope='col'>2019-01-01</td>"+
								      "<td scope='col'>59:59:59</td>"+
								      "<td scope='col'>1234567890123456</td>"+
								      "<td scope='col'>1234567890123456</td>"+
								      "<td scope='col'>1</td>"+
								     " <td scope='col'>123</td>"+
								     " <td scope='col'>-123</td>"+
								      "<td scope='col'>12345678,12345678</td>"+
							  	  "</tr>";
	
	
	$("#falcon_table tbody").append(tt);}
	resizeTable("falcon_table");*/
	
	

	/*$("#nodes").PopupWindow({
        autoOpen    : false,
        width:250
    });
	$("#nodes").PopupWindow("open");*/
	
});





