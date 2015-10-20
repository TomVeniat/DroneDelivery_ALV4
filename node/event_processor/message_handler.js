var express = require("express"),
	app = express();

app.post('/drone_message', function (req, res) {
	res.end('On a reçu ton POST');
});

app.listen(8080);