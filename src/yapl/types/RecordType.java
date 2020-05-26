package yapl.types;

import yapl.lib.Type;

public class RecordType extends Type {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RecordType;
    }
}
