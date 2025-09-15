# Hack Assembler

This project implements an assembler that converts Hack assembly code (`.asm` files) into Hack binary machine code (`.hack` files).

## Usage

1. **Compile the Java source files:**
From Project root (above HackAssembler):
    ```sh
    javac HackAssembler/src/*.java HackAssembler/src/parser/*.java HackAssembler/src/code/*.java HackAssembler/src/symboltable/*.java
    ```

2. **Run the assembler:**

    ```sh
    java -cp HackAssembler/src HackAssembler <path/to/your/Prog.asm>
    ```

3. **Output:**
    - The assembler will generate a `.hack` file in the same directory as your `.asm` file.
    - For example, `Prog.asm` will produce `Prog.hack`.

## Project Structure

```
HackAssembler/
├── src/
│   ├── HackAssembler.java
│   ├── parser/
│   │   └── Parser.java
│   ├── code/
│   │   └── Code.java
│   └── symboltable/
│       └── SymbolTable.java
└── README.md
```

## Notes

- The assembler supports both symbolic and numeric addresses.
- Predefined symbols (e.g., `R0`, `SCREEN`, `KBD`) are handled automatically.
