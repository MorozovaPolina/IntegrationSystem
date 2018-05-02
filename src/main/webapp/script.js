var Num =1;
$(document).ready(function() {
    $(".addBT").click(function() {
        Num++;
        var newSection = $(this).parent().clone(true, true).attr("id", "step" + Num);
        newSection.insertAfter($(this).parent());
        $(this).fadeOut(20);
        // $(".step").last().append(newSection);
        newSection.text($('newSection').attr('id'));
    });
});
$(document).ready(function() {
    $(".removeBT").click(function () {
        if($(".step").length>1) {
            // $(this).parent().fadeOut(300);
            var next = $(this).parent().next($(this).parent()).attr("class");
            // alert(next!="step");
            if(next!="step"){
                $(this).parent().prev($(this).parent()).find(".addBT").fadeIn(30);
            }
            $(this).parent().remove();
        };
    });
});

function createSteps($form){
    var jsonObj = [];
    $form.children('.step').each(function () {
        var source = $(this).find(".source").val();
        var target = $(this).find(".target").val();
        var count = new Number( $(this).find(".count").val());
        var item = {};
        item ["source"] = source;
        item ["target"] = target;
        item ["numberOfMessagesToSend"] = count;
        jsonObj.push(item);
    });
    return jsonObj;
}

function post(json, url){
    $.ajax({
        cache: false,
        type: "POST",
        contentType :"application/json",
        url: url,
        data: JSON.stringify(json),
        dataType: 'json'
    });
}

$(document).ready(function() {
    $("#submitRouting").click(function() {
       var jsonObj = createSteps($("#form"));
        var api = $("#form").find(".api").val();
        var json = {
            "api": api,
            "scenario": jsonObj
        };
        post(json, '/api/RoutingHandler');
    });
});



$(document).ready(function() {
    $("#submitQueue").click(function() {
        var jsonObj = createSteps($("#form"));
        var api = $("#form").find(".api").val();
        var seconds = Number($("#form").find(".secondsForProcessing").val());
        var json = {
            "api": api,
            "scenario": jsonObj,
            "seconds_to_wait": seconds
        };

        post(json, '/api/QueueHandler');
    });
});

$(document).ready(function() {
    $("#submitCB").click(function() {
        alert("Hi");
        var jsonObj = createSteps($("#form"));
        var api = $("#form").find(".api").val();
        var Message = Number($("#form").find(".messageToRej").val());
        var NumberOfRejection = Number($("#form").find(".RejTime").val());
        var json = {
            "api": api,
            "scenario": jsonObj,
            "on_of_message_to_be_rejected": Message,
            "rejection_quantity":NumberOfRejection
        };

        post(json, '/api/CBHandler');
    });
});

$(document).ready(function() {
    $("#submitTransformation").click(function() {
        var jsonObj = createSteps($("#form"));
        var api = $("#form").find(".api").val();
        var json = {
            "api": api,
            "scenario": jsonObj
        };
        post(json, '/api/Transformation');
    });
});
function getFormData($form){
    var unindexedArray = $form.serializeArray();
    var indexedArray = {};
    $.map(unindexedArray, function (n, i){
        indexedArray[n['name']] = n['value'];

    });
    return indexedArray;
}

