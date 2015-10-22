/**
 * Created by Quentin on 10/21/2015.
 */

var express = require("express"),
    router = express.Router();

router.post('/subscription/create', function (req, res) {
    console.log("derp");
    var message = req.body;
    console.log(message);
    res.end();
});


module.exports = router;