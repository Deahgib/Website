$(document).ready(function () {
    jQuery.getJSON("data/projects.json", function (data) {
        var genHTML = "";
        for (var i = 0; i < data.projects.length; i++){
            var project = data.projects[i];
            if (project.showProject) {
                genHTML += "<div class='project centered-image-item'>";
                genHTML += "<img src='" + project.imgURL + "'>";
                genHTML += "<h3>" + project.title + "</h3>";
                genHTML += "<a href='project.html?id="+project.id+"'><div class='overlay'></div></a>";
                genHTML += "</div>";
            }
        }
        $(".project-listing").html(genHTML);
        $(".centered-image-item img").load(function () {
            repositionSplashImages();
        });
    });
    $(window).on('resize', function () {
        repositionSplashImages();
    });
});
