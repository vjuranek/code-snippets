#!/usr/bin/env python

import asyncio
import websockets

async def hello(conn, path):
    num = await conn.recv()
    await conn.send(f"Hello client #{num}!")


def start_server():
    start_server = websockets.serve(hello, "localhost", 8000)
    asyncio.get_event_loop().run_until_complete(start_server)
    asyncio.get_event_loop().run_forever()


def stop_server():
    asyncio.get_event_loop().stop()
    
    
if __name__ == '__main__':
    try:
        start_server()
    except KeyboardInterrupt:
        stop_server()

