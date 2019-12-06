#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <sys/types.h>
#include <sys/stat.h>


extern int errno;

int write_file() {
  int fd = open("/tmp/test", O_WRONLY | O_CREAT | O_TRUNC, S_IWUSR | S_IRUSR | S_IWGRP | S_IRGRP | S_IROTH);
  if (fd == -1) {
    perror("cannot open file");
  }

  const char *buf = "hello world!";

  int len = strlen(buf);
  ssize_t nw, written = 0;
  
  while (len != 0 && (nw = write(fd, buf, len)) != 0) {
    if (nw == -1) {
      if (errno == EINTR) { // signal received before any bytes were written and call can be reissued
	continue;
      }
      perror("cannot write to file");
    }

    len -= nw;
    buf += nw;
    written += nw;
  }
  
  if (close(fd) == -1) {
    perror("cannot close file");
  }
  
  return written;
}

void read_file(int len) {
  int fd = open("/tmp/test", O_RDONLY);
  if (fd == -1) {
    perror("cannot open file");
  }

  char *buf = (char *) malloc(256 * sizeof(char));
  ssize_t nr, nread = 0;
  
  while (len != 0 && (nr = read(fd, buf, len)) != 0) {
    if (nr == -1) {
      if (errno == EINTR) { // signal received before any bytes were read and call can be reissued
	continue;
      }
      perror("cannot read from file");
    }

    len -= nr;
    buf += nr;
    nread += nr;
  }

  if (close(fd) == -1) {
    perror("cannot close file");
  }

  buf -= nread; 
  printf("read:\n%s", buf);
}

int main() {
  int written = write_file();
  printf("written %d bytes\n", written);
  read_file(written);
}
 
