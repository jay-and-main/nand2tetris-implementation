import java.io.BufferedReader;
import java.io.IOException;

public class Parser {
    private BufferedReader br;
    private String nextLine;
    private String currentInstruction;
    public enum CommandType {
        C_ARITHMETIC, C_PUSH, C_POP, C_LABEL, C_GOTO, C_IF, C_FUNCTION, C_RETURN, C_CALL
    }

    public Parser(BufferedReader fileStream) throws IOException {
        this.br = fileStream;
        this.nextLine = br.readLine();
        this.currentInstruction = null;
    }

    public boolean hasMoreLines() {
        return nextLine != null;
    }

    public String advance() throws IOException {
        String line = nextLine;
        while (line != null && (line.trim().isEmpty() || line.trim().startsWith("//"))) {
            line = br.readLine();
        }
        nextLine = br.readLine();
        while (nextLine != null && (nextLine.trim().isEmpty() || nextLine.trim().startsWith("//"))) {
            nextLine = br.readLine();
        }
        currentInstruction = line;
        return line;
    }

    public CommandType commandType() {
        if (currentInstruction == null) return null;
        String instruction = currentInstruction.trim();
        String[] parts = instruction.split("\\s+");
        String command = parts[0];

        switch (command) {
            case "push":
                return CommandType.C_PUSH;
            case "pop":
                return CommandType.C_POP;
            case "label":
                return CommandType.C_LABEL;
            case "goto":
                return CommandType.C_GOTO;
            case "if-goto":
                return CommandType.C_IF;
            case "function":
                return CommandType.C_FUNCTION;
            case "call":
                return CommandType.C_CALL;
            case "return":
                return CommandType.C_RETURN;
            default:
                return CommandType.C_ARITHMETIC;
        }
    }

    public String arg1() {
        if (commandType() == CommandType.C_RETURN) {
            return null;
        }
        String instruction = currentInstruction.trim();
        String[] parts = instruction.split("\\s+");
        if (commandType() == CommandType.C_ARITHMETIC) {
            return parts[0];
        } else {
            return parts[1];
        }
    }

    public int arg2() {
        if (commandType() == CommandType.C_PUSH ||
            commandType() == CommandType.C_POP ||
            commandType() == CommandType.C_FUNCTION ||
            commandType() == CommandType.C_CALL) {
            String instruction = currentInstruction.trim();
            String[] parts = instruction.split("\\s+");
            return Integer.parseInt(parts[2]);
        } else {
            return -1;
        }
    }
}
