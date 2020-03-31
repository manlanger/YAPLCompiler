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
		t1.main(new String[] {generatePath("Test1.mj")});
		t2.main(new String[] {generatePath("Test2.mj")});
		t3.main(new String[] {generatePath("Test3.mj")});
		t4.main(new String[] {generatePath("Test4.mj")});
		t5.main(new String[] {generatePath("Test5.mj")});
		t6.main(new String[] {generatePath("Test6.mj")});
		Test7.main(new String[]{generatePath("Test7.mj")});
		Test8.main(new String[] {generatePath("Test8.mj")});
	}

	private static String generatePath( String filename)
	{
		return System.getProperty("user.dir") + "\\src\\yapl\\test\\backend\\sm\\" + filename;
	}

}
