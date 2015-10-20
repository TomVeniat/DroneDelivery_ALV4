var express = require("express"),
	bodyParser = require("body-parser"),
	app = express();

app.use(bodyParser.json());

app.post('/drone_message', function (req, res) {
	var message = req.body;

	console.log(message);
	res.end('On a reçu ton POST');
});

app.listen(8080);