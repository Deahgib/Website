Array.prototype.contains = function (obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}


function repositionSplashImages() {
    var splashItems = $("body").find(".centered-image-item");
    for (var i = 0; i < splashItems.length; i++) {
        var img = $(splashItems[i]).find("img");
        var imgWidth = $(img).prop('naturalWidth');
        var imgHeight = $(img).prop('naturalHeight');
        if ((imgWidth / imgHeight) > ($(splashItems[i]).width() / $(splashItems[i]).height())) {
            $(img).removeClass("tall");
            $(img).addClass("wide");
        } else {
            $(img).removeClass("wide");
            $(img).addClass("tall");
        }
        //$(img).css("margin-top", "-" + (Math.round($(img).height() - $(splashItems[i]).height()) / 2) + "px");
        //$(img).css("margin-left", "-" + (Math.round($(img).width() - $(splashItems[i]).width()) / 2) + "px");
    }
}