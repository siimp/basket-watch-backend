import base64
import os
import fnmatch

for file_name in os.listdir('.'):
  if fnmatch.fnmatch(file_name, "*.png"):
    print(file_name)
    file = open(file_name, 'rb')
    print((base64.b64encode(file.read())).decode('ascii'))
    print()
