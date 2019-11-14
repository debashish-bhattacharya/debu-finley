/*************************************************constants***************************************************/


var connectSocket = function()
{
	
	var stompClient = null;


	var socket = new SockJS(endPoint.socket);
	
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function (frame) 
    {
    		stompClient.subscribe(topics.status, function (data) 
    	    		{
    	    			if(data.body != "")
    	    			{
    	    				var data = jQuery.parseJSON(data.body);
    	    				var statusColor = (data.status=="Down")?nodeDownColor:nodeUpColor;
    	    				$("#"+data.type+"_status .statusClass span").text(data.status);
    	    				$("#"+data.type+"_status").css("background",statusColor);
    	    			}
    	    		});
    });
}




var registeredEvents = function()
{
	$('#GetFile').on('click', function () {
		
		var startTime = $("#rpt_startDate").val()+":00";
		var stopTime = $("#rpt_endDate").val()+":59";
		var reportData = 
		{
				"START_TIME":startTime.split("/").join("-"),//+" 00:00:00",
				"STOP_TIME":stopTime.split("/").join("-"),//+" 23:59:59",
				"REPORT_TYPE":$("#report_type").val()
		}
		
		console.log($("#node_type").val());
		if($("#node_type").val()=="TRGL" || $("#node_type").val()=="ALL"){
//			Date date1 = new Date($("#rpt_startDate").val());
//			Date date2 = new Date($("#rpt_endDate").val());
			
			var hours = Math.abs(Date.parse($("#rpt_startDate").val()) - Date.parse($("#rpt_endDate").val())) / 36e5;
			if(hours>1){
				alert("For Hummer please download report for less than 1 hours");
				return;
			}
		}
		
	    $.ajax({
	        url: serviceUrls.report+$("#node_type").val(),
	        type:"post",
	        data:JSON.stringify(reportData),
	        contentType: 'application/json',
	        xhrFields: {
	            responseType: 'blob'
	        },
	        beforeSend:function()
			{
				$("#test_modal").modal({
					  keyboard: false,
					  backdrop:"static",
					  show:true
				});
			},
	        success: function (data) {
	            var a = document.createElement('a');
	            var url = window.URL.createObjectURL(data);
	            a.href = url;
	            a.download = 'reports.zip';
	            a.click();
	            window.URL.revokeObjectURL(url);
	        },
	        complete:function()
			{
	        	setTimeout(function(){
	    	    	$("#test_modal").modal("hide");
	    	    }, 500);
			}
	    });
	});
	
	
	
	
	$('#rpt_startDate').datetimepicker();
	$('#rpt_endDate').datetimepicker();
	/*{ 
		//format: 'yyyy-mm-dd',
                }*/
	
	$(".testbtn").click(function(){
		
		var url = $(this).data("url");
		startTest(url,this);
	});
	
	$("#test_modal_data").on('show.bs.modal', function (e) {
	    setTimeout(function(){
	    	$("#test_modal").modal("hide");
	    }, 500);
	});
	
}



var startTest = function(url,element)
{
	var serverUrl = serviceUrls[url];
	var requestData={}
	if(serverUrl == serviceUrls.testadu)
	{
		requestData={"node":$(element).data("node"),"cmd":$(element).data("cmd")}
	}
	
	$.ajax({
		url:serviceUrls[url],
		data:requestData,
		dataType:"json",
		beforeSend:function()
		{
			$("#test_modal").modal({
				  keyboard: false,
				  backdrop:"static",
				  show:true
			});
		},
		success:function(data)
		{
			
			if(serverUrl == serviceUrls.testips)
			{
				createTableForIPS(data);
			}
			else
			{
				createTableForModal(data);
			}
			
		},
		complete:function()
		{
			$("#test_modal").modal("hide");
		}
	});
}

