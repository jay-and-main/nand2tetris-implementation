import java.io.BufferedWriter;
import java.io.IOException;

public class Code {
    private BufferedWriter bw;

    public Code(BufferedWriter fileStream) throws IOException {
        this.bw = fileStream;
    }

    public void writeArithmetic(String command) throws IOException {
        // Implementation for writing arithmetic commands
        if (command.equals("add")){
            bw.write("@SP\n");
            bw.write("M=M-1\n");
            bw.write("A=M\n");
            bw.write("D=M\n");
            bw.write("A=A-1\n");
            bw.write("M=D+M\n");
        }

        if (command.equals("sub")){
            bw.write("@SP\n");
            bw.write("M=M-1\n");
            bw.write("A=M\n");
            bw.write("D=M\n");
            bw.write("A=A-1\n");
            bw.write("M=M-D\n");
        }

        if (command.equals("neg")){
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=-M\n");
        }

        if (command.equals("eq")) {
            String labelTrue = "LABEL_TRUE_" + System.nanoTime();
            String labelEnd = "LABEL_END_" + System.nanoTime();

            bw.write("@SP\n");
            bw.write("M=M-1\n");
            bw.write("A=M\n");
            bw.write("D=M\n");
            bw.write("A=A-1\n");
            bw.write("D=M-D\n");
            bw.write("@" + labelTrue + "\n");
            bw.write("D;JEQ\n");
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=0\n");
            bw.write("@" + labelEnd + "\n");
            bw.write("0;JMP\n");
            bw.write("(" + labelTrue + ")\n");
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=-1\n");
            bw.write("(" + labelEnd + ")\n");
        }

        if (command.equals("gt")) {
            String labelTrue = "LABEL_TRUE_" + System.nanoTime();
            String labelEnd = "LABEL_END_" + System.nanoTime();

            bw.write("@SP\n");
            bw.write("M=M-1\n");
            bw.write("A=M\n");
            bw.write("D=M\n");
            bw.write("A=A-1\n");
            bw.write("D=M-D\n");
            bw.write("@" + labelTrue + "\n");
            bw.write("D;JGT\n");
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=0\n");
            bw.write("@" + labelEnd + "\n");
            bw.write("0;JMP\n");
            bw.write("(" + labelTrue + ")\n");
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=-1\n");
            bw.write("(" + labelEnd + ")\n");
        }

        if (command.equals("lt")) {
            String labelTrue = "LABEL_TRUE_" + System.nanoTime();
            String labelEnd = "LABEL_END_" + System.nanoTime();

            bw.write("@SP\n");
            bw.write("M=M-1\n");
            bw.write("A=M\n");
            bw.write("D=M\n");
            bw.write("A=A-1\n");
            bw.write("D=M-D\n");
            bw.write("@" + labelTrue + "\n");
            bw.write("D;JLT\n");
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=0\n");
            bw.write("@" + labelEnd + "\n");
            bw.write("0;JMP\n");
            bw.write("(" + labelTrue + ")\n");
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=-1\n");
            bw.write("(" + labelEnd + ")\n");
        }

        if (command.equals("and")){
            bw.write("@SP\n");
            bw.write("M=M-1\n");
            bw.write("A=M\n");
            bw.write("D=M\n");
            bw.write("A=A-1\n");
            bw.write("M=D&M\n");
        }

        if (command.equals("or")){
            bw.write("@SP\n");
            bw.write("M=M-1\n");
            bw.write("A=M\n");
            bw.write("D=M\n");
            bw.write("A=A-1\n");
            bw.write("M=D|M\n");
        }

        if (command.equals("not")){
            bw.write("@SP\n");
            bw.write("A=M-1\n");
            bw.write("M=!M\n");
        }
    }

    public void writePushPop(String command, String segment, int index) throws IOException {
        // Implementation for writing push/pop commands
        if (command.equals("C_PUSH")) {
            if (segment.equals("constant")) {
                bw.write("@" + index + "\n");
                bw.write("D=A\n");
                bw.write("@SP\n");
                bw.write("A=M\n");
                bw.write("M=D\n");
                bw.write("@SP\n");
                bw.write("M=M+1\n");
            }

            if (segment.equals("local") || segment.equals("argument") || segment.equals("this") || segment.equals("that")) {
                String base;
                if (segment.equals("local")) base = "LCL";
                else if (segment.equals("argument")) base = "ARG";
                else if (segment.equals("this")) base = "THIS";
                else base = "THAT";

                bw.write("@" + base + "\n");
                bw.write("D=M\n");
                bw.write("@" + index + "\n");
                bw.write("A=D+A\n");
                bw.write("D=M\n");
                bw.write("@SP\n");
                bw.write("A=M\n");
                bw.write("M=D\n");
                bw.write("@SP\n");
                bw.write("M=M+1\n");
            }

            if (segment.equals("static")) {
                bw.write("@" + (16 + index) + "\n");
                bw.write("D=M\n");
                bw.write("@SP\n");
                bw.write("A=M\n");
                bw.write("M=D\n");
                bw.write("@SP\n");
                bw.write("M=M+1\n");
            }

            if (segment.equals("temp")) {
                bw.write("@" + (5 + index) + "\n");
                bw.write("D=M\n");
                bw.write("@SP\n");
                bw.write("A=M\n");
                bw.write("M=D\n");
                bw.write("@SP\n");
                bw.write("M=M+1\n");
            }

            if (segment.equals("pointer")) {
                if (index == 0) {
                    bw.write("@THIS\n");
                } else if (index == 1) {
                    bw.write("@THAT\n");
                }
                bw.write("D=M\n");
                bw.write("@SP\n");
                bw.write("A=M\n");
                bw.write("M=D\n");
                bw.write("@SP\n");
                bw.write("M=M+1\n");
            }

        } else if (command.equals("C_POP")) {
            if (segment.equals("local") || segment.equals("argument") || segment.equals("this") || segment.equals("that")) {
                String base;
                if (segment.equals("local")) base = "LCL";
                else if (segment.equals("argument")) base = "ARG";
                else if (segment.equals("this")) base = "THIS";
                else base = "THAT";
                
                bw.write("@" + base + "\n");
                bw.write("D=M\n");
                bw.write("@" + index + "\n");
                bw.write("D=D+A\n");
                bw.write("@R13\n");
                bw.write("M=D\n");
                bw.write("@SP\n");
                bw.write("M=M-1\n");
                bw.write("A=M\n");
                bw.write("D=M\n");
                bw.write("@R13\n");
                bw.write("A=M\n");
                bw.write("M=D\n");
            }

            if (segment.equals("static")) {
                bw.write("@SP\n");
                bw.write("M=M-1\n");
                bw.write("A=M\n");
                bw.write("D=M\n");
                bw.write("@" + (16 + index) + "\n");
                bw.write("M=D\n");
            }

            if (segment.equals("temp")) {
                bw.write("@SP\n");
                bw.write("M=M-1\n");
                bw.write("A=M\n");
                bw.write("D=M\n");
                bw.write("@" + (5 + index) + "\n");
                bw.write("M=D\n");
            }

            if (segment.equals("pointer")) {
                bw.write("@SP\n");
                bw.write("M=M-1\n");
                bw.write("A=M\n");
                bw.write("D=M\n");
                if (index == 0) {
                    bw.write("@THIS\n");
                } else if (index == 1) {
                    bw.write("@THAT\n");
                }
                bw.write("M=D\n");
            }
        }
    }

    public void close() throws IOException {
        bw.close();
    }
}
