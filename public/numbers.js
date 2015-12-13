(function () {

    var addLog = function (ele) {
        ele = $("<li/>").append(ele);

        if ($("#log li").length === 0) {
            $("#log").append(ele);
        } else {
            $("#log li").first().before(ele);
        }
    };

    var outputText = function (t) {
        addLog(
            $('<div/>', { text: t })
        );
    };

    var attemptToHtml = function (attempt, target) {
        var result = $('<span class="result"/>').text(attempt.result);
        var string = $('<span class="string"/>').text(attempt.string);

        var offByNum = Math.abs(target - attempt.result);
        var scoreNum = (offByNum === 0 ? 10 : offByNum <= 10 ? 7 : 0);

        var offBy = $('<span class="offBy"/>').text(offByNum);
        var score = $('<span class="score"/>').text(scoreNum);

        return $("<div/>").append(string, result, offBy, score);
    };

    var showResults = function (t, n, data) {
        var container = $('<div class="qanda"/>');

        var q = $('<div class="question"/>');
        q.text("Results for " + t + " from " + n.join(","));
        q.appendTo(container);

        var answerList = $('<ol class="answerList"/>');
        data.map(function (attempt) {
            $("<li/>").append(attemptToHtml(attempt, t)).appendTo(answerList);
        });
        answerList.appendTo(container);

        addLog(container);
    };

    var submit = function () {
        var t = parseInt($("#f .t")[0].value);
        var n = $("#f .n").toArray().map(function (e) { return parseInt(e.value); });

        var url = "/numbers/target/" + t + "/numbers/" + n.join("+");

        $.ajax(url, {
            async: true,
            error: function () {
                outputText("error: " + JSON.stringify(arguments));
            },
            success: function (data, statusCode, statusText) {
                showResults(t, n, data);
            },
        });

        return false;
    };

    var init = function () {
        $("#f").submit(submit);
    };

    init();

})();
