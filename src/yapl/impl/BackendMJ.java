package yapl.impl;

import java.io.IOException;
import java.io.OutputStream;

import yapl.interfaces.BackendBinSM;
import yapl.interfaces.MemoryRegion;

public class BackendMJ implements BackendBinSM {

	
	@Override
	public int wordSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boolValue(boolean value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void assignLabel(String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeObjectFile(OutputStream outStream) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int allocStaticData(int words) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int allocStringConstant(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int allocStack(int words) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void allocHeap(int words) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeArrayDim(int dim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void allocArray() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadConst(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadWord(MemoryRegion region, int offset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeWord(MemoryRegion region, int offset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadArrayElement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeArrayElement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void arrayLength() {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeInteger() {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeString(int addr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void neg() {
		// TODO Auto-generated method stub

	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sub() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mul() {
		// TODO Auto-generated method stub

	}

	@Override
	public void div() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mod() {
		// TODO Auto-generated method stub

	}

	@Override
	public void and() {
		// TODO Auto-generated method stub

	}

	@Override
	public void or() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isEqual() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isLess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isLessOrEqual() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isGreater() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isGreaterOrEqual() {
		// TODO Auto-generated method stub

	}

	@Override
	public void branchIf(boolean value, String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jump(String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void callProc(String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterProc(String label, int nParams, boolean main) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitProc(String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public int paramOffset(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

}
