import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Daniel Galarza
 *
 * THIS CLASS USES TWO INPUT FILES (THE FILES THAT YOU WANT TO COMPARE)
 * AND COMPARES THEM, LINE BY LINE, TO CHECK WHETHER OR NOT THEY ARE THE SAME.
 */

public class Verifier {

    static String fileOne = "expectedOutput.txt";
    static String fileTwo = "FinalOutput.txt";
    static boolean isTheSame = true;

    public static void main(String [] args) {

        verify();
    }

    public static void verify() {

        short counter = 0;

        try {

            //Scanner reads the input file
            Scanner file1 = new Scanner(new File(fileOne));

            //Scanner reads the input file
            Scanner file2 = new Scanner (new File(fileTwo));



            while(file1.hasNext() && file2.hasNext()) {

                String lineOne = file1.nextLine();
                String lineTwo = file2.nextLine();

                if(lineOne.equals(lineTwo)) {

                    System.out.println("line #: " + counter + "   same");
                }

                else {
                    System.out.println("line #: " + counter + "   not the same");
                    isTheSame = false;
                }

                counter++;
            }

            System.out.println("Are both files the same?: " + isTheSame);

            file1.close();
            file2.close();
        }

        catch(FileNotFoundException ex) {
            System.out.println("");
        }
    }
}