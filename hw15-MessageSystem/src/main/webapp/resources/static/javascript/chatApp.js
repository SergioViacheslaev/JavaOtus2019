var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}


const connect = () => {
    //building absolute path so that websocket doesnt fail when deploying with a context path
    var loc = window.location;
    var url = '//' + loc.host + loc.pathname + '/websocket';

    stompClient = Stomp.over(new SockJS(url));
    stompClient.connect({}, frame => {

       setConnected(true);

    console.log(`Connected: ${frame}`);

    stompClient.subscribe('/topic/chatMessages', greeting =>
    showGreeting(JSON.parse(greeting.body).messageStr));

    stompClient.subscribe('/topic/info', greeting =>
    showGreeting(JSON.parse(greeting.body).messageStr));


});
};

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
};

const sendName = () => stompClient.send(
    "/app/message",
    {},
    JSON.stringify({'messageStr': $("#message").val()}));

const showGreeting = messageStr =>
$("#chatLine").append(`<tr><td>${messageStr}</td></tr>`);

$(() => {
    $("form").on('submit', event => event.preventDefault());
$("#connect").click(connect);
$("#disconnect").click(disconnect);
$("#send").click(sendName);
});