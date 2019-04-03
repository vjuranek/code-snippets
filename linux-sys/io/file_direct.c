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
  //opening file on device which doesn't support O_DIRECT (e.g. /tmp with tmpfs) will result in error when opening file
  int fd = open("/home/vjuranek/tmp/test", O_WRONLY | O_CREAT | O_TRUNC | O_DIRECT, S_IWUSR | S_IRUSR | S_IWGRP | S_IRGRP | S_IROTH);
  if (fd == -1) {
    perror("cannot open file");
  }


  ssize_t pagesize = 512;//getpagesize();
  printf("page size: %d\n", pagesize);

  char* buff = (char*) malloc(pagesize);
  char* alig_buff = (char*) ((((ssize_t)buff + pagesize - 1) / pagesize) * pagesize);
  printf("buff: %d\n", (ssize_t)buff);
  printf("alig_buff: %d\n", (ssize_t)alig_buff);
  strcpy(alig_buff, "hello world!");

  int len = strlen(alig_buff);
  printf("buffer size: %d\n", len);
  
  ssize_t nw = 0;
  nw = write(fd, alig_buff, pagesize);
  if (nw == -1) {
    perror("cannot write to file");
  }

  free(buff);
  
  if (close(fd) == -1) {
    perror("cannot close file");
  }
  
  return nw;
}

int main() {
  int written = write_file_direct();
  printf("written %d bytes\n", written);
}
 
