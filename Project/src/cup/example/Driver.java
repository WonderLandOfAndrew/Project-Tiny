package cup.example;
import java.io.FileInputStream;
import java.io.IOException;
import java_cup.runtime.*;
import symbols.SymbolsTable;
import classes.*;

class Driver {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        parser.parse();
        //parser.debug_parse();
        TreeNode root = parser.getSyntaxTree();
        TreeNodePrinter treePrinter = new TreeNodePrinter(root);
        treePrinter.print();
        
        SymbolsTable table = new SymbolsTable(root);
        table.createTable();
        table.printTable();
        /*
        ComplexSymbolFactory f = new ComplexSymbolFactory();
          
          File file = new File("input.txt");
          FileInputStream fis = null;
          try {
            fis = new FileInputStream(file);
          } catch (IOException e) {
            e.printStackTrace();
          } 
          Lexer lexer = new Lexer(f,fis);
          Symbol currentSymbol ;
          while ((currentSymbol = lexer.next_token()).sym != sym.EOF) {
              System.out.println("currentSymbol == " + currentSymbol);
          }
        */  
    }
    
}