/**
 * Module responsible for receiving drones messages by using the TCP protocol.
 */
var net = require('net');

var client = new net.Socket();
client.connect(8280, '127.0.0.1', function() {
	console.log('Connected');
});

client.on('data', function(data) {
	console.log('Received: ' + data);
	
});

client.on('close', function() {
	console.log('Connection closed');
});