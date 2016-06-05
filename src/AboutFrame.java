/**
 * Created by danielgalarza on 6/4/16.
 */

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {

    public JTextArea about;

    public AboutFrame() {

        setTitle("About Comment Stripper");
        about = new JTextArea("This is a partial preprocessor program that removes "
                + "blank lines and Java-style comments from a textArea file.");
        about.setEditable(false);
        about.setLineWrap(true);
        about.setWrapStyleWord(true);
        about.setBackground(new Color(217, 244, 246));
        add(about, BorderLayout.CENTER);

    }
}
