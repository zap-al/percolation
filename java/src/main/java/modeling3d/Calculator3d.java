package modeling3d;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.lang.Math.random;

public class Calculator3d {
    public boolean[][][] generate_3d_corn_cluster(int size, double probability) {
        int mid = size / 2;

        Set<Point3d> current_step_set = new HashSet<>();

        current_step_set.add(new Point3d(mid + 1, mid, mid));
        current_step_set.add(new Point3d(mid - 1, mid, mid));
        current_step_set.add(new Point3d(mid, mid + 1, mid));
        current_step_set.add(new Point3d(mid, mid - 1, mid));
        current_step_set.add(new Point3d(mid, mid, mid + 1));
        current_step_set.add(new Point3d(mid, mid, mid - 1));

        float[][][] arr = new float[size][size][size];
        arr[mid][mid][mid] = 1;

        Set<Point3d> next_step_set = current_step_set;

        while (next_step_set.size() > 0) {
            Set<Point3d> next_set = new HashSet<>();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        System.out.print(arr[i][j][k] + " \t");
                    }
                    System.out.println();
                }
                System.out.println();

                for (Point3d each : next_step_set) {
                    if (random() < probability) {
                        arr[each.z][each.y][each.x] = 1;
                        if (each.z != (size - 1) && arr[each.z + 1][each.y][each.x] == 0) {
                            next_set.add(new Point3d(each.x, each.y, each.z + 1));
                        }
                        if (each.z != 0 && arr[each.z - 1][each.y][each.z] == 0) {
                            next_set.add(new Point3d(each.x, each.y, each.z - 1));
                        }
                        if (each.y != (size - 1) && arr[each.z][each.y + 1][each.x] == 0) {
                            next_set.add(new Point3d(each.x, each.y + 1, each.z));
                        }
                        if (each.y != 0 && arr[each.z][each.y - 1][each.x] == 0) {
                            next_set.add(new Point3d(each.x, each.y - 1, each.z));
                        }
                        if (each.x != (size - 1) && arr[each.z][each.y][each.x + 1] == 0) {
                            next_set.add(new Point3d(each.x + 1, each.y, each.z));
                        }
                        if (each.x != 0 && arr[each.z][each.y][each.x - 1] == 0) {
                            next_set.add(new Point3d(each.x - 1, each.y, each.z));
                        }
                    } else {
                        arr[each.z][each.y][each.x] = -1;
                    }
                }

                next_step_set = next_set;
            }
        }

        boolean[][][] final_cube = new boolean[size][size][];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    final_cube[i][j][k] = arr[i][j][k] == 1;
                }
            }
        }

        return final_cube;
    }
}

class Point3d {
    public int x;
    public int y;
    public int z;

    Point3d(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "modeling3d.Point3d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3d point3d = (Point3d) o;
        return x == point3d.x &&
                y == point3d.y &&
                z == point3d.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

