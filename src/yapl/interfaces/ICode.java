package yapl.interfaces;

public interface ICode {
    /*
     * byte constants
     */
    public static final byte ZERO = (byte) 0x00;
    public static final byte ONE = (byte) 0x01;
    public static final byte LOAD = (byte) 0x01;
    public static final byte STORE = (byte) 0x06;
    public static final byte CONST0 = (byte) 0x0F;
    public static final byte CONST = (byte) 0x16;
    public static final byte POP = (byte) 0x26;
    public static final byte JMP = (byte) 0x27;
    public static final byte JEQ = (byte) 0x28;
    public static final byte CALL = (byte) 0x2E;
    public static final byte RETURN = (byte) 0x2F;
    public static final byte ENTER = (byte) 0x30;
    public static final byte EXIT = (byte) 0x31;
    public static final byte PRINT = (byte) 0x33;
    public static final byte SPRINT = (byte) 0x37;
    public static final byte GET_STATIC = (byte) 0x0B;
    public static final byte PUT_STATIC = (byte) 0x0C;

    public static final byte ADD = (byte) 0x17;
    public static final byte SUB = (byte) 0x18;
    public static final byte MUL = (byte) 0x19;
    public static final byte LOAD_ZERO = (byte) 0x02;

    public static final byte ALLOC_ARRAY = (byte) 0x20;
    public static final byte LOAD_EL_ARRAY = (byte) 0x21;
    public static final byte STORE_EL_ARRAY = (byte) 0x22;
    public static final byte ARRAY_LENGTH = (byte) 0x25;

    public static final byte ALLOC_HEAP =(byte)0x1F;
    public static final byte GET_FIELD = (byte)0x0D;
    public static final byte PUT_FIELD = (byte)0x0E;

    /*
     * short constants
     */
    public static final short TEMP = (byte) 0xFFFF;
}
