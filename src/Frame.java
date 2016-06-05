import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;

/**
 * Created by danielgalarza on 6/4/16.
 */
public class Frame extends JFrame {

    public String text;
    public String fontF;

    public int fontD = Font.PLAIN;
    public int fontS = 14;

    public TxtStripper run;
    public AboutFrame aboutFrame;
    public HelpFrame helpFrame;

    public JPanel upperPane, bottomPane;

    public JLabel status;

    public JButton processButton;
    public JButton undoButton;

    public JMenuBar mainMenu;

    public JMenu file, edit, font, fontDecor, help;

    public JMenuItem fileNew, fileOpen,fileSave, fileSaveAs, fileExit;
    public JMenuItem editToggleEditTable;
    public JMenuItem fontArial, fontConsolas;
    public JMenuItem fontDecorPlain, fontDecorBold, fontDecorItalic;
    public JMenuItem helpHelp, helpAbout;

    public JTextArea textArea;

    public JScrollPane scroller;

    public JFileChooser fileChooser;

    public File currentFile;

    public Font orgFont;

    /*************** FRAME CONSTRUCTOR ****************/
    public Frame() {

        text = "";

        mainMenu = new JMenuBar();

        status = new JLabel(("Comment Stripper"));

        orgFont = new Font(fontF, fontD, fontD);

        processButton = new JButton("Remove Comments");
        undoButton = new JButton("Undo Comment Removal");

        file = new JMenu("File");
        edit = new JMenu("Edit");
        font = new JMenu("Font");
        fontDecor = new JMenu("Font Decoration");
        help = new JMenu("Help");

        fileNew = new JMenuItem("New");
        fileOpen = new JMenuItem("Open");
        fileSave = new JMenuItem("Save");
        fileSaveAs = new JMenuItem("Save As");
        fileExit = new JMenuItem("Exit");

        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileSaveAs);
        file.add(fileExit);

        editToggleEditTable = new JMenuItem("Toggle Editable State");

        edit.add(editToggleEditTable);

        fontArial = new JMenuItem("Arial");
        fontConsolas = new JMenuItem("Consolas");

        font.add(fontArial);
        font.add(fontConsolas);

        fontDecorPlain = new JMenuItem("Plain");
        fontDecorBold = new JMenuItem("Bold");
        fontDecorItalic = new JMenuItem("Italic");

        fontDecor.add(fontDecorPlain);
        fontDecor.add(fontDecorBold);
        fontDecor.add(fontDecorItalic);

        helpAbout = new JMenuItem("About..");
        helpHelp = new JMenuItem("Help..");

        help.add(helpAbout);
        help.add(helpHelp);

        mainMenu.add(file);
        mainMenu.add(edit);
        mainMenu.add(font);
        mainMenu.add(fontDecor);
        mainMenu.add(help);

        upperPane = new JPanel(new GridLayout(3, 2));
        upperPane.add(mainMenu);
        upperPane.add(processButton);
        upperPane.add(undoButton);

        add(upperPane, BorderLayout.NORTH);

