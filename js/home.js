
$(document).ready(function () {
    jQuery.getJSON("data/proj-splash.json", function (data) {
        var big = $("body").find(".big");
        if (big.length < data.main.length) {
          console.log(data.main.length)
            var usedIndex = new Array();
            for (var i = 0; i < big.length; i++) {
                var randomIndex = Math.floor((Math.random() * data.main.length));
                while (usedIndex.contains(randomIndex)) {
                    randomIndex = Math.floor((Math.random() * data.main.length));
                }

                console.log(randomIndex)
                usedIndex.push(randomIndex);
                $(big[i]).html("<img src='" + data.main[randomIndex].imgURL + "' /><a href='"+data.main[randomIndex].path+"?id=" + data.main[randomIndex].id + "'><div class='overlay'></div></a><h2>" + data.main[randomIndex].title + "</h2>");
            }
        } else if (big.length == data.main.length) {
            for (var i = 0; i < big.length; i++){
                $(big[i]).html("<img src='" + data.main[i].imgURL + "' /><a href='" + data.main[randomIndex].path + "?id=" + data.main[i].id + "'><div class='overlay'></div></a><h2>" + data.main[i].title + "</h2>");
            }
        } else {
            for (var i = 0; i < big.length; i++) {
                $(big[i]).html("Data error"); // Won't happen if json file is correct
            }
        }

        var small = $("body").find(".small");
        if (small.length < data.small.length) {
            var usedIndex = new Array();
            for (var i = 0; i < small.length; i++) {
                var randomIndex = Math.floor((Math.random() * data.small.length));
                while (usedIndex.contains(randomIndex)) {
                    randomIndex = Math.floor((Math.random() * data.small.length));
                }
                usedIndex.push(randomIndex);
                $(small[i]).html("<img src='" + data.small[randomIndex].imgURL + "' /><a href='" + data.small[randomIndex].path + "?id=" + data.small[randomIndex].id + "'><div class='overlay'></div></a><h4>" + data.small[randomIndex].title + "</h4>");
            }
        } else if (small.length == data.small.length) {
            for (var i = 0; i < small.length; i++) {
                $(small[i]).html("<img src='" + data.small[i].imgURL + "' /><a href='" + data.small[randomIndex].path + "?id=" + data.small[i].id + "'><div class='overlay'></div></a><h4>" + data.small[i].title + "</h4>");
            }
        } else {
            for (var i = 0; i < small.length; i++) {
                $(small[i]).html("Data error"); // Won't happen if json file is correct
            }
        }
        $(".centered-image-item img").load(function () {
            repositionSplashImages();
        });
    });
    $(window).on('resize', function () {
        repositionSplashImages();
    });

    $("body").on("click", "lb-navbar a", function () {
        console.log("Clicked");
    });
});