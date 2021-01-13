import os
import glob

for file in glob.glob(".\*.txt"):
    print(file) 
    os.remove(file)
