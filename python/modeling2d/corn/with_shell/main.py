import matplotlib.pyplot as plt
import python.modeling2d.direct.calculation as c

from python.modeling2d.corn.with_shell.calculation import generate_2d_corn_cluster, \
                                                          expand_existing_cluster_with_shell, \
                                                          prepare_cluster_for_splitting

from python.modeling2d.corn.visualize import draw_array_as_grid


grid_size = 10

nucleus_probability = 0.6
shell_probability = 0.7

nucleus_size = 15
shell_size = 2

cluster_size_to_drop = 5

grid = generate_2d_corn_cluster(grid_size, nucleus_probability)
res_grid = expand_existing_cluster_with_shell(grid, nucleus_size, shell_size, shell_probability)
res_grid = prepare_cluster_for_splitting(res_grid)
res_grid_dict = c.split_on_clusters(res_grid, False)
only_large_clusters, only_large_clusters_dict = c.drop_cluster_lower_than(res_grid,
                                                                          res_grid_dict,
                                                                          nucleus_size ** 2 * cluster_size_to_drop)
draw_array_as_grid('some', only_large_clusters)

plt.show()
