from matplotlib import pyplot as plt
from python.modeling3d.corn.visualize import impose_cube_on_figure

from python.modeling3d import generate_3d_cluster


grid_size = 10
probability = 0.3

cubeGrid = generate_3d_cluster(grid_size, probability)

impose_cube_on_figure('Corn cluster', cubeGrid)

plt.show()
