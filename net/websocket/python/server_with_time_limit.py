#!/usr/bin/env python

import asyncio
import websockets

async def hello(conn, path):
    num = await conn.recv()
    await conn.send(f"Hello client #{num}!")


async def start_server():
    await websockets.serve(hello, "localhost", 8000)


async def run_server(time):
    await start_server()
    await asyncio.sleep(time)


if __name__ == '__main__':
    try:
        asyncio.run(run_server(10))
    except KeyboardInterrupt:
        asyncio.get_event_loop().stop()
