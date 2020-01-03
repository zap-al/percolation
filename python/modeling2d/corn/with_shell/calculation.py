import random

import numpy as np
import math as m


def find_max_derivative(array, step_count):
    max_derivative = 0
    max_derivative_num = 0
    der = 1 / (step_count - 1)

    for i in range(0, len(array) - 3):
        if array[i + 2] - array[i] > max_derivative:
            max_derivative_num = i + 1
            max_derivative = array[i + 2] - array[i]

    return der * max_derivative_num


def generate_2d_corn_cluster(size, probability):
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


def expand_existing_cluster_with_shell(_grid, nucleus_size, shell_size, shell_probability):
    scale = nucleus_size + shell_size + shell_size
    current_size = _grid[0].size
    new_size = _grid[0].size * scale
    center_bias = m.floor(scale / 2)
    nucleus_r = m.floor(nucleus_size / 2)

    new_grid = np.zeros((new_size, new_size), int)

    for i in range(current_size):
        for j in range(current_size):
            if _grid[i][j] == 1:
                left_shell_border = j * scale + center_bias - nucleus_r - shell_size
                right_shell_border = j * scale + center_bias + nucleus_r + shell_size + 1
                top_shell_border = i * scale + center_bias - nucleus_r
                bot_shell_border = i * scale + center_bias + nucleus_r + 1

                for k in range(i * scale + center_bias - nucleus_r, i * scale + center_bias + nucleus_r + 1):
                    for n in range(j * scale + center_bias - nucleus_r, j * scale + center_bias + nucleus_r + 1):
                        new_grid[k][n] = 1

                for n in range(1, shell_size + 1):
                    for k in range(left_shell_border, right_shell_border):
                        if random.random() > shell_probability:
                            top_border_row = i * scale + center_bias - nucleus_r - n
                            new_grid[top_border_row][k] = 1

                        if random.random() > shell_probability:
                            bot_border_row = i * scale + center_bias + nucleus_r + n
                            new_grid[bot_border_row][k] = 1

                for n in range(1, shell_size + 1):
                    for k in range(top_shell_border, bot_shell_border):
                        if random.random() > shell_probability:
                            left_border_col = j * scale + center_bias - nucleus_r - n
                            new_grid[k][left_border_col] = 1
                        if random.random() > shell_probability:
                            right_border_col = j * scale + center_bias + nucleus_r + n
                            new_grid[k][right_border_col] = 1

    return new_grid


def prepare_cluster_for_splitting(grid):
    for i in range(grid[0].size):
        for j in range(grid[0].size):
            if grid[i][j] != 0:
                grid[i][j] = -1

    return grid