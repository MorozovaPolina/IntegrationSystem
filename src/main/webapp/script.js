var Num =1;
$(document).ready(function() {
    $(".addBT").click(function () {
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
        }
    });
});

$(document).ready(function () {
    $("#form").submit(function () {
        var $form = $("#form");
        var formClass = $("#form").value();
        alert('CLASS');
        var json;
        $("#form").each(function () {
            alert('child');
            alert(this.value());
        });

        var json = getFormData($form);
        $.ajax({
            cache: false,
            type: "POST",
            url: '/api/RoutingHandler',
            data: JSON.stringify(json),
            dataType: 'json',
            success: function (data) {
                alert('success');
            },
            failure: function (errMsg) {
                alert('failure');

            }
        })
    })
})

function getFormData($form){

    var unindexedArray = $form.serializeArray();

    var indexedArray = {};
    $.map(unindexedArray, function (n, i){
        indexedArray[n['name']] = n['value'];

    });
    return indexedArray;}