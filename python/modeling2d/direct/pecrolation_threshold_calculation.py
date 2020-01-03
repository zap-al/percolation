import time

from numpy import linspace

from python.modeling2d.direct.calculation import split_on_clusters, get_grid, NOT_CHECKED
from python.modeling2d.corn.without_shell.calculation import find_max_derivative
from python.modeling2d.direct.visualize import EMPTY
import matplotlib.pyplot as plt


def plot_of_greatest_cluster_per_probability():
    grid_size = 1000
    repeat_count = 300

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

            print(i)
            if i % repeat_count == 0:
                now = time.time()
                print(f"probability: {probability}\n repeat_count: {i}\n  time: {now - start} \n")

            sum_max_len += max_len
        print()
        avg = sum_max_len / repeat_count
        average_per_probability.append(avg)

    finish = time.time()
    print(f"\ntotal time: {finish - start}")

    max_der = find_max_derivative(average_per_probability, probability_step_count)
    print(f"max_der: {max_der}")
    plt.axvline(x=max_der, color='r', linestyle='-')
    plt.plot(probability_arr, average_per_probability)


start = time.time()
plot_of_greatest_cluster_per_probability()
end = time.time()
print("time spent: " + str(end - start))
plt.show()
