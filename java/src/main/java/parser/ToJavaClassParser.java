package parser;

import java.io.*;

public class ToJavaClassParser {
    public static void main(String[] args) throws IOException {
        String to_parse_path = "/Users/me/programs/scala/workplace/percolation/java/src/main/java/copy_of_method.html";
        String to_write_parsed_path = "/Users/me/programs/scala/workplace/percolation/java/src/main/java/parsed.java";
        File file = new File(to_parse_path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        File parsed_file = new File(to_write_parsed_path);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(parsed_file));
        bufferedWriter.write("");

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains("name=\"p:code\"")) {
                    continue;
                }
                line = line.split("\"")[3];
                System.out.println("\"");
                System.out.println(line);
                System.out.println("\"");
                line = line.replace("&#xa;", "\n");
                System.out.println("\"");
                System.out.println(line);
                System.out.println("\"");
                line = line.replace("&gt;", ">");
                System.out.println("\"");
                System.out.println(line);
                System.out.println("\"");
                line = line.replace("&lt;", "<");
                System.out.println("\"");
                System.out.println(line);
                System.out.println("\"");
                line = line.replace("&quot;", "\"");
                bufferedWriter.write(line);
                break;
            }
        } catch (IOException e) {
            // ... handle IO exception
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
}
