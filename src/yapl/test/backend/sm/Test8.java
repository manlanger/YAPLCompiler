package yapl.test.backend.sm;

import java.io.FileOutputStream;
import java.io.IOException;

import yapl.impl.BackendMJ;
import yapl.interfaces.BackendBinSM;

public class Test8 
{

	/**
	 * Usage: java yapl.test.backend.sm.Test8 object_file
	 */
	public static void main(String[] args) throws IOException
	{
        BackendBinSM backend = new BackendMJ();
        int addrNewline = backend.allocStringConstant("\n");

        backend.enterProc("main", 0, true);
        
        // ___________________ isEqual _____________________
        // false
        backend.loadConst(7);
        backend.loadConst(8);
        backend.isEqual();
        backend.writeInteger();
        
        // true 
        backend.loadConst(9);
        backend.loadConst(9);
        backend.isEqual();
        backend.writeInteger();
        
        // false
        backend.loadConst(10);
        backend.loadConst(9);
        backend.isEqual();
        backend.writeInteger();
        
        // ___________________ isLess _____________________
        // true
        backend.loadConst(7);
        backend.loadConst(8);
        backend.isLess();
        backend.writeInteger();
        
        // false 
        backend.loadConst(9);
        backend.loadConst(9);
        backend.isLess();
        backend.writeInteger();
        
        // false
        backend.loadConst(10);
        backend.loadConst(9);
        backend.isLess();
        backend.writeInteger();
        
        // ___________________ isLessOrEqual _____________________
        // true
        backend.loadConst(7);
        backend.loadConst(8);
        backend.isLessOrEqual();
        backend.writeInteger();
        
        // true 
        backend.loadConst(9);
        backend.loadConst(9);
        backend.isLessOrEqual();
        backend.writeInteger();
        
        // false
        backend.loadConst(10);
        backend.loadConst(9);
        backend.isLessOrEqual();
        backend.writeInteger();
        
        // ___________________ isGreater _____________________
        // false
        backend.loadConst(7);
        backend.loadConst(8);
        backend.isGreater();
        backend.writeInteger();
        
        // false 
        backend.loadConst(9);
        backend.loadConst(9);
        backend.isGreater();
        backend.writeInteger();
        
        // true
        backend.loadConst(10);
        backend.loadConst(9);
        backend.isGreater();
        backend.writeInteger();
        
        // ___________________ isGreaterOrEqual _____________________
        // false
        backend.loadConst(7);
        backend.loadConst(8);
        backend.isGreaterOrEqual();
        backend.writeInteger();
        
        // true 
        backend.loadConst(9);
        backend.loadConst(9);
        backend.isGreaterOrEqual();
        backend.writeInteger();
        
        // true
        backend.loadConst(10);
        backend.loadConst(9);
        backend.isGreaterOrEqual();
        backend.writeInteger();
        
        backend.exitProc("main_end");
        
        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
	}

}
