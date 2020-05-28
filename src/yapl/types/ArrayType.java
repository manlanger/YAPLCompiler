package yapl.types;

import yapl.lib.Type;

public class ArrayType extends Type {
	private Type elementType;
	private int length;
	
	public ArrayType(Type type) {
		this.elementType = type;
		this.length = 0;
	}
	
	public Type getElementType() {
		return elementType;
	}
	
	public int getLength() {
		return length;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ArrayType)
		{
			return elementType.equals(((ArrayType) obj).elementType);
		}
		return false;
	}
}
