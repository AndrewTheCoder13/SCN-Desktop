package Program;

import Program.SchoolNeeds.HomeWork;
import Program.SchoolNeeds.Test;

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
import java.util.TreeMap;


public class MainFrame implements FrameClass {

    private JPanel mainPanel;
    private JPanel nearestTest;
    private JLabel testName;
    private JLabel description;
    private JLabel date;
    private JPanel topBanner;
    private JPanel testsAndNeedsForTommorow;
    private JPanel testNamePanel;
    private JPanel testDescription;
    private JPanel testDate;
    private JPanel centralPanel;
    private JPanel list;
    private JPanel list1;
    private JPanel list2;
    private JLabel урокиИКонтрольныеНаLabel;
    private JPanel bottomControlButtons;
    private JPanel label;
    private JLabel testNameLabel;
    private JPanel needsForTommorow;
    private JPanel nearestNeed;
    private JPanel homeWorkLabel;
    private JPanel homeWorkNamePanel;
    private JPanel homeWorkDate;
    private JPanel homeWorkDescription;
    private JLabel homeWorkName;
    private JLabel hwDate;
    private JLabel hwDescription;
    private Test test;
    private HomeWork work;
    private int testsCount;
    private int curentTest;
    private int needsCount;
    private int curentNeed;
    private static TreeMap<String, HomeWork> hwForTomorow = new TreeMap<>();
    private static TreeMap<String, Test> testForTomorow = new TreeMap<>();
    private static int monitorWidth;
    private static int monitorHeight;
    private static JList<String> stringJListTest;
    private static JList<String> stringJListNeed;
    private static JLabel testLabel;
    private static JLabel need;
    private static ArrayList<Test> listOfTest;
    private static ArrayList<HomeWork> listOfHomeWork;
    private String testPath;
    private String workPath;
    private JLabel testImage;
    private JLabel workImage;


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public MainFrame(Test test, HomeWork work, TreeMap<String, HomeWork> hwForTomorow, TreeMap<String, Test> testForTomorow) throws IOException {
        this.test = test;
        this.work = work;
        this.hwForTomorow = hwForTomorow;
        this.testForTomorow = testForTomorow;
        getMonitorSize();
        intialization();
        /*maikingList();*/
    }

    public void getMonitorSize() {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        monitorHeight = sSize.height;
        monitorWidth = sSize.width;
    }

    public void intialization() throws IOException {
        initializationOfPanels();
        intializationOfTestAndNeedsForTomorrow();
    }

    private void initializationOfPanels() throws IOException {
        setingElementsProperties();
        isNothing();
        Font font = new Font("Arial Narrow", Font.ITALIC, 26);
        if (hwForTomorow.size() > 0) {
            initializationOfWorks();
            mainPanel.updateUI();
        } else {
            if ((Test.getTestTreeSet().size() > 0) || (HomeWork.getHWTreeSet().size() > 0)) {
                nearestNeed.setLayout(new BorderLayout());
                JLabel text = new JLabel("Домашние работы на завтра");
                text.setForeground(Color.RED);
                text.setFont(font);
                JPanel panel = new JPanel();
                panel.setBackground(new Color(255, 255, 255, 30));
                panel.add(text);
                String path = "src/main/resources/pictures/nothingForTomorrow.png";
                InputStream in = MainFrame.class.getResourceAsStream("/pictures/nothingForTomorrow.png");
                /*File file = new File(path);*/
                Image img = ImageIO.read(in);
                JLabel label = new JLabel(new ImageIcon(img));
                nearestNeed.add(label);
                nearestNeed.add(panel, BorderLayout.NORTH);
                int heigh = (int) ((int) monitorHeight * 0.1);
                Component spacer = Box.createVerticalStrut(heigh);
                needsForTommorow.add(spacer);
            } else {
                needsForTommorow.setVisible(false);
            }
        }
        if (testForTomorow.size() > 0) {
            initializationOfTests();
            mainPanel.updateUI();
        } else {
            if (HomeWork.getHWTreeSet().size() > 0) {
                nearestTest.setLayout(new BorderLayout());
                JLabel text = new JLabel("Ближайшие контрольные работы");
                text.setForeground(Color.RED);
                text.setFont(font);
                JPanel panel = new JPanel();
                panel.setBackground(new Color(255, 255, 255, 30));
                panel.add(text);
                String path = "src/main/resources/pictures/nothingForTomorrow.png";
                InputStream in = MainFrame.class.getResourceAsStream("/pictures/nothingForTomorrow.png");
                /*File file = new File(path);*/
                Image img = ImageIO.read(in);
                JLabel label = new JLabel(new ImageIcon(img));
                nearestTest.add(label);
                nearestTest.add(panel, BorderLayout.NORTH);
                int heigh = (int) ((int) monitorHeight * 0.1);
                Component spacer = Box.createVerticalStrut(heigh);
                testsAndNeedsForTommorow.add(spacer);
            } else {
                testsAndNeedsForTommorow.setVisible(false);
            }
        }
        topBannerInitialization();
        bottomBanerInitialization();
    }

