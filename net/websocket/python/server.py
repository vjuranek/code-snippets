#!/usr/bin/env python

import asyncio
import websockets

async def hello(conn, path):
    num = await conn.recv()
    await conn.send(f"Hello client #{num}!")

start_server = websockets.serve(hello, "localhost", 8000)

asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
