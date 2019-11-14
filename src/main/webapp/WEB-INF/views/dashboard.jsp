<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/lib/bootstrap.min.css" rel="stylesheet" />
	<!--  <link rel="stylesheet" href="css/lib/fontawesome-all.min.css">-->
	<link rel="stylesheet" href="css/lib/font-awesome.min.css">
	<link rel="stylesheet" href="css/lib/bootstrap-colorpicker.min.css">
	<link rel="stylesheet" href="css/lib/leaflet.css">
	<link rel="stylesheet" href="css/lib/leaflet.fullscreen.css">
	<link rel="stylesheet" href="css/lib/popupwindow.css">
	<link rel="stylesheet" href="css/lib/jquery.contextMenu.min.css">
	<link href="css/main.css" rel="stylesheet" />
	<link href="css/dashboard.css" rel="stylesheet" />
	<title></title>
</head>
<body>
<%@include file="header.jsp" %>
<div class=".container-fluid h-100_custom">
	<div class="row h-60 cont_row">
		<div class="col-7">
			<div id="map_leaf" class="box_border"></div>
    	</div>
    	<div class="col-5">
    		<div class="row h-100">
    			<div class="col-12">
    				<div class="row h-50">
    					<div class="col-12 box_border">
    					<div class="box_title">
    					<span class="head-left">Alarm</span>
  						<span class="head-right fullscreen"><i class="fa fa-window-maximize" aria-hidden="true"></i></span>
    					</div>
    					<div class="table_cont">
    						<table class="table e_tab fixed_header" id="bl_table">
							  <thead>
							    <tr>
							      <th scope="col">Date</th>
							      <th scope="col">Time</th>
							      <th scope="col">Node</th>
							      <th scope="col">Id</th>
							    </tr>
							  </thead>
							  <tbody>
							    
							  </tbody>
							</table>
    					</div>
    					</div>
    				</div>
    				<div class="row h-50">
    					<div class="col-12  box_border">
    					<div class="box_title">
    					<span class="head-left">Event Cue</span>
  						<span class="head-right fullscreen"><i class="fa fa-window-maximize" aria-hidden="true"></i></span>
    					</div>
    					<div class="table_cont table_cont_cue">
    						<table class="table e_tab fixed_header hide_container" id="el_table">
							  <thead>
							    <tr>
							      <th scope="col">Date</th>
							      <th scope="col">Time</th>
							      <th scope="col">Node</th>
							      <th scope="col">Event</th>
							    </tr>
							  </thead>
							  <tbody>
							    
							  </tbody>
							</table>
							 <!-- <table class="table pointer hover table-striped e_tab" id="cue_table" >-->
							 <table class="table e_tab fixed_header" id="cue_table" >
							  <thead>
							    <tr >
							      <th scope="col">Date</th>
							      <th scope="col">Time</th>
							      <th scope="col">Node</th>
							      <th scope="col">Id</th>
							      <th scope="col">Source</th>
							      <th scope="col">Detail</th>
							      
							    </tr>
							  </thead>
							  <tbody>
							    
							  </tbody>
							</table> 
    					</div>					
    					</div>
    				</div>	
    			</div>
    			<!-- <div class="col-5">
					<div class="row h-100">
						<div class="col-12  box_border">
							
							<div class="box_title">
							<span class="head-left">Nodes Information</span>
  			<span class="head-right fullscreen"><i class="fa fa-window-maximize" aria-hidden="true"></i></span>
							
							</div>
							<div class="staus_container">
							
								<!--  <div class="table_cont h-100">
									<table class="table e_tab" id="legend_table">
									  <thead>
									    
									  </thead>
									  <tbody>
									    <tr>
									    	<td><img src="" class="legend_image" id="legend_ugs_image" /></td>
									    	<td><span>Oxfam</span></td>
									    </tr>
									    <tr>
									    	<td><img src="" class="legend_image" id="legend_trgl_image" /></td>
									    	<td><span>Hummer</span></td>
									    </tr>
									    <tr>
									    	<td><img src="" class="legend_image" id="legend_tmdas_image" /></td>
									    	<td><span>Falcon</span></td>
									    </tr>
									  </tbody>
									</table>
									</div>
									
									
									<c:forEach var="node" items="${nodes}" >
										<div class="row top-bottom">
											<div class=col-12>
												<div class="ticketClass myBox" id="${node.nodeName}_box">	
								               		<div class="gridColumn">
								                  		<img class="imageClass" src="images/${node.nodeIcon}"></img>
								                     	<div class="gridRow">
								                     		<div><strong>${node.displayName}</strong></div>
									                        <table>
									                  			<tr><td>IP</td><td class="ip"></td></tr>
									                  			<c:if test = "${node.displayName != 'ADU'}"> 
										                  			<tr><td>Mode</td><td class="mode"></td></tr>
										                  			<tr><td>Used Space</td><td class="space"></td></tr>
									                  			</c:if>
									                  			<tr><td>Status</td><td class="statusClass"></td></tr>
								                  			</table>
								                     	</div>
								               		</div>
							               		</div>
											</div>
										</div>		
								</c:forEach>
							</div>
						</div>		
					</div>    				
    			</div>	-->	
    		</div>		
    	</div>
  	</div>
  	<div class="row h-35 cont_row">
  		<div class="col-4  box_border">
  		<div class="box_title">
  			<span class="head-left">Hummer</span>
  			<span class="head-right fullscreen"><i class="fa fa-window-maximize" aria-hidden="true"></i></span>
  			<!-- <c:out value="${nodes[1].nodeIp}${nodes[1].url}${nodes[1].loginPath}"/>-->
  			<span class="head-right"><i data-url="http://${nodes[1].nodeIp}${nodes[1].loginPath}" class="connect_hummer fa fa-gear connect_c2" aria-hidden="true"></i></span>
  			<!--  <span class="head-right">
  				<a href="https://google.com/" target="_blank">
   					 <img src="images\google.png" alt="Google Home" style="width:20px;height:20px;border:0;">
				</a>
  			</span>-->		
  		</div>
  			<canvas id="canvas"></canvas>
  		</div>
  		<div class="col-4">
  		<div class="box_title">
  			<span class="head-left">Falcon</span>
  			<span class="head-right fullscreen"><i class="fa fa-window-maximize" aria-hidden="true"></i></span>
  			<span class="head-right"><i data-url="http://${nodes[2].nodeIp}${nodes[2].loginPath}" class="connect_falcon fa fa-gear connect_c2" aria-hidden="true"></i></span>
  		</div>
  									<div class="table_cont box_border" id="falcon_table_container">
    						   <table class="table e_tab fixed_header" id="falcon_table">
							 <!-- <table class="table e_tab" id="falcon_table">-->
							  	<thead>
							  		<th scope="col">Date</th>
							       	<th scope="col">Time</th>
							      	<th scope="col">IMEI</th>
							      	<th scope="col">IMSI</th>
							      	<th scope="col">TA</th>
							      	<th scope="col">Freq(MHz)</th>
							      	<th scope="col">RxL</th>
									<th scope="col">Sector</th>
									<th scope="col">Distance</th>
							      	<th scope="col">Location</th>
							  	</thead>
							  <tbody>
							  	<!--    <tr>
								  	  <td scope="col">2</td>
								      <td scope="col">5</td>
								      <td scope="col">1</td>
								      <td scope="col">1</td>
								      <td scope="col">1</td>
								      <td scope="col">123</td>
								      <td scope="col">-123</td>
								      <td scope="col">12345678,12345678</td>
							  	  </tr> -->
							  </tbody>
							</table>
    					</div>					
  		</div>
  		<div class="col-4">
  			  			<div class="box_title">
  			  			<span class="head-left">Oxfam</span>
  						<span class="head-right fullscreen"><i class="fa fa-window-maximize" aria-hidden="true"></i></span>
  			  			<span class="head-right"><i data-url="http://${nodes[0].nodeIp}${nodes[0].loginPath}" class="connect_oxfam fa fa-gear connect_c2" aria-hidden="true"></i></span>
  			  			</div>
  			  			<div class="table_cont box_border">
    						<table class="table  e_tab fixed_header" id="oxfam_table">
							  <thead>
							    <tr>
							      <th scope="col">Date</th>
							      <th scope="col">Time</th>
							      <th scope="col">Location</th>
							      <th scope="col">Classification</th>
							    </tr>
							  </thead>
							  <tbody>
										    
							  </tbody>
							</table>
    					</div>
  		</div>
  	</div>
  	<div class="row h-5">
  		<div class="col-12 box_border">
	  		<div class = "row" id="status_bar">
	  		<div class="col-4 text-left font-weight-bold"><span  id="sys_location" class="status_elem">NA</span></div>
	  		<div class="col-4 text-center font-weight-bold hvr-grow">
		  		
		  		<c:forEach var="node" items="${nodes}" >
		  			<span class="border"></span>
		  			<div id="${node.nodeName}_legend" class="legend_block" ><span id="${node.nodeName}_state" class="border rounded-circle sensor_status status_elem <c:out value="${node.status ==1?'upBox': 'downBox'}"/>">&nbsp;&nbsp;</span>${node.displayName}</div>
		  			<span class="border"></span>
		  		</c:forEach>
		  		
		  		
		  		<!--  <div id="UGS_legend" class="legend_block" ><span id="UGS_state" class="border rounded-circle sensor_status status_elem" style="background:red;">&nbsp;&nbsp;</span>Oxfam</div>
		  		<span class="border"></span>
		  		<div id="TRGL_legend"  class="legend_block" ><span id="TRGL_state"  class="border rounded-circle sensor_status status_elem"  style="background:red;">&nbsp;&nbsp;</span>Hummer</div>
		  		<span class="border seprator"></span>
		  		<div id="TMDAS_legend"  class="legend_block" ><span id="TMDAS_state"  class="border rounded-circle sensor_status status_elem"  style="background:red;">&nbsp;&nbsp;</span>Falcon</div>-->
	  		</div>
	  		<div class="col-4 text-right font-weight-bold">System Up Time : <span id="current_time"></span></div>
	  		</div>
  		</div>
  	</div>
