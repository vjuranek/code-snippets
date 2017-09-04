import sys

def get_arg():
    if len(sys.argv) < 2 :
        sys.exit("At least one argument has to be provided!")
    return sys.argv[1]
