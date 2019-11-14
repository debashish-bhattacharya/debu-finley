<script>
		var remoteAddr = '<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>';
</script>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link dashboard" href="dashboard">Dashboard <span class="sr-only">(current)</span></a>
      <a class="nav-item nav-link operation" href="operation">Operation</a>
    </div>
  </div>
  <button class="btn my-2 my-sm-0 btn_Gen nav-btn"><a href="logout">Logout</a></button>
</nav>