</div>


<div id="event_detail" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Details</h4>
      </div>
      <div class="modal-body">
        
        
      </div>
    </div>

  </div>
</div>

<div id="status_detail" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Status</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        
      </div>
      <div class="modal-body">
        <div>
        <div class="col-12">
					<div class="row h-100">
						<div class="col-12  box_border">
							
							<div class="box_title">
							<span class="head-left">Nodes Information</span>
  			
							
							</div>
							<div class="staus_container">
							
								
									
									
									<c:forEach var="node" items="${nodes}" >
										<div class="row top-bottom">
											<div class=col-12>
												<div class="ticketClass myBox" id="${node.nodeName}_box">	
								               		<div class="gridColumn">
								                  		<img class="imageClass" src="images/${node.nodeIcon}"></img>
								                     	<div class="gridRow">
								                     		<div><strong>${node.displayName}</strong></div>
									                        <table>
									                  			<tr><td>IP</td><td class="ip"></td></tr>
									                  			<c:if test = "${node.displayName != 'ADU'}"> 
										                  			<tr><td>Mode</td><td class="mode"></td></tr>
										                  			<tr><td>Used Space</td><td class="space"></td></tr>
									                  			</c:if>
									                  			<tr><td>Status</td><td class="statusClass"></td></tr>
								                  			</table>
								                     	</div>
								               		</div>
							               		</div>
											</div>
										</div>		
								</c:forEach>
							</div>
						</div>		
					</div>    				
    			</div>
    			</div>
        
      </div>
    </div>

  </div>
