import java.io.*;

class Nuke2 {
	public static void main(String[] arg) throws Exception{
		BufferedReader keyboard;
		String input; 
		String output; 

		keyboard = new BufferedReader(new InputStreamReader(System.in));

		input = keyboard.readLine(); 
		output = input.substring(0, 1) + input.substring(2);

		System.out.println(output);
	}
}