// A $( document ).ready() block.
$( document ).ready(function() {
    poulateDate()

    $( "#forDate" ).change(function() {
        $( "#dateSelectionForm" ).submit()
    });

    //run the countdown if we are not on the error page
    if( !$("#no-data-error").length ) {
        revealWinner()
    }
});

function poulateDate() {
    console.log("calling the populate date")
    var selectedDate = getParameterByName("forDate")
    console.log("selectedDate: " + selectedDate)
    $("#forDate").val(selectedDate);
    console.log("done")
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function revealWinner() {
    console.log("calling the reveal function")

    var totalTimeout = $("#countdown").text() || 0;
    var countdown = totalTimeout;

    console.log("starting the countdown")

    var timer = setInterval(function() {
        $("#countdown").text(--countdown);
        if (countdown == 0) {
            $('#message-label').text('Flat to the tin...');
            clearInterval(timer);
        }
    }, 1000);

    console.log("starting the reveal timeout");

    setTimeout(function() {
        $("#winner-total").attr('style','');
        $("#loser-total").attr('style','');
        $("#winner-image").addClass("trigger-event");
        $("#loser-image").addClass("trigger-event");
    }, totalTimeout*1000);

    console.log("done!")
}