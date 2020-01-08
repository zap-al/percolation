package builder;

import com.comsol.api.ApplicationMethod;

import java.util.*;

import static java.lang.Math.random;

public class method1 extends ApplicationMethod {

    public String execute(double p, int N) {
        String textlabel1 = null;
        {
            model.component().create("comp1", true);
            model.component("comp1").geom().create("geom1", 2);
            model.component("comp1").geom("geom1").lengthUnit("km");
            model.component("comp1").geom("geom1").scaleUnitValue(true);
            model.component("comp1").geom("geom1").scaleUnitValue(false);

            int size = 3;
            double probability = 0.6;
            int nucleus_size = 3;
            int shell_size = 1;
            double shell_probability = 0.5;

            String SQUARE_NAME_STRING = "sq:%d-%d";

            boolean[][] grid = generate_2d_corn_cluster(size, probability);

            boolean [][]grid2 = expand_existing_cluster_with_shell(
                    grid,
                    nucleus_size,
                    shell_size,
                    shell_probability
            );

            int[][] grid3 = prepare_cluster_for_splitting(grid2);

            int[][] grid4 = split_on_clusters(grid3);
            int[][] biggest_cluster_grid = retrieve_cluster_with_biggest_nucleus_count(grid4, size);
            for (int[] ints : grid4) {
                for (int each : ints) {
                    System.out.print("\t" + each);
                }
                System.out.println();
            }
            List<String> nameList = new ArrayList<String>();

            for (int i = 0; i < biggest_cluster_grid.length; i++) {
                for (int j = 0; j < biggest_cluster_grid.length; j++) {
                    if (biggest_cluster_grid[i][j] > 0) {
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
    public boolean[][] expand_existing_cluster_with_shell(boolean[][] grid, int nucleus_size, int shell_size, double shell_probability) {
        int scale = nucleus_size + shell_size + shell_size;
        int current_size = grid[0].length;
        int new_size = grid[0].length * scale;
        int center_bias = scale / 2;
        int nucleus_r = nucleus_size / 2;

        boolean[][] new_grid = new boolean[new_size][new_size];

        for (int i = 0; i < current_size; i++) {
            for (int j = 0; j < current_size; j++) {
                if (grid[i][j]) {
                    int left_shell_border = j * scale + center_bias - nucleus_r - shell_size;
                    int right_shell_border = j * scale + center_bias + nucleus_r + shell_size + 1;
                    int top_shell_border = i * scale + center_bias - nucleus_r;
                    int bot_shell_border = i * scale + center_bias + nucleus_r + 1;

                    for (int k = i * scale + center_bias - nucleus_r; k < i * scale + center_bias + nucleus_r + 1; k++) {
                        for (int n = j * scale + center_bias - nucleus_r; n < j * scale + center_bias + nucleus_r + 1; n++) {
                            new_grid[k][n] = true;
                        }
                    }

                    for (int n = 1; n < shell_size + 1; n++) {
                        for (int k = left_shell_border; k < right_shell_border; k++) {
                            if (Math.random() > shell_probability) {
                                int top_border_row = i * scale + center_bias - nucleus_r - n;
                                new_grid[top_border_row][k] = true;
                            }

                            if (Math.random() > shell_probability) {
                                int bot_border_row = i * scale + center_bias + nucleus_r + n;
                                new_grid[bot_border_row][k] = true;
                            }
                        }
                    }


                    for (int n = 1; n < shell_size + 1; n++) {
                        for (int k = top_shell_border; k < bot_shell_border; k++) {
                            if (Math.random() > shell_probability) {
                                int left_border_col = j * scale + center_bias - nucleus_r - n;
                                new_grid[k][left_border_col] = true;
                            }
                            if (Math.random() > shell_probability) {
                                int right_border_col = j * scale + center_bias + nucleus_r + n;
                                new_grid[k][right_border_col] = true;
                            }
                        }
                    }
                }
            }
        }

        return new_grid;
    }

    public int[][] prepare_cluster_for_splitting(boolean[][] grid) {
        int[][] res = new int[grid[0].length][grid[0].length];

        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j]) {
                    res[i][j] = -1;
                }
            }
        }

        return res;
    }

    public int[][] split_on_clusters(int[][] grid) {
        int grid_len = grid[0].length;
        int last_cluster_number = 1;
        Map<Integer, List<Integer[]>> cluster_dict = new HashMap<Integer, List<Integer[]>>();

        for (int i = 0; i < grid_len; i++) {
            for (int j = 0; j < grid_len; j++) {

                if (grid[i][j] == -1) {
                    boolean is_top_busy = false;
                    boolean is_left_busy = false;

                    if (j > 0) {
                        is_left_busy = grid[i][j - 1] > 0;
                    }

                    if (i > 0) {
                        is_top_busy = grid[i - 1][j] > 0;
                    }

                    if (is_left_busy) {
                        if (is_top_busy) {
                            int top_val = grid[i - 1][j];
                            int left_val = grid[i][j - 1];
                            if (top_val != left_val) {
                                List<Integer[]> top_cluster = cluster_dict.get(top_val);
                                List<Integer[]> left_cluster = cluster_dict.get(left_val);
                                if (left_cluster.size() > top_cluster.size()) {
                                    for (Integer[] each : top_cluster) {
                                        grid[each[0]][each[1]] = left_val;
                                        left_cluster.add(each);
                                    }
                                    cluster_dict.remove(top_val);
                                    left_cluster.add(new Integer[]{i, j});
                                    cluster_dict.put(left_val, left_cluster);
                                    grid[i][j] = left_val;
                                } else {
                                    for (Integer[] each : left_cluster) {
                                        grid[each[0]][each[1]] = top_val;
                                        top_cluster.add(each);
                                    }
                                    cluster_dict.remove(left_val);

                                    top_cluster.add(new Integer[]{i, j});
                                    cluster_dict.put(top_val, top_cluster);
                                    grid[i][j] = top_val;
                                }
                            } else {
                                List<Integer[]> cluster = cluster_dict.get(grid[i][j - 1]);
                                cluster.add(new Integer[]{i, j});
                                cluster_dict.put(grid[i][j - 1], cluster);
                                grid[i][j] = grid[i][j - 1];
                            }
                        }
                    }
                    if (is_left_busy) {
                        if (!is_top_busy) {
                            grid[i][j] = grid[i][j - 1];
                            int val = grid[i][j];
                            List<Integer[]> cluster = cluster_dict.get(grid[i][j]);
                            cluster.add(new Integer[]{i, j});
                            cluster_dict.put(val, cluster);
                        }
                    }
                    if (is_top_busy) {
                        if (!is_left_busy) {
                            grid[i][j] = grid[i - 1][j];
                            int val = grid[i][j];
                            List<Integer[]> cluster = cluster_dict.get(grid[i][j]);
                            cluster.add(new Integer[]{i, j});
                            cluster_dict.put(val, cluster);
                        }
                    }
                    if (!is_left_busy) {
                        if (!is_top_busy) {
                            grid[i][j] = last_cluster_number;
                            List<Integer[]> new_cluster = new ArrayList<Integer[]>();
                            new_cluster.add(new Integer[]{i, j});
                            cluster_dict.put(grid[i][j], new_cluster);
                            last_cluster_number += 1;
                        }
                    }
                }
            }
        }

        Set<Integer> kSet = cluster_dict.keySet();
        Integer[] keys = new Integer[kSet.size()];
        kSet.toArray(keys);

        return grid;
    }

    public int[][] retrieve_cluster_with_biggest_nucleus_count(int[][] grid, int core_count) {
        int core_with_shell_size = grid.length / core_count;
        int center_disp = (int)Math.floor(core_with_shell_size >> 1);

        Map<Integer, Integer> coreCountList = new HashMap<Integer, Integer>();
        int x;
        int y;
        int knotVal;

        for (int i = 0; i < core_count; i++) {
            for (int j = 0; j < core_count; j++) {
                x = j * core_with_shell_size + center_disp;
                y = i * core_with_shell_size + center_disp;

                knotVal = grid[y][x];
                if (knotVal != 0) {
                    if (coreCountList.containsKey(knotVal)) {
                        coreCountList.replace(knotVal, coreCountList.get(knotVal) + 1);
                    } else {
                        coreCountList.put(knotVal, 1);
                    }
                }
            }
        }

        int maxVal = 0;

        for (Map.Entry<Integer,Integer> each : coreCountList.entrySet()) {
            if (each.getValue() > maxVal) {
                maxVal = each.getValue();
            }

            System.out.println("\t\tkey: " + each.getKey() + "\t\tvalue: " + each.getValue());
        }

        System.out.println("max val: " + maxVal);

        Set<Integer> maxValKeySet = new HashSet<Integer>();

        for (Map.Entry<Integer, Integer> each : coreCountList.entrySet()) {
            if (each.getValue() == maxVal) {
                maxValKeySet.add(each.getKey());
            }
        }

        Map<Integer, Integer> maxValPointsCount = new HashMap<Integer, Integer>();
        for (int each : maxValKeySet) {
            maxValPointsCount.put(each, 0);
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (maxValPointsCount.containsKey(grid[i][j])) {
                    maxValPointsCount.replace(grid[i][j], maxValPointsCount.get(grid[i][j]) + 1);
                }
            }
        }

        for (Map.Entry<Integer,Integer> each : maxValPointsCount.entrySet()) {
            System.out.println("\t\tmax key: " + each.getKey() + "\t\tcount: " + each.getValue());
        }

        int maxKey = 0;
        maxVal = 0;

        for (Map.Entry<Integer, Integer> each : maxValPointsCount.entrySet()) {
            if (each.getValue() > maxVal) {
                maxKey = each.getKey();
                maxVal = each.getValue();
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != maxKey) {
                    grid[i][j] = 0;
                }
            }
        }

        return grid;
    }
}
