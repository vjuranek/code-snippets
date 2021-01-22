#!/usr/bin/env python

import argparse
import asyncio
import logging
import sys

import websockets

URI = "ws://localhost:8000/"
logging.basicConfig(stream=sys.stdout, level=logging.INFO)
log = logging.getLogger("client")


def parse_args():
    parser = argparse.ArgumentParser(description="Load testing: Web socket client.")
    parser.add_argument("--conn", type=int, default=10, help='Number of connections')
    return parser.parse_args()


async def hello():
    async with websockets.connect(URI) as conn:
        await conn.send("test")
        resp = await websocket.recv()
        print(f"{resp}")


async def prepare_connections(amount):
    conns = []
    for i in range(0, amount):
        # log.info(f"Creating connection #{i}")
        conn = await websockets.connect(URI)
        conns.append(conn)
    return conns


async def close_connections(conns):
    for conn in conns:
        await conn.close()


async def send_msg(conn, i):
    # if i%2 == 0:
    #     await asyncio.sleep(1)
    await conn.send(f"{i}")
    resp = await conn.recv()
    log.info(resp)


async def send_msgs(conns):
    i = 0
    for conn in conns:
        i += 1
        task = asyncio.create_task(send_msg(conn, i))
    # Await last task - not correct, but should cover most of the precceding tasks.
    await task


async def run(amount):
    conns = await prepare_connections(amount)
    await send_msgs(conns)
    await close_connections(conns)


args = parse_args()
log.info("Number of connections: {}".format(args.conn))

asyncio.get_event_loop().run_until_complete(run(args.conn))
