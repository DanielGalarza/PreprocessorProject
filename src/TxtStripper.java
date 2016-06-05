/**
 * @author DANIEL GALARZA
 *
 */

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class TxtStripper {

    static String OUTPUT_FILE = "TempOutput.txt";        // The name of the temporary file to write to.
    static String FINAL_OUTPUT_FILE = "FinalOutput.txt";        // The name of a FINAL output file to write to.

    static String fileContentsNoComments = "";
    static String fileContentsWithComments = "";

    public TxtStripper(String inputFile, JTextArea textArea) {
        stripAll(inputFile, OUTPUT_FILE,  FINAL_OUTPUT_FILE, textArea);
    }

    /****************************************************************************
     * THIS METHOD REMOVES ALL BLANK LINES (WITH SPACES, TABS, EMPTY LINE ETC.),
     * SINGLE COMMENT LINES, AND BLOCK COMMENTS.
     *
     * @param inputFile         NAME OF THE INPUT FILE TO READ FROM.
     * @param outputFile        NAME OF THE TEMPORARY OUTPUT FILE TO WRITE TO.
     * @param finalOutputFile   NAME OF THE FILE TO WRITE FINAL OUTPUT TO.
     */

    public static void stripAll(String inputFile, String outputFile, String finalOutputFile, JTextArea textArea) {

        //End of substring.
        int endIndex = -1;

        //Input line.
        String line;

        //toggles.
        boolean isNotBlockComment = true;
        boolean isNotEmbeddedComment = true;

        //Generic counter
        //int counter = 0;

        try {

            //Scanner reads the input file
            Scanner input = new Scanner(new File(inputFile));



            //Writes to the temporary output file
            PrintWriter output = new PrintWriter(new File(outputFile));

            while (input.hasNext()) {

                line = input.nextLine();
                //fileContentsWithComments += line + "\n";

                //counter++;
                //System.out.println("line: " + counter);

                /****************************************************
                 *  REMOVES ALL BLOCK COMMENTS FROM THE FILE.
                 * **************************************************/

                if (line.contains("/*") && !line.contains("*/")) {

                    isNotBlockComment = !isNotBlockComment;
                    isNotEmbeddedComment = ! isNotEmbeddedComment;

                    System.out.println("Beginning of a block comment (multp. lines)");

                    //offset from the beginning of the String and the comment on the same line.
                    int offset = line.indexOf("/*");

                    if (offset != endIndex) {

                        //Removing the comment from the line.
                        line = line.substring(0, offset);

                        //Gets rid a comment line without textArea.
                        if (line.isEmpty()) {

                            System.out.println("Removing blank line");
                        }

                        else {
                            output.println(line);
                            fileContentsNoComments += line + "\n";
                        }
                    }
                }

                else if (line.contains("*/")) {

                    isNotBlockComment = !isNotBlockComment;
                    isNotEmbeddedComment = ! isNotEmbeddedComment;

                    System.out.println("End of a block comment (multp. lines)");

                    //offsetEnd is the offset starting from "*/" to the end of the string.
                    int offsetEnd = line.indexOf("*/");

                    if (offsetEnd != endIndex) {

                        //Removing the comment from the line.
                        line = line.substring(offsetEnd + 2, line.length());

                        //Gets rid a comment line without textArea.
                        if (line.isEmpty()) {

                            System.out.println("Removing blank line");
                        }

                        else {
                            output.println(line);
                            fileContentsNoComments += line + "\n";
                        }
                    }
                }

                /****************************************************
                 *  REMOVES ALL BLOCK COMMENTS THAT ARE ON ONE LINE
                 *  FROM THE FILE.
                 * **************************************************/

                else if (line.contains("/*") && line.contains("*/")) {

                    System.out.println("Removing block comment on one line");

                    //offset from beginning of the String and the comment on the same line.
                    int offset = line.indexOf("/*");

                    if (offset != 0) {

                        //Removing the comment from the line.
                        line = line.substring(0, offset) + line.substring(line.indexOf("*/") + 2, line.length());

                        //Gets rid a comment line without textArea.
                        if (line.isEmpty()) {

                            System.out.println("Removing blank line");
                        }

                        else {
                            output.println(line);
                            fileContentsNoComments += line + "\n";
                        }
                    }

                    else if(offset == 0) {

                        int endComment = line.indexOf("*/");

                        //Removing the comment from the line.
                        line = line.substring(endComment + 2, line.length());

                        //Gets rid a comment line without textArea.
                        if (line.isEmpty()) {

                            System.out.println("Removing blank line");
                        }

                        else {
                            output.println(line);
                            fileContentsNoComments += line + "\n";
                        }
                    }
                }

                /****************************************************
                 *  REMOVES ALL SINGLE LINE COMMENTS FROM THE FILE.
                 * **************************************************/

                else if (line.contains("//") &&  isNotEmbeddedComment) {

                    System.out.print("Removing standard comments\n");

                    //offset from beginning of the String and the comment on the same line.
                    int offset = line.indexOf("//");

                    if (offset != endIndex) {

                        //Removing the comment from the line.
                        line = line.substring(0, offset);

                        //Gets rid a comment line without textArea.
                        if (line.isEmpty()) {

                            System.out.println("Removing blank line");
                        }

                        else {
                            output.println(line);
                            fileContentsNoComments += line + "\n";
                        }
                    }
                }

                /****************************************************
                 *  PRINTS EVERYTHING ELSE THAT IS NOT A SINGLE LINE
                 *  COMMENT,BLOCK COMMENT, OR AN EMPTY LINE
                 *  (BLANK LINES, LINES WITH SPACES ONLY, LINES WITH
                 *  TABS ONLY, OR COMBINATION).
                 * **************************************************/

                else if (isNotBlockComment) {
                    output.println(line);
                    fileContentsNoComments += line + "\n";
                }

            }//End of while

            //Closing the first input file and temporary output file
            input.close();
            output.close();

            textArea.setText(fileContentsNoComments);

    /*
        /****************************************************
         *  REMOVES ALL EMPTY LINES/ BLANK LINES
         *  (THAT MAY CONTAIN SPACES OR TABS) FROM THE FILE.
         * **************************************************

        //Starting to remove blank lines
        Scanner newInput = new Scanner(new File(outputFile));
        PrintWriter outputFinal = new PrintWriter(new File(finalOutputFile));

        //Running through the temporary file to remove empty lines and print to final output file.
        while (newInput.hasNext()) {

            line = newInput.nextLine();

            stripBlanks(line, outputFinal);
        }

        //Closing the input file (temp output file) and the final output file
        newInput.close();
        outputFinal.close(); */

        }//End of try

        catch (FileNotFoundException ex) {


            System.out.println("cannot open file '" + inputFile + "' or '"
                    + outputFile + "' or '" + finalOutputFile);
        }

    } //End of stripAll


    /****************************************************************************
     * THIS METHOD REMOVES ALL BLANK LINES (WITH SPACES, TABS, EMPTY LINE ETC.)
     *
     * @param line          A LINE FROM THE INPUT FILE TO CHECK
     * @param output        OUTPUT FILE TO PRINT FINAL OUTPUT TO.
     */

    public static void stripBlanks(String line, PrintWriter output) {

    /*  int counter = 0;

        counter++;

        System.out.println("line: " + counter); */

        //To break the String "line" in to an array of chars.
        char [] chrs;

        if (line.contains(" ") || line.contains("\t")) {

            chrs = line.toCharArray();

            if(chrs[0] == ' ') {
                System.out.println("starts with a space");

                for(int i = 0; i < chrs.length; i ++) {

                    if (chrs[i] == ' ') {
                        //Skips line
                        System.out.println("Another Space..");
                    }

                    else if (chrs[i] == '\t') {
                        //Skips line
                        System.out.println("A tab..");
                    }

                    else {
                        System.out.println("break");
                        output.println(line);
                        break;
                    }
                }
            }//End of if statement

            else if (chrs [0] == '\t') {
                System.out.println("starts with a tab");

                for(int i = 0; i < chrs.length; i ++) {

                    if (chrs[i] == ' ') {
                        System.out.println("Another Space..");
                    }

                    else if (chrs[i] == '\t') {
                        System.out.println("A tab..");
                    }

                    else {
                        System.out.println("break");
                        //return line;
                        output.println(line);
                        break;
                    }
                }
            }//End of else-if statement

            else
                output.println(line);

        }//End of if statement

        else if (line.isEmpty()) {

            //skips empty lines
            System.out.print("Removing empty line\n");
        }

        else
            output.println(line);

    }//End of stripBlanks
}//End of TxtStripper

