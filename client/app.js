/**
 * Created by Quentin on 11/7/2015.
 */


function run() {
    console.log("derp");
    $.post("http://localhost:9000/drone_message",
        {
            "id": 3,
            "lat": 10.1,
            "lon": 12.1,
            "alt": 15.0,
            "fuel": 99,
            "event": "usual_tracking_message"
        }).done(function(data) {
            alert("derp");
        });

    start();

};

function derp() {
    console.log("herp");
}