import matplotlib.pyplot as plt
import numpy as np

from modeling2d.calculation import split_on_clusters, get_random_fill_grid, NOT_CHECKED, drop_cluster_lower_than, \
    drop_not_conductive_clusters
from modeling2d.visualize import draw_array_as_grid, BUSY, EMPTY

grid_size = 10
probability = 0.5

grid = get_random_fill_grid(probability, grid_size, variant1=NOT_CHECKED, variant2=EMPTY)
grid_split_on_clusters = split_on_clusters(grid, verbose=False, normalize=True)

print("clusters count: " + str(len(np.unique(grid_split_on_clusters))))
potentially_necessary = drop_cluster_lower_than(grid, lowest=grid_size, normalize=True)
conductive_clusters = drop_not_conductive_clusters(grid)

# draw_array_as_grid(figure="Random grid", grid=get_random_fill_grid(0.5, grid_size, variant1=BUSY, variant2=EMPTY))
draw_array_as_grid(figure="Split on clusters", grid=grid_split_on_clusters)
draw_array_as_grid(figure="Only potentially necessary", grid=potentially_necessary)
draw_array_as_grid(figure="Conductive clusters", grid=conductive_clusters)

plt.show()
