package modeling3d;

public class Main {
    public static void main(String[] args) {
        int size = 10;
        double probability = 0.6;
        Calculator3d calculator = new Calculator3d();

        boolean [][][]grid = calculator.generate_3d_corn_cluster(size, probability);

        System.out.println(grid);
    }
}
