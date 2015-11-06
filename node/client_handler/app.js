/**
 * Created by Quentin on 10/21/2015.
 */

var express = require("express"),
    bodyParser = require("body-parser"),
    requestHandler = require("./request_handler")
    app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false}));

app.use("/",requestHandler);

app.listen(9001);