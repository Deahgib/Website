var DataCharts = {
    pieChartSexData: null,
    pieChartClassData: null
}

function initAllCharts() {
    DataCharts.pieChartSexData = [
        {
            value: 0,
            color: "#FDB45C",
            highlight: "#FFC870",
            label: "Male"
        },
        {
            value: 0,
            color: "#46BFBD",
            highlight: "#5AD3D1",
            label: "Female"
        }
    ];

    var classKeys = Object.keys(allClasses);
    DataCharts.pieChartClassData = new Array(classKeys.length)
    for (var i = 0 ; i < classKeys.length; i++) {
        DataCharts.pieChartClassData[i] = {
            value: 0,
            color: colorCodes.classCodes[classKeys[i]].color,
            highlight: colorCodes.classCodes[classKeys[i]].color,
            label: allClasses[classKeys[i]]
        }
    }
}

function populateAllCharts() {

    // Class data
    var urlStr = url.rootURL + url.guild + url.realmName + url.guildName + "?fields=members" + url.api_key;
    $.getJSON(urlStr, function (data) {
        $.each(data.members, function (i, member) {
            DataCharts.pieChartSexData[member.character.gender].value++;
            DataCharts.pieChartClassData[member.character.class - 1].value++;
            
            var charURL = url.rootURL + url.character + url.realmName + "/" + data.members[i].character.name + "?fields=stats" + url.api_key
            $.getJSON(charURL, function (charData) {
                console.log(charData);
            });
        });
        var sexChart = $("#mySexChart").get(0).getContext("2d");
        var myPieSexChart = new Chart(sexChart).Pie(DataCharts.pieChartSexData, options);
        var classChart = ctx = $("#myClassChart").get(0).getContext("2d");
        var myPieClassChart = new Chart(classChart).Pie(DataCharts.pieChartClassData, options);
    }).promise().done(function () { alert("Big calculation done."); });


}

function Get(yourUrl) {
    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open("GET", yourUrl, false);
    Httpreq.send(null);
    return Httpreq.responseText;
}



// Standard options
var options = {
    //Boolean - Show a backdrop to the scale label
    scaleShowLabelBackdrop: true,
    //String - The colour of the label backdrop
    scaleBackdropColor: "rgba(255,255,255,0.75)",
    // Boolean - Whether the scale should begin at zero
    scaleBeginAtZero: true,
    //Number - The backdrop padding above & below the label in pixels
    scaleBackdropPaddingY: 2,
    //Number - The backdrop padding to the side of the label in pixels
    scaleBackdropPaddingX: 2,
    //Boolean - Show line for each value in the scale
    scaleShowLine: true,
    //Boolean - Stroke a line around each segment in the chart
    segmentShowStroke: true,
    //String - The colour of the stroke on each segement.
    segmentStrokeColor: "#000",
    //Number - The width of the stroke value in pixels
    segmentStrokeWidth: 1,
    //Number - Amount of animation steps
    animationSteps: 100,
    //String - Animation easing effect.
    animationEasing: "easeOutBounce",
    //Boolean - Whether to animate the rotation of the chart
    animateRotate: true,
    //Boolean - Whether to animate scaling the chart from the centre
    animateScale: false,
    //String - A legend template
    legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"

};