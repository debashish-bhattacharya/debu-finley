<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/lib/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/lib/fontawesome-all.min.css">
	<link rel="stylesheet" href="css/lib/bootstrap-colorpicker.min.css">
	<link rel="stylesheet" href="css/lib/leaflet.css">
	<link rel="stylesheet" href="css/lib/leaflet.fullscreen.css">
	<link rel="stylesheet" href="css/lib/popupwindow.css">
	<link href="css/dashboard.css" rel="stylesheet" />
	<title></title>
<script>
	var remoteAddr = '<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>';
</script>
</head>
<body>
<div class="container dashcont">

<div class="row dashrow">
	<div class="col-md-12 dashcol" >
		<div id="map_leaf"></div>
		  <div id="nodes" class="example_content">
  	<table>
  		<thead>
  			<th>Node</th>
  			<th>IP</th>
  		</thead>
  		<tbody>
  			<tr><td>UGS</td><td><input type="text" id="ugs_node_ip" /></td></tr>
  			<tr><td>TRGL</td><td><input type="text" id="trgl_node_ip" /></td></tr>
  			<tr><td>TMDAS</td><td><input type="text" id="tmdas_node_ip" /></td></tr>
  			<tr><td colspan="2"><input type="button" value="Update" id="udpate_node" /></td></tr>
  		</tbody>
  	</table>
  </div>
	</div>
  </div>
  
</div>
  



<!--  SCRIPT ATTACHMENT SECTION -->
<script src="js/lib/jquery-3.3.1.min.js" ></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBzeZS7SkEdznPknWj0AgRRbd9dJYKTtz4"></script>
<script src="js/lib/sockjs.min.js" ></script>
<script src="js/lib/stomp.min.js" ></script>
<script src="js/lib/bootstrap.min.js" ></script>
<script src="js/lib/Chart.bundle.min.js" ></script>
<script src="js/lib/bootstrap-colorpicker.min.js" ></script>

<script src="js/lib/leaflet.js" ></script>
<script src="js/lib/Leaflet.GoogleMutant.js" ></script>
<script src="js/lib/leaflet.geometryutil.js" ></script>
<script src="js/lib/Leaflet.fullscreen.min.js" ></script>
<script src="js/lib/Semicircle.js" ></script>
<script src="js/lib/turf.min.js" ></script>
<script src="js/lib/popupwindow.min.js"></script>
<script src="js/urls.js"></script>
<script src="js/dashboard.js"></script>
<!--  SCRIPT ATTACHMENT SECTION -->
</body>
</html>