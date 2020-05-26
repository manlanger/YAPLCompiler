package yapl.types;

import yapl.lib.Type;

public class VoidType extends Type {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VoidType;
    }
}
