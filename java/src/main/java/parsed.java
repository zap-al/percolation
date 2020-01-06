package builder;

import com.comsol.api.ApplicationMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.random;

public class method21 extends ApplicationMethod {

    public String execute(double p, int N) {
        String textlabel1 = null;
        {
            int size = 10;
            double probability = 0.7;

            String SQUARE_NAME_STRING = "sq:%d-%d";

            model.component().create("comp1", true);

            model.component("comp1").geom().create("geom1", 2);
            model.component("comp1").mesh().create("mesh1");
            model.component("comp1").geom("geom1").lengthUnit("km");
            model.component("comp1").geom("geom1").scaleUnitValue(true);
            model.component("comp1").geom("geom1").scaleUnitValue(false);


            boolean[][] grid = new boolean[size][size];
            int mid = size / 2;

            Set<int[]> current_step_set = new HashSet<int[]>();

            current_step_set.add(new int[]{mid + 1, mid});
            current_step_set.add(new int[]{mid - 1, mid});
            current_step_set.add(new int[]{mid, mid + 1});
            current_step_set.add(new int[]{mid, mid - 1});

            float[][] arr = new float[size][size];
            arr[mid][mid] = 1;

            Set<int[]> next_step_set = current_step_set;

          while (next_step_set.size() > 0) {
            Set<int[]> next_set = new HashSet<int[]>();

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

          for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
              grid[i][j] = arr[i][j] == 1;
            }
          }

          List<String> nameList = new ArrayList<String>();

          for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

              if (grid[i][j]) {
                model.component("comp1").geom("geom1").create("sq:"+i+"-"+j, "Square");
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

        }
        return textlabel1;
    }

}
