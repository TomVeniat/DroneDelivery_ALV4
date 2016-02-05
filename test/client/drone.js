/**
 * Created by Quentin on 11/8/2015.
 */

/**
 * Here we create an objet Drone with an id and an event.
  * @param id of the drone
 * @param event that he will to the server
 * @param totalMessages that will be posted by this drone.
 * @param logPeriod The drone will log one out of logPeriod messages.
 * @constructor
 */
var Drone = function(id, event, totalMessages, pingFrequency) {
    this.id = id;
    this.event = event;
    var sentMessages = 0;

    /**
     * This function starts a drone. Now the drone just send one message, but he is supposed
     * to send 1 message every 5 seconds. TODO !(OK?)
     */
    this.run = function() {
        totalPingFrequency += pingFrequency;

        //Every 5 secondes send a message
         var sending = setInterval(function sendInfo() {


             if( sentMessages % 5 == 0 ){
                 console.log("Drone n°" + id + " : Message n°" + sentMessages + " posted.");
             }


             var test = {"lon":"13.7","lat":"13.7","alt":"10","fuel":"95.00541661529698","id":"" + id,"event":"usual_tracking_message"};
             console.log(test);
             $.post("http://37.187.103.241:9000/drone_message",test).done(function(data) {
                    if((sentMessages % 5) == 0 )
                        console.log("Drone n°" + id + " : Message n°" + (sentMessages) + " : OK.");

                 });
             gloablTotalMessages++;

             if (++sentMessages == totalMessages){
                 clearInterval(sending);
                 totalPingFrequency -= pingFrequency;
                 console.log("Drone " + id + " finished.");
             }
         }, 1000/pingFrequency);
    }
};

Drone.prototype = new Drone();
