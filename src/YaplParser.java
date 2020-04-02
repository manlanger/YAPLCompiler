/* Generated By:JavaCC: Do not edit this line. YaplParser.java */
public class YaplParser implements YaplParserConstants {
        public static void main(String args []) throws ParseException
        {
        YaplParser parser = null;

            if(args.length == 0){
                System.out.println ("YaplParser: Reading input ...");
                parser = new YaplParser(System.in);
            }
            else if(args.length == 1){
                System.out.println ("YaplParser: Reading the file " + args[0] + " ..." );

                try {
                  parser = new YaplParser(new java.io.FileInputStream(args[0]));
                }

                catch(java.io.FileNotFoundException e) {
                        System.out.println ("ExampleParser: The file " + args[0] + " was not found.");
                        return;
                }
            }

                if (parser == null) {
                        System.out.println ("Parser initialization failed!");
                } else {
                        parser.Start();
                }
        }

/*
RelOp = "<" | "<=" | ">=" | ">" .
EqualOp = "==" | "!=" .
AddOp = "+" | "-" .
MulOp = "*" | "/" | "%" .
*/
  static final public void RelOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LESSTHAN:
      jj_consume_token(LESSTHAN);
      break;
    case 33:
      jj_consume_token(33);
      break;
    case 34:
      jj_consume_token(34);
      break;
    case GREATERTHAN:
      jj_consume_token(GREATERTHAN);
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void EqualOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 35:
      jj_consume_token(35);
      break;
    case 36:
      jj_consume_token(36);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void AddOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
      jj_consume_token(PLUS);
      break;
    case MINUS:
      jj_consume_token(MINUS);
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void MulOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MULT:
      jj_consume_token(MULT);
      break;
    case SLASH:
      jj_consume_token(SLASH);
      break;
    case PERC:
      jj_consume_token(PERC);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

/*
Literal = "True" | "False" | number .
Selector = ( "[" Expr "]" | "." ident ) [ Selector ] .
ArrayLen = "#" ident [ Selector ] .
*/
  static final public void Literal() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
      jj_consume_token(TRUE);
      break;
    case FALSE:
      jj_consume_token(FALSE);
      break;
    case NUMBER:
      jj_consume_token(NUMBER);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void Selector() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LEFTBRACKET:
      jj_consume_token(LEFTBRACKET);
      Expr();
      jj_consume_token(RIGHTBRACKET);
      break;
    case DOT:
      jj_consume_token(DOT);
      jj_consume_token(IDENT);
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DOT:
    case LEFTBRACKET:
      Selector();
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
  }

  static final public void ArrayLen() throws ParseException {
    jj_consume_token(HASH);
    jj_consume_token(IDENT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DOT:
    case LEFTBRACKET:
      Selector();
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
  }

/*
PrimaryExpr = Literal | "(" Expr ")" | ProcedureCall | ident [ Selector ] | ArrayLen .
UnaryExpr = [AddOp] PrimaryExpr .
MulExpr = UnaryExpr { MulOp UnaryExpr } .
AddExpr = MulExpr { AddOp MulExpr } .
RelExpr = AddExpr [ RelOp AddExpr ] .
*/
  static final public void PrimaryExpr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case NUMBER:
      Literal();
      break;
    case LEFTPAR:
      jj_consume_token(LEFTPAR);
      Expr();
      jj_consume_token(RIGHTPAR);
      break;
    default:
      jj_la1[9] = jj_gen;
      if (jj_2_1(2)) {
        ProcedureCall();
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENT:
          jj_consume_token(IDENT);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case DOT:
          case LEFTBRACKET:
            Selector();
            break;
          default:
            jj_la1[8] = jj_gen;
            ;
          }
          break;
        case HASH:
          ArrayLen();
          break;
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
  }

  static final public void UnaryExpr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
    case MINUS:
      AddOp();
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
    PrimaryExpr();
  }

  static final public void MulExpr() throws ParseException {
    UnaryExpr();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PERC:
      case MULT:
      case SLASH:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_1;
      }
      MulOp();
      UnaryExpr();
    }
  }

  static final public void AddExpr() throws ParseException {
    MulExpr();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_2;
      }
      AddOp();
      MulExpr();
    }
  }

  static final public void RelExpr() throws ParseException {
    AddExpr();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LESSTHAN:
    case GREATERTHAN:
    case 33:
    case 34:
      RelOp();
      AddExpr();
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
  }

