#!/usr/bin/env python3

import pika

from durable_queue import QUEUE_NAME


def rec_callback(channel, method, properties, body):
    print("Received {}".format(body))
    print("Sending message ACK")
    channel.basic_ack(delivery_tag=method.delivery_tag)


with pika.BlockingConnection(pika.ConnectionParameters('localhost')) as conn:
    ch = conn.channel()
    ch.queue_declare(queue=QUEUE_NAME, durable=True)  # ensure queue exists

    ch.basic_consume(rec_callback, queue=QUEUE_NAME)

    print("Waiting for messages. To exit press CTRL+C")
    ch.start_consuming()
