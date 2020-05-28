package yapl.types;

import yapl.lib.Type;

public class BoolType extends Type {
	
	private boolean value;
	
	public BoolType() {
	
	}
	
	public BoolType(boolean value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BoolType;
	}
}
