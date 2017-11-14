nomefich = input("prompt")
print('graph Locais {')
print('\tnode [shape = circle];')
file = open(nomefich, 'r').read().split('\n')
for line in file:
        line_split = line.split(',')
        if (len(line_split) == 3):
            print("{} -- {}[label=\"{}\"];".format(line_split[0], line_split[1], line_split[2])) 
print('}')
