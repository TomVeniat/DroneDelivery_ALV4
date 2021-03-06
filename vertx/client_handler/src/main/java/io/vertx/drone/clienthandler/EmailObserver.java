package io.vertx.drone.clienthandler;

import io.vertx.drone.clienthandler.smtpservice.GmailManager;

public class EmailObserver implements  Observer {
    private GmailManager gmailManager;

    @Override
    public void notify(String msg) {
        gmailManager.sendMessage(msg);
    }
    public EmailObserver(String destination) {
        gmailManager = new GmailManager();
        gmailManager.setDestination(destination);
    }
}
