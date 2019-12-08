import random

import numpy as np
from matplotlib import pyplot as plt
from mpl_toolkits.mplot3d import Axes3D  # noqa: F401 unused import

from modeling2d.visualize import EMPTY
from modeling3d.calculation import generate_3d_cluster


grid_size = 5
cubeGrid = generate_3d_cluster(grid_size, 0.2)

print(cubeGrid)

colors = np.empty(cubeGrid.shape, dtype=object)
colors[cubeGrid] = 'blue'
fig = plt.figure()
ax = fig.gca(projection='3d')
ax.voxels(cubeGrid, facecolors=colors, edgecolor='k')

plt.show()
