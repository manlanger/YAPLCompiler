package yapl.impl;

import yapl.interfaces.Attrib;
import yapl.interfaces.Symbol;
import yapl.lib.Type;

public class AttribImpl implements Attrib {
	
	private byte kind;
	private Type type;
	private boolean isConstant;
	private boolean isReadonly;
	private boolean isGlobal;
	private int offset;
	private byte register;
	
	public AttribImpl(Type type) {
		this.type = type;
	}

	public AttribImpl(byte attribType, Type type) {
		this.kind = attribType;
		this.type = type;
		this.isConstant = false;
		
		switch (attribType) {
		case Attrib.Constant:
			this.isConstant = true;
			this.isReadonly = true;
			break;
		}
	}
	
	public AttribImpl(Symbol symbol) {
		this.type = symbol.getType();
		this.isConstant = (symbol.getKind() == Symbol.Constant);
		this.isGlobal = symbol.isGlobal();
		this.offset = symbol.getOffset();
	}
	
	@Override
	public byte getKind() {
		return kind;
	}

	@Override
	public void setKind(byte kind) {
		this.kind = kind;
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
	public boolean isConstant() {
		return isConstant;
	}

	@Override
	public void setConstant(boolean isConstant) {
		this.isConstant = isConstant;
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
	public byte getRegister() {
		return register;
	}

	@Override
	public void setRegister(byte register) {
		this.register = register;
	}

}
