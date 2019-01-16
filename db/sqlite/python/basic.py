#!/usr/bin/env python

import sqlite3

DB_FILE="/tmp/test.db"
TABLE_NAME="my_table"

conn = sqlite3.connect(DB_FILE)
c = conn.cursor()

c.execute("CREATE TABLE IF NOT EXISTS {tn} (id INTEGER PRIMARY KEY, msg TEXT)".format(tn=TABLE_NAME))
conn.commit()

c.execute("INSERT INTO {tn}(id, msg) VALUES (?, ?)".format(tn=TABLE_NAME), (1, "hello world1"))
conn.commit()

res = c.execute("SELECT * FROM {tn}".format(tn=TABLE_NAME))
for r in res:
    print(r)

conn.close()