var createTableForModal = function(data)
{
	var count = 0;
	var table = "";
	for(var i in data)
	{
		var th = "";
		var td = "";
		var colCount = 0;
		for(var j in data[i])
		{
			if(count == 0)
			{
				th+="<th style='text-transform: uppercase;'>"+j+"</th>";
			}
			td +="<td>"+data[i][j]+"</td>";
		}
		if(count == 0)
		{
			table +="<thead>"+th+"</thead>";
		}
		table +="<tr>"+td+"</tr>";
		count++;
		
	}
	
	$("#test_modal_data .modal-body").html("");
	$("#test_modal_data .modal-body").append("<table class='table e_tab'>"+table+"</table>");
	$("#test_modal_data").modal("show");
}


var createTableForIPS = function(data)
{
	var count = 0;
	var table = "";
	table += "<table class='table e_tab'><thead><th>Name</th><th>IP</th><th>Status</th></thead><tbody>";
	for(var i=0;i<data.length;i++)
	{
		var tr = "";
		//if(i!=0)
		//{
			tr += "<tr><td style='background:black;color:white;text-align:center;' colspan=3>"+data[i].NAME+"</td></tr>";
		//}
		tr += "<tr><td>"+data[i].NAME+"</td><td>"+data[i].IP+"</td><td>"+data[i].RESULT+"</td></tr>"
		
		//if(data[i].TYPE == "UGS")
			//data[i].SUB_DATA = {"ip":"192.168.6.2","DeviceInfo":[{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":1,"LATITUDE":28.524796056231434,"LONGITUDE":77.09036976099014},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":2,"LATITUDE":28.524687651789943,"LONGITUDE":77.0902705192566},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":3,"LATITUDE":28.525173114290133,"LONGITUDE":77.08992183208466},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":2,"DEVICE_ID":4,"LATITUDE":28.525168401072712,"LONGITUDE":77.09010154008867},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":2,"DEVICE_ID":5,"LATITUDE":28.525140121763823,"LONGITUDE":77.09029197692873},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":6,"LATITUDE":28.524642876009835,"LONGITUDE":77.09064066410065}]};
		if(data[i].SUB_DATA != null)
		{
			if(data[i].TYPE != "UGS")
			{
				for(var k in data[i].SUB_DATA)
				{
					tr += "<tr><td>"+data[i].SUB_DATA[k].Name+"</td><td>"+data[i].SUB_DATA[k].IP+"</td><td>"+nodesStatus[(data[i].SUB_DATA[k].Status).toLowerCase()]+"</td></tr>";
					
				}
			}
			else
			{
				
				tr += createIPStatusTableUGS(data[i].SUB_DATA.DeviceInfo)
			}
		}
		table +=tr;
		
		
		
	}
	$("#test_modal_data .modal-body").html("");
	$("#test_modal_data .modal-body").append("<table class='table e_tab'>"+table+"</table>");
	$("#test_modal_data").modal("show");
}


function validateIPaddress(ipaddress) 
{
	if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ipaddress))
	{
		return (true)
	}
	$.alert({
	    title: 'Error!',
	    content: 'You have entered an invalid IP address!',
	});
return (false)
}


var updateIpOfNodes = function(nodeId,nodeElementId)
{
	if(validateIPaddress($("#"+nodeElementId).val())){
		
		var flag=false;
		$.each(ips, function(index, value){
			if(value == $("#"+nodeElementId).val())
			{
				flag=true;
			}
		});
		
		if(flag==true){
			$.alert({
			    title: 'Error!',
			    content: 'This IP Already exist!',
			});	
		}
		else{
			$.confirm({
			    title: 'Confirm!',
			    content: 'Are you sure?',
			    buttons: {
			        confirm: function () {
				        	$.ajax(
			        			{
			        				url:serviceUrls.updateNodes,
			        				data:{"nodeId":nodeId,"nodeIp":$("#"+nodeElementId).val()},
			        				success:function(data)
			        				{
			        					location.reload();
			        				}
			        			});
			        },
			        cancel: function () 
			        {
			            //$.alert('Canceled!');
			        }
			    }
			});
		}
	}
}

var restart = function(nodeId,nodeElementId)
{
	$.confirm({
	    title: 'Confirm!',
	    content: 'Are you sure?',
	    buttons: {
	        confirm: function () {
	        	$.ajax(
	        			{
	        				url:serviceUrls.restart,
	        				data:{"id":nodeId,"ip":$("#"+nodeElementId).val()},
	        				success:function(data)
	        				{
	        					//location.reload();
	        				}
	        			});
	        },
	        cancel: function () {
	            //$.alert('Canceled!');
	        }
	    }
	});
}