    private void setingElementsProperties() throws IOException {
        Dimension dim = new Dimension();
        bottomControlButtons.setBorder(new RoundEdgedBorder());
        testsAndNeedsForTommorow.setLayout(new BoxLayout(testsAndNeedsForTommorow, BoxLayout.Y_AXIS));
        needsForTommorow.setLayout(new BoxLayout(needsForTommorow, BoxLayout.Y_AXIS));
        nearestTest.setLayout(new BoxLayout(nearestTest, BoxLayout.Y_AXIS));
        nearestTest.setBorder(new RoundEdgedBorder());
        nearestNeed.setLayout(new BoxLayout(nearestNeed, BoxLayout.Y_AXIS));
        nearestNeed.setBorder(new RoundEdgedBorder());
        dim.setSize(422, 276);
        nearestTest.setMinimumSize(dim);
        nearestNeed.setMinimumSize(dim);
        homeWorkDate.setBackground(new Color(255, 255, 255, 30));
        homeWorkDescription.setBackground(new Color(255, 255, 255, 30));
        homeWorkLabel.setBackground(new Color(255, 255, 255, 30));
        homeWorkNamePanel.setBackground(new Color(255, 255, 255, 30));
        testNamePanel.setBackground(new Color(255, 255, 255, 30));
        testDescription.setBackground(new Color(255, 255, 255, 30));
        testDate.setBackground(new Color(255, 255, 255, 30));
        label.setBackground(new Color(255, 255, 255, 30));
        nearestTest.setVisible(true);
    }

    private void isNothing() throws IOException {
        Dimension dim = new Dimension();
        if ((HomeWork.getHWTreeSet().size() == 0) && (Test.getTestTreeSet().size() == 0)) {
            String path = "src/main/resources/pictures/forLoadedMonitor.png";
            InputStream in = MainFrame.class.getResourceAsStream("/pictures/forLoadedMonitor.png");
            /*File file = new File(path);*/
            Image img = ImageIO.read(in);
            centralPanel.setVisible(true);
            JLabel label = new JLabel(new ImageIcon(img));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            centralPanel.add(label);
        }
    }

