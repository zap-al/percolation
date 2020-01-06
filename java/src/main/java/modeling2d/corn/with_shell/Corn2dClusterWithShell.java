package modeling2d.corn.with_shell;

import com.comsol.model.Model;
import com.comsol.model.util.ModelUtil;
import modeling2d.corn.with_shell.Calculator2d;

import java.util.ArrayList;
import java.util.List;

public class Corn2dClusterWithShell {
    public static void main(String[] args) {
        int size = 10;
        double probability = 0.7;

        Calculator2d calculator2d = new Calculator2d();

        boolean[][] grid = calculator2d.generate_2d_corn_cluster(size, probability);

    }
}


