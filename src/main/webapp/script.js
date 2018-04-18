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

$(document).ready(function() {
    $("#form").submit(function() {
        alert('hi!');
        var jsonObj = [];
        $("#form").children('.step').each(function () {
        var source = $(this).find(".source").val();
        var target = $(this).find(".target").val();
        var count = $(this).find(".count").val();
        var item = {};
        alert('item');
        item ["source"] = source;
        item ["target"] = target;
        item ["count"] = count;
        jsonObj.push(item);
        });
        var api = $(this).find(".api").val();
        var json = {
            "api": api,
            "scenario": jsonObj
        };
        $.ajax({
            cache: false,
            type: "POST",
            contentType :"application/json",
            url: '/api/RoutingHandler',
            data: JSON.stringify(json),
            dataType: 'json',
            success : function(result) {
                alert('wow');
            }
        });
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