    private void initializationOfTests() throws IOException {
        centralPanel.setVisible(false);
        testsAndNeedsForTommorow.setVisible(true);
        testsCount = testForTomorow.size();
        listOfTest = new ArrayList<>();
        for (String key : testForTomorow.keySet()) {
            listOfTest.add(testForTomorow.get(key));
        }
        listOfTest.sort(new NeedComparator());
        if (curentTest == -1) {
            curentTest = 0;
        }
        test = listOfTest.get(curentTest);
        int heigh = (int) ((int) monitorHeight * 0.1);
        Component spacer = Box.createVerticalStrut(heigh);
        testsAndNeedsForTommorow.add(spacer);
        if (test.isEnded()) {
            testPath = "/pictures/ended.png";
        } else {
            int random = (int) ((Math.random() * (7 - 4)) + 4);
            testPath = "/pictures/" + random + ".png";
        }
        InputStream in = MainFrame.class.getResourceAsStream(testPath);
        /*File file = new File(testPath);*/
        Image img = ImageIO.read(in);
        testImage = new JLabel(new ImageIcon(img));
        nearestTest.add(testImage);
        nearestTest.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String s = testName.getText();
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
        nearestTest.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (testsCount > 1) {
            JPanel panel = new JPanel();
            int width = (int) (monitorWidth * 0.25);
            String path = "src/main/resources/pictures/redArrowLeft.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowLeft.png");
            /*file = new File(path);*/
            img = ImageIO.read(in);
            JLabel label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if (curentTest == 0) {
                        curentTest = testsCount - 1;
                        test = listOfTest.get(curentTest);

                        /*Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/ /*Этот метод работает хорошо*/
                        try {
                            changeTest();  /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentTest--;
                        test = listOfTest.get(curentTest);
                        /* Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/
                        try {
                            changeTest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            spacer = Box.createHorizontalStrut(width);
            panel.add(spacer);
            path = "src/main/resources/pictures/redArrowRight.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowRight.png");
            /*file = new File(path);*/
            img = ImageIO.read(in);
            label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if ((curentTest + 1) == testsCount) {
                        curentTest = 0;
                        test = listOfTest.get(curentTest);
                        /*Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/ /*Этот метод работает хорошо*/
                        try {
                            changeTest();  /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentTest++;
                        test = listOfTest.get(curentTest);

                        /*Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/
                        try {
                            changeTest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            panel.setBackground(new Color(255, 255, 255, 30));
            nearestTest.add(panel);
        }

    }

    private void changeTest() throws IOException {
        String preMounths = "Января Февраля Марта Апреля Мая Июня Июля Августа Сентября Октбября Ноября Декабря";
        String[] mounths = preMounths.split(" ");
        String s = test.getNeedName();
        if (test.isEnded()) {
            testPath = "/pictures/ended.png";
        } else {
            int random = (int) ((Math.random() * (7 - 4)) + 4);
            testPath = "/pictures/" + random + ".png";
        }
        InputStream in = MainFrame.class.getResourceAsStream(testPath);
        /*File file = new File(testPath);*/
        Image img = ImageIO.read(in);
        nearestTest.remove(5);
        nearestTest.remove(4);
        testImage = new JLabel(new ImageIcon(img));
        testImage.updateUI();
        nearestTest.add(testImage);
        if (testsCount > 1) {
            JPanel panel = new JPanel();
            int width = (int) (monitorWidth * 0.25);
            String path = "src/main/resources/pictures/redArrowLeft.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowLeft.png");
            /*file = new File(path);*/
            img = ImageIO.read(in);
            JLabel label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if (curentTest == 0) {
                        curentTest = testsCount - 1;
                        test = listOfTest.get(curentTest);
                        /*Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/ /*Этот метод работает хорошо*/
                        try {
                            changeTest();  /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentTest--;
                        test = listOfTest.get(curentTest);
                        /* Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/
                        try {
                            changeTest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            Component spacer = Box.createHorizontalStrut(width);
            panel.add(spacer);
            path = "src/main/resources/pictures/redArrowRight.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowRight.png");
            /*file = new File(path);*/
            img = ImageIO.read(in);
            label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if ((curentTest + 1) == testsCount) {
                        curentTest = 0;
                        test = listOfTest.get(curentTest);
                        /*Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/ /*Этот метод работает хорошо*/
                        try {
                            changeTest();  /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentTest++;
                        test = listOfTest.get(curentTest);
                        /*Main.resetMainFrameWithProperties(test, work, curentTest, curentNeed, true, false, null, oldWorkPath);*/
                        try {
                            changeTest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            panel.setBackground(new Color(255, 255, 255, 30));
            nearestTest.add(panel);
        }
        if (s.length() > 25) {
            s = s.substring(0, 25) + "...";
        }
        testName.setText(s);
        testName.setBackground(Color.WHITE);
        testName.revalidate();
        s = test.getNeedDescription();
        if (s.length() > 25) {
            s = s.substring(0, 25) + "...";
        }
        description.setText(s);
        description.setBackground(Color.WHITE);
        description.revalidate();
        Calendar date1 = test.getImplementationDate();
        String year = Integer.toString(date1.get(Calendar.YEAR));
        String mounth = mounths[date1.get(Calendar.MONTH)];
        String day = Integer.toString(date1.get(Calendar.DAY_OF_MONTH));
        s = day + " " + mounth + " " + year;
        if (s.length() > 25) {
            s = s.substring(0, 25) + "...";
        }
        date.setText(s);
        date.setBackground(Color.WHITE);
        date.revalidate();
        mainPanel.updateUI();
    }

    private void initializationOfWorks() throws IOException {
        needsForTommorow.setVisible(true);
        centralPanel.setVisible(false);
        needsCount = hwForTomorow.size();
        listOfHomeWork = new ArrayList<>();
        for (String key : hwForTomorow.keySet()) {
            listOfHomeWork.add(hwForTomorow.get(key));
        }
        listOfHomeWork.sort(new NeedComparator());
        if (curentTest == -1) {
            curentNeed = 0;
        }
        work = listOfHomeWork.get(curentNeed);
        int heigh = (int) ((int) monitorHeight * 0.1);
        Component spacer = Box.createVerticalStrut(heigh);
        needsForTommorow.add(spacer);
        if (work.isEnded()) {
            workPath = "/pictures/ended.png";
        } else {
            int random = (int) ((Math.random() * (3 - 1)) + 1);
            workPath = "/pictures/" + random + ".png";
        }
        /*File file = new File(workPath);*/
        InputStream in = MainFrame.class.getResourceAsStream(workPath);
        Image img = ImageIO.read(in);
        workImage = new JLabel(new ImageIcon(img));
        nearestNeed.add(workImage);

        nearestNeed.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String s = homeWorkName.getText();
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
        nearestNeed.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (needsCount > 1) {
            JPanel panel = new JPanel();
            int width = (int) (monitorWidth * 0.25);
            String path = "src/main/resources/pictures/redArrowLeft.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowLeft.png");
            /*file = new File(path);*/
            img = ImageIO.read(in);
            JLabel label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if (curentNeed == 0) {
                        curentNeed = needsCount - 1;
                        work = listOfHomeWork.get(curentNeed);
                        /*Main.resetMainFrameWithProperties(test, work);*/ /*Этот метод работает хорошо*/
                        try {
                            changeWork(); /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentNeed--;
                        work = listOfHomeWork.get(curentNeed);
                        /*Main.resetMainFrameWithProperties(test, work);*/
                        try {
                            changeWork();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            spacer = Box.createHorizontalStrut(width);
            panel.add(spacer);
            path = "src/main/resources/pictures/redArrowRight.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowRight.png");
            /*file = new File(path);*/
            img = ImageIO.read(in);
            label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if ((curentNeed + 1) == needsCount) {
                        curentNeed = 0;
                        work = listOfHomeWork.get(curentNeed);
                        /*Main.resetMainFrameWithProperties(test, work);*/ /*Этот метод работает хорошо*/
                        try {
                            changeWork();  /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentNeed++;
                        work = listOfHomeWork.get(curentNeed);
                        /*Main.resetMainFrameWithProperties(test, work);*/
                        try {
                            changeWork();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            panel.setBackground(new Color(255, 255, 255, 30));
            nearestNeed.add(panel);
        }
    }

    private void changeWork() throws IOException {
        String preMounths = "Января Февраля Марта Апреля Мая Июня Июля Августа Сентября Октбября Ноября Декабря";
        String[] mounths = preMounths.split(" ");
        String s = work.getNeedName();
        if (work.isEnded()) {
            workPath = "/pictures/ended.png";
        } else {
            int random = (int) ((Math.random() * (3 - 1)) + 1);
            workPath = "/pictures/" + random + ".png";
        }
        InputStream in = MainFrame.class.getResourceAsStream(workPath);
        /*File file = new File(workPath);*/
        Image img = ImageIO.read(in);
        nearestNeed.remove(5);
        nearestNeed.remove(4);
        workImage = new JLabel(new ImageIcon(img));
        workImage.updateUI();
        nearestNeed.add(workImage);
        if (needsCount > 1) {
            JPanel panel = new JPanel();
            int width = (int) (monitorWidth * 0.25);
            String path = "src/main/resources/pictures/redArrowLeft.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowLeft.png");
            /* file = new File(path);*/
            img = ImageIO.read(in);
            JLabel label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if (curentNeed == 0) {
                        curentNeed = needsCount - 1;
                        work = listOfHomeWork.get(curentNeed);
                        /*Main.resetMainFrameWithProperties(test, work);*/ /*Этот метод работает хорошо*/
                        try {
                            changeWork(); /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentNeed--;
                        work = listOfHomeWork.get(curentNeed);
                        /*Main.resetMainFrameWithProperties(test, work);*/
                        try {
                            changeWork();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            Component spacer = Box.createHorizontalStrut(width);
            panel.add(spacer);
            path = "src/main/resources/pictures/redArrowRight.png";
            in = MainFrame.class.getResourceAsStream("/pictures/redArrowRight.png");
            /*file = new File(path);*/
            img = ImageIO.read(in);
            label = new JLabel(new ImageIcon(img));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if ((curentNeed + 1) == needsCount) {
                        curentNeed = 0;
                        work = listOfHomeWork.get(curentNeed);

                        /*Main.resetMainFrameWithProperties(test, work);*/ /*Этот метод работает хорошо*/
                        try {
                            changeWork();  /*при использовании этого метода все превращается в билеберду*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        curentNeed++;
                        work = listOfHomeWork.get(curentNeed);
                        /*Main.resetMainFrameWithProperties(test, work);*/
                        try {
                            changeWork();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            panel.add(label);
            panel.setBackground(new Color(255, 255, 255, 30));
            nearestNeed.add(panel);
        }
        if (s.length() > 25) {
            s = s.substring(0, 25) + "...";
        }
        homeWorkName.setText(s);
        homeWorkName.setBackground(Color.WHITE);
        homeWorkName.revalidate();
        s = work.getNeedDescription();
        if (s.length() > 25) {
            s = s.substring(0, 25) + "...";
        }
        hwDescription.setText(s);
        hwDescription.setBackground(Color.WHITE);
        hwDescription.revalidate();
        Calendar date1 = work.getImplementationDate();
        String year = Integer.toString(date1.get(Calendar.YEAR));
        String mounth = mounths[date1.get(Calendar.MONTH)];
        String day = Integer.toString(date1.get(Calendar.DAY_OF_MONTH));
        s = day + " " + mounth + " " + year;
        if (s.length() > 25) {
            s = s.substring(0, 25) + "...";
        }
        hwDate.setText(s);
        hwDate.setBackground(Color.WHITE);
        hwDate.revalidate();
        mainPanel.updateUI();
    }

    private void topBannerInitialization() throws IOException {
        String path = "src/main/resources/pictures/mainImage.png";
        InputStream in = MainFrame.class.getResourceAsStream("/pictures/mainImage.png");
        /*File file = new File(path);*/
        Image img = ImageIO.read(in);
        JLabel label = new JLabel(new ImageIcon(img));
        topBanner.add(label);
    }

    private void bottomBanerInitialization() throws IOException {
        Dimension dim = new Dimension();
        dim.setSize(400, 1);
        Component spacer = Box.createHorizontalStrut(400);
        spacer.setPreferredSize(dim);
        dim.setSize(350, 1);
        spacer.setMinimumSize(dim);

        String path = "src/main/resources/pictures/home.png";
        /*File file = new File(path);*/
        InputStream in = MainFrame.class.getResourceAsStream("/pictures/home.png");
        Image img = ImageIO.read(in);
        JButton button = new JButton(new ImageIcon(img));
        button.setBorder(null);
        button.setBackground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        UIManager.put("Button.select", Color.WHITE);
        button.setHorizontalAlignment(0);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setMainFrame();
            }
        });
        bottomControlButtons.add(button);
        bottomControlButtons.add(spacer);


        path = "src/main/resources/pictures/addButton.png";
        spacer = Box.createHorizontalStrut(400);
        dim.setSize(400, 1);
        spacer.setPreferredSize(dim);
        dim.setSize(350, 1);
        spacer.setMinimumSize(dim);

        /*file = new File(path);*/
        in = MainFrame.class.getResourceAsStream("/pictures/addButton.png");
        img = ImageIO.read(in);
        button = new JButton(new ImageIcon(img));
        button.setBorder(null);
        button.setBackground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(0);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setAddFrame();
            }
        });
        bottomControlButtons.add(button);
        bottomControlButtons.add(spacer);

        path = "src/main/resources/pictures/list.png";
        /*file = new File(path);*/
        in = MainFrame.class.getResourceAsStream("/pictures/list.png");
        img = ImageIO.read(in);
        button = new JButton(new ImageIcon(img));
        button.setBorder(null);
        button.setBackground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(0);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setListFrame();
            }
        });
        bottomControlButtons.add(button);
    }

    private void intializationOfTestAndNeedsForTomorrow() {
        String preMounths = "Января Февраля Марта Апреля Мая Июня Июля Августа Сентября Октбября Ноября Декабря";
        String[] mounths = preMounths.split(" ");
        initializationOfTest(mounths);
        initializationOfNeed(mounths);
    }

    private void initializationOfTest(String[] mounths) {
        if (test != null) {
            String s = test.getNeedName();
            if (s.length() > 25) {
                s = s.substring(0, 25) + "...";
            }
            testName.setText(s);
            s = test.getNeedDescription();
            s = s.replaceAll(";", ", ");
            if (s.length() > 25) {
                s = s.substring(0, 25) + "...";
            }
            description.setText(s);
            Calendar date1 = test.getImplementationDate();
            String year = Integer.toString(date1.get(Calendar.YEAR));
            String mounth = mounths[date1.get(Calendar.MONTH)];
            String day = Integer.toString(date1.get(Calendar.DAY_OF_MONTH));
            s = day + " " + mounth + " " + year;
            if (s.length() > 25) {
                s = s.substring(0, 25) + "...";
            }
            date.setText(s);
        } else {
            /*nearestTest.setVisible(false);*/
        }
    }

    public void initializationOfNeed(String[] mounths) {
        if (work != null) {
            String s = work.getNeedName();
            if (s.length() > 25) {
                s = s.substring(0, 25) + "...";
            }
            homeWorkName.setText(s);
            s = work.getNeedDescription();
            if (s.length() > 25) {
                s = s.substring(0, 25) + "...";
            }
            hwDescription.setText(s);
            Calendar date1 = work.getImplementationDate();
            String year = Integer.toString(date1.get(Calendar.YEAR));
            String mounth = mounths[date1.get(Calendar.MONTH)];
            String day = Integer.toString(date1.get(Calendar.DAY_OF_MONTH));
            if (s.length() > 25) {
                s = s.substring(0, 25) + "...";
            }
            s = day + " " + mounth + " " + year;
            hwDate.setText(s);
        } else {
            /*nearestNeed.setVisible(false);*/
        }
    }

    public void maikingList() {
        Dimension d = new Dimension();
        d.setSize(450, 28);
        list.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        list2.setLayout(new BoxLayout(list2, BoxLayout.Y_AXIS));
        Font mainFont = new Font("Times New Roman", Font.BOLD, 18);
        Font secondaryFont = new Font("Times New Roman", Font.BOLD, 14);
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.white);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        testLabel = new JLabel();
        testLabel.setText("Тесты:");
        testLabel.setFont(mainFont);
        Component spacer = Box.createHorizontalStrut(400);
        jPanel.add(testLabel);
        jPanel.add(spacer);
        list2.add(jPanel);
        String[] listFor = null;
        int size = testForTomorow.keySet().size();
        if (size < 5) {
            listFor = new String[size];
        } else {
            listFor = new String[6];
        }
        int i = 1;
        for (String key : testForTomorow.keySet()) {
            if (i == 6) {
                listFor[i - 1] = "...";
            }
            listFor[i - 1] = i + ") " + key;
            i++;
        }
        stringJListTest = new JList<>(listFor);
        stringJListTest.setFont(secondaryFont);
        stringJListTest.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stringJListTest.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String s = stringJListTest.getSelectedValue().substring(stringJListTest.getSelectedValue().indexOf(" ")).trim();
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
        list2.add(stringJListTest);


        jPanel = new JPanel();
        jPanel.setBackground(Color.white);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        need = new JLabel();
        need.setText("Задания:");
        need.setFont(mainFont);
        spacer = Box.createHorizontalStrut(400);
        jPanel.add(need);
        jPanel.add(spacer);
        list2.add(jPanel);
        size = hwForTomorow.keySet().size();
        if (size < 5) {
            listFor = new String[size];
        } else {
            listFor = new String[6];
        }

        i = 1;
        for (String key : hwForTomorow.keySet()) {
            if (i == 6) {
                listFor[i - 1] = "...";
            }
            listFor[i - 1] = i + ") " + key;
            i++;
        }
        stringJListNeed = new JList<>(listFor);
        stringJListNeed.setFont(secondaryFont);
        stringJListNeed.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String s = stringJListNeed.getSelectedValue().substring(stringJListNeed.getSelectedValue().indexOf(" ")).trim();
                    HomeWork work = HomeWork.getHWTreeSet().get(s);
                    Main.gettingNeedFrame(work, false);
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
        stringJListNeed.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        list2.add(stringJListNeed);
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
        mainPanel.setBackground(new Color(-20187));
        topBanner = new JPanel();
        topBanner.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));
        topBanner.setBackground(new Color(-20442));
        topBanner.setForeground(new Color(-20442));
        mainPanel.add(topBanner, BorderLayout.NORTH);
        testsAndNeedsForTommorow = new JPanel();
        testsAndNeedsForTommorow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        testsAndNeedsForTommorow.setBackground(new Color(-20442));
        testsAndNeedsForTommorow.setForeground(new Color(-16777216));
        mainPanel.add(testsAndNeedsForTommorow, BorderLayout.WEST);
        nearestTest = new JPanel();
        nearestTest.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        nearestTest.setBackground(new Color(-20442));
        nearestTest.setPreferredSize(new Dimension(450, 100));
        testsAndNeedsForTommorow.add(nearestTest);
        label = new JPanel();
        label.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        label.setBackground(new Color(-1));
        label.setForeground(new Color(-20442));
        nearestTest.add(label);
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-1));
        label1.setEnabled(true);
        Font label1Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-60905));
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Ближайшие контрольные работы");
        label.add(label1);
        testNamePanel = new JPanel();
        testNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        testNamePanel.setBackground(new Color(-1));
        testNamePanel.setForeground(new Color(-1));
        nearestTest.add(testNamePanel);
        testNameLabel = new JLabel();
        testNameLabel.setBackground(new Color(-12828863));
        Font testNameLabelFont = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, testNameLabel.getFont());
        if (testNameLabelFont != null) testNameLabel.setFont(testNameLabelFont);
        testNameLabel.setForeground(new Color(-60905));
        testNameLabel.setText(" Контрольная: ");
        testNamePanel.add(testNameLabel);
        testName = new JLabel();
        Font testNameFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 16, testName.getFont());
        if (testNameFont != null) testName.setFont(testNameFont);
        testName.setForeground(new Color(-16777216));
        testName.setText("Label");
        testNamePanel.add(testName);
        testDate = new JPanel();
        testDate.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        testDate.setBackground(new Color(-1));
        testDate.setForeground(new Color(-1));
        nearestTest.add(testDate);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-60905));
        label2.setText(" Дата:                ");
        testDate.add(label2);
        date = new JLabel();
        Font dateFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 16, date.getFont());
        if (dateFont != null) date.setFont(dateFont);
        date.setForeground(new Color(-16777216));
        date.setText("Label");
        testDate.add(date);
        testDescription = new JPanel();
        testDescription.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        testDescription.setBackground(new Color(-1));
        testDescription.setForeground(new Color(-1));
        nearestTest.add(testDescription);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-60905));
        label3.setText("Повторить:    ");
        testDescription.add(label3);
        description = new JLabel();
        description.setEnabled(true);
        Font descriptionFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 16, description.getFont());
        if (descriptionFont != null) description.setFont(descriptionFont);
        description.setForeground(new Color(-16777216));
        description.setText("Label");
        testDescription.add(description);
        bottomControlButtons = new JPanel();
        bottomControlButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bottomControlButtons.setAlignmentX(1.0f);
        bottomControlButtons.setAlignmentY(1.0f);
        bottomControlButtons.setAutoscrolls(true);
        bottomControlButtons.setBackground(new Color(-20442));
        bottomControlButtons.setMaximumSize(new Dimension(40, 40));
        bottomControlButtons.setMinimumSize(new Dimension(40, 40));
        bottomControlButtons.setPreferredSize(new Dimension(40, 40));
        mainPanel.add(bottomControlButtons, BorderLayout.SOUTH);
        needsForTommorow = new JPanel();
        needsForTommorow.setLayout(new BorderLayout(0, 0));
        needsForTommorow.setBackground(new Color(-20442));
        needsForTommorow.setForeground(new Color(-20442));
        mainPanel.add(needsForTommorow, BorderLayout.EAST);
        nearestNeed = new JPanel();
        nearestNeed.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        nearestNeed.setBackground(new Color(-20442));
        nearestNeed.setForeground(new Color(-20442));
        nearestNeed.setPreferredSize(new Dimension(450, 100));
        needsForTommorow.add(nearestNeed, BorderLayout.WEST);
        homeWorkLabel = new JPanel();
        homeWorkLabel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        homeWorkLabel.setBackground(new Color(-1));
        homeWorkLabel.setForeground(new Color(-20442));
        nearestNeed.add(homeWorkLabel);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Times New Roman", Font.BOLD | Font.ITALIC, 22, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-60905));
        label4.setHorizontalAlignment(0);
        label4.setHorizontalTextPosition(0);
        label4.setText("Домашние работы на завтра");
        homeWorkLabel.add(label4);
        homeWorkNamePanel = new JPanel();
        homeWorkNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        homeWorkNamePanel.setBackground(new Color(-1));
        homeWorkNamePanel.setForeground(new Color(-1));
        nearestNeed.add(homeWorkNamePanel);
        final JLabel label5 = new JLabel();
        label5.setBackground(new Color(-12828863));
        Font label5Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-60905));
        label5.setText(" Контрольная: ");
        homeWorkNamePanel.add(label5);
        homeWorkName = new JLabel();
        Font homeWorkNameFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 16, homeWorkName.getFont());
        if (homeWorkNameFont != null) homeWorkName.setFont(homeWorkNameFont);
        homeWorkName.setForeground(new Color(-16777216));
        homeWorkName.setText("Label");
        homeWorkNamePanel.add(homeWorkName);
        homeWorkDate = new JPanel();
        homeWorkDate.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        homeWorkDate.setBackground(new Color(-1));
        homeWorkDate.setForeground(new Color(-1));
        nearestNeed.add(homeWorkDate);
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-60905));
        label6.setText(" Дата:                ");
        homeWorkDate.add(label6);
        hwDate = new JLabel();
        Font hwDateFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 16, hwDate.getFont());
        if (hwDateFont != null) hwDate.setFont(hwDateFont);
        hwDate.setForeground(new Color(-16777216));
        hwDate.setText("Label");
        homeWorkDate.add(hwDate);
        homeWorkDescription = new JPanel();
        homeWorkDescription.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        homeWorkDescription.setBackground(new Color(-1));
        homeWorkDescription.setForeground(new Color(-1));
        nearestNeed.add(homeWorkDescription);
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setForeground(new Color(-60905));
        label7.setText("Повторить:    ");
        homeWorkDescription.add(label7);
        hwDescription = new JLabel();
        hwDescription.setEnabled(true);
        Font hwDescriptionFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 16, hwDescription.getFont());
        if (hwDescriptionFont != null) hwDescription.setFont(hwDescriptionFont);
        hwDescription.setForeground(new Color(-16777216));
        hwDescription.setText("Label");
        homeWorkDescription.add(hwDescription);
        centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout(0, 0));
        centralPanel.setBackground(new Color(-20442));
        centralPanel.setForeground(new Color(-20442));
        centralPanel.setVisible(false);
        mainPanel.add(centralPanel, BorderLayout.CENTER);
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
