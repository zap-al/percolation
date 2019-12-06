def find_max_derivative(array, step_count):
    max_derivative = 0
    max_derivative_num = 0
    xStep = 1 / (step_count - 1)

    for i in range(0, len(array) - 3):
        if array[i + 2] - array[i] > max_derivative:
            max_derivative_num = i + 1
            max_derivative = array[i + 2] - array[i]

    return xStep * max_derivative_num
