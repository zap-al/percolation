import time

import matplotlib.pyplot as plt
import numpy as np

from modeling2d.calculation import split_on_clusters, get_grid, NOT_CHECKED, drop_cluster_lower_than, \
    calculate_clusters_diameters, drop_not_conductive_clusters_dict
from modeling2d.visualize import draw_array_as_grid, map_dict_on_grid, EMPTY

grid_size = 50
probability = 0.01
min_diameter = grid_size * 0.8

init_grid = get_grid(grid_size, probability, variant1=NOT_CHECKED, variant2=EMPTY)

start = time.time()
clustered_grid_dict = split_on_clusters(init_grid, verbose=False)
empty_grid = get_grid(grid_size)
clustered_grid = map_dict_on_grid(empty_grid, clustered_grid_dict)
draw_array_as_grid(figure="Split on clusters",
                   grid=clustered_grid)

print("clusters count: " + str(len(np.unique(clustered_grid)) - 1))
big_clusters, big_clusters_dict = drop_cluster_lower_than(clustered_grid, clustered_grid_dict, lowest=grid_size)

if len(big_clusters_dict) > 0:
    draw_array_as_grid(figure="Only potentially necessary",
                       grid=big_clusters)

    conductive_clusters_dict = drop_not_conductive_clusters_dict(big_clusters_dict, grid_size)
    if len(conductive_clusters_dict) > 0:
        conductive_clusters = get_grid(grid_size)
        conductive_clusters = map_dict_on_grid(conductive_clusters, conductive_clusters_dict)
        draw_array_as_grid(figure="Conductive clusters",
                           grid=conductive_clusters)

    clusters_greater_grid_diameter_dict = calculate_clusters_diameters(big_clusters_dict, min_diameter)
    if len(clusters_greater_grid_diameter_dict) > 0:
        empty_grid = get_grid(grid_size)
        clusters_greater_grid_diameter = map_dict_on_grid(empty_grid, clusters_greater_grid_diameter_dict)
        draw_array_as_grid(figure=f"Clusters greater diameter: {min_diameter}",
                           grid=clusters_greater_grid_diameter)
else:
    print("no good clusters")
end = time.time()
print(end - start)

plt.show()
