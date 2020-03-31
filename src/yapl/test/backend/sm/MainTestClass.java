package yapl.test.backend.sm;

import java.io.IOException;

public class MainTestClass {

	public static void main(String[] args) throws IOException {
		Test1.main(new String[] {generatePath("Test1.mj")});
		Test2.main(new String[] {generatePath("Test2.mj")});
		Test3.main(new String[] {generatePath("Test3.mj")});
		Test4.main(new String[] {generatePath("Test4.mj")});
		Test5.main(new String[] {generatePath("Test5.mj")});
		Test6.main(new String[] {generatePath("Test6.mj")});
		Test7.main(new String[] {generatePath("Test7.mj")});
		Test8.main(new String[] {generatePath("Test8.mj")});
	}

	private static String generatePath( String filename)
	{
		return System.getProperty("user.dir") + "\\src\\yapl\\test\\backend\\sm\\" + filename;
	}

}
