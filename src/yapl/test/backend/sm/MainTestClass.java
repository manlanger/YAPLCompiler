package yapl.test.backend.sm;

import java.io.IOException;

public class MainTestClass {

	public static void main(String[] args) throws IOException {
		Test1 t1 = new Test1();
		Test2 t2 = new Test2();
		Test3 t3 = new Test3();
		Test4 t4 = new Test4();
		Test5 t5 = new Test5();
		Test6 t6 = new Test6();
		t1.main(new String[] {"C:\\Users\\Manuel\\Desktop\\t1.txt"});
		t2.main(new String[] {"C:\\Users\\Manuel\\Desktop\\t2.txt"});
		t3.main(new String[] {"C:\\Users\\Manuel\\Desktop\\t3.txt"});
		t4.main(new String[] {"C:\\Users\\Manuel\\Desktop\\t4.txt"});
		t5.main(new String[] {"C:\\Users\\Manuel\\Desktop\\t5.txt"});
		t6.main(new String[] {"C:\\Users\\Manuel\\Desktop\\t6.txt"});

	}

}
