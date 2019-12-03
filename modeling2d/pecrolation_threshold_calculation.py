import time

from numpy import linspace

from modeling2d.calculation import split_on_clusters, get_grid, NOT_CHECKED, drop_cluster_lower_than
from modeling2d.visualize import map_dict_on_grid, EMPTY
import matplotlib.pyplot as plt


def plot_of_greatest_cluster_per_probability():
    grid_size = 100
    repeat_count = 10000

    # 3 -> 0.0, 0.5, 1.0   |   6 -> 0.0, 0.2, 0.4, 0.6, 0.8, 1.0
    probability_step_count = 101

    probability_arr = linspace(0, 1, probability_step_count)

    average_per_probability = []
    start = time.time()
    for probability in probability_arr:
        sum_max_len = 0
        for i in range(repeat_count):
            max_len = 0
            init_grid = get_grid(grid_size, probability, variant1=NOT_CHECKED, variant2=EMPTY)
            clustered_grid_dict = split_on_clusters(init_grid, verbose=False)

            for key, value in clustered_grid_dict.items():
                if len(value) > max_len:
                    max_len = len(value)

            if i % 1000 == 0:
                now = time.time()
                print(f"probability: {probability}\n repeat_count: {i}\n  time: {now - start} \n")

            sum_max_len += max_len
        print()
        avg = sum_max_len / repeat_count
        average_per_probability.append(avg)

    finish = time.time()
    print(f"\ntotal time: {finish - start}")

    max_derivative = 0
    max_derivative_num = 0
    xStep = 1 / (probability_step_count - 1)

    for i in range(0, len(average_per_probability) - 3):
        if average_per_probability[i + 2] - average_per_probability[i] > max_derivative:
            max_derivative_num = i + 1
            max_derivative = average_per_probability[i + 2] - average_per_probability[i]

    print(f"max_der: {(xStep * max_derivative_num)}")
    plt.axvline(x=(xStep * max_derivative_num), color='r', linestyle='-')
    plt.plot(probability_arr, average_per_probability)


start = time.time()
plot_of_greatest_cluster_per_probability()
end = time.time()
print("time spent: " + str(end - start))
plt.show()
