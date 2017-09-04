#!/usr/bin/env python3

import base64
from commons import get_arg

to_encode = get_arg()
encoded = base64.b64encode(str.encode(to_encode))
#print('Base64 hash of "{}" is "{}"'.format(to_encode, encoded.decode("utf-8")))
print(encoded.decode("utf-8"))
