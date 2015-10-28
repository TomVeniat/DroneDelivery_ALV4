/*
 * Module responsible for sending drones messages, it uses the TCP protocol.
 */

var net = require('net'),
	sleep = require('sleep');
var id = 1;
var longitude="50.332W";
var latitude="40.212N";
var altitude=200;
var battrie=1;
var event = "";

function simulate() {
	
	id++;
	battrie *= 0.99 ;
}

function generateMessage() {
	var message = '';
	message += 'id :' + id + ';longitude :'+longitude
				+';latitude :'+latitude+';battrie :'+(battrie*100)+'%;'+'event:';
	return message;
}
exports.server = function () {
	return net.createServer(function(socket) {
		while(true) {
				socket.write(generateMessage());
				socket.pipe(socket);
				sleep.sleep(2);
				simulate();
			}
			});
}



