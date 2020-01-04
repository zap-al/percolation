import random

import numpy as np


def find_max_derivative(array, step_count):
    max_derivative = 0
    max_derivative_num = 0
    der = 1 / (step_count - 1)

    for i in range(0, len(array) - 3):
        if array[i + 2] - array[i] > max_derivative:
            max_derivative_num = i + 1
            max_derivative = array[i + 2] - array[i]

    return der * max_derivative_num


def generate_2d_corn_cluster_without_shell(size, probability):
    mid = int(size / 2)

    current_step_set = set()

    current_step_set.add((mid + 1, mid))
    current_step_set.add((mid - 1, mid))
    current_step_set.add((mid, mid + 1))

    arr = np.zeros((size, size), float)
    arr[mid][mid] = 1
    next_step_set = current_step_set

    while len(next_step_set) != 0:
        next_set = set()
        for each in next_step_set:
            if random.random() < probability:
                arr[each[0]][each[1]] = 1
                if each[0] != (size - 1) and arr[each[0] + 1][each[1]] == 0:
                    next_set.add((each[0] + 1, each[1]))
                if each[0] != 0 and arr[each[0] - 1][each[1]] == 0:
                    next_set.add((each[0] - 1, each[1]))
                if each[1] != (size - 1) and arr[each[0]][each[1] + 1] == 0:
                    next_set.add((each[0], each[1] + 1))
                if each[1] != 0 and arr[each[0]][each[1] - 1] == 0:
                    next_set.add((each[0], each[1] - 1))

        next_step_set = next_set

    final_cube = np.zeros((size, size), int)

    for i in range(size):
        for j in range(size):
            final_cube[i][j] = True if arr[i][j] == 1 else False

    return final_cube


