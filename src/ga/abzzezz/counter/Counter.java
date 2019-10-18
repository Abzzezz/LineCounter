package ga.abzzezz.counter;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Counter extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Counter frame = new Counter();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Counter() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JCheckBox chckbxSubdirectories = new JCheckBox("Subdirectories");
        chckbxSubdirectories.setBounds(337, 96, 97, 23);
        contentPane.add(chckbxSubdirectories);

        JCheckBox chckbxTextFiles = new JCheckBox("Exclude empty lines");
        chckbxTextFiles.setBounds(337, 122, 97, 23);
        contentPane.add(chckbxTextFiles);

        JCheckBox chckbxjavaFiles = new JCheckBox("Diplay lines");
        chckbxjavaFiles.setBounds(337, 148, 97, 23);
        contentPane.add(chckbxjavaFiles);

        JButton btnChooseDircetory = new JButton("Choose Dircetory");
        btnChooseDircetory.setBounds(0, 227, 89, 23);

        btnChooseDircetory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            /*
           Get the current Folder to count the lines
            */
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(null);
                input = jFileChooser.getCurrentDirectory();
                super.mouseClicked(e);
            }
        });
        contentPane.add(btnChooseDircetory);

        JButton btnScan = new JButton("Scan");
        btnScan.setBounds(165, 227, 89, 23);
        btnScan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getFolder(chckbxSubdirectories.isSelected(), chckbxjavaFiles.isSelected(), chckbxTextFiles.isSelected() );
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        contentPane.add(btnScan);
    }

    File input;

    public void getFolder(boolean subDirectory, boolean displayLines, boolean emptyLines) {

        /*
        Set two variables: count for the lines in the current file, linesTotal for the lines in the files * the files
         */
        int count = 0;
        int linesTotal = 0;
        /*
        Loop through all the files and check for certain file extensions(text files and .java files)
         */
        for (File listFile : FileUtils.listFiles(input, new String[]{"java", "txt"}, subDirectory)) {
            try {
                /*
                Initialise a new BufferedReader to count the lines and sort out empty lines.
                 */
                BufferedReader bufferedReader = new BufferedReader(new FileReader(listFile));
                String line;
                /*
                Create a while loop to loop read the file till @line is null
                 */
                while ((line = bufferedReader.readLine()) != null) {
                    /*
                    If the line is not empty set (int) count + 1
                     */
                   if(emptyLines) {
                       if (!line.isEmpty() && !line.startsWith("/*" ) && !line.startsWith("//") && !line.startsWith("*/")) {
                           count++;
                       }
                   } else {
                       if(!line.startsWith("/*" ) && !line.startsWith("//") && !line.startsWith("*/")) {
                           count++;
                       }
                   }
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

        if(displayLines) JOptionPane.showMessageDialog(null, "Lines in all files combine: " + linesTotal);
    }
}
