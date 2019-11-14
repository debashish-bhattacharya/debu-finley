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
	workerFor = new Worker('js/worker.js');
	
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
//importScripts('lib/jquery.cookie.js');
importScripts('lib/sockjs.min.js');
importScripts('lib/stomp.min.js');
//importScripts('urls.js');


var topics = 
{
		//add your topic
		"node":"api/node",
		"que":"/topic/PeakInfo"
}

var endPoint = 
{
		
		//add your end point
		"socket":location.protocol+"//"+location.host+"/gs-guide-websocket"
}

var hh = [];




var connectSocket = function(ip,url )
{
	
	var stompClient = null;
	
	
	
	endPoint.socket="http://"+ip+""+url+"/gs-guide-websocket";

	var socket = new SockJS(endPoint.socket);
	
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function (frame) 
    {
		stompClient.subscribe(topics.que, function (data) 
		{
			if(data.body != "")
			{	
				//var statusBody = jQuery.parseJSON(data.body);
				//console.log("enter");
				if(hh.length>20){
					hh.splice(-1,1);
				}
				hh.push(data.body);
				//console.log(data.body);
				//console.log("out");
			}
		});
    }, function(error) {
        console.log("@sunil"+error);
    });
	
	onmessage = function(e) 
	{
		  //console.log('Worker: Message received from main script');
		  var workerResult = 'Result: ' + (e.data);
		  //console.log('Worker: Posting message back to main script');
		  
		  if(hh.length>0){
			  var data = 
			  {
					"length":hh.length,
					"data":hh[0],
					"tag":"sonu"
			  }
			  
			  self.postMessage(JSON.stringify(data));
			  hh.splice(0,1);
		  }
		  else{
			  var data = 
			  {
					"length":hh.length,
			  }
			  
			  self.postMessage(JSON.stringify(data));
			  hh.splice(0,1);
		  }
		}
	
}

//connectSocket();
$.ajax({
	url:"../"+topics.node+"/TRGL",
	dataType:"json",
	success:function(data)
	{
		connectSocket(data.nodeIp,data.url)
	}
	
});