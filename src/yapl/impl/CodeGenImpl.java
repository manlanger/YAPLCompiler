package yapl.impl;

import yapl.interfaces.Attrib;
import yapl.interfaces.BackendBinSM;
import yapl.interfaces.CodeGen;
import yapl.interfaces.CompilerError;
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
	
	public CodeGenImpl(BackendBinSM backend) {
		this.backend = backend;
	}

	@Override
	public String newLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignLabel(String label) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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

	}

	@Override
	public Attrib op1(Token op, Attrib x) throws YAPLException {
		if (op != null) {
			System.out.println("op1 " + op!=null?op.image:" null" + " " + x.toString());
			
			if (!(x.getType() instanceof IntType)) {
				throw new YAPLException("illegal operand type for unary operator " + op.image, CompilerError.IllegalOp1Type, op);
			}
		}
		return x;
	}

	@Override
	public Attrib op2(Attrib x, Token op, Attrib y) throws YAPLException {
		System.out.println("op2 " + x.getType() + " " + op.image + " " + y.getType());
		
		if (x.getType() instanceof IntType && y.getType() instanceof IntType) {
			
		} else {
			throw new YAPLException("illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type, op);
		}
		
		x.setConstant(x.isConstant() && y.isConstant());
		return x;
	}

	@Override
	public Attrib relOp(Attrib x, Token op, Attrib y) throws YAPLException {
		System.out.println("relOp " + x.toString() + " " + op.image + " " + y.toString());
		
		if (x.getType() instanceof IntType && y.getType() instanceof IntType) {

		} else {
			throw new YAPLException("non-integer operand types for relational operator " + op.image, CompilerError.IllegalRelOpType, op);
		}
		
		x.setConstant(x.isConstant() && y.isConstant());
		
		return new AttribImpl(new BoolType());
	}

	@Override
	public Attrib equalOp(Attrib x, Token op, Attrib y) throws YAPLException {
		System.out.println("equalOp " + x.toString() + " " + op.image + " " + y.toString());
		
		if (x.getType() instanceof IntType && y.getType() instanceof IntType) {
			
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
		if (args != null) {
			for (int i=0; i < args.length; i++) {
				if (args[i].getType() instanceof IntType) {
					backend.loadConst(((IntType)args[i].getType()).getValue());
				} else if (args[i].getType() instanceof BoolType) {
					backend.loadConst(backend.boolValue(((BoolType)args[i].getType()).getValue()));
				}
			}
		}
		
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
		// TODO Auto-generated method stub

	}

	@Override
	public void jump(String label) {
		// TODO Auto-generated method stub

	}

}
