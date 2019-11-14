/**
 * @sunil Kumar
 * 
 * 
 * It will crate a seprate worker thread that will be connected to socket and will recive data and place 
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
importScripts('lib/sockjs.min.js');
importScripts('lib/stomp.min.js');


var topics = 
{
		//add your topic
		"que":"/topic/testingQueue"
}

var endPoint = 
{
		
		//add your end point
		"socket":"http://localhost:9000/finley/gs-guide-websocket"
}


var hh = [];

var connectSocket = function()
{
	
	var stompClient = null;

	var socket = new SockJS(endPoint.socket);
	
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function (frame) 
    {
		stompClient.subscribe(topics.que, function (data) 
		{
			if(data.body != "")
			{	
				console.log("enter");
				self.hh.push(data.body);
				console.log(data.body);
				console.log("out");
			}
		});
    });
	
	onmessage = function(e) 
	{
		  console.log('Worker: Message received from main script');
		  var workerResult = 'Result: ' + (e.data);
		  console.log('Worker: Posting message back to main script');
		  
		  var data = 
		  {
				"length":hh.length,
		  		"data":hh[0],
		  		"tag":"sonu"
		  }
		  
		  postMessage(JSON.stringify(data));
		  hh.splice(0,1);
		}
	
}

connectSocket();