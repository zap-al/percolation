import random

import numpy as np


def generate_3d_cluster(size: int, probability):
    mid = int(size / 2)

    current_check_step = set()

    current_check_step.add((mid + 1, mid, mid))
    current_check_step.add((mid - 1, mid, mid))
    current_check_step.add((mid, mid + 1, mid))
    current_check_step.add((mid, mid - 1, mid))
    current_check_step.add((mid, mid, mid + 1))
    current_check_step.add((mid, mid, mid - 1))

    arr = np.zeros((size, size, size), int)
    arr[mid][mid][mid] = 1
    next_check_step = current_check_step

    while len(next_check_step) != 0:
        next_set = set()

        for each in next_check_step:
            if random.random() < probability:
                arr[each[0]][each[1]][each[2]] = 1
                if each[0] != (size - 1) and arr[each[0] + 1][each[1]][each[2]] == 0:
                    next_set.add((each[0] + 1, each[1], each[2]))
                if each[0] != 0 and arr[each[0] - 1][each[1]][each[2]] == 0:
                    next_set.add((each[0] - 1, each[1], each[2]))

                if each[1] != (size - 1) and arr[each[0]][each[1] + 1][each[2]] == 0:
                    next_set.add((each[0], each[1] + 1, each[2]))
                if each[1] != 0 and arr[each[0]][each[1] - 1][each[2]] == 0:
                    next_set.add((each[0], each[1] - 1, each[2]))

                if each[2] != (size - 1) and arr[each[0]][each[1]][each[2] + 1] == 0:
                    next_set.add((each[0], each[1], each[2] + 1))
                if each[2] != 0 and arr[each[0]][each[1]][each[2] - 1] == 0:
                    next_set.add((each[0], each[1], each[2] - 1))

        next_check_step = next_set

    final_cube = np.zeros((size, size, size), bool)

    for i in range(size):
        for j in range(size):
            for k in range(size):
                final_cube[i][j][k] = True if arr[i][j][k] == 1 else False

    return final_cube
