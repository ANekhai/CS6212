import random
import sys


def generate_graph(n, file):
    outfile = open(file, 'w')

    for i in range(1, n):
        outfile.write(str(i) + "\t" + str(i + 1) + "\n")

    for i in range(1, n*10):
        i = random.randint(1, n)
        j = random.randint(1, n)
        while (i == j):
            i = random.randint(1, n)
            j = random.randint(1, n)

        outfile.write(str(i) + "\t" + str(j) + "\n")

    outfile.close()




if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Must include an int n and a file name to write to")
        exit(1)
    n = int(sys.argv[1])
    filename = sys.argv[2]
    generate_graph(n, filename)
