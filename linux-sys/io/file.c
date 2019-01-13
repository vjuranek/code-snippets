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
  ssize_t nr = write(fd, buf, strlen(buf));
  if (nr == -1) {
    perror("cannot write to file");
  }

  if (close(fd) == -1) {
    perror("cannot close file");
  }
}

int main() {
  write_file();
}

  
