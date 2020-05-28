package yapl.types;

import yapl.interfaces.Symbol;
import yapl.lib.Type;

import java.util.HashSet;
import java.util.Set;

public class RecordType extends Type {

    private Symbol recordSymbol;

    public RecordType(Symbol recordSymbol) {
        this.recordSymbol = recordSymbol;
    }


    public Symbol getRecordSymbol() {
        return recordSymbol;
    }

    private Set<Symbol> getFields() {
        Set<Symbol> myFields = new HashSet<>();
        Symbol next = recordSymbol.getNextSymbol();
        while (next != null && next.getKind() == Symbol.Field) {
            myFields.add(next);
            next = next.getNextSymbol();
        }
        return myFields;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RecordType) {
            if(recordSymbol.getName().equals(((RecordType) obj).recordSymbol.getName()))
            {
                return true;
            }
            Set<Symbol> myFields = getFields();
            Set<Symbol> otherObjFields = ((RecordType) obj).getFields();
            if (myFields.size() != otherObjFields.size()) {
                return false;
            }


            for (Symbol mySym :
                    myFields) {
                boolean isCompatible = false;
                for (Symbol otherSym :
                        otherObjFields) {
                    if (mySym.getType().isCompatibleTo(otherSym.getType())) {
                        isCompatible = true;
                    }
                }
                if(!isCompatible)
                {
                    return false;
                }
            }


            return true;
        }
        return false;
    }
}
