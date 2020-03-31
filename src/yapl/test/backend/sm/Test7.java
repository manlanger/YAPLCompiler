package yapl.test.backend.sm;

import yapl.impl.BackendMJ;
import yapl.interfaces.BackendBinSM;

import java.io.FileOutputStream;
import java.io.IOException;

public class Test7 {
    /**
     * Usage: java yapl.test.backend.sm.Test8 object_file
     */
    public static void main(String[] args) throws IOException {
        BackendBinSM backend = new BackendMJ();
        int addrNewline = backend.allocStringConstant("\n");

        backend.enterProc("main", 0, true);
        //Test Division
        backend.loadConst(4);
        backend.loadConst(2);
        backend.div();

        //Test Modulo
        backend.loadConst(4);
        backend.loadConst(2);
        backend.mod();

        //Test Negation
        backend.loadConst(1);
        backend.neg();

        //Print Output
        backend.writeInteger(); // -1
        backend.writeString(addrNewline);
        backend.writeInteger(); // 0
        backend.writeString(addrNewline);
        backend.writeInteger(); // 2

        backend.exitProc("main_end");

        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
    }
}
