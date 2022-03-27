package zad2;

import javax.swing.*;
import java.awt.*;

public class Monitor extends JPanel{
    public Monitor() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Monitor"));
        setLayout(new BorderLayout());
    }
}
