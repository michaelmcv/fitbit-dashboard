// A $( document ).ready() block.
$( document ).ready(function() {
    revealWinner()
});

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
