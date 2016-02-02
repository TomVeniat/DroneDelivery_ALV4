var express = require("express"),
	bodyParser = require("body-parser"),
	app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false}));

app.post("/", function (req, res) {
	console.log(req.body);
	res.end();
});

app.listen(8080);