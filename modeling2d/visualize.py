import numpy as np
import matplotlib.pyplot as plt
import random

# probability of busy
p = 0.1

GRID_SIZE = 10

COLOR_WHITE = 0
COLOR_BLACK = 1

BUSY = COLOR_BLACK
EMPTY = COLOR_WHITE

grid = np.zeros((GRID_SIZE, GRID_SIZE), int)
for i in range(GRID_SIZE):
    for j in range(GRID_SIZE):
        grid[i][j] = BUSY if random.random() <= p else EMPTY

plt.imshow(grid, cmap='binary', vmin=COLOR_WHITE, vmax=COLOR_BLACK)
plt.show()
