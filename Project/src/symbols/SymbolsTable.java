package symbols;

import java.util.HashMap;
import java.util.Map;


import classes.TreeNode;

public class SymbolsTable {
	
	//private Tree syntaxTree;
	private HashMap<String, SymbolDetails> table = new HashMap<String, SymbolDetails>();
	
	private TreeNode root = null;
	
	private void extractSymbolsFromNode(TreeNode node, String currentContext, IdentifierScope scope)
	{
		String context = currentContext;
		IdentifierScope localScope = scope;
		if (node.getData().equals("VarDeclaration") && node.getChildren().length > 0)
		{
			for (int i = 0; i < node.getChildren().length; i++) {
				var variableNode = node.getChildren()[i];
				var variableInfoNodes = variableNode.getChildren();
				if (variableInfoNodes.length == 2)
				{
					var dataTypeNode = variableInfoNodes[1].getChildren()[0];
					var variableNameNode = variableInfoNodes[0];
					
					SymbolDetails details = new SymbolDetails();
					details.contextName = currentContext;			
					details.symbolName = variableNameNode.getData();
					details.dataType = dataTypeNode.getData();
					details.symbolScope = scope;
					details.symbolType = SymbolType.Variable;
					table.put(details.symbolName, details);
				}
			}
		}
		if (node.getData().equals("FunDeclaration") )
		{
			SymbolDetails details = new SymbolDetails();
			details.contextName = currentContext;
			TreeNode node1 = node.getChildren()[0];
			details.symbolName = node1.getExtraData();
			if (node1.getChildren().length > 0)
			{
				TreeNode node2 = node1.getChildren()[0];
				details.dataType = node2.getChildren()[0].getData();
			}			
			details.symbolScope = scope;
			details.symbolType = SymbolType.Function;
			context = details.symbolName;
			localScope = IdentifierScope.Local;
			table.put(details.symbolName, details);
			
		}
		for (int i = 0; i < node.getChildren().length; i++)
		{
			extractSymbolsFromNode(node.getChildren()[i], context, localScope);
		}		
	}
	
	public SymbolsTable(TreeNode root) 
	{
		this.root = root;
	}
	
	public void createTable()
	{
		extractSymbolsFromNode(root, "Global", IdentifierScope.Global);
	}	
	
	public SymbolDetails getSymbol(String symbol)
	{
		if (table.containsKey(symbol))
		{
			return table.get(symbol);
		}
		return null;
	}
	
	public void printTable()
	{
		for (Map.Entry<String, SymbolDetails> mapEntry : table.entrySet()) {
			String symbol = mapEntry.getKey();
			SymbolDetails details = mapEntry.getValue();
			System.out.println("------------ SYMBOL: " + symbol + " -----------------");
			System.out.println("Data Type: " + details.dataType);
			System.out.println("Context: " + details.contextName);
			System.out.println("Symbol Type: " + details.symbolType);
			System.out.println("Symbol Scope: " + details.symbolScope);
		}		
	}
}
