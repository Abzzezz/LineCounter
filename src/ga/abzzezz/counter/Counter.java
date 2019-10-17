package ga.abzzezz.counter;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;

public class Counter {

    public static void main(String[] args) {
        Counter c = new Counter();
        c.getFolder();
    }


    public void getFolder() {
        /*
        Get the current Folder to count the lines
         */
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(null);
        File input = jFileChooser.getCurrentDirectory();

        /*
        Set two variables: count for the lines in the current file, linesTotal for the lines in the files * the files
         */
        int count = 0;
        int linesTotal = 0;
        /*
        Loop through all the files and check for certain file extensions(text files and .java files)
         */
        for (File listFile : FileUtils.listFiles(input, new String[]{"java", "txt"}, true)) {
            try {
                /*
                Initialise a new BufferedReader to count the lines and sort out empty lines.
                 */
                BufferedReader bufferedReader = new BufferedReader(new FileReader(listFile));
                String line;
                /*
                Create a while loop to loop read the file till @line is null
                 */
                while((line = bufferedReader.readLine()) != null) {
                    /*
                    If the line is not empty set (int) count + 1
                     */
                    if(!line.isEmpty()) count++;
                }
                /*
                Print the FileName and the lines in file.
                 */
                System.out.println("File " + listFile.getName() + " Lines:" + count);
                /*
                Close the buffered reader
                 */
                bufferedReader.close();
                /*
                Add the value of count to linesTotal then reset count to 0 to read the next file.
                 */
                linesTotal += count;
                count = 0;
                /*
                If something fails throw an exception and print the reason
                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        Print the value of linesTotal in red
         */
        System.err.println("Lines in all files combine: " + linesTotal);
    }
}
