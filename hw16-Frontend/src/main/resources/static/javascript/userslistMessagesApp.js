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
        "/app/getUsersList", {}, "getAllUsersLis"
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


};

$(document).ready(connect());

$(() => {
      $("#getUsersList").click(sendMessage);
});
