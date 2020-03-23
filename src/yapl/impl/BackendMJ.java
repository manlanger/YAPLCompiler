package yapl.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import yapl.interfaces.BackendBinSM;
import yapl.interfaces.ICode;
import yapl.interfaces.MemoryRegion;

public class BackendMJ implements BackendBinSM {

	private ByteBuffer code;
	private IntBuffer data;
	private Integer startPC;
	private Integer dataSize;
	private Map<String, Short> labelPosition;
	private Map<String, ArrayList<Integer>> backPatching;
	
	public BackendMJ() {
		code = ByteBuffer.allocate(100);
		data = IntBuffer.allocate(100);
		
		startPC = 0;
		dataSize = 0;
		
		labelPosition = new HashMap<String, Short>();
		backPatching = new HashMap<String, ArrayList<Integer>>();
	}
	
	@Override
	public int wordSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boolValue(boolean value) {
		return value ? 1 : 0;
	}

	@Override
	public void assignLabel(String label) {
		manageLabelPosition(label);
	}

	@Override
	public void writeObjectFile(OutputStream outStream) throws IOException {
		// magic marker
		outStream.write((byte)0x4d);
		outStream.write((byte)0x4a);
		
		// code size
		Integer codeSize = code.position();
		ByteBuffer bbCodeSize = ByteBuffer.allocate(4);
		bbCodeSize.putInt(codeSize);
		outStream.write(bbCodeSize.array());
		
		// data size
		ByteBuffer bbDataSize = ByteBuffer.allocate(4);
		bbDataSize.putInt(dataSize);
		outStream.write(bbDataSize.array());
		
		// start PC
		ByteBuffer bbStartPC = ByteBuffer.allocate(4);
		bbStartPC.putInt(startPC);
		outStream.write(bbStartPC.array());
		
		// code
		byte[] byteCode = new byte[codeSize];
		code.flip();
		code.get(byteCode, 0, codeSize);
		outStream.write(byteCode);
		
		// data		
		for (int i = 0; i < data.position(); i++) {
			byte[] bytes = ByteBuffer.allocate(4).putInt(data.get(i)).array();
			outStream.write(bytes);
		}		
	}

	@Override
	public int allocStaticData(int words) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int allocStringConstant(String string) {
		int position = data.position();
		int length = string.length();
		int stringSize;
		
		if (length % 4 == 0) {
			stringSize = length/4 + 1;
		}
		else { 
			stringSize = (length+3)/4;
		}
		
		dataSize += stringSize;

		byte[] bytes = new byte[stringSize*4];

		for (int i = 0; i < length; i++) 
			bytes[i] = string.getBytes()[i];
		
		for (int i = length; i < stringSize*4; i++)
			bytes[i] = (byte)0x00;
		
		IntBuffer temp = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
		data.put(temp);
		
		return position;
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
		// TODO Auto-gallocStringConstantenerated method stub

	}

	@Override
	public void loadConst(int value) {
		code.put(ICode.CONST);
		
		Integer val = value;
		
		code.putInt(val);
	}

	@Override
	public void loadWord(MemoryRegion region, int offset) {
		if (region == MemoryRegion.STACK) {
			code.put(ICode.LOAD);
			
			Integer off = offset;
			code.put(off.byteValue());
		}
	}

	@Override
	public void storeWord(MemoryRegion region, int offset) {
		if (region == MemoryRegion.STACK) {
			code.put(ICode.STORE);
			
			Integer off = offset;
			code.put(off.byteValue());
		}
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
		code.put(ICode.CONST0);
		code.put(ICode.PRINT);
	}

	@Override
	public void writeString(int addr) {
		code.put(ICode.SPRINT);
		Integer i = addr;
		code.putShort(i.shortValue());
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
	
	/*
	 * Checks if label is known already. 
	 * If address of label is known, puts address into code buffer, 
	 * otherwise adds label to backpatching list
	 * @param label 	The label for which the address needs to be determined
	 */
	private void putAddress(String label) {
		if (labelPosition.containsKey(label)) {
			short pos = labelPosition.get(label);
			code.putShort(pos);
		} else {
			addBackPatching(label, code.position());
		}
	}

	@Override
	public void branchIf(boolean value, String label) {
		int intVal = value ? 1 : 0;
		loadConst(intVal);
		
		code.put(ICode.JEQ);

		putAddress(label);
	}
	
	private void addBackPatching(String label, int pos) {
		ArrayList<Integer> positions;
		
		if (backPatching.containsKey(label)) {
			positions = backPatching.get(label);
		} else {
			positions = new ArrayList<Integer>();
		}
		
		positions.add(pos);
		backPatching.put(label, positions);
		
		code.putShort(ICode.TEMP);
	}

	@Override
	public void jump(String label) {
		code.put(ICode.JMP);
		
		putAddress(label);
	}

	@Override
	public void callProc(String label) {
		code.put(ICode.CALL);
		
		putAddress(label);
	}
	
	/*
	 * Checks if this label is in the back patching list.
	 * If true, performs back patching and removes label from back patching list.
	 * @param label 	The label for which the back patching needs to be checked
	 */
	private void manageLabelPosition(String label) {
		labelPosition.put(label, (short)code.position());
		
		if (backPatching.containsKey(label)) {
			
			ArrayList<Integer> positions = backPatching.get(label);
			
			int position = code.position();
			
			for (Integer pos : positions) {
				code.position(pos);
				code.putShort((short)position);
			}
			
			code.position(position);
			backPatching.remove(label);
		}
	}

	@Override
	public void enterProc(String label, int nParams, boolean main) {
		if (main) {
			startPC = code.position();
		}
		
		Integer params = nParams;

		manageLabelPosition(label);
		
		code.put(ICode.ENTER);
		code.put(params.byteValue());	// nparams
		code.put(params.byteValue()); // framesize
	}

	@Override
	public void exitProc(String label) {
		manageLabelPosition(label);

		code.put(ICode.EXIT);
		code.put(ICode.RETURN);
	}

	@Override
	public int paramOffset(int index) {
		// TODO implement correctly
		return index;
	}

}
