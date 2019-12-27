import matplotlib.pyplot as plt

from python.modeling2d.corn.calculation import generate_2d_corn_cluster
from python.modeling2d.corn.visualize import draw_array_as_grid

size = 10
probability = 0.6

grid = generate_2d_corn_cluster(size, probability)
draw_array_as_grid('some', grid)

plt.show()
