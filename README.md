# Event Notification and Accurate Status Tracking

Defense slides are [here][3].

And the demonstration video (not very understandable without a speaker) can be found [there][4].

## Setup

For now, setup instructions are only provided to deploy the full stack on Ubuntu locally.
[Contact us](#team) if you need further information.

The instructions for the Node.js version are not provided at this time.

### Local

#### Prerequisites

For the Vert.x version you should have [maven][6] installed at a version superior to `3.0.0`.

To run the Node.js one, you have to install [Node.js][7] at a version superior to `4.0.0` and npm.

Node.js is also used to run drone simulation scripts in order to test the application.

#### Ubuntu

##### Kafka

Get `Kafka 2.11-0.8.2.0` from the [official website][2], extract the tarball and launch a broker with the default configuration by following [these instructions][5].

#### Drone Message Handler

##### Vert.x version

Open a new shell from the project root:
 * `cd vertx/drone_message_handler/`
 * `mvn clean package`
 * `java -jar target/drone-message-handler-1.0-fat.jar`

##### Node.js version

// TODO

#### Event Processor

##### Storm version

Open a new shell from the project root:
 * `cd java/event_tracking_processor`
 * `mvn clean package`
 * `mvn compile exec:java -Dstorm.topology=basic_topology.KafkaTopology`

##### Node.js version (deprecated)

// TODO

#### Client Request Handler

##### Vert.x version

Open a new shell from the project root:
 * `cd vertx/client_handler/`
 * `mvn clean package`
 * `java -jar target/client-handler-1.0-fat.jar`

##### Node.js version

// TODO:

### Remote deployment

Contact [Tom Veniat][1] for further information.

## Testing the app

From the project root, run the node script `demo2.js` located in the `test` folder with an `host:port` argument which corresponds to the host and port where the Drone Message Handler is deployed (`localhost:9000` in local mode with the default configuration). Example:

`node demo2.js localhost:9000`

This will start some drone simulators that will send tracking messages to the Drone Message Handler.

To get email notifications, send a `POST` request to the host where the Client Request Handler is set-up (`localhost:9001 in local mode with the default configuration) at the path `/subscription/create` with the following request body:

```
{
    "email" : "your.email@yourdomain.dot",
    "topicId" : "topicXXXXX"
}
```

where XXXXX is a number between 19960 and 19970.

Example:

```
POST localhost:9001/subscription/create
{
    "email" : "your.email@yourdomain.dot",
    "topicId" : "topicXXXXX"
}
```

## Team

 * [Quentin Cornevin](qcornevin@gmail.com)
 * [Jin Hong](jinhong211@gmail.com)
 * [Marc Karassev](marc.karassev@yahoo.fr)
 * [Sun Quan](sun.quan@etu.unice.fr)
 * [Tom Veniat](veniat.tom@gmail.com)

## Links

// TODO
 * [apache-kafka][8]
 * [apache-storm][9]
 * [node.js][7]
 * [vert.x][10]

[1]: tom.veniat@etu.unice.fr
[2]: kafka.apache.org/downloads.html
[3]: https://docs.google.com/presentation/d/1ZhgDGsev7n3H-ZRyo_SWD8tYVQNxCFYM7SInkIHUmfk/pub?start=false&loop=false&delayms=3000
[4]: https://drive.google.com/file/d/0B7VSdYFJuGRuRHI5Q3dxaHRfanc/view?usp=sharing
[5]: http://kafka.apache.org/documentation.html#quickstart_startserver
[6]: https://maven.apache.org/
[7]: https://nodejs.org/en/
[8]: kafka.apache.org/
[9]: http://storm.apache.org/
[10]: http://vertx.io/