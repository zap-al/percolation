import matplotlib.pyplot as plt

COLOR_WHITE = 0
COLOR_BLACK = 1

EMPTY = COLOR_WHITE
BUSY = COLOR_BLACK


def draw_array_as_grid(figure, grid):
    plt.figure(figure)
    plt.imshow(grid, cmap='binary', vmin=COLOR_WHITE, vmax=COLOR_BLACK)