var inventory = function(nodeId,nodeElementId)
{
	
	var dataUrl = serviceUrls.subInventory; 
	if(nodeId == "1")
	{
		dataUrl = serviceUrls.ugsInventory;
	}
	
	$.confirm({
	    content: function () {
	    	var self = this;
	    	return $.ajax({
	        	url:dataUrl,
	        	dataType:"json",
	        	data:{"id":nodeId,"ip":$("#"+nodeElementId).val()},
	        }).done(function (response) {
	        	self.close();
	        	if(nodeId == "1")
	        	{
	        		//response = {"ip":"192.168.6.2","DeviceInfo":[{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":1,"LATITUDE":28.524796056231434,"LONGITUDE":77.09036976099014},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":2,"LATITUDE":28.524687651789943,"LONGITUDE":77.0902705192566},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":3,"LATITUDE":28.525173114290133,"LONGITUDE":77.08992183208466},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":2,"DEVICE_ID":4,"LATITUDE":28.525168401072712,"LONGITUDE":77.09010154008867},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":2,"DEVICE_ID":5,"LATITUDE":28.525140121763823,"LONGITUDE":77.09029197692873},{"DEVICE_TYPE":1,"COVERAGE":0,"ip":null,"STATE":1,"DEVICE_ID":6,"LATITUDE":28.524642876009835,"LONGITUDE":77.09064066410065}]};
	        		createTableForModalForUGSInventory(response.DeviceInfo)
	        	}
	        	else
	        	{
	        		createTableForModal(response);
	        	}
	        	
	        }).fail(function(){
	        	self.setTitle("Fail");
	            self.setContent('Something went wrong.');
	        });
	    }
	});

}


var createTableForModalForUGSInventory = function(data)
{
	var count = 0;
	var table = "";
	
	
	var th = "";
	
	var colCount = 0;

	th+="<th>ID</th>";
	th+="<th>Name</th>";
	th+="<th>location</th>";
	th+="<th>Staus</th>";
	table +="<thead>"+th+"</thead><tbody>";
	table +=createInventoryStatusTableUGS(data);

	table +="</tbody>";
	
	$("#test_modal_data .modal-body").html("");
	$("#test_modal_data .modal-body").append("<table class='table e_tab'>"+table+"</table>");
	$("#test_modal_data").modal("show");
}

var createInventoryStatusTableUGS = function(data)
{
	
	var deviceType=[];
	deviceType[1]="UGS";
	var tr = "";
	for(var i in data)
	{
		var td = "";
		td +="<td>"+data[i].DEVICE_ID+"</td>";
		td +="<td>"+deviceType[data[i].DEVICE_TYPE]+"</td>";
		td +="<td>"+parseFloat(data[i].LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data[i].LONGITUDE).toFixed(latLonUptoPlaces)+"</td>";
		td +="<td>"+((data[i].STATE=="1")?"UP":"DOWN")+"</td>";
		tr +="<tr>"+td+"</tr>";
	}
	return tr;

}

var createIPStatusTableUGS = function(data)
{
	
	var deviceType=[];
	deviceType[1]="UGS";
	var tr = "";
	for(var i in data)
	{
		var td = "";
		td +="<td>"+deviceType[data[i].DEVICE_TYPE]+"</td>";
		td +="<td>"+data[i].DEVICE_ID+"</td>";
		//td +="<td>"+parseFloat(data[i].LATITUDE).toFixed(latLonUptoPlaces)+","+parseFloat(data[i].LONGITUDE).toFixed(latLonUptoPlaces)+"</td>";
		td +="<td>"+((data[i].STATE=="1")?"UP":"DOWN")+"</td>";
		tr +="<tr>"+td+"</tr>";
	}
	return tr;

}







/***************************************CMS*****************************************************************/
$(document).ready(function()
{	
	registeredEvents();
	connectSocket();
	$(".operation").addClass("active");	
});

/*********************************************************************************************************/

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
