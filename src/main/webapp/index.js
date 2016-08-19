function sendRequest(){
    var login = document.getElementById("loginInput").value;
    var password = document.getElementById("passwordInput").value;
    var data = {};
    data.login = login;
    data.password = password;
    $.ajax({
        type: "POST",
        url: "/",
        processData: false,
        contentType: 'application/json',
        data: JSON.stringify(data)
    });
}