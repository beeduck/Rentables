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
      'Authorization': 'Bearer 37177f44-66f4-436a-9075-ea09e9a589f7'
    };
    var socket = new SockJS('http://localhost:8080/rent-chat?access_token=37177f44-66f4-436a-9075-ea09e9a589f7');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
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
    stompClient.send("/msg/hello/" + $("#destinationUserId").val(), {}, JSON.stringify({'userId': fromUserId, 'message': $("#message").val()}));
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