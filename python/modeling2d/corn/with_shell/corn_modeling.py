import time

import numpy as np
from matplotlib import pyplot as plt

from python.modeling2d.corn.without_shell.calculation import find_max_derivative, generate_2d_corn_cluster

size = 10
repeat_count = 1000
probability_variants_count = 101
prob_arr = np.linspace(0, 1, probability_variants_count)

cluster_size_arr = []
calc_time_arr = []
start = time.time()

for probability in prob_arr:
    print(f"probability: \t{probability}, \ntime spent: \t{time.time() - start}\n")
    sizeSumm = 0
    timeSumm = 0

    for i in range(repeat_count):
        stepStart = time.time()
        arr = generate_2d_corn_cluster(size, probability)
        uniq, counts = np.unique(arr, return_counts=True)
        uniq_dict = dict(zip(uniq, counts))
        sizeSumm += uniq_dict[1]
        stepEnd = time.time()
        timeSumm += stepEnd - stepStart

    cluster_size_arr.append(sizeSumm / repeat_count)
    calc_time_arr.append(timeSumm / repeat_count)

PATH_PATTERN = "/Users/me/PycharmProjects/percolation/modeling2d/corn/{}/size={}|repeat_count={}.txt"

with open(PATH_PATTERN.format("time", size, repeat_count), "w") as timeFile:
    timeFile.write(str(calc_time_arr))

with open(PATH_PATTERN.format("graphic", size, repeat_count), "w") as graphicFile:
    graphicFile.write(str(cluster_size_arr))

max_der = find_max_derivative(cluster_size_arr, probability_variants_count)
print(f"max_der: {max_der}")
plt.axvline(x=max_der, color='r', linestyle='-')
plt.subplot(2, 1, 1)
plt.plot(prob_arr, cluster_size_arr)
plt.subplot(2, 1, 2)
plt.plot(prob_arr, calc_time_arr)

plt.show()
