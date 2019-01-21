$(document).ready(function () {
    repositionSplashImages();
    
    jQuery.getJSON("data/projects.json", function (data) {
        var url = window.location.href;
        var projVar = url.split("?")[1];
        var projId = projVar.split("=")[1];

        var project;
        for (var i = 0; i < data.projects.length; i++) {
            if (projId === data.projects[i].id) {
                project = data.projects[i];
                break;
            }
        }

        if (project === undefined) {
            $(".project").html("<h1>Error:</h1><p>Project \"" + projId + "\" does not exist.</p>");
            document.title = "Error";
        } else {

            var genHTML = "";
            // BANNER + DESCRIPTION
            genHTML += "<h1 class='titleFont'>" + project.title + "</h1>";
            genHTML += "<div class='banner centered-image-item'>";
            genHTML += "<img src='" + project.imgURL + "' />";
            
            genHTML += "<div class='gradient-overlay'></div>";
            genHTML += "</div>";
            if (project.description !== undefined) {
                genHTML += "<p>" + project.description + "</p>";
            }

            // DOWNLOADS
            if (project.download !== undefined) {
                var download = project.download;
                genHTML += "<h2>Download</h2>"
                if (download.text !== undefined) {
                    genHTML += "<p>" + download.text + "</p>";
                }
                if (download.buttons !== undefined) {

                    for (var i = 0; i < download.buttons.length; i++) {
                        var button = download.buttons[i];
                        genHTML += "<div class='project-row download'>";
                        genHTML += "<a class='btn btn-default' target='_blank' href='" + button.link + "' role='button'>" + button.name + "</a>";
                        genHTML += "<p class='text'>" + button.text + "</p>";
                        genHTML += "</div>";
                    }
                }
            }

            // INSTALLATION
            if (project.installation !== undefined) {
                genHTML += "<h2>Installation</h2>";
                if (project.installation.text !== undefined) {
                    genHTML += "<p>" + project.installation.text + "</p>";
                }
                if (project.installation.platforms !== undefined) {
                    for (var i = 0; i < project.installation.platforms.length; i++) {
                        var platform = project.installation.platforms[i];
                        genHTML += "<h4>" + platform.name + "</h4>";
                        genHTML += "<p>" + platform.text + "</p>";
                    }
                }
            }

            // PREVIOUS VERSION DOWNLOADS
            if (project.prevVersions !== undefined) {
                genHTML += "<h3>Old Versions</h3>";
                genHTML += "<button class='btn btn-default project-collapse-toggle' type='button' data-toggle='collapse' data-target='#prev-version-content' aria-expanded='false' aria-controls='collapseExample'>";
                genHTML += "Previous Versions";
                genHTML += "</button>";
                genHTML += "<div class='collapse' id='prev-version-content'>";
                var prevVersions = project.prevVersions;
                for (var i = 0; i < prevVersions.length; i++) {
                    genHTML += "<div class='project-row'>";
                    genHTML += "<div class='prev-versions-left'><h4>" + prevVersions[i].name + ":</h4></div>";
                    genHTML += "<div class='prev-versions-right'><a class='btn btn-default' target='_blank' href='" + prevVersions[i].link + "' role='button'>" + prevVersions[i].name + "</a></div>";
                    genHTML += "</div>";
                }
                genHTML += "</div>";
            }

            // CHANGELOG
            if (project.changelog !== undefined) {
                genHTML += "<h3>Changelog</h3>";
                genHTML += "<button class='btn btn-default project-collapse-toggle' type='button' data-toggle='collapse' data-target='#changelog-content' aria-expanded='false' aria-controls='collapseExample'>";
                genHTML += "Changelog";
                genHTML += "</button>";
                genHTML += "<div class='collapse' id='changelog-content'>";
                for (var i = 0; i < project.changelog.length; i++) {
                    var changelog = project.changelog[i];
                    genHTML += "<div class='project-row change'>";
                    genHTML += "<h4>" + changelog.name + "</h4>";
                    genHTML += "<ul class='text'>";
                    for (var j = 0; j < changelog.verChanges.length; j++) {
                        genHTML += "<li>" + changelog.verChanges[j] + "</li>";
                    }
                    genHTML += "</ul>";
                    if (changelog.testedOn !== undefined) {
                        genHTML += "<p class='tested-on'>Tested on " + changelog.testedOn + "</p>"
                    }
                    genHTML += "</div>";
                }
                genHTML += "</div>";
            }

            // EXTRA
            if (project.extra !== undefined) {
                var extra = project.extra;
                for (var i = 0; i < extra.length; i++) {
                    genHTML += "<h2>" + extra[i].subTitle + "</h2>";
                    genHTML += "<p>" + extra[i].content + "</p>";
                }
            }

            // DOCUMENTATION
            if (project.documentation !== undefined) {
                genHTML += "<h2>Documentation</h2>";
                genHTML += "<div class='button-container'>";
                for (var i = 0; i < project.documentation.length; i++) {
                    var file = project.documentation[i];
                    genHTML += "<a class='btn btn-default' target='_blank' href='" + file.link + "' role='button'>" + file.name + "</a>";
                }
                genHTML += "</div>";
            }

            // SOURCE CODE
            if (project.sourceCode !== undefined) {
                genHTML += "<h2>Source Code</h2>";
                genHTML += "<div class='button-container'>";
                for (var i = 0; i < project.sourceCode.length; i++) {
                  var sourceCode = project.sourceCode[i];
				  if (sourceCode.root != undefined && sourceCode.root.valueOf() != "" ){
					var sourceCodeLink = "/codeViewer.html?rootPath=" + sourceCode.root +  "&projId=" + project.id + "&srcObj=" + i;
					genHTML += "<div class='proj-button-holder'><a class='btn btn-default' href='" + sourceCodeLink + "' role='button'>" + sourceCode.name + "</a></div>";
				  }
			      if (sourceCode.github != undefined && sourceCode.github.valueOf() != "") {
				    genHTML += "<div class='proj-button-holder'><b>" + ((sourceCode.name != undefined && sourceCode.name.valueOf() != "") ? (sourceCode.name + ":") : "") + "</b> <a class='github btn btn-default' href='" + sourceCode.github + "'><img src='img/git-icon.png' alt='" + ((sourceCode.name != undefined && sourceCode.name.valueOf() != "") ? sourceCode.name : "") + "'/></a></div>";
				  }
                }
                genHTML += "</div>";
            }


            $(".project").html(genHTML);
            $(".centered-image-item img").load(function () {
                repositionSplashImages();
            });
            document.title = project.title;
        }

    });
    $(window).on('resize', function () {
        repositionSplashImages();
    });
});