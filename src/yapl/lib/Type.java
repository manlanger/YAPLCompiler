package yapl.lib;

import yapl.types.ArrayType;
import yapl.types.VoidType;

public class Type {
	
	public Type() {
		
	}
	
	public boolean isCompatibleTo(Type other) {
		if (other instanceof VoidType)
			return false;
		
		if (this instanceof ArrayType && other instanceof ArrayType) {
			return ((ArrayType)this).getElementType().isCompatibleTo(((ArrayType)other).getElementType());
		} else {
			return this.getClass().equals(other.getClass()); 
		}
	}
}
