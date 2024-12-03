#!/usr/bin/env python3

import json

import zulip

# Pass the path to your zuliprc file here.
client = zulip.Client(config_file="./zuliprc")

request = {
    "anchor": "newest",
    "num_before": 100,
    "num_after": 0,
    "narrow": [
        {"operator": "channel", "operand": "community-oracle"},
    ],
}
result = client.get_messages(request)

for msg in result["messages"]:
    print(msg["subject"])
    print(msg["sender_full_name"])
    print(msg["content"])
