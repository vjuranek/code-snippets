#!/bin/env python

# taken from http://ldap3.readthedocs.io/tutorial.html, see this tutorial for more info

from ldap3 import Server, Connection, SUBTREE, ALL

total_entries = 0
server = Server('10.8.175.233', get_info=ALL)
c = Connection(server, user='cn=Manager', password='ispnPassword')
c.bind()
c.search(search_base = 'dc=infinispan,dc=org',
         search_filter = '(ou=*)',
         search_scope = SUBTREE,
         attributes = ['cn', 'uid'],
         paged_size = 5)
total_entries += len(c.response)
print "Total entirs: %s" % total_entries
for entry in c.response:
    print(entry['dn'], entry['attributes'])
