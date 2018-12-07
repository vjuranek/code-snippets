CC=gcc
# CFLAGS=-I.

LIBS = -lvirt
BIN_DIR = bin
INC__DIR = include
O_DIR = obj
SRC_DIR = src

# _DEPS = basic.h
# DEPS = $(patsubst %,$(INC_DIR)/%,$(_DEPS))

_OBJ = basic.o
OBJ = $(patsubst %,$(O_DIR)/%,$(_OBJ))

SRC = $(patsubst %,$(SRC_DIR)/%,%.c)

$(O_DIR)/%.o: $(SRC) $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

basic: $(OBJ)
	$(CC) -o $(BIN_DIR)/$@ $^ $(CFLAGS) $(LIBS)

.PHONY: clean

clean:
	rm -f $(BIN_DIR)/* $(O_DIR)/*.o $(SRC_DIR)/*~ core # $(INC_DIR)/*~ 
