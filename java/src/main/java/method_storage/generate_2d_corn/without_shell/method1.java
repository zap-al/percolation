package builder;

import com.comsol.api.ApplicationMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.random;

public class method1 extends ApplicationMethod {

    public String execute(double p, int N) {
        String textlabel1 = null;
        {
            model.component().remove("comp1");

            model.component().create("comp1", true);
            model.component("comp1").geom().create("geom1", 2);
            model.component("comp1").geom("geom1").lengthUnit("km");
            model.component("comp1").geom("geom1").scaleUnitValue(true);
            model.component("comp1").geom("geom1").scaleUnitValue(false);

            String SQUARE_NAME_STRING = "sq:%d-%d";

            boolean[][] grid = generate_2d_corn_cluster(N, p);

            List<String> nameList = new ArrayList<String>();

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j]) {
                        model.component("comp1").geom("geom1").create("sq:" + i + "-" + j, "Square");
                        model.component("comp1").geom("geom1").feature(String.format(SQUARE_NAME_STRING, i, j)).set("size", 1);
                        model.component("comp1").geom("geom1").feature(String.format(SQUARE_NAME_STRING, i, j)).set("pos", new double[]{i, j});
                        nameList.add(String.format(SQUARE_NAME_STRING, i, j));
                    }
                }
            }

            String[] strAr = nameList.toArray(new String[nameList.size()]);

            model.component("comp1").geom("geom1").create("uni1", "Union");
            model.component("comp1").geom("geom1").feature("uni1").set("intbnd", false);
            model.component("comp1").geom("geom1").feature("uni1").selection("input").set(strAr);
            model.component("comp1").geom("geom1").run();
        }
        return textlabel1;
    }

    public boolean[][] generate_2d_corn_cluster(int size, double probability) {
        int N = size;
        double p = probability;
        int mid = N / 2;

        Set<int[]> current_step_set = get_init_set(mid);

        float[][] arr = new float[N][N];
        arr[mid][mid] = 1;

        Set<int[]> next_step_set = current_step_set;

        while (next_step_set.size() > 0) {
            Set<int[]> next_set = new HashSet<int[]>();

            for (int[] each : next_step_set) {
                if (random() < p) {
                    arr[each[1]][each[0]] = 1;
                    if (each[1] != (N - 1)) {
                        if (arr[each[1] + 1][each[0]] == 0) {
                            int[] new_point = new int[]{each[0], each[1] + 1};

                            boolean set_contains_point = false;
                            for (int[] point : next_set) {
                                if (point[0] == new_point[0]) {
                                    if (point[1] == new_point[1]) {
                                        set_contains_point = true;
                                        break;
                                    }
                                }
                            }
                            if (!set_contains_point) {
                                next_set.add(new_point);
                            }
                        }
                    }
                    if (each[1] != 0) {
                        if (arr[each[1] - 1][each[0]] == 0) {
                            int[] new_point = new int[]{each[0], each[1] - 1};

                            boolean set_contains_point = false;
                            for (int[] point : next_set) {
                                if (point[0] == new_point[0]) {
                                    if (point[1] == new_point[1]) {
                                        set_contains_point = true;
                                        break;
                                    }
                                }
                            }

                            if (!set_contains_point) {
                                next_set.add(new_point);
                            }
                        }
                    }
                    if (each[0] != (N - 1)) {
                        if (arr[each[1]][each[0] + 1] == 0) {
                            int[] new_point = new int[]{each[0] + 1, each[1]};

                            boolean set_contains_point = false;
                            for (int[] point : next_set) {
                                if (point[0] == new_point[0]) {
                                    if (point[1] == new_point[1]) {
                                        set_contains_point = true;
                                        break;
                                    }
                                }
                            }
                            if (!set_contains_point) {
                                next_set.add(new_point);
                            }
                        }
                    }
                    if (each[0] != 0) {
                        if (arr[each[1]][each[0] - 1] == 0) {
                            int[] new_point = new int[]{each[0] - 1, each[1]};

                            boolean set_contains_point = false;
                            for (int[] point : next_set) {
                                if (point[0] == new_point[0]) {
                                    if (point[1] == new_point[1]) {
                                        set_contains_point = true;
                                        break;
                                    }
                                }
                            }
                            if (!set_contains_point) {
                                next_set.add(new_point);
                            }
                        }
                    }
                } else {
                    arr[each[1]][each[0]] = -1;
                }
            }

            for (int[] each : next_step_set) {
                System.out.println("x: " + each[0]);
                System.out.println("y: " + each[1]);
                System.out.println();
            }

            next_step_set = next_set;
        }

        boolean[][] grid = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = arr[i][j] == 1;
            }
        }

        return grid;
    }

    Set<int[]> get_init_set(int mid) {
        Set<int[]> init_set = new HashSet<int[]>();

        init_set.add(new int[]{mid + 1, mid});
        init_set.add(new int[]{mid - 1, mid});
        init_set.add(new int[]{mid, mid + 1});
        init_set.add(new int[]{mid, mid - 1});

        return init_set;
    }

}
