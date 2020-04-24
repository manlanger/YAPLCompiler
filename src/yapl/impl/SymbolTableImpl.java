package yapl.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import yapl.interfaces.Scope;
import yapl.interfaces.Symbol;
import yapl.interfaces.Symboltable;
import yapl.lib.YAPLException;

public class SymbolTableImpl implements Symboltable {

	private Deque<Scope> scopes = new ArrayDeque<>();
	private boolean isDebugMode = false;
	
	@Override
	public void openScope(boolean isGlobal) {
		
		Scope newScope = new ScopeImpl(isGlobal);
		newScope.openScope();
		
		if (!scopes.isEmpty()) {
			Scope currScope = scopes.pop();
			scopes.push(currScope);
			newScope.setParentScope(currScope);
		}
		
		scopes.push(newScope);
	}

	@Override
	public void closeScope() {
		Scope currScope = scopes.pop();
		
		currScope.closeScope();
		
		scopes.push(currScope);
	}

	@Override
	public void addSymbol(Symbol s) throws YAPLException {
		Scope currScope = scopes.pop();
		
		s.setGlobal(currScope.isGlobal());
		currScope.addSymbol(s);
		
		scopes.push(currScope);
	}

	@Override
	public Symbol lookup(String name) throws YAPLException {
		if (isDebugMode) {
			System.out.println("looking up " + name + " in symbol table...");
		}
		
		if (name == null)
			throw new YAPLException("identifier " + name + " not declared");
		
		for (Scope scope : scopes) {
			if (scope.hasSymbol(name)) {
				Symbol symbol = scope.getSymbol(name);
				
				if (symbol.getKind() != Symbol.Procedure) {
					if (!scope.isOpen())
						return  null;
				}
				if (isDebugMode) {
					System.out.println("found " + name + " in symbol table.");
				}
				return scope.getSymbol(name);
			}
		}
		
		if (isDebugMode) {
			System.out.println("Did not find " + name + " in symbol table!");
		}
		
		return null;
	}

	@Override
	public void setParentSymbol(Symbol sym) {
		Scope currScope = scopes.pop();
		
		currScope.setParentSymbol(sym);
		
		scopes.push(currScope);
	}

	@Override
	public Symbol getNearestParentSymbol(int kind) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDebug(boolean on) {
		isDebugMode = on;
	}

}
