package ga.abzzezz.counter;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Counter extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Counter frame = new Counter();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
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
                getFolder(chckbxSubdirectories.isSelected(), chckbxjavaFiles.isSelected(), chckbxTextFiles.isSelected());
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
        assert input.listFiles() != null : "List file null";
        final List<File> files = new ArrayList<>();

        getSubDirectories(input, files);

        long size = files.stream().filter(file -> {
            final String[] extensions = new String[]{"java", "txt"};
            boolean doesExtend = false;
            for (final String extension : extensions) {
                if (file.getName().endsWith("." + extension)) {
                    doesExtend = true;
                    break;
                }
            }
            return doesExtend;
        }).map(file -> {
            try {
                return new BufferedReader(new FileReader(file)).lines().filter(s -> !s.isEmpty() && !s.startsWith("/")).collect(Collectors.toList());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).map(List::size).reduce(Integer::sum).get();

        /*
        Print the value of linesTotal in red
         */
        System.err.println("Lines in all files combine: " + size);

        if (displayLines) JOptionPane.showMessageDialog(null, "Lines in all files combine: " + size);
    }

    private void getSubDirectories(final File file, final List<File> files) {
        assert input.listFiles() != null : "List file sub dir null";
        for (final File listFile : file.listFiles()) {
            if (listFile.isDirectory()) {
                getSubDirectories(listFile, files);
            } else {
                files.add(listFile);
            }
        }
    }
}
