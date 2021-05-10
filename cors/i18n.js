var text = [
    {
        id: "cors_uebung",
        en: "CORS Exercise",
        de: "CORS Übung"
    }, {
        id: "dont_reload",
        en: "Stay on this page to finish the exercise. Don't reload.",
        de: "Bleib auf dieser Seite, um die Übung abzuschließen. Nicht neu laden."
    }, 
    
    {
        id: "get_reject",
        en: "<b>The GET request has been rejected from the server</b>",
        de: "<b>Der GET Request wurde vom Server abgelehnt.</b>"
    }, {
        id: "get_resolve",
        en: "For the request to be accepted, the server needs to allow the origin https://attacat.de:8666. To achieve this, uncomment the line <pre>CorsDemoFilter.java:15: response.setHeader(\"Access-Control-Allow-Origin\", \"https://attacat.de:8666\");</pre> and restart vulnerads.",
        de: "Damit er angenommen wird muss der Server https://attacat.de:8666 als Anfragenden zulassen. Dazu die Zeile <pre>CorsDemoFilter.java:15: response.setHeader(\"Access-Control-Allow-Origin\", \"https://attacat.de:8666\");</pre> einkommentieren und vulnerads neu starten."
    }, {
        id: "get_success",
        en: "<b>The GET request has been accepted by the server.</b>",
        de: "<b>Der GET Request wurde vom Server bearbeitet.</b>"
    }, {
        id: "get_cors_correct",
        en: "CORS is configured correctly.",
        de: "CORS ist richtig konfiguriert."
    }, 
    
    {
        id: "default_post_reject",
        en: "<b>The default POST request has been rejected from the server</b>",
        de: "<b>Der Standard POST Request wurde vom Server abgelehnt.</b>"
    }, {
        id: "default_post_resolve",
        en: "For the request to be accepted, the server needs to allow the origin https://attacat.de:8666. To achieve this, uncomment the line <pre>CorsDemoFilter.java:15: response.setHeader(\"Access-Control-Allow-Origin\", \"https://attacat.de:8666\");</pre> and restart vulnerads.",
        de: "Damit er angenommen wird muss der Server https://attacat.de:8666 als Anfragenden zulassen. Dazu die Zeile <pre>CorsDemoFilter.java:15: response.setHeader(\"Access-Control-Allow-Origin\", \"https://attacat.de:8666\");</pre> einkommentieren und vulnerads neu starten."
    }, {
        id: "default_post_success",
        en: "<b>The default POST request has been accepted by the server.</b>",
        de: "<b>Der Standard POST Request wurde vom Server bearbeitet.</b>"
    }, {
        id: "default_post_cors_correct",
        en: "CORS is configured correctly.",
        de: "CORS ist richtig konfiguriert."
    }, 
    
    
    {
        id: "preflight_post_reject",
        en: "<b>The preflight POST request has been rejected from the server.</b>",
        de: "<b>Der Preflight POST Request wurde vom Server abgelehnt.</b>"
    }, {
        id: "preflight_post_resolve",
        en: "For the request to be accepted, the server needs to allow the origin https://attacat.de:8666, as well as the header \"X-My-Own-Header\". To achieve this, additionally uncomment the line <pre>CorsDemoFilter.java:16: response.setHeader(\"Access-Control-Allow-Headers\", \"X-My-Own-Header\");</pre> and restart vulnerads.",
        de: "Damit er angenommen wird muss der Server https://attacat.de:8666 als Anfragenden und den Header \"X-My-Own-Header\" zulassen. Dazu auch die Zeile <pre>CorsDemoFilter.java:16: response.setHeader(\"Access-Control-Allow-Headers\", \"X-My-Own-Header\");</pre> einkommentieren und neu kompilieren."
    }, {
        id: "preflight_post_success",
        en: "<b>The preflight POST request has been accepted by the server.</b>",
        de: "<b>Der Preflight POST Request wurde vom Server bearbeitet.</b>"
    }, {
        id: "preflight_post_cors_correct",
        en: "CORS is configured correctly.",
        de: "CORS ist richtig konfiguriert."
    }, 
    
    
    {
        id: "delete_reject",
        en: "<b>The DELETE request has been rejected from the server.</b>",
        de: "<b>Der DELETE Request wurde vom Server abgelehnt.</b>"
    }, {
        id: "delete_resolve",
        en: "For the request to be accepted, the server needs to allow DELETE requests. To achieve this, add the method to the line <pre>CorsDemoFilter.java:14: response.setHeader(\"Access-Control-Allow-Methods\", \"GET, POST, <b>DELETE</b>\");</pre> and restart vulnerads.",
        de: "Damit er angenommen wird muss der Server DELETE Requests zulassen. Dazu in der Zeile <pre>CorsDemoFilter.java:14: response.setHeader(\"Access-Control-Allow-Methods\", \"GET, POST, <b>DELETE</b>\");</pre> ergänzen und vulnerads neu starten."
    }, {
        id: "delete_success",
        en: "<b>The DELETE request has been accepted by the server.</b>",
        de: "<b>Der DELETE Request wurde vom Server bearbeitet.</b>"
    }, {
        id: "delete_cors_correct",
        en: "CORS is configured correctly.",
        de: "CORS ist richtig konfiguriert."
    }, 
    
    {
        id: "congrats",
        en: "Congratulations!",
        de: "Herzlichen Glückwunsch!"
    }, {
        id: "finished",
        en: "<b>You have successfully finished the CORS exercise!</b>",
        de: "<b>Du hast die CORS Übung erfolgreich abgeschlossen!</b>"
    } 
    
]

function changelanguage(lang) {
    for(var i=0; i<text.length; i++) {
        var cur = text[i];
        var element = document.getElementById(cur.id);
        if (element) {
            element.innerHTML=cur[lang];
        } else {
            console.log("Element " + cur.id + " could not be found for translation");
        }
    }
}
