<script src="http://code.jquery.com/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
        $("#+").click(function() {
        var num = $(".step").length;
        var newNum  = new Number(num + 1);
        var newSection = $("#step" + num).clone().attr("id", "step" + newNum);
        $(".step").last().append(newSection)
        newSection.children(":first").children(":first").attr("id", "source" + newNum);
        newSection.children(":nth-child(3)").children(":first").attr("id", "target" + newNum);
        newSection.children(":nth-child(4)").children(":first").attr("id", "count" + newNum);
        $(".step").last().append(newSection)
</script>