$(document).ready(function () {
    $('#split-layout-container').layout();
    
    /*
    var url = window.location.href
    var filePath = url.split("&filePath=")[1].split("&")[0].split(".");
    var extention = filePath[filePath.length - 1];
    $("#code-content").removeClass().addClass("hljs " + extention);

    if (extention.valueOf() != "html" && extention.valueOf() != "htm") {
        $('#code-content').each(function (i, block) {
            hljs.highlightBlock(block);
        });
    }
    $('#code-body').css("background-color", $(".hljs").css("background-color"));
    var title = url.split("&projName=")[1];
    document.title = (title + " code");
    */
    
    $.getJSON("data/projects.json", function (data) {
        var url = window.location.href;
        var projVar = url.split("?")[1];
        var projId = projVar.split("projId=")[1].split("&")[0];
        var projSrcPos = projVar.split("srcObj=")[1].split("&")[0];
        var projSrcPos = projVar.split("srcObj=")[1].split("#")[0];

        var project;
        for (var i = 0; i < data.projects.length; i++) {
            if (projId === data.projects[i].id) {
                project = data.projects[i];
                break;
            }
        }
        var title = project.title;
        console.log(title)
        console.log(projSrcPos)
        var filePath = project.sourceCode[projSrcPos].main;
        if (project.sourceCode[projSrcPos].github != undefined && project.sourceCode[projSrcPos].github.valueOf() != "") {
            console.log("Github address found!")
            $("#github-hover-sidebar").html(("<a class='github btn btn-default' href='" + project.sourceCode[projSrcPos].github + "'><img src='img/git-icon.png' /> Github</a>"));
        }
        $('#code-tree h3').html(title);
        document.title = (title + " code");
        loadNewFile(filePath);
    });
    
});

function loadNewFile(fileName) {
    var url = fileName.split(".");
    var extention = url[url.length - 1];
    $("#code-content").removeClass().addClass("hljs " + extention);
    $.get(fileName, function (data) {
        $("#code-content").html(data);
        if (extention.valueOf() != "html" && extention.valueOf() != "htm") {
            $('#code-content').each(function (i, block) {
                hljs.highlightBlock(block);
                $('#code-body pre').css("background-color", $(".hljs").css("background-color"));
                $('#code-body').css("background-color", $(".hljs").css("background-color"));
            });
        }
    });
}

