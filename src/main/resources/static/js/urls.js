var apiPrefix = "api";
var inventoryApiPrefix = "inventory";
var reprotsApiPrefix = "reports";
var bistPrefix = "bist";
var eventPrefix = "event";

var serviceUrls=
{
		"subInventory":apiPrefix+"/subinventory",
		"node":apiPrefix+"/node",
		"restart":apiPrefix+"/restart",
		"startupTime":apiPrefix+"/system/start_time",
		"inventory":inventoryApiPrefix+"/",
		"nodes":inventoryApiPrefix+"/nodes/",
		"ugsInventory":inventoryApiPrefix+"/ugs/",
		"updateNodes":inventoryApiPrefix+"/updateNodes/",
		"cells":inventoryApiPrefix+"/cells/",
		"report":reprotsApiPrefix+"/",
		"event":reprotsApiPrefix+"/event",
		"ackAlarm":reprotsApiPrefix+"/ackAlarm",
		"testips":bistPrefix+"/testips",
		"testadu":bistPrefix+"/testadu",
		"testpower":bistPrefix+"/testpower",
		"cues":eventPrefix+"/cues",
		"updateAngle":apiPrefix+"/updateAngle",
		"getCurrentAngle":apiPrefix+"/getCurrentAngle"
}



/*var serviceUrls=
{
		"getNodesData":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/getDevicesList",
		"getNodeConfig":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/getConfigDataByID",
		"addNode":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/addDevice",
		"getNodeDetails":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/getDeviceInfo",
		"updateConfig":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/recordConfigData",
		"peakData":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/getPeakInfoData",
		"ledStatus":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/getLedOnStatus",
		"startDef":"http://10.100.207.117:9000/security_server/"+apiPrefix+"/startDef"
			
}*/

var endPoint = 
{
		"socket":remoteAddr+"/gs-guide-websocket",
		//"socket":"http://1:9000/gs-guide-websocket",
}

var topics = 
{
		"status":"/topic/status",
		"event":"/topic/event",
		"reload":"/topic/reload",
		"cell":"/topic/cell",
		"statusDetail":"/topic/statusDetail",
		"que":"/topic/testingQueue",
		"cue":"/topic/cue"	
}

