package yapl.types;

import yapl.lib.Type;

public class IntType extends Type {
	
	private int value;
	
	public IntType() {
		
	}
	
	public IntType(int value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof IntType;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
