public class EchoEvenLengthArgs {
	public static void main (String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: java EchoEvenLengthArgs <1 or more strings>");
		}
		for (int i = 0; i < args.length; i++) {
			if (hasEvenLength(args[i])) {
				System.out.printf(args[i] + " ");
			}
		}
	}

	public static boolean hasEvenLength(String str) {
		return (str.length() % 2 == 0);
	}
}
