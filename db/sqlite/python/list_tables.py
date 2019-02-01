#!/usr/bin/env python

import sqlite3

DB_FILE="/tmp/test.db"

conn = sqlite3.connect(DB_FILE)

res = conn.execute("SELECT name FROM sqlite_master")
tables = res.fetchone()

for table in tables:
    print(table)

conn.close()
