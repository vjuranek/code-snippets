#!/usr/bin/env python

import sys

import libvirt

conn = libvirt.open("qemu:///system")

if conn == None:
    print("Failed to containe connection to QEMU")
    sys.exit(1)

print(conn.getCapabilities())

conn.close()
