import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileChanges{
    
    public static void main(String[] args) throws IOException{
        if(args.length < 2){
            System.out.println("invalid arguements");
            System.exit(1);
        }
        String inputPath = args [0]; 
        String outputPath = args [1];
        Path ipPath = Paths.get(inputPath);
        Path opPath = Paths.get(outputPath);
        StringBuffer output = new StringBuffer();
        List<String> lines = Files.readAllLines(ipPath, Charset.forName("utf-8"));
            for(String line : lines){
                output.append("##" + line + "\n");
            }
        Files.write(opPath, output.toString().getBytes("utf-8"), StandardOpenOption.CREATE);
    }
}