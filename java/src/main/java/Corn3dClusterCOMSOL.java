import com.comsol.model.Model;
import com.comsol.model.util.ModelUtil;
import modelingSurface.Calculator3d;

public class Corn3dClusterCOMSOL {
    public static Model run() {
        int size = 10;
        double probability = 0.3;
        Calculator3d calculator = new Calculator3d();
        String BLOCK_NAME_STRING = "blk=%d-%d-%d";

        boolean [][][]grid = calculator.generate_3d_corn_cluster(size, probability);


        Model model = ModelUtil.create("Model");

        model.modelPath("/Users/me/programs/scala/workplace/percolation/java/src/main/java");

        model.component().create("comp1", true);
        model.component("comp1").geom().create("geom1", 3);
        model.component("comp1").mesh().create("mesh1");
        model.component("comp1").geom("geom1").lengthUnit("km");
        model.component("comp1").geom("geom1").scaleUnitValue(true);
        model.component("comp1").geom("geom1").scaleUnitValue(false);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (grid[i][j][k]) {
                        model
                                .component("comp1")
                                .geom("geom1")
                                .create(String.format(BLOCK_NAME_STRING, i, j, k), "Block");

                        model
                                .component("comp1")
                                .geom("geom1")
                                .feature(String.format(BLOCK_NAME_STRING, i, j, k))
                                .set("size", new double[]{1, 1, 1});

                        model
                                .component("comp1")
                                .geom("geom1")
                                .feature(String.format(BLOCK_NAME_STRING, i, j, k))
                                .set("pos", new double[]{i, j, k});
                    }
                }
            }
        }
        model.component("comp1").geom("geom1").run();

        return model;
    }

    public static void main(String[] args) {
        run();
    }
}
