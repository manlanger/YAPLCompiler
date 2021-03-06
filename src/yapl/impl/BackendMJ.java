package yapl.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.*;

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
    private Stack<Integer> currentMethodStack;
    private int backPatchPos = 0;
    private int numberWords = 0;
    private List<Integer> arrayDimDescriptor;

    public BackendMJ() {
        code = ByteBuffer.allocate(800);
        data = IntBuffer.allocate(400);

        startPC = 0;
        dataSize = 0;
        currentMethodStack = new Stack<>();
        labelPosition = new HashMap<String, Short>();
        backPatching = new HashMap<String, ArrayList<Integer>>();
        arrayDimDescriptor = new ArrayList<>();
        // add predefined procedures
        enterProc("writeln", 0, false);
        int addr = allocStringConstant("\n");
        writeString(addr);
        exitProc("writeln");
        
        enterProc("writeint", 1, false);
        loadWord(MemoryRegion.STACK, paramOffset(0));
        writeInteger();
        exitProc("writeint");
        
        enterProc("writebool", 1, false);
        loadWord(MemoryRegion.STACK, paramOffset(0));
        int true_addr = allocStringConstant("True");
		int false_addr = allocStringConstant("False");
		// if true is on the stack, jump to writebool_true and write string on true_addr
		branchIf(true, "writebool_true");
		writeString(false_addr);
		jump("proc_end");
		assignLabel("writebool_true");
		writeString(true_addr);
		assignLabel("proc_end");
        exitProc("writebool");
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
        outStream.write((byte) 0x4d);
        outStream.write((byte) 0x4a);

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
        int position = data.position();
        int length = 4 * words;
        byte[] bytes = new byte[length];
        dataSize += words;
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) 0x00;
        }

        IntBuffer temp = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        data.put(temp);
        return position;
    }

    @Override
    public int allocStringConstant(String string) {
        int position = data.position();
        int length = string.length();
        int stringSize;

        if (length % 4 == 0) {
            stringSize = length / 4 + 1;
        } else {
            stringSize = (length + 3) / 4;
        }

        dataSize += stringSize;

        byte[] bytes = new byte[stringSize * 4];

        for (int i = 0; i < length; i++)
            bytes[i] = string.getBytes()[i];

        for (int i = length; i < stringSize * 4; i++)
            bytes[i] = (byte) 0x00;

        IntBuffer temp = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        data.put(temp);

        return position;
    }

    @Override
    public int allocStack(int words) {

        int position = currentMethodStack.peek();
        for (int i = 0; i < words; i++) {
            Integer currentPs = position + i;
            code.put(ICode.CONST0);
            code.put(ICode.STORE);
            code.put(currentPs.byteValue());
        }
        
        this.numberWords += words;
        currentMethodStack.set(0, position + words);
        return position;
    }

    @Override
    public void allocHeap(int words) {
        Integer size = words;
        code.put(ICode.ALLOC_HEAP);
        code.putShort(size.shortValue());

    }

    @Override
    public void storeArrayDim(int dim) {
        // TODO storeArrayDim: For multi dimensional array only ignore for CA1
        arrayDimDescriptor.add(dim);
    }

    @Override
    public void allocArray() {
        code.put(ICode.ALLOC_ARRAY);
        // Allocated Word Array as default
        code.put(ICode.ONE);
        // TODO Auto-gallocStringConstantenerated method stub
        //arrayDimDescriptor.clear();
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
        } else if (region == MemoryRegion.STATIC) {
            code.put(ICode.GET_STATIC);
            Integer off = offset;
            code.putShort(off.shortValue());
            /*byte[] bytes = ByteBuffer.allocate(2).putShort(off.shortValue()).order(ByteOrder.BIG_ENDIAN).array();
            for (byte cByte : bytes) {
                code.put(cByte);
            }*/

        } else if (region == MemoryRegion.HEAP) {
            code.put(ICode.GET_FIELD);
            Integer off = offset;
            code.putShort(off.shortValue());
        }
    }

    @Override
    public void storeWord(MemoryRegion region, int offset) {
        if (region == MemoryRegion.STACK) {
            code.put(ICode.STORE);

            Integer off = offset;
            code.put(off.byteValue());
        } else if (region == MemoryRegion.STATIC) {
            code.put(ICode.PUT_STATIC);
            Integer off = offset;
            code.putShort(off.shortValue());
            /*byte[] bytes = ByteBuffer.allocate(2).putShort(off.shortValue()).order(ByteOrder.BIG_ENDIAN).array();
            for (byte cByte : bytes) {
                code.put(cByte);
            }*/
        } else if (region == MemoryRegion.HEAP) {
            code.put(ICode.PUT_FIELD);
            Integer off = offset;
            code.putShort(off.shortValue());
        }
    }

    @Override
    public void loadArrayElement() {
        code.put(ICode.LOAD_EL_ARRAY);

    }

    @Override
    public void storeArrayElement() {
        code.put(ICode.STORE_EL_ARRAY);

    }

    @Override
    public void arrayLength() {
        code.put(ICode.ARRAY_LENGTH);

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
        code.put(ICode.NEG);
    }

    @Override
    public void add() {
        code.put(ICode.ADD);
    }

    @Override
    public void sub() {
        code.put(ICode.SUB);
    }

    @Override
    public void mul() {
        code.put(ICode.MUL);
    }

    @Override
    public void div() {
       code.put(ICode.DIV);
    }

    @Override
    public void mod() {
        code.put(ICode.REM);
    }

    @Override
    public void and() {
        code.put(ICode.MUL);
    }

    @Override
    public void or() {
        code.put(ICode.ADD);
    }

    @Override
    public void isEqual() {
    	HandleComparison(ICode.JEQ);
    }

    @Override
    public void isLess() {
    	HandleComparison(ICode.JLT);
    }

    @Override
    public void isLessOrEqual() {
    	HandleComparison(ICode.JLE);
    }

    @Override
    public void isGreater() {
    	HandleComparison(ICode.JGT);
    }

    @Override
    public void isGreaterOrEqual() {
    	HandleComparison(ICode.JGE);
    }
    
    private void HandleComparison(byte compType) {
    	code.put(compType);
    	int iPos =  code.position();
    	putAddress("trueLabel" + iPos);
    	loadConst(0);
    	code.put(ICode.JMP);
    	putAddress("falseLabel" + iPos);
    	manageLabelPosition("trueLabel" + iPos);
    	loadConst(1);
    	manageLabelPosition("falseLabel" + iPos);
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
    	System.out.println("Backend callProc: " + label);
    	
        code.put(ICode.CALL);

        putAddress(label);
    }

    /*
     * Checks if this label is in the back patching list.
     * If true, performs back patching and removes label from back patching list.
     * @param label 	The label for which the back patching needs to be checked
     */
    private void manageLabelPosition(String label) {    	
        if (!labelPosition.containsKey(label)) {
        	short pos = (short)code.position();
        	labelPosition.put(label, pos);
        	System.out.println("Backend stored label " + label + " at " + pos);
        }

        if (backPatching.containsKey(label)) {

            ArrayList<Integer> positions = backPatching.get(label);

            int position = code.position();

            for (Integer pos : positions) {
                code.position(pos);
                code.putShort((short) position);
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
        List<Integer> procedureStack = new ArrayList<>();
        currentMethodStack.push(nParams);

        code.put(ICode.ENTER);
        code.put(params.byteValue());    // nparams
        
        backPatchPos = code.position();	// remember position for backpatching framesize later 
        numberWords = nParams;	// numberWords = numberParams + numberLocals (backpatched later)
        
        code.put((byte)nParams);    // framesize - temporary nParams, will be backpatched after exitProc
    }

    @Override
    public void exitProc(String label) {
        manageLabelPosition(label + "_end");
        
        System.out.println("Number of words (will be backpatched to pos " + backPatchPos + "): " + this.numberWords);
        
        int backupPos = code.position();	// remember current pos
        
        code.position(backPatchPos);		// jump to position of framesize
        code.put((byte)numberWords);		// and put correct number of words there
        
        code.position(backupPos);			// jump back to backuped position
        
        currentMethodStack.pop();
        code.put(ICode.EXIT);
        code.put(ICode.RETURN);
    }

    @Override
    public int paramOffset(int index) {
       return index;
    }

}
