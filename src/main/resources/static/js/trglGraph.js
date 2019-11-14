var xData = [];
var myLineChart = "";
var currentTime='';
var antennaId="41";
//var updateChartFrequency = ""


var custom = Chart.controllers.scatter.extend({
    draw: function(ease) {
        // Call super method first
        Chart.controllers.scatter.prototype.draw.call(this, ease);

        // Now we can do some custom drawing for this dataset. Here we'll draw a red box around the first point in each dataset
        var meta = this.getMeta();
//       console.log(meta);
        /*var ctx1 = this.chart.chart.ctx;
        ctx1.beginPath();
ctx1.moveTo(parseInt($("#start_freq").val()),parseInt($("#threshold").val()));
ctx1.lineTo(pt0._xScale.right,pt0._view.x,parseInt($("#threshold").val()));

        ctx1.strokeStyle="blue";
        ctx1.stroke();*/


        var points = meta.data;
        
        for(var i in points)
        {
            var pt0 = points[i];
            var radius = pt0._view.radius;
            //console.log(pt0);
            var ctx = this.chart.chart.ctx;
            //console.log(ctx);
            ctx.save();
            //ctx.strokeStyle = 'red';
            ctx.lineWidth = 1;
           //console.log(pt0);
            // ctx.strokeRect(pt0._view.x - radius, pt0._view.y - radius, 2 * radius, 2 * radius);
            ctx.beginPath();
            ctx.moveTo(pt0._view.x,pt0._view.y);
            ctx.lineTo(pt0._view.x,pt0._yScale.bottom);

            //ctx.strokeStyle="red";
            ctx.stroke();



        }

    }
});

Chart.controllers.derivedline = custom;
Chart.defaults.derivedline = Chart.defaults.scatter;
Chart.defaults.global.animation=false;


var workerExample = function()
{
	workerFor = new Worker('js/woker.js');
	
	// listen to message event of worker
	workerFor.addEventListener('message', function(event) 
	{
	    //console.log(event);
	    var statusData = jQuery.parseJSON(event.data);
		if(statusData.length == 0){
			workerFor.postMessage("Macro!");
		}
		else{
			var status = jQuery.parseJSON(statusData.data);
			createOrUpdateGraph(status);
			workerFor.postMessage("Marco!");
		}
	    //do your chart work here
	    
	});
	
	// listen to error event of worker
	workerFor.addEventListener('error', function(event) {
	    console.error('error received from workerFor => ', event);
	});
	
	workerFor.postMessage("Marco!");
}

var createOrUpdateGraph = function(data)
{
	var appendFlag = false;
    for(var i in data)
    {
		if(i == 0 )
		{
			if(data[i].time.split(" ")[1] == currentTime){
				appendFlag = true;
			}
			currentTime = data[i].time.split(" ")[1];
			graphDataForCurrentSelectedNode = [];
		}
		if(data[i].antennaid!=antennaId)
			graphDataForCurrentSelectedNode[data[i].frequency]=data[i];
    }
    convertToData(graphDataForCurrentSelectedNode, appendFlag);
    myLineChart.update();
}

var convertToData = function(data, appendFlag)
{
    var temp = [];
    for(var t =0;t<13;t++){
		if(t<1)
			myLineChart.data.datasets[t].data = [];
	}
	
	if(appendFlag){
		temp = xData.shift();
	}
	
    for(var j in data)
    {
        var obj1 = {};

        obj1.x = j;
        obj1.y = data[j].power;
		temp.unshift(obj1);
    }
    xData.unshift(temp);
    
	var len = xData.length;
	if(len>2){
		xData.pop();
	}
	
	/*var count =0;
	for(var x in xData){
		for(var y in xData[x]){
			var object = xData[x][y];
			if(count<1){
				myLineChart.data.datasets[count].data.push(object);
			}
			else{}
		}
		count++;
	}*/
	
	for(var j in data)
    {
        var obj1 = {};

        obj1.x = j;
		var sector = parseFloat(data[j].sector);
        obj1.y = data[j].power;
		//temp.unshift(obj1);
		myLineChart.data.datasets[sector-1].data.push(obj1);
    }
	
}

var config1 = {
        type: 'derivedline',
        data: {
            //labels: ['0', '500', '1000', '1500', '2000', '2500', '3000'],
            datasets: [{
                label: 'Sector 1',
                backgroundColor: colorToHex(167, 147, 141),
                borderColor: colorToHex(167, 147, 141),
                //data: [{x: 2400,y: -90}, {x: 1300,y: -20},{x:1800,y:-39}],
                data: [],
                fill: false
                //,showLine:true
            },
            {
				label: 'Sector 2',
				borderColor: colorToHex(21,151,155),
				backgroundColor: colorToHex(21,151,155),
				data: [],
				fill:false
			},
			{
				label: 'Sector 3',
				borderColor: colorToHex(255,195,0),
				backgroundColor: colorToHex(255,195,0),
				data: [],
				fill:false
			}]
        },
        options: {
            responsive: true,
            chartArea: {
        		backgroundColor: 'rgba(10,10,8, 1)'
        },
        animation:false,
            //onClick: updateChartFrequency,
            title: {
                display: true,
                //text: 'Freq(MHz) vs Power(dBm)'
                text: ''
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Freq(MHz)'
                    },
                ticks: {
                    min: 0,
                    max: 6000,
                    stepSize: 600
                }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Power(dBm)'
                    },
                ticks: {
                    min: -120,
                    max: 0,
                    stepSize: 20
                }
                }]
            }
        }
    };





function colorToHex(red, green, blue) {

	green = parseInt(green);
	blue = parseInt(blue);
	red = parseInt(red);
	return "#" + hex(red) + hex(green) + hex(blue);

}

function hex(x) {
	var hexDigits = new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f");
	return isNaN(x) ? "00" : hexDigits[(x - x % 16) / 16] + hexDigits[x % 16];
}



$(document).ready(function()
{
	var ctx = document.getElementById('canvas').getContext('2d');
	myLineChart = new Chart(ctx, config1);
	workerExample();
});