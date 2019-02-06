#!/usr/bin/env python3

import pika

QUEUE_NAME = "test_durable"

with pika.BlockingConnection(pika.ConnectionParameters('localhost')) as conn:
    ch = conn.channel()
    ch.queue_declare(queue=QUEUE_NAME, durable=True)  # ensure queue exists

    msg_properties = pika.BasicProperties(delivery_mode=2)
    ch.basic_publish(exchange='',
                     routing_key=QUEUE_NAME,
                     body='Durable Hello World!',
                     properties=msg_properties)
    print("Sent durable 'Hello World!'")
