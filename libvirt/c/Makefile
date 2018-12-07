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
	mkdir -p $(@D)
	$(CC) -c -o $@ $< $(CFLAGS)

basic: $(OBJ)
	mkdir -p $(BIN_DIR)
	$(CC) -o $(BIN_DIR)/$@ $^ $(CFLAGS) $(LIBS)

.PHONY: clean

clean:
	rm -rf $(BIN_DIR) $(O_DIR) $(SRC_DIR)/*~ core # $(INC_DIR)/*~ 
