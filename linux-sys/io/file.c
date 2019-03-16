#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include <sys/types.h>
#include <sys/stat.h>

void write_file() {
  int fd;

  fd = open("/tmp/test", O_WRONLY | O_CREAT | O_TRUNC, S_IWUSR | S_IRUSR | S_IWGRP | S_IRGRP | S_IROTH);
  if (fd == -1) {
    perror("cannot open file");
  }

  const char *buf = "hello world!";

  int len = strlen(buf);
  ssize_t nw;
  extern int errno;
  
  while (len != 0 && (nw = write(fd, buf, len)) != 0) {
    if (nw == -1) {
      if (errno == EINTR) { // signal received before any bytes were written and call can be reissued
	continue;
      }
      perror("cannot write to file");
    }

    len -= nw;
    buf += nw;
  }
  
  if (close(fd) == -1) {
    perror("cannot close file");
  }
}

int main() {
  write_file();
}

  
