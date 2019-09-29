
public class TinyPL {

	public static void main(String[] args) {
		System.out.println("Enter program and terminate with 'end'.\n");

		Lexer.initialize();
		Lexer.lex(); // scan one token at the very start

		new Program(); // parse the entire user's program

		System.out.println("\nStarting TinyPL interpreter, enter 'exit;' to end session ...\n");
		while (true) {
			try {
				System.out.print("Enter a call, end with semi-colon: ");

				SymTab.initialize(); // initialize for every function parsed
				ByteCode.initialize(); // initialize for every function parsed
				Lexer.initialize(); // initialize Lexer again

				Lexer.lex();
				Top_level_call t = new Top_level_call();

				if (t.done) {
					System.out.println("\n... good bye, thanks for using Tiny PL!");
					return;
				} else
					Interpreter.go();
			} catch (Exception e) {
				System.out.println("Syntax or other error, please re-enter.");
			}
		}
	}
}

class Top_level_call { // top_level_call -> id ‘(‘ [ int_lit { , int_lit } ] ‘)’ ‘;’

	boolean done = false;
	String fname = Lexer.ident;
	Funcall f;

	public Top_level_call() {

		if (fname.equals("exit")) {
			done = true;
		} else {
			Lexer.lex();
			f = new Funcall(fname);
			// missing code: to be supplied by you
			ByteCode.gen_return();
			ByteCode.output("top_level_call_");
			Interpreter.initialize("top_level_call_" + Interpreter.funptr, 0, 0, ByteCode.code, ByteCode.arg,
					ByteCode.codeptr);
			done = false;
		}
	}
}
