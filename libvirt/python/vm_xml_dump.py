#!/usr/bin/env python

import sys

import libvirt

vm_name = "test-vm"
conn = libvirt.open("qemu:///system")

if conn == None:
    print("Failed to containe connection to QEMU")
    sys.exit(1)

try:
    vm = conn.lookupByName(vm_name)
except libvirt.libvirtError:
    print("Failed to find the VM %s" % vm_name)
    sys.exit(1)

print(vm.XMLDesc())

conn.close()
