package yapl.interfaces;

import yapl.lib.YAPLException;

public interface Scope {
	
	public void addSymbol(Symbol s) throws YAPLException;
	public void setParentSymbol(Symbol s);
	public boolean hasSymbol(String name);
	public Symbol getSymbol(String name);
	public void setParentScope(Scope s);
	public boolean isGlobal();
	
}
