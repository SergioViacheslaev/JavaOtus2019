var stompClient = null;

function setConnected(connected) {
    if (connected) {
        $("#usersListTable").show();
        sendMessage();
    } else {
        $("#usersListTable").hide();
    }
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
            showUsersList(JSON.parse(DBServiceMessage.body)));


    });
};

const sendMessage = () => {


    stompClient.send(
        "/app/getUsersList",
        {}, "Hi"
    );

};


const showUsersList = (jsonUser) => {
    var tbody = $("#allUsersListTable");

    for (var i = 0; i < jsonUser.length; i++) {
        let user_data = '';

        user_data += '<tr>';
        user_data += '<td>' + jsonUser[i].id + '</td>';
        user_data += '<td>' + jsonUser[i].firstName + '</td>';
        user_data += '<td>' + jsonUser[i].lastName + '</td>';
        user_data += '<td>' + jsonUser[i].age + '</td>';
        user_data += '</tr>';

        tbody.append(user_data);
    }


    /*    let user_data = '';

        user_data += '<tr>';
        user_data += '<td>' + jsonUser.id + '</td>';
        user_data += '<td>' + jsonUser.firstName + '</td>';
        user_data += '<td>' + jsonUser.lastName + '</td>';
        user_data += '<td>' + jsonUser.age + '</td>';
        user_data += '</tr>';


        $("#allUsersListTable").append(user_data);*/
};

$(document).ready(connect());

$(() => {
    $("form").on('submit', event => event.preventDefault());
    $("#getUsersList").click(sendMessage);
});
