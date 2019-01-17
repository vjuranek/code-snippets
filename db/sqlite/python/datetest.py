#!/usr/bin/env python

import sqlite3

DB_FILE="/tmp/date.db"
TABLE_NAME="my_table"

conn = sqlite3.connect(DB_FILE)
c = conn.cursor()

c.execute("CREATE TABLE IF NOT EXISTS {tn} (id INTEGER PRIMARY KEY, created DATETIME)".format(tn=TABLE_NAME))
conn.commit()

c.execute("INSERT INTO {tn}(id, created) VALUES (?, datetime('now'))".format(tn=TABLE_NAME), (3,))
conn.commit()

res_cur = c.execute("SELECT * FROM {tn}".format(tn=TABLE_NAME))
res = res_cur.fetchall()
print(len(res))
for r in res:
    print(r)
    print(r[1])

conn.close()
