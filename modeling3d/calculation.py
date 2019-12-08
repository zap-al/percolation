import random
import time

import numpy as np


def generate_3d_cluster(size: int, probability):
    repeat_count = 1
    probability_variants_count = 101
    mid = int(size / 2)
    prob_arr = np.linspace(0, 1, probability_variants_count)

    start_Set = set()

    start_Set.add((mid + 1, mid, mid))
    start_Set.add((mid - 1, mid, mid))
    start_Set.add((mid, mid + 1, mid))
    start_Set.add((mid, mid - 1, mid))
    start_Set.add((mid, mid, mid + 1))
    start_Set.add((mid, mid, mid - 1))

    clusterArr = []
    timeArr = []
    start = time.time()

    #for probability in prob_arr:
    sizeSumm = 0
    timeSumm = 0
    #print(f"probability: \t{probability}, \ntime spent: \t{time.time() - start}\n")

    for i in range(repeat_count):
        stepStart = time.time()
        arr = np.zeros((size, size, size), int)
        print(arr)
        arr[mid][mid][mid] = 1
        mySet = start_Set

        while len(mySet) != 0:
            newSet = set()
            for each in mySet:
                if random.random() < probability:
                    arr[each[0]][each[1]][each[2]] = 1
                    if each[0] != (size - 1) and arr[each[0] + 1][each[1]][each[2]] == 0:
                        newSet.add((each[0] + 1, each[1], each[2]))
                    if each[0] != 0 and arr[each[0] - 1][each[1]][each[2]] == 0:
                        newSet.add((each[0] - 1, each[1], each[2]))

                    if each[1] != (size - 1) and arr[each[0]][each[1] + 1][each[2]] == 0:
                        newSet.add((each[0], each[1] + 1, each[2]))
                    if each[1] != 0 and arr[each[0]][each[1] - 1][each[2]] == 0:
                        newSet.add((each[0], each[1] - 1, each[2]))

                    if each[2] != (size - 1) and arr[each[0]][each[1]][each[2] + 1] == 0:
                        newSet.add((each[0], each[1], each[2] + 1))
                    if each[2] != 0 and arr[each[0]][each[1]][each[2] - 1] == 0:
                        newSet.add((each[0], each[1], each[2] - 1))
                else:
                    print(each)
                    print(each[0])
                    print(each[1])
                    print(each[2])
                    arr[each[0]][each[1]][each[2]] = -1
            mySet = newSet
        uniq, counts = np.unique(arr, return_counts=True)
        uniq_dict = dict(zip(uniq, counts))
        sizeSumm += uniq_dict[1]
        stepEnd = time.time()
        timeSumm += stepEnd - stepStart

        timeArr.append(timeSumm / repeat_count)
        clusterArr.append(sizeSumm / repeat_count)

        print(arr)

        cubeGrid = np.zeros((size, size, size), bool)

        for i in range(size):
            for j in range(size):
                for k in range(size):
                    cubeGrid[i][j][k] = True if arr[i][j][k] == 1 else False

        return cubeGrid
