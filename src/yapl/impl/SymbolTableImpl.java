package yapl.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import yapl.interfaces.Scope;
import yapl.interfaces.Symbol;
import yapl.interfaces.Symboltable;
import yapl.lib.YAPLException;

public class SymbolTableImpl implements Symboltable {

	private Deque<Scope> scopes = new ArrayDeque<>();
	
	@Override
	public void openScope(boolean isGlobal) {
		
		Scope newScope = new ScopeImpl(isGlobal);
		
		if (!scopes.isEmpty()) {
			Scope currScope = scopes.pop();
			scopes.push(currScope);
			newScope.setParentScope(currScope);
		}
		
		scopes.push(newScope);
	}

	@Override
	public void closeScope() {
		scopes.pop();
	}

	@Override
	public void addSymbol(Symbol s) throws YAPLException {
		Scope currScope = scopes.pop();
		
		currScope.addSymbol(s);
		
		scopes.push(currScope);
	}

	@Override
	public Symbol lookup(String name) throws YAPLException {
		if (name == null)
			throw new YAPLException();
		
		for (Scope scope : scopes) {
			if (scope.hasSymbol(name)) {
				return scope.getSymbol(name);
			}
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
		// TODO Auto-generated method stub

	}

}
