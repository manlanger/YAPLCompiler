package yapl.interfaces;

public interface ICode {
	public static final byte ZERO	= (byte)0x00;
	public static final byte LOAD	= (byte)0x01;
	public static final byte CONST0	= (byte)0x0F;
	public static final byte CONST	= (byte)0x16;
	public static final byte CALL	= (byte)0x2E;
	public static final byte RETURN = (byte)0x2F;
	public static final byte ENTER	= (byte)0x30;
	public static final byte EXIT	= (byte)0x31;
	public static final byte PRINT	= (byte)0x33;
	public static final byte SPRINT = (byte)0x37;
}