package yapl.impl;

import yapl.interfaces.Symbol;
import yapl.lib.Type;

public class SymbolImpl implements Symbol {
	
	private int kind = -1;
	private String name = null;
	private Type type = null;
	private boolean isReference = false;
	private boolean isReadonly = false;
	private boolean isGlobal = false;
	private int offset = -1;
	private Symbol nextSymbol = null;
	private boolean returnSeen = false;
	
	public SymbolImpl() {
		
	}
	
	public SymbolImpl(String name, int kind) {
		this.name = name;
		this.kind = kind;
	}

	@Override
	public int getKind() {
		return kind;
	}

	@Override
	public String getKindString() {
		switch (this.kind) {
		case 0:
			return "program";
		case 1:
			return "procedure";
		case 2:
			return "variable";
		case 3:
			return "constant";
		case 4:
			return "typename";
		case 5:
			return "field";
		case 6:
			return "parameter";
		}
		
		return "unknown";
	}

	@Override
	public void setKind(int kind) {
		this.kind = kind;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public boolean isReference() {
		return isReference;
	}

	@Override
	public void setReference(boolean isReference) {
		this.isReference = isReference;
	}

	@Override
	public boolean isReadonly() {
		return isReadonly;
	}

	@Override
	public void setReadonly(boolean isReadonly) {
		this.isReadonly = isReadonly;
	}

	@Override
	public boolean isGlobal() {
		return isGlobal;
	}

	@Override
	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public Symbol getNextSymbol() {
		return nextSymbol;
	}

	@Override
	public void setNextSymbol(Symbol symbol) {
		this.nextSymbol = symbol;
	}

	@Override
	public boolean getReturnSeen() {
		return returnSeen;
	}

	@Override
	public void setReturnSeen(boolean seen) {
		this.returnSeen = seen;
	}

}
