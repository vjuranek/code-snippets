#!/usr/bin/env python3

import pika

from basic_sender import QUEUE_NAME


def rec_callback(ch, method, properties, body):
    print("Received %r" % body)


with pika.BlockingConnection(pika.ConnectionParameters('localhost')) as conn:
    ch = conn.channel()
    ch.queue_declare(queue=QUEUE_NAME)
    ch.basic_consume(rec_callback, queue=QUEUE_NAME, no_ack=True)

    print("Waiting for messages. To exit press CTRL+C")
    ch.start_consuming()
