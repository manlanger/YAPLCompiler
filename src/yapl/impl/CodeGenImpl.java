package yapl.impl;

import yapl.interfaces.Attrib;
import yapl.interfaces.CodeGen;
import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;
import yapl.types.ArrayType;
import yapl.types.BoolType;
import yapl.types.IntType;
import yapl.types.RecordType;
import yapl.Token;

public class CodeGenImpl implements CodeGen {

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
		// TODO Auto-generated method stub

	}

	@Override
	public void recordOffset(Attrib record, Symbol field) throws YAPLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attrib arrayLength(Attrib arr) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assign(Attrib lvalue, Attrib expr) throws YAPLException {
		// TODO Auto-generated method stub

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
		System.out.println("op2 " + x.toString() + " " + op.image + " " + y.toString());
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
		return x;
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
		return x;
	}

	@Override
	public void enterProc(Symbol proc) throws YAPLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitProc(Symbol proc) throws YAPLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void returnFromProc(Symbol proc, Attrib returnVal) throws YAPLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attrib callProc(Symbol proc, Attrib[] args) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeString(String string) throws YAPLException {
		// TODO Auto-generated method stub

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
