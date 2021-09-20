package Program;

import Program.SchoolNeeds.HomeWork;
import Program.SchoolNeeds.Test;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.inject.internal.util.Collections2;
import com.google.inject.internal.util.Lists;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListFrame implements FrameClass {
    private JPanel mainPanel;
    private JPanel central;
    private JLabel label;
    private static int monitorWidth;
    private static int monitorHeight;
    private static JScrollPane panelForTests;
    private static JScrollPane panelForNeeds;
    private static JPanel paneForTests = new JPanel();
    private static JPanel paneForNeeds = new JPanel();

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ListFrame() {
        getMonitorSize();
        intialization();
    }

    private void getMonitorSize() {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        monitorHeight = sSize.height;
        monitorWidth = sSize.width;
    }

    public void intialization() {
        initializationOfPanels();
    }

    private void initializationOfPanels() {
        initializationOfSpacers();
        initializationOfPanelsProperties();
        formationOfButttons();
    }

    private void initializationOfSpacers() {
        int heigh = (int) ((int) monitorHeight * 0.1);
        int width = (int) ((int) monitorWidth * 0.1);
        Component spacer = Box.createVerticalStrut(heigh);
        mainPanel.add(spacer, BorderLayout.NORTH);
        spacer = Box.createVerticalStrut(heigh);
        mainPanel.add(spacer, BorderLayout.SOUTH);
        spacer = Box.createHorizontalStrut(width);
        mainPanel.add(spacer, BorderLayout.WEST);
        spacer = Box.createHorizontalStrut(width);
        mainPanel.add(spacer, BorderLayout.EAST);
    }

    private void initializationOfPanelsProperties() {
        central.setBorder(new RoundEdgedBorder());
        if ((Test.getTestTreeSet().size() == 0) && (HomeWork.getHWTreeSet().size() == 0)) {
            String path = "src/main/resources/pictures/.png";
            InputStream in = ListFrame.class.getResourceAsStream("/pictures/forLoadedMonitor.png");

            /*File file = new File(path);*/
            Image img = null;
            try {
                img = ImageIO.read(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel labelImage = new JLabel(new ImageIcon(img));
            central.add(labelImage);
        } else {
            initializationOfTwoLists();
            /*central.add(panelForTests, BorderLayout.WEST);*/
        }
    }

    private void initializationOfTwoLists() {
        Font font = new Font("Arial Narrow", Font.BOLD, 26);
        panelForNeeds = new JScrollPane();
        panelForTests = new JScrollPane();
        panelForTests.setBackground(Color.WHITE);
        panelForNeeds.setBackground(Color.WHITE);
        /*panelForNeeds.setLayout(new BoxLayout(panelForNeeds, BoxLayout.Y_AXIS));*/
        /* panelForTests.setLayout(new BoxLayout(panelForTests, BoxLayout.Y_AXIS));*/
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        JLabel textForTest = new JLabel("Тесты");
        textForTest.setFont(font);
        textForTest.setForeground(Color.RED);
        panel.add(textForTest);
        paneForTests = new JPanel();
        paneForTests.add(panel);
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        JLabel textForNeed = new JLabel("Задания");
        textForNeed.setFont(font);
        textForNeed.setForeground(Color.RED);
        panel.add(textForNeed);
        paneForNeeds = new JPanel();
        paneForNeeds.add(panel);
        initializationOfTests();
        initializationOfNeeds();

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(panelForTests);
        panel.add(panelForNeeds);
        central.add(panel);
    }

    private void initializationOfTests() {
        pastDueTests();
        testForTomorrow();
        testForFuture();
        panelForTests.setBorder(null);
        panelForTests = new JScrollPane(paneForTests);
        /*panelForTests.add(paneForTests);*/
    }

    private void pastDueTests() {
        paneForTests.setBackground(Color.WHITE);
        paneForTests.setAutoscrolls(true);
        paneForTests.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        paneForTests.setLayout(new BoxLayout(paneForTests, BoxLayout.Y_AXIS));
        ArrayList<Test> list = new ArrayList<>();
        for (String key : Test.getTestTreeSet().keySet()) {
            list.add(Test.getTestTreeSet().get(key));
        }
        Stream<Test> stream = list.stream().filter(o1 -> o1.getStatus().equals("Просрочено"));
        ArrayList<Test> pastDue = (ArrayList<Test>) stream.collect(Collectors.toList());
        if (pastDue.size() != 0) {
            Font font = new Font("Arial Narrow", Font.BOLD, 20);
            JPanel allPastDueTests = new JPanel();
            allPastDueTests.setLayout(new BoxLayout(allPastDueTests, BoxLayout.Y_AXIS));
            allPastDueTests.setBackground(Color.WHITE);
            JPanel panelOfLabel = new JPanel();
            JLabel pastDueLabel = new JLabel("Просроченные: ");
            pastDueLabel.setFont(font);
            pastDueLabel.setForeground(new Color(150, 75, 0));
            pastDueLabel.setBackground(Color.WHITE);
            panelOfLabel.setBackground(Color.WHITE);
            panelOfLabel.add(pastDueLabel);
            allPastDueTests.add(panelOfLabel);
            pastDue.forEach(test -> {
                Font font1 = new Font("Arial Narrow", Font.BOLD, 20);
                JPanel panelOfTest = new JPanel();
                JLabel labelOfTest = new JLabel(test.getNeedName());
                labelOfTest.setFont(font1);
                labelOfTest.setBackground(Color.WHITE);
                panelOfTest.setBackground(Color.WHITE);
                panelOfTest.add(labelOfTest);
                labelOfTest.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (mouseEvent.getClickCount() == 2) {
                            String s = labelOfTest.getText();
                            Test test = Test.getTestTreeSet().get(s);
                            Main.gettingNeedFrame(test, true);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {

                    }
                });
                allPastDueTests.add(panelOfTest);
            });
            int heigh = (int) ((int) monitorHeight * 0.15);
            Component spacer = Box.createVerticalStrut(heigh);
            allPastDueTests.add(spacer);
            paneForTests.add(allPastDueTests);
        }
    }

    private void testForTomorrow() {
        ArrayList<Test> list = new ArrayList<>();
        for (String key : Test.getTestTreeSet().keySet()) {
            list.add(Test.getTestTreeSet().get(key));
        }
        Stream<Test> stream = list.stream().filter(o1 -> {
            Calendar curent = Calendar.getInstance();
            Calendar testDate = o1.getImplementationDate();
            int curentDay = curent.get(Calendar.DAY_OF_MONTH);
            int testDay = testDate.get(Calendar.DAY_OF_MONTH);
            int curentMounth = curent.get(Calendar.MONTH);
            int testMounth = testDate.get(Calendar.MONTH);
            if ((curentDay + 1) == testDay) {
                return true;
            } else {
                if (((curentDay == 30) || (curentDay == 31) || ((curentDay == 28)
                        && (curentMounth == 1)) ||
                        ((curentDay == 29) && (curentMounth == 1))) &&
                        (testDay == 1) && ((curentMounth + 1) == testMounth)) {
                    return true;
                }
                return false;
            }
        });
        ArrayList<Test> forTomorrow = (ArrayList<Test>) stream.collect(Collectors.toList());
        if (forTomorrow.size() != 0) {
            Font font = new Font("Arial Narrow", Font.BOLD, 20);
            JPanel allTomorrowTests = new JPanel();
            allTomorrowTests.setLayout(new BoxLayout(allTomorrowTests, BoxLayout.Y_AXIS));
            allTomorrowTests.setBackground(Color.WHITE);
            JPanel panelOfLabel = new JPanel();
            JLabel tomorrowLabel = new JLabel("На завтра: ");
            tomorrowLabel.setFont(font);
            tomorrowLabel.setForeground(Color.RED);
            tomorrowLabel.setBackground(Color.WHITE);
            panelOfLabel.setBackground(Color.WHITE);
            panelOfLabel.add(tomorrowLabel);
            allTomorrowTests.add(panelOfLabel);
            forTomorrow.forEach(test -> {
                Font font1 = new Font("Arial Narrow", Font.BOLD, 20);
                JPanel panelOfTest = new JPanel();
                JLabel labelOfTest = new JLabel(test.getNeedName());
                labelOfTest.setFont(font1);
                labelOfTest.setBackground(Color.WHITE);
                panelOfTest.setBackground(Color.WHITE);
                panelOfTest.add(labelOfTest);
                labelOfTest.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (mouseEvent.getClickCount() == 2) {
                            String s = labelOfTest.getText();
                            Test test = Test.getTestTreeSet().get(s);
                            Main.gettingNeedFrame(test, true);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {

                    }
                });
                allTomorrowTests.add(panelOfTest);
            });
            int heigh = (int) ((int) monitorHeight * 0.15);
            Component spacer = Box.createVerticalStrut(heigh);
            allTomorrowTests.add(spacer);
            paneForTests.add(allTomorrowTests);
        }
    }

    private void testForFuture() {
        ArrayList<Test> list = new ArrayList<>();
        for (String key : Test.getTestTreeSet().keySet()) {
            list.add(Test.getTestTreeSet().get(key));
        }
        Stream<Test> stream = list.stream().filter(o1 -> {
            Calendar curent = Calendar.getInstance();
            Calendar testDate = o1.getImplementationDate();
            int curentDay = curent.get(Calendar.DAY_OF_MONTH);
            int testDay = testDate.get(Calendar.DAY_OF_MONTH);
            int curentMounth = curent.get(Calendar.MONTH);
            int testMounth = testDate.get(Calendar.MONTH);
            if ((curentDay + 1) < testDay) {
                return true;
            } else {
                Calendar dayAfter = new GregorianCalendar(curent.get(Calendar.YEAR), curent.get(Calendar.MONTH),
                        curentDay + 1);
                if (dayAfter.before(testDate)) {
                    return true;
                }
                return false;
            }
        });
        ArrayList<Test> forFuture = (ArrayList<Test>) stream.collect(Collectors.toList());
        if (forFuture.size() != 0) {
            Font font = new Font("Arial Narrow", Font.BOLD, 20);
            JPanel allTomorrowTests = new JPanel();
            allTomorrowTests.setLayout(new BoxLayout(allTomorrowTests, BoxLayout.Y_AXIS));
            allTomorrowTests.setBackground(Color.WHITE);
            JPanel panelOfLabel = new JPanel();
            JLabel tomorrowLabel = new JLabel("На будующее: ");
            tomorrowLabel.setFont(font);
            tomorrowLabel.setForeground(Color.BLUE);
            tomorrowLabel.setBackground(Color.WHITE);
            panelOfLabel.setBackground(Color.WHITE);
            panelOfLabel.add(tomorrowLabel);
            allTomorrowTests.add(panelOfLabel);
            forFuture.forEach(test -> {
                Font font1 = new Font("Arial Narrow", Font.BOLD, 20);
                JPanel panelOfTest = new JPanel();
                JLabel labelOfTest = new JLabel(test.getNeedName());
                labelOfTest.setFont(font1);
                labelOfTest.setBackground(Color.WHITE);
                panelOfTest.setBackground(Color.WHITE);
                panelOfTest.add(labelOfTest);
                labelOfTest.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (mouseEvent.getClickCount() == 2) {
                            String s = labelOfTest.getText();
                            Test test = Test.getTestTreeSet().get(s);
                            Main.gettingNeedFrame(test, true);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {

                    }
                });
                allTomorrowTests.add(panelOfTest);
            });
            int heigh = (int) ((int) monitorHeight * 0.15);
            Component spacer = Box.createVerticalStrut(heigh);
            allTomorrowTests.add(spacer);
            paneForTests.add(allTomorrowTests);
        }
    }

    private void initializationOfNeeds() {
        pastDueHW();
        hWForTomorrow();
        hWForFuture();
        panelForNeeds.setBorder(null);
        panelForNeeds.setWheelScrollingEnabled(true);
        panelForNeeds = new JScrollPane(paneForNeeds);
    }

    private void pastDueHW() {
        paneForNeeds.setBackground(Color.WHITE);
        paneForNeeds.setAutoscrolls(true);
        paneForNeeds.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        paneForNeeds.setLayout(new BoxLayout(paneForNeeds, BoxLayout.Y_AXIS));
        ArrayList<HomeWork> list = new ArrayList<>();
        for (String key : HomeWork.getHWTreeSet().keySet()) {
            list.add(HomeWork.getHWTreeSet().get(key));
        }
        Stream<HomeWork> stream = list.stream().filter(o1 -> o1.getStatus().equals("Просрочено"));
        ArrayList<HomeWork> pastDue = (ArrayList<HomeWork>) stream.collect(Collectors.toList());
        if (pastDue.size() != 0) {
            Font font = new Font("Arial Narrow", Font.BOLD, 20);
            JPanel allPastDueTests = new JPanel();
            allPastDueTests.setLayout(new BoxLayout(allPastDueTests, BoxLayout.Y_AXIS));
            allPastDueTests.setBackground(Color.WHITE);
            JPanel panelOfLabel = new JPanel();
            JLabel pastDueLabel = new JLabel("Просроченные: ");
            pastDueLabel.setFont(font);
            pastDueLabel.setForeground(new Color(150, 75, 0));
            pastDueLabel.setBackground(Color.WHITE);
            panelOfLabel.setBackground(Color.WHITE);
            panelOfLabel.add(pastDueLabel);
            allPastDueTests.add(panelOfLabel);
            pastDue.forEach(test -> {
                Font font1 = new Font("Arial Narrow", Font.BOLD, 20);
                JPanel panelOfTest = new JPanel();
                JLabel labelOfTest = new JLabel(test.getNeedName());
                labelOfTest.setFont(font1);
                labelOfTest.setBackground(Color.WHITE);
                panelOfTest.setBackground(Color.WHITE);
                panelOfTest.add(labelOfTest);
                labelOfTest.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (mouseEvent.getClickCount() == 2) {
                            String s = labelOfTest.getText();
                            HomeWork test = HomeWork.getHWTreeSet().get(s);
                            Main.gettingNeedFrame(test, false);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {

                    }
                });
                allPastDueTests.add(panelOfTest);
            });
            int heigh = (int) ((int) monitorHeight * 0.15);
            Component spacer = Box.createVerticalStrut(heigh);
            allPastDueTests.add(spacer);
            paneForNeeds.add(allPastDueTests);
        }
    }

    private void hWForTomorrow() {
        ArrayList<HomeWork> list = new ArrayList<>();
        for (String key : HomeWork.getHWTreeSet().keySet()) {
            list.add(HomeWork.getHWTreeSet().get(key));
        }
        Stream<HomeWork> stream = list.stream().filter(o1 -> {
            Calendar curent = Calendar.getInstance();
            Calendar testDate = o1.getImplementationDate();
            int curentDay = curent.get(Calendar.DAY_OF_MONTH);
            int testDay = testDate.get(Calendar.DAY_OF_MONTH);
            int curentMounth = curent.get(Calendar.MONTH);
            int testMounth = testDate.get(Calendar.MONTH);
            if ((curentDay + 1) == testDay) {
                return true;
            } else {
                if (((curentDay == 30) || (curentDay == 31) || ((curentDay == 28)
                        && (curentMounth == 1)) ||
                        ((curentDay == 29) && (curentMounth == 1))) &&
                        (testDay == 1) && ((curentMounth + 1) == testMounth)) {
                    return true;
                }
                return false;
            }
        });
        ArrayList<HomeWork> forTomorrow = (ArrayList<HomeWork>) stream.collect(Collectors.toList());
        if (forTomorrow.size() != 0) {
            Font font = new Font("Arial Narrow", Font.BOLD, 20);
            JPanel allTomorrowTests = new JPanel();
            allTomorrowTests.setLayout(new BoxLayout(allTomorrowTests, BoxLayout.Y_AXIS));
            allTomorrowTests.setBackground(Color.WHITE);
            JPanel panelOfLabel = new JPanel();
            JLabel tomorrowLabel = new JLabel("На завтра: ");
            tomorrowLabel.setFont(font);
            tomorrowLabel.setForeground(Color.RED);
            tomorrowLabel.setBackground(Color.WHITE);
            panelOfLabel.setBackground(Color.WHITE);
            panelOfLabel.add(tomorrowLabel);
            allTomorrowTests.add(panelOfLabel);
            forTomorrow.forEach(test -> {
                Font font1 = new Font("Arial Narrow", Font.BOLD, 20);
                JPanel panelOfTest = new JPanel();
                JLabel labelOfTest = new JLabel(test.getNeedName());
                labelOfTest.setFont(font1);
                labelOfTest.setBackground(Color.WHITE);
                panelOfTest.setBackground(Color.WHITE);
                panelOfTest.add(labelOfTest);
                labelOfTest.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (mouseEvent.getClickCount() == 2) {
                            String s = labelOfTest.getText();
                            HomeWork test = HomeWork.getHWTreeSet().get(s);
                            Main.gettingNeedFrame(test, false);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {

                    }
                });
                allTomorrowTests.add(panelOfTest);
            });
            int heigh = (int) ((int) monitorHeight * 0.15);
            Component spacer = Box.createVerticalStrut(heigh);
            allTomorrowTests.add(spacer);
            paneForNeeds.add(allTomorrowTests);
        }
    }

    private void hWForFuture() {
        ArrayList<HomeWork> list = new ArrayList<>();
        for (String key : HomeWork.getHWTreeSet().keySet()) {
            list.add(HomeWork.getHWTreeSet().get(key));
        }
        Stream<HomeWork> stream = list.stream().filter(o1 -> {
            Calendar curent = Calendar.getInstance();
            Calendar testDate = o1.getImplementationDate();
            int curentDay = curent.get(Calendar.DAY_OF_MONTH);
            int testDay = testDate.get(Calendar.DAY_OF_MONTH);
            int curentMounth = curent.get(Calendar.MONTH);
            int testMounth = testDate.get(Calendar.MONTH);
            if ((curentDay + 1) < testDay) {
                return true;
            } else {
                Calendar dayAfter = new GregorianCalendar(curent.get(Calendar.YEAR), curent.get(Calendar.MONTH),
                        curentDay + 1);
                if (dayAfter.before(testDate)) {
                    return true;
                }
                return false;
            }
        });
        ArrayList<HomeWork> forTomorrow = (ArrayList<HomeWork>) stream.collect(Collectors.toList());
        if (forTomorrow.size() != 0) {
            Font font = new Font("Arial Narrow", Font.BOLD, 20);
            JPanel allTomorrowTests = new JPanel();
            allTomorrowTests.setLayout(new BoxLayout(allTomorrowTests, BoxLayout.Y_AXIS));
            allTomorrowTests.setBackground(Color.WHITE);
            JPanel panelOfLabel = new JPanel();
            JLabel tomorrowLabel = new JLabel("На будующее: ");
            tomorrowLabel.setFont(font);
            tomorrowLabel.setForeground(Color.BLUE);
            tomorrowLabel.setBackground(Color.WHITE);
            panelOfLabel.setBackground(Color.WHITE);
            panelOfLabel.add(tomorrowLabel);
            allTomorrowTests.add(panelOfLabel);
            forTomorrow.forEach(test -> {
                Font font1 = new Font("Arial Narrow", Font.BOLD, 20);
                JPanel panelOfTest = new JPanel();
                JLabel labelOfTest = new JLabel(test.getNeedName());
                labelOfTest.setFont(font1);
                labelOfTest.setBackground(Color.WHITE);
                panelOfTest.setBackground(Color.WHITE);
                panelOfTest.add(labelOfTest);
                labelOfTest.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (mouseEvent.getClickCount() == 2) {
                            String s = labelOfTest.getText();
                            HomeWork test = HomeWork.getHWTreeSet().get(s);
                            Main.gettingNeedFrame(test, false);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {

                    }
                });
                allTomorrowTests.add(panelOfTest);
            });
            int heigh = (int) ((int) monitorHeight * 0.15);
            Component spacer = Box.createVerticalStrut(heigh);
            allTomorrowTests.add(spacer);
            paneForNeeds.add(allTomorrowTests);
        }
    }


    private void formationOfButttons() {
        Font mainFont = new Font("Times New Roman", Font.BOLD, 18);
        JButton back = new JButton("Назад");
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setFont(mainFont);
        back.setBackground(Color.white);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setMainFrame();
            }
        });
        central.add(back, BorderLayout.SOUTH);

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(-20442));
        central = new JPanel();
        central.setLayout(new BorderLayout(0, 0));
        central.setBackground(new Color(-20442));
        mainPanel.add(central, BorderLayout.CENTER);
        label = new JLabel();
        Font labelFont = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 28, label.getFont());
        if (labelFont != null) label.setFont(labelFont);
        label.setForeground(new Color(-60905));
        label.setHorizontalAlignment(0);
        label.setHorizontalTextPosition(0);
        label.setText("Полный список заданий");
        central.add(label, BorderLayout.NORTH);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
