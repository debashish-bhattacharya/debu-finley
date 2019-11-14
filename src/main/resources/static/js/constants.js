/**
 * @author Sunil Kumar
 * Constants for application
 */

//Rounds the latitude and longitude up to configured value throughout the application
var latLonUptoPlaces = 5;

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
			"ugs_normal":"images/Marker-O1.png",
			"spotlight":"images/spotlight.png",
			"normal":"images/Marker-O1.png"
	},
	"TMDAS": 
	{
			"tmdas_up":"images/Marker-F1.png",
			"tmdas_down":"images/Marker-F1.png",
			"tmdas_detect":"images/f.gif",
			"tmdas_normal":"images/Marker-F1.png",
			"spotlight":"images/spotlight.png",
			"normal":"images/Marker-F1.png"
			
	},
	"TRGL": 
	{
			"trgl_up":"images/Marker-H1.png",
			"trgl_down":"images/Marker-H1.png",
			"trgl_detect":"images/h.gif",
			"trgl_normal":"images/Marker-H1.png",
			"spotlight":"images/spotlight.png",
			"normal":"images/Marker-H1.png"
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
	},
	"SYSTEM":
	{
		"center":"images/adu.png"
	}
};

var nodesStatus = 
{
		"RUNNING":"OK",
		"WAITING FOR CONFIG":"OK",
		"NOT REACHABLE":"DOWN",
		"SYSTEM DOWN":"OK",
		"Reachable":"OK",
		"running":"OK",
		"waiting for config":"OK",
		"not reachable":"DOWN",
		"system down":"OK",
		"reachable":"OK"
		
		
		
}

var blinkTime = 
{
		ugs:30000,
		tmdas:30000,
		trgl:30000
}

var trglColorCode  = "#78c8f3";
var ugsColorCode  = "#ffce42";
var tmdasColorCode  = "#939497";
var aduColorCode = "orange";
var ugsCoverageCircleColor = "skyblue";

var nodeDownColor = "rgb(240, 141, 144)";
var nodeUpColor = "rgb(198, 227, 159)";

var radiusBelowMarker = 2;
var yesterdayColor="pink";
var dayBeforeYestrdayColor="grey";

var heightLightColor = "#FF0000";

var mapRotationOffset=-90;

var ROTATE_MAP = true;
