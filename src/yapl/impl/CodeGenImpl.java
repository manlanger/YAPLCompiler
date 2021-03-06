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
		System.out.println("CodeGen allocating variable " + sym.getName() + " at " + sym.isGlobal());
		
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
		backend.storeArrayDim(((IntType)length.getType()).getValue());
	}

	@Override
	public Attrib allocArray(ArrayType arrayType) throws YAPLException {
		backend.allocArray();
		Attrib a = new AttribImpl(arrayType);
		return a;
	}

	@Override
	public Attrib allocRecord(RecordType recordType) throws YAPLException {
		int heapSize = 0;
		Symbol nextSymbol = recordType.getRecordSymbol();
		do {
			nextSymbol = nextSymbol.getNextSymbol();
			if(nextSymbol != null && nextSymbol.getKind() == Symbol.Field)
			{
				nextSymbol.setOffset(heapSize);
				heapSize++;
			}else
			{
				nextSymbol = null;
			}

		}while (nextSymbol != null);
		backend.allocHeap(heapSize);
		Attrib a = new AttribImpl(recordType);
		a.setKind(Attrib.RegAddress);
		// TODO Auto-generated method stub
		return a;
	}

	@Override
	public void setParamOffset(Symbol sym, int pos) {
		sym.setOffset(pos);
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
		System.out.println("Codegen ArrayLength");

		backend.arrayLength();
		return new AttribImpl(new IntType());
	}

	@Override
	public void assign(Attrib lvalue, Attrib expr) throws YAPLException {
		if (lvalue.isConstant()) {
			throw new YAPLException("l-value is read-only", CompilerError.ReadonlyAssign, null);
		}
		checkTypeAssignment(lvalue.getType(),expr.getType());

		//TODO Check Array
		if(lvalue.getKind() == Attrib.ArrayElement)
		{
			backend.storeArrayElement();
			return;
		}
		MemoryRegion region = MemoryRegion.STACK;
		if(lvalue.isGlobal())
		{
			region = MemoryRegion.STATIC;
		}
		if(lvalue.getKind() == Attrib.RecordField)
		{
			region = MemoryRegion.HEAP;
		}
		backend.storeWord(region, lvalue.getOffset());
	}

	private void checkTypeAssignment(Type lvalueType, Type exprType) throws YAPLException
	{
		if (exprType instanceof VoidType) {
			throw new YAPLException("using procedure proc (not a function) in expression", CompilerError.ProcNotFuncExpr, null);
		}

		if (!lvalueType.isCompatibleTo(exprType)) {
			throw new YAPLException("type mismatch in assignment", CompilerError.TypeMismatchAssign, null);
		}
	}

	@Override
	public void setArrayElement(Attrib selectorAttrib, Attrib expr) throws YAPLException{

		checkTypeAssignment(selectorAttrib.getType(),expr.getType());
		backend.storeArrayElement();

	}

	@Override
	public void loadArrayVariable(Attrib array) throws YAPLException {
		if (!(array.getType() instanceof ArrayType)) {
			throw new YAPLException(YAPLException.Internal);
		}
		backend.loadArrayElement();
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
		if (proc == null) {
			backend.enterProc("main", 0, true);
		} else {
			System.out.println("CodeGen enterProc: " + proc.getName());
			int nParam = 0;
			
			Symbol next = proc.getNextSymbol();
			
			while (next != null) {
				System.out.println("CodeGen got parameter: " + next.getName() + ", assigning offset: " + nParam);

				next.setOffset(nParam);
				nParam++;
				
				if (next.getKind() == Symbol.Constant) {
					if (next.getType() instanceof IntType || next.getType() instanceof BoolType) {
						int iValue;
						
						if (next.getType() instanceof IntType) {
							iValue = ((IntType)next.getType()).getValue();
						} else {
							iValue = backend.boolValue(((BoolType)next.getType()).getValue());
						}
						
						backend.loadConst(iValue);
					}
				} else if (next.getKind() == Symbol.Variable || next.getKind() == Symbol.Parameter) {
					loadVariable(next);
				}
				
				next = next.getNextSymbol();
			}
			
			backend.enterProc(proc.getName(), nParam, false);
		}
	}

	@Override
	public void exitProc(Symbol proc) throws YAPLException {		
		if (proc == null) {
			backend.exitProc("main");
		} else {
			System.out.println("CodeGen exitProc: " + proc.getName());
			backend.exitProc(proc.getName());
		} 
	}

	@Override
	public void returnFromProc(Symbol proc, Attrib returnVal) throws YAPLException {
		backend.jump(proc.getName()+"_end");
	}

	@Override
	public Attrib callProc(Symbol proc, Attrib[] args) throws YAPLException {
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
		System.out.println("CodeGen loading int constant: " + value);
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
		System.out.println("CodeGen loading bool constant: " + value);
		backend.loadConst(backend.boolValue(value));
	}

	@Override
	public void loadVariable(Symbol symbol) {
		System.out.println("CodeGen loading " + (symbol.isGlobal()?"global ":"local ") + "variable " + symbol.getName() + " with offset " + symbol.getOffset());
		
		if (symbol.isGlobal()) {
			backend.loadWord(MemoryRegion.STATIC, symbol.getOffset());
		} else {
			backend.loadWord(MemoryRegion.STACK, symbol.getOffset());
		}
	}

	@Override
	public void loadVariable(Attrib attrib) {
		System.out.println("CodeGen loading " + (attrib.isGlobal()?"global ":"local ") + " with offset " + attrib.getOffset());

		MemoryRegion region = MemoryRegion.STACK;
		if(attrib.isGlobal())
		{
			region = MemoryRegion.STATIC;
		}
		if(attrib.getKind() == Attrib.RecordField)
		{
			region = MemoryRegion.HEAP;
		}
		backend.loadWord(region, attrib.getOffset());
	}


}
