import random

import numpy as np

from modeling2d.visualize import EMPTY

NOT_CHECKED = -1


def split_on_clusters(grid, verbose=False, normalize=False):
    grid_len = len(grid)
    last_cluster_number = 1
    cluster_dict = {}

    for i in range(grid_len ** 2):
        cluster_dict[i] = []

    for i in range(0, grid_len):
        for j in range(0, grid_len):
            if verbose:
                print("===================================================================")
                print("i: " + str(i) + "\tj:" + str(j))
                print(grid)
                print("-------------------------------------------------------------------")
            if grid[i][j] == NOT_CHECKED:
                is_top_busy = False
                is_left_busy = False

                if j > 0:
                    is_left_busy = grid[i][j - 1] > 0

                if i > 0:
                    is_top_busy = grid[i - 1][j] > 0

                if is_left_busy & is_top_busy:
                    top_val = grid[i - 1][j]
                    left_val = grid[i][j - 1]
                    if top_val != left_val:
                        top_cluster = cluster_dict[top_val]
                        left_cluster = cluster_dict[left_val]
                        if len(left_cluster) > len(top_cluster):
                            for each in top_cluster:
                                grid[each[0]][each[1]] = left_val
                                left_cluster.append(each)
                                if verbose:
                                    print(str(each) + " -> " + str(left_val))
                            cluster_dict[top_val] = []
                            left_cluster.append((i, j))
                            cluster_dict[left_val] = left_cluster
                            grid[i][j] = left_val
                        else:
                            for each in left_cluster:
                                grid[each[0]][each[1]] = top_val
                                top_cluster.append(each)
                                if verbose:
                                    print(str(each) + " -> " + str(top_val))
                            cluster_dict[left_val] = []
                            top_cluster.append((i, j))
                            cluster_dict[top_val] = top_cluster
                            grid[i][j] = top_val
                    else:
                        cluster = cluster_dict[grid[i][j - 1]]
                        cluster.append((i, j))
                        cluster_dict[grid[i][j - 1]] = cluster
                        grid[i][j] = grid[i][j - 1]
                elif is_left_busy:
                    grid[i][j] = grid[i][j - 1]
                    val = grid[i][j]
                    cluster = cluster_dict[grid[i][j]]
                    cluster.append((i, j))
                    cluster_dict[val] = cluster
                elif is_top_busy:
                    grid[i][j] = grid[i - 1][j]
                    val = grid[i][j]
                    cluster = cluster_dict[grid[i][j]]
                    cluster.append((i, j))
                    cluster_dict[val] = cluster
                else:
                    grid[i][j] = last_cluster_number
                    cluster = cluster_dict[grid[i][j]]
                    cluster.append((i, j))
                    cluster_dict[grid[i][j]] = cluster
                    last_cluster_number += 1

            if verbose:
                print("-------------------------------------------------------------------")
                print(grid)
                print("===================================================================")
                print("\n")

    values = np.unique(grid)
    for i in range(len(values)):
        grid = np.where(grid == values[i], i, grid)

    if normalize:
        grid = np.divide(grid, grid.max())

    return grid


def get_random_fill_grid(probability: float, grid_size: int, variant1: int, variant2: int):
    grid = np.zeros((grid_size, grid_size), int)

    for i in range(grid_size):
        for j in range(grid_size):
            grid[i][j] = variant1 if random.random() < probability else variant2

    return grid


def drop_cluster_lower_than(grid, lowest: int, normalize=True):
    values, counts = np.unique(grid, return_counts=True)

    for i in range(len(values)):
        if counts[i] < lowest:
            grid = np.where(grid == values[i], 0, grid)

    values = np.unique(grid)
    for i in range(len(values)):
        grid = np.where(grid == values[i], i, grid)

    if normalize:
        max_val = grid.max()
        if max_val != 0 :
            grid = np.divide(grid, max_val)

    return grid


def drop_not_conductive_clusters(grid):
    grid_len = len(grid)
    left_border = grid[:, 0]
    right_border = grid[:, grid_len - 1]

    intersection = np.intersect1d(left_border, right_border)
    intersection = np.delete(intersection, 0)
    print(intersection)

    for i in range(grid_len):
        for j in range(grid_len):
            grid[i][j] = grid[i][j] if intersection.__contains__(grid[i][j]) else 0

    grid = np.where(grid == -1, 1, grid)

    return grid

