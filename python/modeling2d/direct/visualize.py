import matplotlib.pyplot as plt
import numpy as np

COLOR_WHITE = 0
COLOR_BLACK = 1

EMPTY = COLOR_WHITE
BUSY = COLOR_BLACK


def draw_array_as_grid(figure, grid):
    plt.figure(figure)

    values = np.unique(grid)
    for i in range(len(values)):
        grid = np.where(grid == values[i], i, grid)

    max_val = grid.max(initial=None)

    if max_val != 0:
        grid = np.divide(grid, max_val)

    plt.imshow(grid, cmap='binary', vmin=COLOR_WHITE, vmax=COLOR_BLACK)


def map_dict_on_grid(grid, dictionary):
    for key in list(dictionary.keys()):
        for val in dictionary[key]:
            grid[val[0]][val[1]] = key

    return grid
