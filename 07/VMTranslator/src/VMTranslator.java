import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VMTranslator {
    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("Usage: java VMTranslator <inputfile.vm>");
            return;
        }

        String filePath = args[0];
        String outputFilePath = filePath.replaceAll("\\.vm$", ".asm");

        try (
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));
        ) {
            Parser parser = new Parser(br);
            Code code = new Code(bw);

            while (parser.hasMoreLines()) {
                parser.advance();
                if (parser.commandType() == Parser.CommandType.C_ARITHMETIC) {
                    String command = parser.arg1();
                    code.writeArithmetic(command);
                } else if (parser.commandType() == Parser.CommandType.C_PUSH || parser.commandType() == Parser.CommandType.C_POP) {
                    String command = parser.commandType() == Parser.CommandType.C_PUSH ? "C_PUSH" : "C_POP";
                    String segment = parser.arg1();
                    int index = parser.arg2();
                    code.writePushPop(command, segment, index);
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
