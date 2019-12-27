import matplotlib.pyplot as plt
import numpy as np

# This import registers the 3D projection, but is otherwise unused.
from mpl_toolkits.mplot3d import Axes3D  # noqa: F401 unused import


def generate_manual_3d_cluster():
    x, y, z = np.indices((8, 8, 8))

    cube1 = (x < 3) & (y < 3) & (z < 3)
    cube2 = (x >= 5) & (y >= 5) & (z >= 5)

    result_cube = cube1 | cube2

    return result_cube


def impose_cube_on_figure(figure_name, cube):
    fig = plt.figure(figure_name)
    colors = np.empty(cube.shape, dtype=object)
    colors[cube] = 'blue'
    ax = fig.gca(projection='3d')
    ax.voxels(cube, facecolors=colors, edgecolor='k')