        textArea = new JTextArea();
        //textArea.setFont(orgFont);
        textArea.setEditable(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scroller = new JScrollPane(textArea);

        bottomPane = new JPanel();
        bottomPane.add(status);

        add(scroller, BorderLayout.CENTER);
        add(bottomPane, BorderLayout.SOUTH);

        fileChooser = new JFileChooser();
        currentFile = new File("");

        fileNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new file?") == 0) {
                    textArea.setText("");
                    status.setText("New File");
                }
            }
        });

        fileOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Are you sure you trash your current work?") == 0) {
                    int openResult = fileChooser.showOpenDialog(null);

                    if(openResult == fileChooser.APPROVE_OPTION) {
                        openFile(fileChooser.getSelectedFile());
                    }
                }
                text = textArea.getText();
                System.out.print(currentFile.toPath().toString());
            }
        });

        fileSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentFile != null) {
                    int saveResult = fileChooser.showSaveDialog(null);

                    if(saveResult == fileChooser.APPROVE_OPTION) {
                        try {
                            saveFile(fileChooser.getSelectedFile(), textArea.getText());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        try {
                            saveFile(currentFile, textArea.getText());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        status.setText("File Saved!");
                    }
                }
            }
        });

        fileSaveAs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int saveResult = fileChooser.showSaveDialog(null);
                try {
                    saveFile(fileChooser.getSelectedFile(), textArea.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                status.setText("File Saved!");
            }
        });

        fileExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Are you sure you want o exit without saving?") == 0) {
                    closeWindow();
                }
            }
        });

        editToggleEditTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()) {
                    textArea.setEditable(false);
                    status.setText("Text area is no longer editable");
                } else if(!textArea.isEditable()) {
                    textArea.setEditable(true);
                    status.setText("Text area is editable");
                }
            }
        });

        fontArial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontF = "Arial";
                textArea.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font set to Arial");
            }
        });

        fontConsolas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontF = "Consolas";
                textArea.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font set to Consolas");
            }
        });

        fontDecorPlain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontD = Font.PLAIN;
                textArea.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decor set to plain");
            }
        });

        fontDecorBold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontD = Font.BOLD;
                textArea.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decor set to bold");
            }
        });

        fontDecorItalic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontD = Font.ITALIC;
                textArea.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decor set to italic");
            }
        });

        helpAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutFrame = new AboutFrame();
                aboutFrame.setSize(600, 300);
                aboutFrame.setPreferredSize(new Dimension(800, 600));
                aboutFrame.setLocationRelativeTo(null);
                aboutFrame.setVisible(true);
            }
        });

        helpHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpFrame = new HelpFrame();
                helpFrame.setSize(600, 300);
                helpFrame.setPreferredSize(new Dimension(800, 600));
                helpFrame.setLocationRelativeTo(null);
                helpFrame.setVisible(true);
            }
        });

/* TODO fix process button so that it doesn't duplicate the text in the text area. */
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text = textArea.getText();
                textArea.setText("");
                run = new TxtStripper(currentFile.toPath().toString(), textArea);
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(text);
            }
        });


        setTitle("Comment Stripper");
        setSize(800, 600);
        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * THIS METHOD PROVIDES THE FRAME WITH THE MEANS OF ACCESSNG A FILE
     * FROM THE USER'S COMPUTER.
     *
     * @param file  THE FILE WE WANT TO OPEN.
     */
    public void openFile(File file) {
        //should work with .java file
        if(file.canRead()) {
            String filePath = file.getPath();
            String fileContents = "";

            //if(filePath.endsWith(".txt")) {
            try {
                Scanner scanner = new Scanner(new FileInputStream(file));

                while (scanner.hasNextLine()) {
                    fileContents += scanner.nextLine() + "\n";
                }

                scanner.close();

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File Not Found");
                System.out.println("File Not Found!");
            }

            textArea.setText(fileContents);
            setTitle("Comment Stripper");
            currentFile = file;
            //} else {
            // JOptionPane
            //}
        } else JOptionPane.showMessageDialog(null, "Cannot Read File");
    }

    /**
     * THIS METHOD PROVIDES THE MEANS FOR A USER TO SAVE A FILE, OVERWRITING
     * THE OLD ONE IN THE DIRECTORY
     *
     * @param file          THE FILE WE WANT TO SAVE
     * @param contents      THE NEW FILE'S EDITS
     * @throws IOException
     */
    public void saveFile(File file, String contents) throws IOException {
        BufferedWriter writer = null;
        String filePath = file.getPath();

        //if(!filePath.endsWith(".txt")){}
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(contents);
            writer.close();
            textArea.setText(contents);
            setTitle("Comment Stripper " + filePath);
            currentFile = file;

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Cannot write to file");
        }

    }


    /**
     * THIS METHOD PROVIDES THE AN EXIT FUNCTION, WITHOUT SAVING ANY OF THE EDITS.
     */
    public void closeWindow() {
        WindowEvent close = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}