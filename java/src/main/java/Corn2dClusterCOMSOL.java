import com.comsol.model.Model;
import com.comsol.model.util.ModelUtil;
import com.comsol.util.webclient.WebTest;
import modeling2d.Calculator2d;

import java.util.ArrayList;
import java.util.List;

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
        Calculator2d calculator2d = new Calculator2d();
        boolean[][] grid = calculator2d.generate_2d_corn_cluster(size, 0.5);

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
}
