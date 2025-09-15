package parser;

import java.io.BufferedReader;
import java.io.IOException;

public class Parser {
    private BufferedReader br;
    private String nextLine;
    private String currentInstruction;
    public enum CommandType {
        A_COMMAND, C_COMMAND, L_COMMAND
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

    public CommandType instructionType() {
        if (currentInstruction == null) return null;
        String instruction = currentInstruction.trim();
        if (instruction.startsWith("@")) {
            return CommandType.A_COMMAND;
        } else if (instruction.startsWith("(") && instruction.endsWith(")")) {
            return CommandType.L_COMMAND;
        } else {
            return CommandType.C_COMMAND;
        }
    } 

    public String symbol() {
        if (instructionType() == CommandType.L_COMMAND){
            return currentInstruction.trim().substring(1, currentInstruction.trim().length() - 1);
        } else if (instructionType() == CommandType.A_COMMAND) {
            return currentInstruction.trim().substring(1);
        } else {
            return null;
        }
    }

    public String dest() {
        if (instructionType() != CommandType.C_COMMAND) return null;
        String instruction = currentInstruction.trim();
        int eqIndex = instruction.indexOf('=');
        if (eqIndex != -1) {
            return instruction.substring(0, eqIndex).trim();
        }
        return "null";
    }

    public String comp() {
        if (instructionType() != CommandType.C_COMMAND) return null;
        String instruction = currentInstruction.trim();
        int eqIndex = instruction.indexOf('=');
        int scIndex = instruction.indexOf(';');
        if (eqIndex != -1 && scIndex != -1) {
            return instruction.substring(eqIndex + 1, scIndex).trim();
        } else if (eqIndex != -1) {
            return instruction.substring(eqIndex + 1).trim();
        } else if (scIndex != -1) {
            return instruction.substring(0, scIndex).trim();
        } else {
            return instruction.trim();
        }
    }

    public String jump() {
        if (instructionType() != CommandType.C_COMMAND) return null;
        String instruction = currentInstruction.trim();
        int scIndex = instruction.indexOf(';');
        if (scIndex != -1) {
            return instruction.substring(scIndex + 1).trim();
        }
        return "null";
    }
}
