import random

import numpy as np
from matplotlib import pyplot as plt

from modeling2d.common import find_max_derivative

size = 100
repeat_count = 10000
probability_variants_count = 101
mid = int(size / 2)
prob_arr = np.linspace(0, 1, probability_variants_count)

start_Set = set()

start_Set.add((mid + 1, mid))
start_Set.add((mid - 1, mid))
start_Set.add((mid, mid + 1))
start_Set.add((mid, mid - 1))

res = []

for probability in prob_arr:
    summ = 0
    print(probability)
    for i in range(repeat_count):
        arr = np.zeros((size, size), float)
        arr[mid][mid] = 1
        mySet = start_Set

        while len(mySet) != 0:
            newSet = set()
            for each in mySet:
                if random.random() < probability:
                    arr[each[0]][each[1]] = 1
                    if each[0] != (size - 1) and arr[each[0] + 1][each[1]] == 0:
                        newSet.add((each[0] + 1, each[1]))
                    if each[0] != 0 and arr[each[0] - 1][each[1]] == 0:
                        newSet.add((each[0] - 1, each[1]))
                    if each[1] != (size - 1) and arr[each[0]][each[1] + 1] == 0:
                        newSet.add((each[0], each[1] + 1))
                    if each[1] != 0 and arr[each[0]][each[1] - 1] == 0:
                        newSet.add((each[0], each[1] - 1))
                else:
                    arr[each[0]][each[1]] = -1
            mySet = newSet

        uniq, counts = np.unique(arr, return_counts=True)
        uniq_dict = dict(zip(uniq, counts))
        summ += uniq_dict[1]

    res.append(summ / repeat_count)

max_der = find_max_derivative(res, probability_variants_count)
print(f"max_der: {max_der}")
plt.axvline(x=max_der, color='r', linestyle='-')
plt.plot(prob_arr, res)

plt.show()