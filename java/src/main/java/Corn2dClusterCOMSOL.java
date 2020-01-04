import com.comsol.model.Model;
import com.comsol.model.util.ModelUtil;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.lang.Math.random;

import java.util.*;

public class Corn2dClusterCOMSOL {
    public static Model run() {
        Model model = ModelUtil.create("Model");
        String SQUARE_NAME_STRING = "sq:%d-%d";

        model.modelPath("/Users/me/programs/scala/workplace/percolation/java/src/main/java");

        model.component().create("comp1", true);

        model.component("comp1").geom().create("geom1", 2);
        model.component("comp1").mesh().create("mesh1");
        model.component("comp1").geom("geom1").lengthUnit("km");
        model.component("comp1").geom("geom1").scaleUnitValue(true);
        model.component("comp1").geom("geom1").scaleUnitValue(false);

        int size = 10;

        boolean[][] grid = generate_2d_corn_cluster(size, 0.8);

        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (grid[i][j]) {
                    model
                            .component("comp1")
                            .geom("geom1")
                            .create(String.format(SQUARE_NAME_STRING, i, j), "Square");

                    model
                            .component("comp1")
                            .geom("geom1")
                            .feature(String.format(SQUARE_NAME_STRING, i, j))
                            .set("size", 1);

                    model
                            .component("comp1")
                            .geom("geom1")
                            .feature(String.format(SQUARE_NAME_STRING, i, j))
                            .set("pos", new double[]{i, j});

                    nameList.add(String.format(SQUARE_NAME_STRING, i, j));
                }
            }
        }

        String[] strAr = nameList.toArray(new String[nameList.size()]);

        model.component("comp1").geom("geom1").create("uni1", "Union");
        model.component("comp1").geom("geom1").feature("uni1").set("intbnd", false);
        model
                .component("comp1")
                .geom("geom1")
                .feature("uni1")
                .selection("input")
                .set(strAr);

        return model;
    }

    public static void main(String[] args) {
        run();
    }


    public static boolean[][] generate_2d_corn_cluster(int size, double probability) {
        int mid = size / 2;

        Set<int[]> current_step_set = new HashSet<>();

        current_step_set.add(new int[]{mid + 1, mid});
        current_step_set.add(new int[]{mid - 1, mid});
        current_step_set.add(new int[]{mid, mid + 1});
        current_step_set.add(new int[]{mid, mid - 1});

        float[][] arr = new float[size][size];
        arr[mid][mid] = 1;

        Set<int[]> next_step_set = current_step_set;

        while (next_step_set.size() > 0) {
            Set<int[]> next_set = new HashSet<>();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(arr[i][j] + " \t");
                }
                System.out.println("");
            }
            System.out.println();

            for (int[] each : next_step_set) {
                if (random() < probability) {
                    arr[each[1]][each[0]] = 1;
                    if (each[1] != (size - 1) && arr[each[1] + 1][each[0]] == 0) {
                        next_set.add(new int[]{each[0], each[1] + 1});
                    }
                    if (each[1] != 0 && arr[ each[1] - 1][each[0]] == 0) {
                        next_set.add(new int[]{each[0], each[1] - 1});
                    }
                    if (each[0] != (size - 1) && arr[ each[1]][each[0] + 1] == 0) {
                        next_set.add(new int[]{each[0] + 1, each[1]});
                    }
                    if (each[0] != 0 && arr[ each[1]][each[0] - 1] == 0) {
                        next_set.add(new int[]{each[0] - 1, each[1]});
                    }
                } else {
                    arr[each[1]][each[0]] = -1;
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

    static class Point2d {
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
}


