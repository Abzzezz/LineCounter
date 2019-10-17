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
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(null);
        File input = jFileChooser.getCurrentDirectory();
        int count = 0;
        int countTotal = 0;
        for (File listFile : FileUtils.listFiles(input, new String[]{"java", "txt"}, true)) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(listFile));
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    if(!line.isEmpty()) {
                        count++;
                    }
                }
                System.out.println("File " + listFile.getName() + " Lines:" + count);
                bufferedReader.close();
                countTotal += count;
                count = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Lines in all files combine: " + countTotal);
    }
}
