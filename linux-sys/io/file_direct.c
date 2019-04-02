// man open says:
// "The O_DIRECT, O_NOATIME, O_PATH, and O_TMPFILE flags are Linux-specific.  One must define _GNU_SOURCE to obtain their definitions."
// Or compile with gcc -D_GNU_SOURCE
#define _GNU_SOURCE

#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <sys/types.h>
#include <sys/stat.h>


extern int errno;

int write_file_direct() {
  //ssize_t pagesize = getpagesize();
  //printf("pagesize: %d\n", pagesize);

  //opening file on device which doesn't support O_DIRECT (e.g. /tmp with tmpfs) will result in error when opening file
  int fd = open("/home/vjuranek/tmp/test", O_WRONLY | O_CREAT | O_TRUNC | O_DIRECT, S_IWUSR | S_IRUSR | S_IWGRP | S_IRGRP | S_IROTH);
  if (fd == -1) {
    perror("cannot open file");
  }

  const char buf[4096] = "hello world!";

  int len = strlen(buf);
  ssize_t nw = 0;

  nw = write(fd, buf, len);
  if (nw == -1) {
    perror("cannot write to file");
  }
  
  if (close(fd) == -1) {
    perror("cannot close file");
  }
  
  return nw;
}

int main() {
  int written = write_file_direct();
  printf("written %d bytes\n", written);
}
 
