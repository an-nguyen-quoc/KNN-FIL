import glob

for file in glob.glob(".\*\*.txt"):
    # filepath = '"' + file[2] + '\\\\' + file[4:] + '"'
    # print (filepath)
    print(file[2])
    