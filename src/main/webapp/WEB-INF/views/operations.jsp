
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/lib/bootstrap.min.css" rel="stylesheet" />
	<link href="css/lib/datepicker.css" rel="stylesheet" />
	<link href="css/lib/jquery.datetimepicker.css" rel="stylesheet" />
	<link href="css/lib/jquery-confirm.css" rel="stylesheet" />
	<link href="css/main.css" rel="stylesheet" />
	<link href="css/dashboard.css" rel="stylesheet" />
	<link href="css/operations.css" rel="stylesheet" />
	<title></title>
	
</head>
<body>
<%@include file="header.jsp" %>
<div class="container-fluid h-100_custom">
	<div class="row h-100">
	<div class="col-12">
			<div class="center_box_container">
			<div class="center_box_cell">
				<div class="device_table">
					<div class="row node_super_row">
    					<div class="col-12 box_border">
	    					<div class="box_title">Nodes</div>
							<div class="row node_cont_row">
								<c:forEach var="node" items="${nodes}" >
									<a href="#" >
										<div class="col-4" >
											<div id="${node.nodeName}_status" class="ticketClass myBox <c:out value="${node.status ==1?'upBox': 'downBox'}" />" >	
						               			<div class="gridColumn" >
						                  			<!--<c:choose>  
														<c:when test="${node.nodeName == 'TRGL' }">  
															<img class="imageClass" src="images/TRGL_UP.png"></img>
														</c:when> 
														<c:when test="${node.nodeName == 'UGS'}"> 
															<img class="imageClass" src="images/UGS_UP.png"></img>
														</c:when>  
														<c:otherwise>  
															<img class="imageClass" src="images/TMDAS_UP.png"></img> 
														</c:otherwise>  
													</c:choose>-->
													<img class="imageClass" src="<c:out value="images/${node.nodeIcon}" />"></img>
						                     		<div class="gridRow" >
								                        <table>
								                        	<thead><th colspan=2><strong>${node.displayName}</strong></th></thead>
								                        	<tbody>
								                        		<tr>
								                        			<td>
								                        				<input type="text" onclick="event.preventDefault()" id="${node.nodeName}_ip" class="inputClass" size="25" maxlength="15s" value="${node.nodeIp}"></input>
								                        				<button class="btnClass" onclick="event.stopPropagation();updateIpOfNodes('${node.nodeId}','${node.nodeName}_ip')" >Update</button class="btnClass" >
								                        			</td>
								                        		</tr>
								                        		<tr><td class="statusClass"><span><c:out value="${node.status ==1?'UP': 'DOWN'}" /></span></td></tr>
								                        		<tr>
								                        			<td>
								                        			    <c:if test="${node.nodeId!='4'}">
									                        				<button class="btnClass" onclick="event.stopPropagation();restart('${node.nodeId}','${node.nodeName}_ip')" >Restart</button>
									                        				<button class="btnClass" onclick="event.stopPropagation();inventory('${node.nodeId}','${node.nodeName}_ip')" >Inventory</button>
									                        				<a target="_blank" href="http://${node.nodeIp}${node.loginPath}"><button class="btnClass">Connect</button></a>
								                        				</c:if>
								                        				<c:if test="${node.nodeId =='4'}">
									                        				<button class="btnClass" disabled="true" onclick="event.stopPropagation();restart('${node.nodeId}','${node.nodeName}_ip')" >Restart</button>
									                        				<button class="btnClass" disabled="true" onclick="event.stopPropagation();inventory('${node.nodeId}','${node.nodeName}_ip')" >Inventory</button>
									                        				<a target="_blank" href="http://${node.nodeIp}${node.loginPath}"><button class="btnClass" disabled="true">Connect</button></a>
								                        				</c:if>
								                        			</td>
								                        		</tr>
								                        	</tbody>
								                        </table>
								                        <!--<div>
								                        <strong>${node.displayName}</strong>
								                        	<!--<c:choose>  
																<c:when test="${node.nodeName == 'TRGL' }">  
																	<strong>Hummer</strong>		
																</c:when> 
																<c:when test="${node.nodeName == 'UGS'}"> 
																	<strong>Oxfam</strong>
																</c:when>  
																<c:otherwise>  
																	<strong>Falcon</strong> 
																</c:otherwise>  
															</c:choose>
														</div>
								                        <div>
								                        	<input type="text" onclick="event.preventDefault()" id="${node.nodeName}_ip" class="inputClass" size="25" maxlength="15s" value="${node.nodeIp}"></input>
								                        	<button class="btnClass" onclick="event.stopPropagation();updateIpOfNodes('${node.nodeId}','${node.nodeName}_ip')" >Update</button class="btnClass" >
								                        	<button onclick="event.stopPropagation();restart('${node.nodeId}','${node.nodeName}_ip')" >Restart</button>
								                        </div>
								                        <div class="statusClass"><span><c:out value="${node.status ==1?'UP': 'DOWN'}" /></span></div>-->
						                     		</div>
						               			</div>
					               			</div>
					            		</div>
			          				</a>
		          				</c:forEach>
							</div>
						</div>
					</div>
					<div class="row node_super_row">
						<div class="col-12 box_border">
	    					<div class="box_title">Report</div>
							<div class="row node_cont_row">
								<div class="col-2">
									<select class="form-control" id="node_type">
										<option value="ALL">All Nodes</option>
										<option value="UGS">Oxfam</option>
          								<option value="TRGL">Hummer</option>
          								<option value="TMDAS">Falcon</option>
									</select>
								</div>
								<div class="col-3"><input class="dateSelector form-control" type="text" id="rpt_startDate" placeholder="Start Date"></div>
								<div class="col-3"><input class="dateSelector form-control" type="text" id="rpt_endDate"  placeholder="End Date"></div>
								<div class="col-2">
									<select class="dateSelector form-control" id="report_type">
										<option value=1>CSV</option>
          								<option value=2>TSV</option>
									</select>
								</div>
								<div class="col-2"><button class="btn btn-primary btn_Gen" id="GetFile">Generate</button> </div>
							</div>
						</div>
		 		  	</div>
		 		  	<div class="row node_super_row" id="test_box">
						<div class="col-12 box_border">
	    					<div class="box_title">Diagnosis</div>
							<div class="row node_cont_row">
								<div class="col-12">
								<table id="test_button">
									<tr>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="testips">IP Connection</button></td>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="testpower">Power Systems</button></td>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="#" disabled>Falcon</button></td>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="#" disabled>Hummer</button></td>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="testadu" data-node="UGS" data-cmd="ADU_ON">Oxfam ADU</button></td>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="testadu" data-node="TMDAS" data-cmd="ADU_ON">Falcon ADU</button></td>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="testadu" data-node="TRGL" data-cmd="ADU_ON">Hummer ADU</button></td>
										<td><button class="btn btn-primary btn_Gen testbtn" data-url="#" disabled>BIST</button></td>
									</tr>
								</table>
								</div>
							</div>
						</div>
		 		  	</div>
		 		  	
					
					
				<!--	<table class="table" id="oxfam_table">
				<thead>
					<th>Device</th>
					<th>IP</th>
					<th>Status</th>
					<th><button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Report</button></th>
				</thead>
				 <tbody>
					<tr>
						<td>UGS</td>
						<td><input id="ugs_ip" type="text" value=""/></td>
						<td id="ugs_status"></td>
						<td><button id="ugs_update">Update</button>&nbsp;&nbsp;<button id="ugs_open">Open</button></td>
					</tr>
					<tr>
						<td>TRGL</td>
						<td><input  id="trgl_ip" type="text" value=""/></td>
						<td id="trgl_status"></td>
						<td><button id="trgl_update">Update</button>&nbsp;&nbsp;<button id="trgl_open">Open</button></td>
					</tr>
					<tr>
						<td>TMDAS</td>
						<td><input  id="tmdas_ip" type="text" value=""/></td>
						<td id="tmdas_status"></td>
						<td><button id="tmdas_update">Update</button>&nbsp;&nbsp;<button id="tmdas_open">Open</button></td>
					</tr>
				</tbody>
				<c:forEach var="node" items="${nodes}" >
				     <tr>
						<td>${node.nodeName}</td>
						<td><input  id="${node.nodeName}_ip" type="text" value="${node.nodeIp}"/></td>
						<td id="${node.nodeName}_status"><c:out value="${node.status ==1?'UP': 'DOWN'}" /></td>
						<td><button onclick="updateIpOfNodes('${node.nodeId}','${node.nodeName}_ip')">Update</button>&nbsp;&nbsp;<button id="${node.nodeName}_open"><a href="http://${node.nodeIp}${node.url}" >Open</button></td>
					</tr>
				</c:forEach>
				
				
				
				
				
			</table> -->
				</div>
				
			</div>
			
			</div>
		</div>
	</div>
