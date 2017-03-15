var stompClient = null;

var fromUserId = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    var headers = {
      // additional header
      'Access-Control-Allow-Origin': ''
    };
    var socket = new SockJS('http://localhost:8080/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect(headers, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + $("#userId").val(), function (greeting) {
            // showGreeting(JSON.parse(greeting.body).content);
            showGreeting(JSON.parse(greeting.body));
        });
        fromUserId = $("#userId").val();
        $("#userId").prop("disabled", true);
    });

}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    $("#userId").prop("disabled", false);
}

function sendMessage() {
    stompClient.send("/app/hello/" + $("#destinationUserId").val(), {}, JSON.stringify({'userId': fromUserId, 'message': $("#message").val()}));
}

function showGreeting(message) {
    $("#messages").append("<tr><td>User: " + message.userId + " - " + message.message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});