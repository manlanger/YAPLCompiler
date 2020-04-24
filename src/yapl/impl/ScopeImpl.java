package yapl.impl;

import java.util.HashMap;

import yapl.interfaces.Scope;
import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;

public class ScopeImpl implements Scope {

	private HashMap<String, Symbol> symbols = new HashMap<>();
	private boolean isGlobal = false;
	private Symbol parentSymbol = null;
	private Scope parentScope = null;
	private boolean isOpen = false;
	
	public ScopeImpl(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}
	
	@Override
	public void addSymbol(Symbol s) throws YAPLException {
		if (symbols.containsKey(s.getName())) {
			Symbol symbol_old = symbols.get(s.getName());
			
			if (symbol_old.getKind() != Symbol.Procedure) {
				throw new YAPLException("symbol " + s.getName() + " already declared in current scope (as " + symbol_old.getKindString() + ")");
			}
		}
		
		s.setGlobal(isGlobal);
		symbols.put(s.getName(), s);		
	}

	@Override
	public void setParentSymbol(Symbol s) {
		parentSymbol = s;	
	}

	@Override
	public boolean hasSymbol(String name) {
		return symbols.containsKey(name);
	}

	@Override
	public Symbol getSymbol(String name) {
		return symbols.get(name);
	}

	@Override
	public void setParentScope(Scope s) {
		parentScope = s;		
	}

	@Override
	public void openScope() {
		isOpen = true;
	}

	@Override
	public void closeScope() {
		isOpen = false;
	}

	@Override
	public boolean isOpen() {
		return isOpen;
	}

	@Override
	public boolean isGlobal() {
		return isGlobal;
	}
}
