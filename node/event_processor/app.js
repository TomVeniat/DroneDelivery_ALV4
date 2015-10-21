/*
	Main app.
*/

var express = require("express"),
	bodyParser = require("body-parser"),
	messageHandler = require("./message_handler"),
	app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use("/", messageHandler);

app.listen(8080);