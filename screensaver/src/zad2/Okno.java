package zad2;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Okno extends Frame {

    public Okno() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new GridBagLayout());

            Monitor monitor = new Monitor();
            Menu menu = new Menu();
            Ekran ekran = new Ekran();
            Buttons buttons = new Buttons();
            JLabel zdj = new JLabel();
            zdj.setSize(ekran.getWidth() - 60, ekran.getHeight() - 60);
            zdj.setLayout(new BorderLayout());
            JLabel ruch = new JLabel();

            ExecutorService executorService = Executors.newCachedThreadPool();

            FutureTask futureTask = new FutureTask(() -> {
                if (Thread.interrupted()) return 1;
                buttons.getOn().addActionListener(e -> {
                    if (buttons.getTaskState() == null) {
                        System.out.println("Wybierz OS");
                        return;
                    }
                    if (buttons.getTaskState() == TaskState.ABORTED) {
                        System.out.println("Wyłącz i włącz, to zawsze pomoże");
                        return;
                    }
                    if (buttons.getTaskState() != TaskState.RUNNING) {
                        String file = buttons.getOs().toString() + ".jpg";
                        BufferedImage myPicture = null;
                        try {
                            myPicture = ImageIO.read(new File(file));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        Image scaledImage = myPicture.getScaledInstance(ekran.getWidth() - 60,
                                ekran.getHeight() - 60, Image.SCALE_SMOOTH);
                        ruch.setSize(new Dimension(100, 100));
                        try {
                            String files = buttons.getOs() + "logo.png";
                            BufferedImage myPictures = ImageIO.read(new File(files));
                            Image scaledImages = myPictures.getScaledInstance(ruch.getWidth(),
                                    ruch.getHeight(), Image.SCALE_SMOOTH);
                            ruch.setIcon(new ImageIcon(scaledImages));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        zdj.add(ruch);
                        zdj.setIcon(new ImageIcon(scaledImage));
                        ekran.add(zdj);
                        frame.revalidate();
                        frame.repaint();
                        buttons.setTaskState(TaskState.RUNNING);

                        new Thread(() -> {
                            int vx, vy;
                            if (((int) (Math.random() * 10)) % 2 == 0) vx = 1;
                            else vx = -1;
                            if (((int) (Math.random() * 10)) % 2 == 0) vy = 1;
                            else vy = -1;

                            ruch.setLocation((int) (Math.random() * 590), (int) (Math.random() * 288) - 170);
                            while (buttons.getTaskState() == TaskState.RUNNING) {
                                if (Thread.interrupted()) break;
                                int speed = buttons.getVolumen();
                                if (ruch.getX() + speed * vx < 0 || ruch.getX() + speed * vx > 790 - 100 - 60) {
                                    vx = vx * -1;
                                }
                                if (ruch.getY() + speed * vy < -288 + 50 + 30 || ruch.getY() + speed * vy > 288 - 50 - 30) {
                                    vy = vy * -1;
                                }
                                ruch.setLocation(ruch.getX() + speed * vx, ruch.getY() + speed * vy);
                                revalidate();
                                repaint();
                                try {
                                    sleep(10);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                                if (((ruch.getX() >= 0 && ruch.getX() <= speed) &&
                                        ((ruch.getY() <= 208 && ruch.getY() >= 208 - speed) ||
                                                (ruch.getY() >= -208 && ruch.getY() <= -208 + speed))) ||
                                        ((ruch.getX() >= 630 - speed && ruch.getX() <= 630) &&
                                                ((ruch.getY() <= 208 && ruch.getY() >= 208 - speed) ||
                                                        (ruch.getY() >= -208 && ruch.getY() <= -208 + speed)))) {
                                    System.out.println("Trafilismy w rog");
                                    buttons.setTaskState(TaskState.READY);
                                }
                            }
                        }).start();

                    } else {
                        System.out.println("System juz zostal uruchomiony.");
                    }


                });
                return 1;
            });


            FutureTask<Integer> futureTask2 = new FutureTask(() -> {
                buttons.getOff().addActionListener(e -> {
                    futureTask.cancel(true);
                    String file = "off.jpg";
                    BufferedImage myPicture = null;
                    try {
                        myPicture = ImageIO.read(new File(file));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    Image scaledImage = myPicture.getScaledInstance(ekran.getWidth() - 60,
                            ekran.getHeight() - 60, Image.SCALE_SMOOTH);
                    zdj.setIcon(new ImageIcon(scaledImage));
                    ruch.setIcon(new ImageIcon(scaledImage));
                    frame.revalidate();
                    frame.repaint();
                    buttons.setTaskState(TaskState.CREATED);
                });
                return 1;
            });

            FutureTask futureTask3 = new FutureTask(() -> {
                buttons.getSpeed().addChangeListener(e -> {
                    buttons.setVolumen(buttons.getSpeed().getValue());
                });
                return 1;
            });

            FutureTask futureTask4 = new FutureTask(() -> {
                buttons.getStatus().addActionListener(e -> {
                    System.out.println("Używany przez Ciebie system to " + buttons.getOs() +
                            ", stan zadania to: " + buttons.getTaskState());
                });
                return 1;
            });

            FutureTask futureTask5 = new FutureTask(() -> {
                buttons.getAbandon().addActionListener(e -> {
                    if (buttons.getTaskState() == TaskState.RUNNING) {
                        buttons.setTaskState(TaskState.ABORTED);
                        futureTask.cancel(true);
                        executorService.shutdownNow();
                    } else {
                        System.out.println("Nie możesz porzucić czegoś co nie jest w toku");
                    }
                });
                return 1;
            });
            FutureTask futureTask6 = new FutureTask(() -> {
                buttons.getList().addListSelectionListener(e -> {
                    if (buttons.getTaskState() == TaskState.RUNNING || buttons.getTaskState() == TaskState.ABORTED) {
                        System.out.println("Nie można aktualnie zmienic OS");
                    } else {
                        buttons.setOs(OS.valueOf(buttons.getList().getSelectedValue()));
                        buttons.setTaskState(TaskState.CREATED);
                    }
                });
                return 1;
            });


            executorService.submit(futureTask);
            executorService.submit(futureTask2);
            executorService.submit(futureTask3);
            executorService.submit(futureTask4);
            executorService.submit(futureTask5);
            executorService.submit(futureTask6);


            menu.add(buttons);
            monitor.add(ekran);
            frame.add(monitor);
            frame.add(menu);
            frame.setResizable(false);
            frame.pack();
        });
    }
}
