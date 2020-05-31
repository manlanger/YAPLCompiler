package yapl.impl;

import yapl.interfaces.Attrib;
import yapl.interfaces.BackendBinSM;
import yapl.interfaces.CodeGen;
import yapl.interfaces.CompilerError;
import yapl.interfaces.MemoryRegion;
import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;
import yapl.types.ArrayType;
import yapl.types.BoolType;
import yapl.types.IntType;
import yapl.types.RecordType;
import yapl.types.VoidType;
import yapl.Token;
import yapl.lib.Type;

public class CodeGenImpl implements CodeGen {
	
	private BackendBinSM backend;
	private int labelId = 0;
	
	public CodeGenImpl(BackendBinSM backend) {
		this.backend = backend;
	}

	@Override
	public String newLabel() {
		return "Label" + labelId++;
	}

	@Override
	public void assignLabel(String label) {
		backend.assignLabel(label);

	}

	@Override
	public byte loadValue(Attrib attr) throws YAPLException {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte loadAddress(Attrib attr) throws YAPLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void freeReg(Attrib attr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void allocVariable(Symbol sym) throws YAPLException {
		if (sym.isGlobal()) {
			int offset = backend.allocStaticData(1);	// int or bool each take 1 byte
			sym.setOffset(offset);
		} else {
			int offset = backend.allocStack(1);
			sym.setOffset(offset);
		}

	}
	
	@Override
	public void setFieldOffsets(RecordType record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeArrayDim(int dim, Attrib length) throws YAPLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attrib allocArray(ArrayType arrayType) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attrib allocRecord(RecordType recordType) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParamOffset(Symbol sym, int pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arrayOffset(Attrib arr, Attrib index) throws YAPLException {
		if (!(arr.getType() instanceof ArrayType)) {
			throw new YAPLException(YAPLException.Internal);
		}
	}

	@Override
	public void recordOffset(Attrib record, Symbol field) throws YAPLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attrib arrayLength(Attrib arr) throws YAPLException {
		return new AttribImpl(new IntType());
	}

	@Override
	public void assign(Attrib lvalue, Attrib expr) throws YAPLException {
		if (lvalue.isConstant()) {
			throw new YAPLException("l-value is read-only", CompilerError.ReadonlyAssign, null);
		}
		
		if (expr.getType() instanceof VoidType) {
			throw new YAPLException("using procedure proc (not a function) in expression", CompilerError.ProcNotFuncExpr, null);
		}
		
		if (!lvalue.getType().isCompatibleTo(expr.getType())) {
			throw new YAPLException("type mismatch in assignment", CompilerError.TypeMismatchAssign, null);
		}
		
		if (lvalue.isConstant() || lvalue.isGlobal()) {
			if (expr.getType() instanceof IntType) {
				backend.storeWord(MemoryRegion.STATIC, lvalue.getOffset());
			}
			else if (expr.getType() instanceof BoolType) {
				backend.storeWord(MemoryRegion.STATIC, lvalue.getOffset());
			}
		}
	}

	@Override
	public Attrib op1(Token op, Attrib x) throws YAPLException {
		if (op != null) {			
			if (!(x.getType() instanceof IntType)) {
				throw new YAPLException("illegal operand type for unary operator " + op.image, CompilerError.IllegalOp1Type, op);
			}
			
			switch (op.image) {
				case "-":
					backend.neg();
				break;
			}
		}
		return x;
	}

	@Override
	public Attrib op2(Attrib x, Token op, Attrib y) throws YAPLException {
		if (x.getType() instanceof IntType && y.getType() instanceof IntType) {			
			switch (op.image) {
			case "+":
				backend.add();
				break;
			case "*":
				backend.mul();
				break;
			case "-":
				backend.sub();
				break;
			case "/":
				backend.div();
				break;
			case "%":
				backend.mod();
				break;
			}
		} else if (x.getType() instanceof BoolType && y.getType() instanceof BoolType){
			switch (op.image) {
			case "And":
				backend.and();
				//backend.loadConst(backend.boolValue(true));
				//backend.isEqual();
				break;
			case "Or":
				backend.or();
				backend.loadConst(2);
				backend.mod();
				backend.loadConst(backend.boolValue(true));
				backend.isEqual();
				break;
			}
		} else {
			throw new YAPLException("illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type, op);
		}
		
		x.setConstant(x.isConstant() && y.isConstant());
		return x;
	}

	@Override
	public Attrib relOp(Attrib x, Token op, Attrib y) throws YAPLException {
		if (x.getType() instanceof IntType && y.getType() instanceof IntType) {
			switch (op.image) {
			case "<":
				backend.isLess();
				break;
			case ">":
				backend.isGreater();
				break;
			case "<=":
				backend.isLessOrEqual();
				break;
			case ">=":
				backend.isGreaterOrEqual();
				break;
			}
		} else {
			throw new YAPLException("non-integer operand types for relational operator " + op.image, CompilerError.IllegalRelOpType, op);
		}
		
		x.setConstant(x.isConstant() && y.isConstant());
		
		return new AttribImpl(new BoolType());
	}

	@Override
	public Attrib equalOp(Attrib x, Token op, Attrib y) throws YAPLException {
		if (x.getType() instanceof IntType && y.getType() instanceof IntType) {
			switch (op.image) {
			case "==":
				backend.isEqual();
				break;
			case "!=":
				//TODO
				break;
			}
		} else if (x.getType() instanceof BoolType && y.getType() instanceof BoolType) {
			
		} else {
			throw new YAPLException("equalOp failure", CompilerError.IllegalEqualOpType, op);
		}
		
		x.setConstant(x.isConstant() && y.isConstant());
		
		return new AttribImpl(new BoolType());
	}

	@Override
	public void enterProc(Symbol proc) throws YAPLException {
		System.out.println("CodeGen enterProc" + proc.getName());
		backend.enterProc(proc.getName(), 0, true);
	}

	@Override
	public void exitProc(Symbol proc) throws YAPLException {
		System.out.println("CodeGen exitProc" + proc.getName());
		backend.exitProc(proc.getName());
	}

	@Override
	public void returnFromProc(Symbol proc, Attrib returnVal) throws YAPLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attrib callProc(Symbol proc, Attrib[] args) throws YAPLException {
		/*if (args != null) {
			for (int i=0; i < args.length; i++) {
				if (args[i].getType() instanceof IntType) {
					if (!args[i].isConstant()) {
						//backend.loadConst(((IntType)args[i].getType()).getValue());
					//} else {
						backend.loadWord(MemoryRegion.STATIC, args[i].getOffset());
					}
				} else if (args[i].getType() instanceof BoolType) {
					if (!args[i].isConstant()) {
						//backend.loadConst(backend.boolValue(((BoolType)args[i].getType()).getValue()));
					//} else {
						backend.loadWord(MemoryRegion.STATIC, args[i].getOffset());
					}
				}
			}
		}*/
		
		System.out.println("Calling proc " + proc.getName());
		backend.callProc(proc.getName());
		return new AttribImpl(proc.getType());
	}

	@Override
	public void writeString(String string) throws YAPLException {
		string = string.substring(1, string.length()-1);
		int addr = backend.allocStringConstant(string);
		backend.writeString(addr);
	}

	@Override
	public void branchIfFalse(Attrib condition, String label) throws YAPLException {
		backend.branchIf(((BoolType)condition.getType()).getValue(), label);
	}

	@Override
	public void jump(String label) {
		backend.jump(label);

	}

	@Override
	public void loadConstant(int value) {
		backend.loadConst(value);
	}
	
	public int storeConstant(boolean value) {
		int intVal = backend.boolValue(value);
		return storeConstant(intVal);
	}
	
	public int storeConstant(int value) {
		int offset = backend.allocStaticData(1);
		backend.loadConst(value);
		backend.storeWord(MemoryRegion.STATIC, offset);
		return offset;
	}

	@Override
	public void loadConstant(boolean value) {
		backend.loadConst(backend.boolValue(value));
	}

	@Override
	public void loadVariable(Symbol symbol) {
		if (symbol.isGlobal()) {
			backend.loadWord(MemoryRegion.STATIC, symbol.getOffset());
		} else {
			backend.loadWord(MemoryRegion.STACK, symbol.getOffset());
		}
		
	}

}
