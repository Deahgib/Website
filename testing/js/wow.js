

$(document).ready(function () {
    url.guildName = "/Primeval";
    url.realmName = "/Sylvanas";

    initAllCharts();

    populateAllCharts();
});



$("#calculate-total-stats").on("click", function () {
    var urlStr = url.rootURL + url.guild + url.realmName + url.guildName + "?fields=members" + url.api_key;

    var totalGuildStrength = 0;
    var totalGuildIntellect = 0;
    var totalGuildStamina = 0;
    var totalGuildAgility = 0;
    var maxStr = 0;
    var maxAgi = 0;
    var maxInt = 0;
    var maxSta = 0;
    var bestStr = "";
    var bestAgi = "";
    var bestInt = "";
    var bestSta = "";

    var data = JSON.parse(Get(urlStr));
    var setSize = data.members.length;
    for (var i = 0; i < data.members.length; i++) {
        var charURL = url.rootURL + url.character + url.realmName + "/" + data.members[i].character.name + "?fields=stats" + url.api_key
        var charData = JSON.parse(Get(charURL));

        if (charData.reason != undefined) {
            console.log(charData.reason + " " + data.members[i].character.name);
        } else if (charData.name != undefined) {
            totalGuildStrength += charData.stats.str;
            totalGuildIntellect += charData.stats.int;
            totalGuildStamina += charData.stats.sta;
            totalGuildAgility += charData.stats.agi;
            if (charData.stats.str > maxStr) {
                maxStr = charData.stats.str;
                bestStr = charData.name
            }
            if (charData.stats.int > maxInt) {
                maxInt = charData.stats.int;
                bestInt = charData.name
            }
            if (charData.stats.agi > maxAgi) {
                maxAgi = charData.stats.agi;
                bestAgi = charData.name
            }
            if (charData.stats.sta > maxSta) {
                maxSta = charData.stats.sta;
                bestSta = charData.name
            }
            console.log(data.members[i].character.name + " ADDED STATS");
        }
        var percentage = Math.round((i + 1) / setSize * 100);
        $(".loader").css("width", percentage + "%");
    }

    $("#strTot").html(totalGuildStrength);
    $("#strMax").html(maxStr);
    $("#strBest").html(bestStr);

    $("#agiTot").html(totalGuildAgility);
    $("#agiMax").html(maxAgi);
    $("#agiBest").html(bestAgi);

    $("#intTot").html(totalGuildIntellect);
    $("#intMax").html(maxInt);
    $("#intBest").html(bestInt);

    $("#staTot").html(totalGuildStamina);
    $("#staMax").html(maxSta);
    $("#staBest").html(bestSta);
});