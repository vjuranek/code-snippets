package main

import (
	"fmt";
	libvirt "libvirt.org/libvirt-go"
)

func main() {
	conn, err := libvirt.NewConnect("qemu:///system")
	if err != nil {
		fmt.Printf("Failed to open connection to QEMU")
	}
	
	fmt.Printf(conn.GetCapabilities())
	
	defer conn.Close()
}
