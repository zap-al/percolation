import com.comsol.model.Model;
import com.comsol.model.util.ModelUtil;
import modeling2d.corn.with_shell.Calculator2d;

import java.util.*;

public class Corn2dClusterWithoutShellCOMSOL {
    public static Model run() {
        int size = 10;
        double probability = 0.7;

        String SQUARE_NAME_STRING = "sq:%d-%d";

        Model model = ModelUtil.create("Model");
        model.modelPath("/Users/me/programs/scala/workplace/percolation/java/src/main/java");

        model.component().create("comp1", true);

        model.component("comp1").geom().create("geom1", 2);
        model.component("comp1").mesh().create("mesh1");
        model.component("comp1").geom("geom1").lengthUnit("km");
        model.component("comp1").geom("geom1").scaleUnitValue(true);
        model.component("comp1").geom("geom1").scaleUnitValue(false);

        Calculator2d calculator2d = new Calculator2d();

        boolean[][] grid = calculator2d.generate_2d_corn_cluster(size, probability);

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

        return model;
    }

    public static void main(String[] args) {
        run();
    }

}


