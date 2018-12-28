package com.github.vjuranek.playground;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SendRec {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        if (argv.length < 1) {
            System.err.println("At least one argument ('send [msg]' or 'rec') must be provided");
            System.exit(1);
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            switch (argv[0]) {
            case "send":
                if (argv.length != 2) {
                    System.err.println("Exactly two arguments ('send [msg]') must be provided");
                    break;
                }
                send(channel, argv[1]);
                break;

            case "rec":
                receive(channel);
                break;
                
            default:
                System.err.println("Unknown command");
                break;
            }

        }
    }

    private static void send(Channel channel, String message) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("Sent '" + message + "'");
    }

    private static void receive(Channel channel) throws IOException, InterruptedException {
        System.out.println("Waiting for messages for 1 sec.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

        Thread.sleep(1_000);
    }
}
