var Num =1;
var R=1;
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
            var next = $(this).parent().next($(this).parent()).attr("class");
            if(next!="step"){
                $(this).parent().prev($(this).parent()).find(".addBT").fadeIn(30);
            }
            $(this).parent().remove();
        };
    });
});

$(document).ready(function() {
    $(".addR").click(function() {
        R++;
        var newSection = $(this).parent().clone(true, true).attr("id", "reject" + R);
        newSection.insertAfter($(this).parent());
        $(this).fadeOut(20);
        // $(".step").last().append(newSection);
        newSection.text($('newSection').attr('id'));
    });
});
$(document).ready(function() {
    $(".removeR").click(function () {
       // alert("remove rej 1");
        if($(".reject").length>1) {
            var next = $(this).parent().next($(this).parent()).attr("class");
            if(next!="reject"){
                $(this).parent().prev($(this).parent()).find(".addR").fadeIn(30);
            }
            $(this).parent().remove();
        };
    });
});

function createSteps($form){
    var jsonObj = [];
  //  alert('steps name ' + $(this).attr("name"));
    $form.children('.step').each(function () {
        var source = $(this).find(".source").val();
        var target = $(this).find(".target").val();
        var count = new Number( $(this).find(".count").val());
       // alert('s:' + source+" t: "+ target+" MC: "+count);
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

function createRejection($form){
    var jsonObj = [];

    $form.children('.reject').each(function () {

        var rejectionMessage = new Number($(this).find(".messageToRej").val());
        var numberOfRejections= new Number($(this).find(".RejTime").val());
       // alert("rej Name: "+$(this).attr("name")+" "+numberOfRejections);
        var item = {};
        item ["rejectionMessage"] = rejectionMessage;
        item ["numberOfRejections"] = numberOfRejections;
      //  alert(rejectionMessage+" "+numberOfRejections)
        jsonObj.push(item);
    });
   // alert('CB + rej all ' + jsonObj);
    return jsonObj;
}

$(document).ready(function() {
    $("#submitRouting").click(function() {
       var jsonObj = createSteps($("#Steps"));
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
        var jsonObj = createSteps($("#Steps"));
        var api = $("#form").find(".api").val();
        var seconds = new Number($("#maxSeconds").val());
        var minSuitableMessageProcessingTime = new Number($("#minSeconds").val());
        var json = {
            "api": api,
            "scenario": jsonObj,
            "maxSuitableMessageProcessingTime": seconds,
            "minSuitableMessageProcessingTime": minSuitableMessageProcessingTime
        };

        post(json, '/api/QueueHandler');
    });
});

$(document).ready(function() {
    $("#submitCB").click(function() {
        var jsonObj = createSteps($("#Steps"));
        var rejections=createRejection($("#Rejection"));
        //alert("rej done")
        var secondAfterrejection = $("#form").find("#minRej").val();
        var api = $("#api").val();
        var json = {
            "api": api,
            "scenario": jsonObj,
            "secondsAfterRejection": secondAfterrejection,
            "rejection": rejections
        };

        post(json, '/api/CBHandler');
    });
});

$(document).ready(function() {
    $("#submitTransformation").click(function() {
        var jsonObj = createSteps($("#Steps"));
        var api = $("#form").find(".api").val();
        var json = {
            "api": api,
            "scenario": jsonObj
        };
        post(json, '/api/Transformation');
    });
});

$(document).ready(function () {
    log();
    var  myVar = setInterval(newMes, 2000);
});
function log() {
    $.ajax({
        cache: false,
        type: "GET",
        contentType :"application/json",
        url: '/api/LogMes',
        success: function (data) {
            for(var i=0, len=data.length; i<len; i++) {
                var logV=$("#log").val();
                $("#log").val(data[i] + '\r\n' + logV);
            };
        }
    });
};
function newMes() {
    $.ajax({
        cache: false,
        type: "GET",
        contentType :"application/json",
        url: '/api/NewMes',
        success: function (data) {
            for(var i=0, len=data.length; i<len; i++) {
                var logV=$("#log").val();
                if(data[i].indexOf('Session will be marked as failed')!=-1||data[i].indexOf("All messages received")!=-1)
                    alert(data[i]);
                $("#log").val(data[i] + '\r\n' + logV);
            };
        }
    });

}

function deleteLog() {
    $("#log").val("");
    alert('clear');
    $.ajax({
        cache: false,
        type: "GET",
        contentType :"application/json",
        url: '/api/deleteLog'});

}
function getFormData($form){
    var unindexedArray = $form.serializeArray();
    var indexedArray = {};
    $.map(unindexedArray, function (n, i){
        indexedArray[n['name']] = n['value'];

    });
    return indexedArray;
}

