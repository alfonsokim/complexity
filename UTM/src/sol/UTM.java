package sol;

import java.io.PrintStream;

public class UTM
{
	public static String NewTape(String TT, String Tape, int N, int P)
	{
		int transicionCounter = 0;
		int nextState = 0;
		int tapeLength = Tape.length();
		String leftSide = Tape.substring(0, P);

		int leftSideLength = leftSide.length();
		String rigthSide = Tape.substring(P + 1);
		int rigthSideLength = rigthSide.length();
		
		for (int transition = 1; transition <= N; transition++) {
			int currentSymbol = Integer.parseInt(Tape.substring(P, P + 1));
			int moveIdx = nextState * 16 + currentSymbol * 8;
			String moveStr = TT.substring(moveIdx, moveIdx + 1);
			int move = Integer.parseInt(TT.substring(moveIdx + 1, moveIdx + 2));
			
			// Validacion de limites de la cinta
			if (move == 0) {
				P++;
				if (P == tapeLength) {
					System.out.println("\nRight limit of tape exceeded");
					return "";
				}
				leftSide = leftSide + moveStr;
				leftSideLength++;
				Tape = leftSide + rigthSide;
				rigthSideLength--;
				rigthSide = rigthSide.substring(1);
			} else {
				P--;
				if (P < 0) {
					System.out.println("\nLeft limit of tape exceeded");
					return "";
				}
				rigthSide = moveStr + rigthSide;
				rigthSideLength++;
				Tape = leftSide + rigthSide;
				leftSideLength--;
				leftSide = leftSide.substring(0, leftSideLength);
			}
			
			nextState = 0;
			for (int nextStateCnt = moveIdx + 2; nextStateCnt < moveIdx + 8; nextStateCnt++) {
				nextState *= 2;
				if (TT.substring(nextStateCnt, nextStateCnt + 1).equals("1")) {
					nextState++;
				}
			}
			if (nextState == 63) {
				System.out.println("\n\nHALT state was reached");
				System.out.printf("%10.0f transitions were performed\n", new Object[] { Float.valueOf(transition) });
				long productivity = 0L;
				
				for (int oneCounter = 0; oneCounter < Tape.length(); oneCounter++) {
					if (Tape.substring(oneCounter, oneCounter + 1).equals("1")) {
						productivity += 1L;
					}
				}
				
				System.out.println("The productivity of this machine is " + productivity);
				return Tape;
			}
			transicionCounter++;
			if (transicionCounter == 10000) {
				//System.out.print("\b\b\b\b\b\b\b\b\b\b");
				System.out.print("\n" + transition);
				transicionCounter = 0;
			}
		}
		System.out.println("\nMaximum number of transitions was reached");
		return Tape;
	}
}