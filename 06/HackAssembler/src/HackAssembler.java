import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import parser.Parser;
import code.Code;
import symboltable.SymbolTable;

public class HackAssembler {
    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("Usage: java HackAssembler <inputfile.asm>");
            return;
        }

        String filePath = args[0];
        String outputFilePath = filePath.replaceAll("\\.asm$", ".hack");

        SymbolTable symbolTable = new SymbolTable();
        try (
            BufferedReader br = new BufferedReader(new FileReader(filePath));
        ) {
            Parser parser = new Parser(br);

            // First pass: handle labels (L_COMMAND)
            int lineNumber = 0;
            while (parser.hasMoreLines()) {
                String line = parser.advance();
                if (line != null){
                    if (parser.instructionType() == Parser.CommandType.L_COMMAND) {
                        String symbol = parser.symbol();
                        symbolTable.addEntry(symbol, lineNumber);
                    } else {
                        lineNumber++;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        try (
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))
        ) {
            Parser parser = new Parser(br);
            Code code = new Code();
        
            // Second pass: generate binary code
            int variableAddress = 16;
            while (parser.hasMoreLines()) {
                String line = parser.advance();
                if (line != null) {
                    String binaryInstruction = null;
                    if (parser.instructionType() == Parser.CommandType.A_COMMAND) {
                        String symbol = parser.symbol();
                        int value;
                        try {
                            value = Integer.parseInt(symbol);
                        } catch (NumberFormatException e) {
                            if (!symbolTable.contains(symbol)) {
                                symbolTable.addEntry(symbol, variableAddress);
                                value = variableAddress;
                                variableAddress++;
                            } else {
                                value = symbolTable.getAddress(symbol);
                            }
                        }
                        String binary = Integer.toBinaryString(value);
                        binaryInstruction = String.format("%16s", binary).replace(' ', '0');
                    }
                    else if (parser.instructionType() == Parser.CommandType.C_COMMAND) {
                        String dest = code.dest(parser.dest());
                        String comp = code.comp(parser.comp());
                        String jump = code.jump(parser.jump());
                        binaryInstruction = "111" + comp + dest + jump;
                    }

                    if (binaryInstruction != null) {
                        bw.write(binaryInstruction);
                        if (parser.hasMoreLines()) {
                            bw.newLine();
                        }
                    }
                }
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