</div>

  
<script>
	var mapServerIp = '${mapserverIp}';
</script>
<!--  SCRIPT ATTACHMENT SECTION -->
<script src="js/lib/jquery-3.3.1.min.js" ></script>

<c:choose>
	<c:when test="${loadGooleMap}">
    	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBzeZS7SkEdznPknWj0AgRRbd9dJYKTtz4"></script>
    </c:when>
</c:choose>
<script src="js/lib/jquery-ui.min.js" ></script>
<script src="js/lib/table-scroll.min.js" ></script>
<script src="js/lib/sockjs.min.js" ></script>
<script src="js/lib/stomp.min.js" ></script>
<script src="js/lib/bootstrap.min.js" ></script>
<script src="js/lib/Chart.bundle.min.js" ></script>
<script src="js/lib/bootstrap-colorpicker.min.js" ></script>
<script src="js/lib/leaflet.js" ></script>
<script src="js/lib/leaflet-src.js" ></script>
<script src="js/lib/Leaflet.GoogleMutant.js" ></script>
<script src="js/lib/leaflet.geometryutil.js" ></script>
<script src="js/lib/Leaflet.fullscreen.min.js" ></script>
<script src="js/lib/Semicircle.js" ></script>
<script src="js/lib/turf.min.js" ></script>
<script src="js/lib/popupwindow.min.js"></script>
<script src="js/lib/jquery.cookie.js"></script>
<script src="js/lib/jquery.contextMenu.min.js"></script>
<script src="js/lib/jquery.ui.position.min.js"></script>

<script src="js/constants.js"></script>
<script src="js/fullscreen.js"></script>
<script src="js/urls.js"></script>
<script src="js/dashboard.js"></script>
<script src="js/trglGraph.js"></script>

<!--  SCRIPT ATTACHMENT SECTION -->
</body>
</html>