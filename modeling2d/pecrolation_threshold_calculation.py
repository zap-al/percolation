from numpy import linspace

from modeling2d.calculation import split_on_clusters, get_grid, NOT_CHECKED, drop_cluster_lower_than
from modeling2d.visualize import map_dict_on_grid, EMPTY
import matplotlib.pyplot as plt


def plot_of_greatest_cluster_per_probability():
    grid_size = 40
    repeat_count = 1000

    # 3 -> 0.0, 0.5, 1.0   |   6 -> 0.0, 0.2, 0.4, 0.6, 0.8, 1.0
    probability_step_count = 101

    probability_arr = linspace(0, 1, probability_step_count)

    average_per_probability = []
    for probability in probability_arr:
        sum_max_len = 0
        for i in range(repeat_count):
            max_len = 0
            init_grid = get_grid(grid_size, probability, variant1=NOT_CHECKED, variant2=EMPTY)
            clustered_grid_dict = split_on_clusters(init_grid, verbose=False)

            for key, value in clustered_grid_dict.items():
                if len(value) > max_len:
                    max_len = len(value)

            sum_max_len += max_len

        avg = sum_max_len / repeat_count
        average_per_probability.append(avg)
    mid = sum(average_per_probability) / len(average_per_probability)
    # plt.axhline(y=mid, color='r', linestyle='-')
    plt.plot(probability_arr, average_per_probability)


plot_of_greatest_cluster_per_probability()
plt.show()
