/**
 * @sunil Kumar
 * 
 * 
 * It will create a seprate worker thread that will be connected to socket and will receive data and place 
 * inside the que
 * 
 *  main thread will post a message to worker thread to get the data
 *  
 *  it will take out the first element from the que send back to main thread
 *  
 *  
 *  inside your chart or ending of calculation function place the following line 
 *  workerFor.postMessage("Marco!");
 * */

/*************************************/
/**
 * Bellow commented code will be used inside main js that will initate the worker thread
 * 
 */

/*
 var workerFor = null;
 
 
var workerExample = function()
{
	workerFor = new Worker('js/startup.js');
	
	// listen to message event of worker
	workerFor.addEventListener('message', function(event) 
	{
	    
	    var dd = jQuery.parseJSON(event.data);
	    //do your chart work here
	    
	});
	
	// listen to error event of worker
	workerFor.addEventListener('error', function(event) {
	    console.error('error received from workerFor => ', event);
	});
}

$(document).ready(function())
{
	workerExample();
	
	//call your char work here function  here
}

*
*
*
*/


/**
 * @author Sunil Kumar
 * Constants for application
 */

//Rounds the latitude and longitude up to configured value throughout the application
var latLonUptoPlaces = 4;

//Max Number of rows a table can have 
var maxRowsInTables = 500;

//Node Display name mapping
var nodeName =
{
		"TRGL":"Hummer",
		"TMDAS":"Falcon",
		"UGS":"Oxfam"
}

//Images used in application
var images =
{ 
	"UGS": 
	{
			"ugs_up":"images/Marker-O1.png",
			"ugs_down":"images/Marker-O1.png",
			"ugs_detect":"images/o.gif",
			"ugs_normal":"images/Marker-O1.png"
	},
	"TMDAS": 
	{
			"tmdas_up":"images/adu.png",
			"tmdas_down":"images/adu.png",
			"tmdas_detect":"images/f.gif",
			"tmdas_normal":"images/Marker-F1.png"
			
	},
	"TRGL": 
	{
			"trgl_up":"images/adu.png",
			"trgl_down":"images/adu.png",
			"trgl_detect":"images/h.gif",
			"trgl_normal":"images/Marker-H1.png"
	},
	"MAP":
	{
		"offline":"images/offline.png",
		"online":"images/online.png",
		"compass":"images/compass.png"
			
	},
	"CELL":
	{
		"tower":"images/Marker-T1.png"
	}
};



var trglColorCode  = "#78c8f3";
var ugsColorCode  = "#ffce42";
var tmdasColorCode  = "#939497";
var aduColorCode = "orange";
var ugsCoverageCircleColor = "skyblue";

var nodeDownColor = "rgb(240, 141, 144)";
var nodeUpColor = "rgb(198, 227, 159)";




var eventsMap = 
{
		"UGS":
		{
			"1":"Detected",
			"2":"Node Up",
			"3":"Node Down",
			"4":"Node Added",
			"5":"Node Removed",
			"7":"Alarm",
		},
		"TRGL":
		{
			"1":"Intiated from HUMMER",
			"2":"Node Up",
			"3":"Node Down",
			"4":"Node Added",
			"5":"Node Removed",
			"6":"Intiated from OXFAM",
			"7":"Alarm",
		},
		"TMDAS":
		{
			"1":"Intiated from FALCON",
			"2":"Node Up",
			"3":"Node Down",
			"4":"Node Added",
			"5":"Node Removed",
			"6":"Intiated from HUMMER",
			"7":"Alarm",
		}
		
}
var document = self.document = { parentNode: null, nodeType: 9, toString: function () { return "FakeDocument" } };
var window = self.window = self;
var fakeElement = Object.create(document);
fakeElement.nodeType = 1;
fakeElement.toString = function () { return "FakeElement" };
fakeElement.parentNode = fakeElement.firstChild = fakeElement.lastChild = fakeElement;
fakeElement.ownerDocument = document;

document.head = document.body = fakeElement;
document.ownerDocument = document.documentElement = document;
document.getElementById = document.createElement = function () { return fakeElement; };
document.createDocumentFragment = function () { return this; };
document.getElementsByTagName = document.getElementsByClassName = function () { return [fakeElement]; };
document.getAttribute = document.setAttribute = document.removeChild =
    document.addEventListener = document.removeEventListener =
    function () { return null; };
document.cloneNode = document.appendChild = function () { return this; };
document.appendChild = function (child) { return child; };
document.childNodes = [];
document.implementation = {
    createHTMLDocument: function () { return document; }
}

importScripts("lib/jquery-3.3.1.min.js");
importScripts("lib/leaflet.js");
importScripts("lib/leaflet.geometryutil.js");

var eventTable = "";
var falconEventTable = "";
var oxfamEventTable = "";
var startCreateingTable = function(data)
{
	for(var i=data.length-1;i>=0;i--)
	{
		placeEvent(data[i],i);
	}
}

var placeEvent = function(data,offset)
{
	currentEventOffset = offset;
	for(var i in data)
	{
		var type = data[i].nodeType;
		var event = jQuery.parseJSON(data[i].eventData);
		event.eventId = data[i].eventId
		event.ack = data[i].acknowledged
		incomingEventHandler(type,event);
	}
}


