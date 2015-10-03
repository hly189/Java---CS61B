/* OpenCommercial.java */

import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;
    URL url; 
    

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();

    url = new URL("http://www." + inputLine +".com"); 
    InputStream is = url.openStream();
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader readUrl = new BufferedReader(isr);

    String[] line =  new String[5];
    String newLine = System.getProperty("line.separator");

    for (int i = 0; i < 5; i++ ){
      line[i] = readUrl.readLine();
    }
    
    System.out.println(line[4] + newLine + line[3] + newLine + line[2] + newLine + line[1] + newLine + line[0]);

   
  }
}
