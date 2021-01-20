#!/usr/bin/env python

import logging
import sys

# Simple way
# logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)


# More detailed setting
handler = logging.StreamHandler(sys.stdout)
handler.setLevel(logging.DEBUG)
fmt = logging.Formatter("[%(name)s::%(levelname)s] %(asctime)s: %(message)s")
handler.setFormatter(fmt)

log = logging.getLogger("test")
log.setLevel(logging.DEBUG)
log.addHandler(handler)


log.debug("start")
log.info("execution")
log.debug("stop")

