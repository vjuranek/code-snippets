CC=gcc
# CFLAGS=-I.

LIBS = -lvirt
I_DIR = include
O_DIR = obj

# _DEPS = basic.h
# DEPS = $(patsubst %,$(IDIR)/%,$(_DEPS))

_OBJ = basic.o
OBJ = $(patsubst %,$(O_DIR)/%,$(_OBJ))


$(O_DIR)/%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

basic: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS) $(LIBS)

.PHONY: clean

clean:
	rm -f $(O_DIR)/*.o *~ core $(INCDIR)/*~ 
