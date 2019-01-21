$(document).ready(function () {
    $('#split-layout-container').layout();
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
            });
        }
    });
}