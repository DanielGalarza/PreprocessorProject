/**
 * Created by danielgalarza on 6/4/16.
 */

import javax.swing.*;
import java.awt.*;

public class HelpFrame extends JFrame {

    JTextArea help;

    public HelpFrame(){

        setTitle("Help");
        help = new JTextArea("1. Open a file that you would like to pre-processButton"
                + "\n2. Click the 'Strip' button to perform the pre-processing"
                + "\n3. Save the new file if you wish");
        help.setEditable(false);
        help.setLineWrap(true);
        help.setWrapStyleWord(true);
        help.setBackground(new Color(217, 244, 246));
        add(help, BorderLayout.CENTER);
    }
}
