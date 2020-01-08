package modeling2d.corn.with_shell;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RunMe {
    public static void main(String[] args) {
        int size = 3;
        double probability = 0.6;
        int nucleus_size = 3;
        int shell_size = 1;
        double shell_probability = 0.5;

        Calculator2d calculator = new Calculator2d();

        boolean [][]grid = calculator.generate_2d_corn_cluster(size, probability);

        for (boolean[] ints : grid) {
            for (boolean each : ints) {
                System.out.print("\t" + (each ? 1 : 0));
            }
            System.out.println();
        }
        System.out.println("\n\n");
        boolean [][]grid2 = calculator.expand_existing_cluster_with_shell(
                grid,
                nucleus_size,
                shell_size,
                shell_probability
        );
        System.out.println("\n\n");
        for (boolean[] ints : grid2) {
            for (boolean each : ints) {
                System.out.print("\t" + (each ? 1 : 0));
            }
            System.out.println();
        }
        System.out.println("\n\n");

        int[][] grid3 = calculator.prepare_cluster_for_splitting(grid2);
        for (int[] ints : grid3) {
            for (int each : ints) {
                System.out.print("\t" + each);
            }
            System.out.println();
        }
        System.out.println("\n\n");
        int[][] grid4 = calculator.split_on_clusters(grid3);
        for (int[] ints : grid4) {
            for (int each : ints) {
                System.out.print("\t" + each);
            }
            System.out.println();
        }

        int[][] biggest_cluster_grid = calculator.retrieve_cluster_with_biggest_nucleus_count(grid4, size);
        for (int[] ints : biggest_cluster_grid) {
            for (int each : ints) {
                System.out.print("\t" + each);
            }
            System.out.println();
        }

    }
}
