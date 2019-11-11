var stompClient = null;

function setConnected(connected) {
    // $("#connect").prop("disabled", connected);
    // $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#usersListTable").show();
    } else {
        $("#usersListTable").hide();
    }
    // $("#greetings").html("");
}


const connect = () => {
    //building absolute path so that websocket doesnt fail when deploying with a context path
    var loc = window.location;
    var url = '//' + loc.host + loc.pathname + '/websocket';

    stompClient = Stomp.over(new SockJS(url));
    stompClient.connect({}, frame => {

        setConnected(true);

        console.log(`Connected: ${frame}`);

        stompClient.subscribe('/topic/DBServiceResponse', DBServiceMessage =>
            showUsersList(JSON.parse(DBServiceMessage.body).messageStr));


    });
};

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
};

const sendName = () => {
    /*   let formdata = $("#saveNewUserForm").serializeArray();
       let data = {};
       $(formdata).each(function (index, obj) {
           data[obj.name] = obj.value;
       });*/

    let newUser = {
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        age: parseInt($("#age").val())

    };


    stompClient.send(
        "/app/createUserMessage",
        {},
        JSON.stringify({
            'messageStr': newUser
        }));


};


const showUsersList = messageStr =>
    $("#usersListTable").append(`<tr><td>${messageStr}</td></tr>`);

$(document).ready(connect());

$(() => {
    $("form").on('submit', event => event.preventDefault());
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#saveUserButton").click(sendName);
});