var incomingEventHandler = function(type,data)
{
	console.log(type,data);
	switch(type)
	{
		case "UGS":
			ugsEventHadler(data);
		break;
			case "TRGL":
			trglEventHadler(data);
			break;
		case "TMDAS":
			tmdasEventHadler(data)
			break;
	}
}


var ugsEventHadler = function(data)
{
	var detectedData = Object.assign({}, data);
	if(currentEventOffset == 0)
	{
		updateOXFAMEvent(data);			
		if(data.EVENT_TYPE == 7)
		{
			//updateAlarmTableUGSdata(detectedData);
		}
		else
		{
			updateEventTableUGSdata(data);
			
		}
	}
	
}

var tmdasEventHadler = function(data)
{
	
	if(currentEventOffset == 0)
	{
		updateFalconEvent(data);
		
		if(data.EVENT_TYPE == 7)
		{
			//updateAlarmTMDASdata(detectedData);
		}
		else
		{
			updateEventTMDASdata(data);
		}
	}
}


var trglEventHadler = function(data)
{
	
	//var detectedData = data;
	if(currentEventOffset ==0)
	{
		
		if(data.EVENT_TYPE == 7)
		{
			//updateAlarmTRGLdata(detectedData);
		}
		else
		{
			updateEventTRGLdata(data);
		}
		
	}
}




var updateEventTableUGSdata = function(data)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.UGS+"</td>";
	var event = "<td><a onclick='showDetailsOfEvent("+JSON.stringify(data)+")'>"+getEventType("UGS",data.EVENT_TYPE)+"</a></td>";
	eventTable = "<tr  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+ugsColorCode+"' >"+date+time+node+event+"</tr>"+eventTable;
}

var updateEventTMDASdata = function(data)
{
	data = calculateLatLonFromTa(data);
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.TMDAS+"</td>";
	var event = "<td><a onclick='showDetailsOfEvent("+JSON.stringify(data)+")'>"+getEventType("TMDAS",data.EVENT_TYPE)+"</a></td>";
	eventTable = "<tr  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+tmdasColorCode+"' >"+date+time+node+event+"</tr>"+eventTable;
}

var updateEventTRGLdata = function(data)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var node = "<td>"+nodeName.TRGL+"</td>";
	var event = "<td><a onclick='showDetailsOfEvent("+JSON.stringify(data)+")'>"+getEventType("TRGL",data.EVENT_TYPE)+"</a></td>";
	eventTable = "<tr  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" style='background:"+trglColorCode+"' >"+date+time+node+event+"</tr>"+eventTable;
}


var updateFalconEvent = function(data)
{
	data = calculateLatLonFromTa(data);
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var IMSI = "<td>"+data.IMSI+"</td>";
	var IMEI = "<td>"+data.IMEI+"</td>";
	var TA = "<td>"+data.TA+"</td>";
	var FREQ = "<td>"+data.FREQ+"</td>";
	var RXL = "<td>"+data.RXL+"</td>";
	var LATLONG = "<td>"+parseFloat(data.LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data.LONGITUDE).toFixed(latLonUptoPlaces)+"</td>";
	falconEventTable ="<tr  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" >"+date+time+IMSI+IMEI+TA+FREQ+RXL+LATLONG+"</tr>"+falconEventTable;
}


var updateOXFAMEvent = function(data)
{
	var dateTime = data.TIME_STAMP.split(" ");
	var date = "<td>"+dateTime[0]+"</td>";
	var time = "<td>"+dateTime[1]+"</td>";
	var description = "<td>"+data.CLASIFICATION+"</td>";
	var LATLONG = "<td>"+parseFloat(data.LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data.LONGITUDE).toFixed(latLonUptoPlaces)+"</td>";
	oxfamEventTable = "<tr  data-lat="+data.LATITUDE+" data-lon="+data.LONGITUDE+" >"+date+time+LATLONG+description+"</tr>"+oxfamEventTable;
}


var calculateLatLonFromTa = function(data)
{
	
	try
	{
		var a = finleyLocation;
		if(parseFloat(data.LATITUDE) == -1.1 || parseFloat(data.LONGITUDE) == -1.1 )
		{
			var distance = 255;
			if(data.TECH != undefined)			
			{
				if(data.TECH == '3G')
				{
					distance=555;
				}
			}
			var b = calulateLatLongAtGivenAngleAndDistance_TA(a.LATITUDE,a.LONGITUDE,a.OFFSET,distance);
			data.LATITUDE=b.lat;
			data.LONGITUDE=b.lng;
		}

	}
	catch(err)
	{
		
	}
		
	return data;
}

var calulateLatLongAtGivenAngleAndDistance_TA = function(lat,lng,angle,distance)
{
	
	return L.GeometryUtil.destination(L.latLng(lat,lng),angle,distance);
}

var finleyLocation="";


var main = function()
{
	onmessage = function(e) 
	{
		  console.log('Worker: Message received from main script');
		  var workerResult = 'Result: ' + (e.data);
		  console.log(e.data);
		  finleyLocation = e.data.finley;
		  startCreateingTable(e.data.eventsData);
		  var resultData = 
		  {
				  "eventTable":eventTable,
				  "falconTable":falconEventTable,
				  "oxfamTable":oxfamEventTable
		  }
		  console.log('Worker: Posting message back to main script');
		  self.postMessage(JSON.stringify(resultData));
		  
	}
}

main();


var getEventType = function(type,eventType)
{
	
	return eventsMap[type][eventType];
}
