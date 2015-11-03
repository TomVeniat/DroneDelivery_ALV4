/*
	Main app.
*/

var http = require('http'),
    fs = require('fs'),
    msgGenerator=require('./message_generator');

function simulate() {
  //The options set the endpoint, the request POST with the data JSON
  var options = {
  	host : '10.188.64.232',
  	path : '/drone_message',
  	port : '8080',
  	method : 'POST',
    headers: {
          'Content-Type': 'application/json'
      }
  };
  //callback gets the reponse of the request 
  var callback = function(response) {
    var str = ''
    response.on('data', function (chunk) {
      str += chunk;
    });

    response.on('end', function () {
      console.log(str);
    });
  }

  var req = http.request(options, callback);
  //This is the data we post
  req.write(JSON.stringify(msgGenerator.generator()));
  req.end();
}

exports.simulate=simulate;