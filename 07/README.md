# VM Translator

This is a partial implementation of the **Virtual Machine (VM) Translator** that converts Hack VM code (`.vm` files) into Hack assembly code (`.asm` files). It supports simple arithmetic and push/pop commands

## Usage

1. **Compile the Java source files:**
From Project root (at VMTranslator):
    ```sh
    javac VMTranslator.java Code.java Parser.java
    ```

2. **Run the translator:**

    ```sh
    java VMTranslator <path/to/your/Program.vm>
    ```

3. **Output:**
    - The assembler will generate a `.asm` file in the same directory as your `.vm` file.
    - For example, `Prog.vm` will produce `Prog.asm`.

## Project Structure

```
VMTranslator/
├── VMTranslator.java   # Main driver: reads VM file, orchestrates parsing and code writing
├── Code.java           # Translates VM commands into Hack assembly instructions
├── Parser.java         # Parses each VM command and identifies command type and arguments
```
