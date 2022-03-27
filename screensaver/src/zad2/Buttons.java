package zad2;

import javax.swing.*;
import java.awt.*;

public class Buttons extends JPanel{
    private JButton on, off, status, abandon;
    private JSlider speed;
    private JList<String> list;
    private int volumen=5;
    private volatile OS  os;
    private volatile TaskState taskState;
    public Buttons() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 40)));
        String[] tab = {"Windows98", "Windows7", "Windows10", "Windows11"};
        list = new JList<>(tab);
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        list.setFont(new Font("Calibri", Font.BOLD, 20));
        list.setBackground(new Color(238, 238, 238));
        list.setAlignmentX(CENTER_ALIGNMENT);
        add(list);
        add(Box.createRigidArea(new Dimension(0, 40)));
        on = new JButton("On");
        on.setAlignmentX(CENTER_ALIGNMENT);
        add(on);
        add(Box.createRigidArea(new Dimension(0, 40)));
        off = new JButton("Off");
        off.setAlignmentX(CENTER_ALIGNMENT);
        add(off);
        add(Box.createRigidArea(new Dimension(0, 40)));
        status = new JButton("Status");
        status.setAlignmentX(CENTER_ALIGNMENT);
        add(status);
        add(Box.createRigidArea(new Dimension(0, 40)));
        abandon = new JButton("Abandon");
        abandon.setAlignmentX(CENTER_ALIGNMENT);
        add(abandon);
        add(Box.createRigidArea(new Dimension(0, 40)));
        speed = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        speed.setMajorTickSpacing(1);
        speed.setMinorTickSpacing(1);
        speed.setPaintLabels(true);
        add(speed);
        JLabel podpis = new JLabel("speed");
        podpis.setFont(new Font("Calibri", Font.BOLD, 14));
        podpis.setAlignmentX(CENTER_ALIGNMENT);
        add(podpis);

        add(Box.createRigidArea(new Dimension(100, 60)));
        JLabel ja = new JLabel("Jan Siemieniec");
        ja.setFont(new Font("Calibri", Font.BOLD, 20));
        ja.setAlignmentX(CENTER_ALIGNMENT);
        add(ja);
    }

    public JButton getOn() {
        return on;
    }

    public JButton getOff() {
        return off;
    }

    public JButton getStatus() {
        return status;
    }

    public JButton getAbandon() {
        return abandon;
    }

    public JList<String> getList() {
        return list;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public OS getOs() {
        return os;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public JSlider getSpeed() {
        return speed;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    public int getVolumen() {
        return volumen;
    }

}

