package parser;

import java.io.*;

public class FromJavaClassToMethodCompiler {
    public static void main(String[] args) throws IOException {
        String to_compile_path = "/Users/me/programs/scala/workplace/percolation/java/src/main/java/to_compile.java";
        String to_write_compiled_path = "/Users/me/programs/scala/workplace/percolation/java/src/main/java/copy_of_method_modified.html";
        String method_copy = "/Users/me/programs/scala/workplace/percolation/java/src/main/java/copy_of_method.html";
        File file = new File(to_compile_path);
        File compiled_file = new File(to_write_compiled_path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(compiled_file));
        StringBuilder stringBuilder = new StringBuilder();
        bufferedWriter.write("");

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println("\"");
            System.out.println(line);
            System.out.println("\"");
            line = line.replace(">", "&gt;");
            System.out.println("\"");
            System.out.println(line);
            System.out.println("\"");
            line = line.replace("<", "&lt;");
            System.out.println("\"");
            System.out.println(line);
            System.out.println("\"");
            line = line.replace("\"", "&quot;");
            stringBuilder.append(line);
            stringBuilder.append("&#xa;");
        }
        bufferedReader.close();

        bufferedReader = new BufferedReader(new FileReader(method_copy));
        bufferedWriter.write("");
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("name=\"p:code\"")) {
                line = line.replaceAll("value=\".+\" name=\"p:code\"", "value=\"" + stringBuilder.toString() + "\" name=\"p:code\"");
                System.out.println(line);
            }

            bufferedWriter.append(line + "\n");

        }
        bufferedWriter.close();
    }
}
