package yapl.lib;

import yapl.Token;
import yapl.interfaces.CompilerError;

public class YAPLException extends Throwable implements CompilerError {

	private int errorNumber = -1;
	private int line = -1;
	private int column = -1;
	private String message = null;
	
	public YAPLException(String message) {
		this.message = message;
	}
	
	public YAPLException(int errorNumber) {
		this.errorNumber = errorNumber;
	}
	
	public YAPLException(String message, int errorNumber, Token token) {
		this.message = message;
		this.errorNumber = errorNumber;
		
		if (token != null) {
			this.line = token.beginLine;
			this.column = token.beginColumn;
		}
	}
	
	@Override
	public int errorNumber() {
		return errorNumber;
	}

	@Override
	public int line() {
		return line;
	}

	@Override
	public int column() {
		return column;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
