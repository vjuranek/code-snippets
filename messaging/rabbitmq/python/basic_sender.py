#!/usr/bin/env python3

import pika

QUEUE_NAME = "test"

with pika.BlockingConnection(pika.ConnectionParameters('localhost')) as conn:
    ch = conn.channel()
    ch.queue_declare(queue='test')

    ch.basic_publish(exchange='', routing_key='test', body='Hello World!')
    print("Sent 'Hello World!'")
