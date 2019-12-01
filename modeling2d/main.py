import matplotlib.pyplot as plt
import numpy as np

from modeling2d.calculation import split_on_clusters, get_random_fill_grid
from modeling2d.visualize import draw_array_as_grid

grid_size = 10
probability = 0.7
# grid = np.random.randint(low=-1, high=1, size=(grid_size, grid_size))
grid = get_random_fill_grid(probability, grid_size)
arr = split_on_clusters(grid, verbose=False)

print(arr)

values, counts = np.unique(arr, return_counts=True)

print()
print("clusters count: " + str(len(values)))
# print(values)
# print(counts)

for i in range(len(values)):
    arr = np.where(arr == values[i], i, arr)

max_val = arr.max()
arr = np.divide(arr, max_val)
draw_array_as_grid(figure=2, grid=arr)

# test_dict()
plt.show()
