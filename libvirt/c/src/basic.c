#include <stdio.h>
#include <libvirt/libvirt.h>

int main(int argc, char *argv[])
{
  virConnectPtr conn;
  conn = virConnectOpen("qemu:///system");
  if (conn == NULL) {
    fprintf(stderr, "Failed to open connection to qemu:///system\n");
    return 1;
  }

  printf(virConnectGetCapabilities(conn));
  
  virConnectClose(conn);
  return 0;
}
