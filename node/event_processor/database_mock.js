/*
	Module simulate the database.
*/

exports.findOne = function(trackingMessageID) {
  var databaseJSON = [];
  generateDatabase(databaseJSON);
  for (var i = 0; i < databaseJSON.length; i++){
    if (databaseJSON[i]['id_drone'] === trackingMessageID) {
      return {num_command:databaseJSON[i]['num_command']};
    }
  }
  return {ERROR:"No command number."};
}

function pad(n, width, z) {
  z = z || '0';
  n = n + '';
  return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}

function generateDatabase(databaseJSON){
  for(var i=0;i<3000;i++){
    databaseJSON.push({id_drone:i,num_command:"P00000"+pad(i,4)});
  }
}
