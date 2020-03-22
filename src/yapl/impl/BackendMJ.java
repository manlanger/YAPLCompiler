package yapl.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
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
	private Map<String, Integer> enterPosition;
	private Map<String, Integer> exitPosition;
	
	public BackendMJ() {
		code = ByteBuffer.allocate(100);
		data = IntBuffer.allocate(100);
		
		startPC = 0;
		dataSize = 0;
		
		enterPosition = new HashMap<String, Integer>();
		exitPosition = new HashMap<String, Integer>();
	}
	
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
		
		if (length % 4 == 0) {
			dataSize += length/4 + 1;
		}
		else { 
			dataSize += (length+3)/4;
		}

		byte[] bytes = new byte[dataSize*4];

		for (int i = 0; i < length; i++) 
			bytes[i] = string.getBytes()[i];
		
		for (int i = length; i < dataSize*4; i++)
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
		if (main) {
			startPC = code.position();
		}
		
		enterPosition.put(label, code.position());
		
		Integer params = nParams;
		
		code.put(ICode.ENTER);
		code.put(params.byteValue());	// nparams
		code.put(params.byteValue()); // framesize
	}

	@Override
	public void exitProc(String label) {
		exitPosition.put(label, code.position());

		code.put(ICode.EXIT);
		code.put(ICode.RETURN);
	}

	@Override
	public int paramOffset(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

}