</div>




<!--  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
     
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">Report</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <table>
          	<tbody>
          		<tr>
          			<td>Start Date</td><td><input type="text" id="rpt_startDate" placeholder="yyyy-MM-DD hh:mm:ss"/>(yyyy-MM-DD hh:mm:ss)</td>
          		</tr>
          		<tr>
          			<td>End Date</td><td><input type="text" id="rpt_endDate" placeholder="yyyy-MM-DD hh:mm:ss"/>(yyyy-MM-DD hh:mm:ss)</td>
          		</tr>
          		<tr>
          			<td>Type</td>
          			<td>
          				<select id="rpt_type">
          					<option value=1>CSV</option>
          					<option value=2>TSV</option>
          				</select>
          			</td>
          		</tr>
          		<tr>
          			<td colspan=2><button type="button" id="GetFile__">Get File!</button></td>
          		</tr>
          	</tbody>
          </table>
        </div>
      </div>
      
    </div>
  </div>-->
  
  
  
  <div class="modal fade" id="test_modal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
      	<div class="modal-body">         
          <img src="images/loading.gif"/>
        </div>
      </div>
    </div>
  </div>
  
  
    <div class="modal fade" id="test_modal_data" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
      	<div class="modal-header">
          <h4 class="modal-title">Result</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
      	<div class="modal-body">         
          
        </div>
      </div>
    </div>
  </div>
 
<script>
	var ips = [];
	
	<c:forEach var="node" items="${nodes}" >
		ips.push('${node.nodeIp}');
	</c:forEach>
	
		 
	
</script>  

<!--  SCRIPT ATTACHMENT SECTION -->
<script src="js/lib/jquery-3.3.1.min.js" ></script>
<script src="js/lib/sockjs.min.js" ></script>
<script src="js/lib/stomp.min.js" ></script>
<script src="js/lib/bootstrap.min.js" ></script>
<script src="js/lib/bootstrap-datepicker.js"></script>
<script src="js/lib/jquery.datetimepicker.full.min.js"></script>
<script src="js/lib/popupwindow.min.js"></script>
<script src="js/lib/jquery-confirm.js"></script>
<script src="js/constants.js"></script>
<script src="js/urls.js"></script>
<script src="js/operations.js"></script>

<!--  SCRIPT ATTACHMENT SECTION -->
</body>
</html>