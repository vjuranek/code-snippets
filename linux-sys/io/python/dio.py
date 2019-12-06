import mmap
import os
import stat

path = './test'

fd = os.open(path, os.O_WRONLY | os.O_DIRECT | os.O_DSYNC | os.O_CREAT | os.O_EXCL, stat.S_IRUSR | stat.S_IWUSR)
# os.unlink(path)

mem = mmap.mmap(-1, 1024)
to_write = b' ' * 1024

mem.write(to_write)

os.write(fd, mem)
os.close(fd)