/*
EqualExpr = RelExpr [ EqualOp RelExpr ] .
CondAndExpr = EqualExpr { "And" EqualExpr } .
CreationExpr = "new" NonArrayType { "[" Expr "]" } .
Expr = CondAndExpr { "Or" CondAndExpr } | CreationExpr .
*/
  static final public void EqualExpr() throws ParseException {
    RelExpr();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 35:
    case 36:
      EqualOp();
      RelExpr();
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
  }

  static final public void CondAndExpr() throws ParseException {
    EqualExpr();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[16] = jj_gen;
        break label_3;
      }
      jj_consume_token(AND);
      EqualExpr();
    }
  }

  static final public void CreationExpr() throws ParseException {
    jj_consume_token(NEW);
    NonArrayType();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LEFTBRACKET:
        ;
        break;
      default:
        jj_la1[17] = jj_gen;
        break label_4;
      }
      jj_consume_token(LEFTBRACKET);
      Expr();
      jj_consume_token(RIGHTBRACKET);
    }
  }

  static final public void Expr() throws ParseException {
    CondAndExpr();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OR:
    case NEW:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        label_5:
        while (true) {
          jj_consume_token(OR);
          CondAndExpr();
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case OR:
            ;
            break;
          default:
            jj_la1[18] = jj_gen;
            break label_5;
          }
        }
        break;
      case NEW:
        CreationExpr();
        break;
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[20] = jj_gen;
      ;
    }
  }

/*
ArgumentList = Expr { "," Expr } .
ProcedureCall = ident "(" [ ArgumentList ] ")" .
*/
  static final public void ArgumentList() throws ParseException {
    Expr();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[21] = jj_gen;
        break label_6;
      }
      jj_consume_token(COMMA);
      Expr();
    }
  }

  static final public void ProcedureCall() throws ParseException {
    jj_consume_token(IDENT);
    jj_consume_token(LEFTPAR);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case HASH:
    case LEFTPAR:
    case PLUS:
    case MINUS:
    case TRUE:
    case FALSE:
    case IDENT:
    case NUMBER:
      ArgumentList();
      break;
    default:
      jj_la1[22] = jj_gen;
      ;
    }
    jj_consume_token(RIGHTPAR);
  }

/*
NonArrayType = "int" | "bool" | ident .
*/
  static final public void NonArrayType() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      jj_consume_token(INT);
      break;
    case BOOL:
      jj_consume_token(BOOL);
      break;
    case IDENT:
      jj_consume_token(IDENT);
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void Start() throws ParseException {
                 Token t;
  do {
        t = getNextToken();
        System.out.println("Token: " + t.toString());
        } while (t.kind != EOF);
    jj_consume_token(0);
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_3R_7() {
    if (jj_scan_token(IDENT)) return true;
    if (jj_scan_token(LEFTPAR)) return true;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_7()) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public YaplParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[24];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x30000,0x0,0x2800,0x8480,0x83000000,0x44000,0x44000,0x44000,0x44000,0x83000100,0x40000040,0x2800,0x8480,0x2800,0x30000,0x0,0x200000,0x40000,0x100000,0x900000,0x900000,0x1000,0xc3002940,0x44400000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x6,0x18,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x6,0x18,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[1];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public YaplParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public YaplParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new YaplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public YaplParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new YaplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public YaplParser(YaplParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(YaplParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[37];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 24; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 37; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
