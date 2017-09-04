#!/usr/bin/env python

import hashlib
from commons import get_arg

to_encode = get_arg()
encoded = hashlib.md5(str.encode(to_encode)).hexdigest()
print(encoded)

