#!/usr/bin/env python

import sqlite3

DB_FILE="/tmp/test.db"
TABLE_NAME="my_table"

conn = sqlite3.connect(DB_FILE)
c = conn.cursor()

c.execute("CREATE TABLE IF NOT EXISTS {tn} (id INTEGER PRIMARY KEY, msg TEXT, msg2 TEXT)".format(tn=TABLE_NAME))
conn.commit()

# c.execute("INSERT INTO {tn}(id, msg) VALUES (?, ?)".format(tn=TABLE_NAME), (1, "hello1"))
# c.execute("INSERT INTO {tn}(id, msg) VALUES (?, ?)".format(tn=TABLE_NAME), (2, "hello2"))
# c.execute("INSERT INTO {tn}(id, msg, msg2) VALUES (?, ?, ?)".format(tn=TABLE_NAME), (3, "hello3", "world"))
# c.execute("INSERT INTO {tn}(id, msg, msg2) VALUES (?, ?, ?)".format(tn=TABLE_NAME), (4, "hello4", "world"))
# conn.commit()

# c.execute("DELETE FROM {tn} WHERE id = ?".format(tn=TABLE_NAME), (2,))

# c.execute("UPDATE {tn} SET msg = ? WHERE id = ?".format(tn=TABLE_NAME), ("hello1 update", 1)) 

# res_cur = c.execute("SELECT * FROM {tn}".format(tn=TABLE_NAME))
ids = [1, 2, 3]
sql = "SELECT id, msg FROM {tn} WHERE id IN ({ids})".format(tn=TABLE_NAME, ids=','.join('?' for _ in ids))
res_cur = c.execute(sql, ids)
res = res_cur.fetchall()

for r in res:
    print(r)

conn.close()
