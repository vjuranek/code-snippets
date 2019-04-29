#include <stdio.h>
#include <stdlib.h>

struct test_struct {
  int x;
  int y;
};

void clean_up_int(int *i) {
  printf("Variable cleaning up\n");
  printf("Int: %d\n", *i);
}


void clean_up_struct(struct test_struct *ts) {
  printf("Variable cleaning up\n");
  printf("Test struct x: %d\n", ts->x);
  printf("Test struct y: %d\n", ts->y);
}

static void use_vars(int *i, struct test_struct *ts) {
  *i = 100;
  ts->x = 10;
  ts->y = 20;
}
  
int main() {
  int i __attribute__ ((__cleanup__(clean_up_int))) = 1; // setting cleanup function and variable initialization
  struct test_struct ts __attribute__ ((__cleanup__(clean_up_struct))); // setting cleanup function
  use_vars(&i, &ts);
}
