package modelingPlane;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.lang.Math.random;

public class Calculator2d {
    public boolean[][] generate_2d_corn_cluster(int size, double probability) {
        int mid = size / 2;

        Set<Point2d> current_step_set = new HashSet<>();

        current_step_set.add(new Point2d(mid + 1, mid));
        current_step_set.add(new Point2d(mid - 1, mid));
        current_step_set.add(new Point2d(mid, mid + 1));
        current_step_set.add(new Point2d(mid, mid - 1));

        float[][] arr = new float[size][size];
        arr[mid][mid] = 1;

        Set<Point2d> next_step_set = current_step_set;

        while (next_step_set.size() > 0) {
            Set<Point2d> next_set = new HashSet<>();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(arr[i][j] + " \t");
                }
                System.out.println("");
            }
            System.out.println();

            for (Point2d each : next_step_set) {
                if (random() < probability) {
                    arr[each.y][each.x] = 1;
                    if (each.y != (size - 1) && arr[each.y + 1][each.x] == 0) {
                        next_set.add(new Point2d(each.x, each.y + 1));
                    }
                    if (each.y != 0 && arr[ each.y - 1][each.x] == 0) {
                        next_set.add(new Point2d(each.x, each.y - 1));
                    }
                    if (each.x != (size - 1) && arr[ each.y][each.x + 1] == 0) {
                        next_set.add(new Point2d(each.x + 1, each.y));
                    }
                    if (each.x != 0 && arr[ each.y][each.x - 1] == 0) {
                        next_set.add(new Point2d(each.x - 1, each.y));
                    }
                } else {
                    arr[each.y][each.x] = -1;
                }
            }

            next_step_set = next_set;
        }

        boolean [][]final_cube = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final_cube[i][j] = arr[i][j] == 1;
            }
        }

        return final_cube;
    }
}

class Point2d {
    Point2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;

    @Override
    public String toString() {
        return "modeling2d.Point2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2d point = (Point2d) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
