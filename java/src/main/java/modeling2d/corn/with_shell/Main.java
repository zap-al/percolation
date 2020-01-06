package modeling2d.corn.with_shell;

public class Main {
    public static void main(String[] args) {
        int size = 10;
        double probability = 0.6;
        Calculator2d calculator = new Calculator2d();

        boolean [][]grid = calculator.generate_2d_corn_cluster(size, probability);

        System.out.println(grid);
    }
}
