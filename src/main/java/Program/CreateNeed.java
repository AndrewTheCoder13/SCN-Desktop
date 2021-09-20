package Program;

import Program.SchoolNeeds.HomeWork;
import Program.SchoolNeeds.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateNeed implements FrameClass {
    private JPanel mainPanel;
    private JPanel central;
    private JLabel label;
    private JPanel descriptionOfNeed;
    private JTextPane hwDescription;
    private JPanel button;
    private JPanel homeWorkNamePanel;
    private JPanel homeWorkDate;
    private JPanel homeWorkDescription;
    private JRadioButton test;
    private JRadioButton work;
    private JTextPane hwDate;
    private JTextPane homeWorkName;
    private static int monitorWidth;
    private static int monitorHeight;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CreateNeed() {
        getMonitorSize();
        intialization();
    }

    public void getMonitorSize() {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        monitorHeight = sSize.height;
        monitorWidth = sSize.width;
    }

    public void intialization() {
        initializationOfPanels();
    }

    private void initializationOfPanels() {
        addListners();
        formatingCentralPane();
        formationOfButttons();
    }

    private void addListners() {
        homeWorkName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (homeWorkName.getText().equals("Просто нажми сюда :)")) {
                    homeWorkName.setText("");
                    mainPanel.updateUI();
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (homeWorkName.getText().equals("")) {
                    homeWorkName.setText("Просто нажми сюда :)");
                    mainPanel.updateUI();
                }
            }
        });
        hwDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (hwDate.getText().equals("Просто нажми сюда :) (формат даты ДД.ММ.ГГ)")) {
                    hwDate.setText("");
                    mainPanel.updateUI();
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (hwDate.getText().equals("")) {
                    hwDate.setText("Просто нажми сюда :) (формат даты ДД.ММ.ГГ)");
                    mainPanel.updateUI();
                }
            }
        });
        hwDescription.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (hwDescription.getText().equals("Просто нажми сюда :)")) {
                    hwDescription.setText("");
                    mainPanel.updateUI();
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (hwDescription.getText().equals("")) {
                    hwDescription.setText("Просто нажми сюда :)");
                    mainPanel.updateUI();
                }
            }
        });

        work.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (test.isSelected()) {
                    test.setSelected(false);
                }
            }
        });

        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (work.isSelected()) {
                    work.setSelected(false);
                }
            }
        });
    }

    private void formatingCentralPane() {
        descriptionOfNeed.setLayout(new BoxLayout(descriptionOfNeed, BoxLayout.Y_AXIS));
        hwDescription.setAlignmentY(Component.CENTER_ALIGNMENT);
        int heigh = (int) ((int) monitorHeight * 0.1);
        int width = (int) ((int) monitorWidth * 0.1);
        String path = "src/main/resources/pictures/homework.png";
        InputStream in = CreateNeed.class.getResourceAsStream("/pictures/homework.png");
        /*File file = new File(path);*/
        Image img = null;
        try {
            img = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension dim = new Dimension();
        dim.setSize(width, heigh);
        JLabel labelImage = new JLabel(new ImageIcon(img));
        label.setPreferredSize(dim);
        central.add(labelImage, BorderLayout.EAST);
        Font mainFont = new Font("Times New Roman", Font.BOLD, 18);
        Component spacer = Box.createVerticalStrut(heigh);
        mainPanel.add(spacer, BorderLayout.NORTH);
        spacer = Box.createVerticalStrut(heigh);
        mainPanel.add(spacer, BorderLayout.SOUTH);
        spacer = Box.createHorizontalStrut(width);
        mainPanel.add(spacer, BorderLayout.WEST);
        spacer = Box.createHorizontalStrut(width);
        mainPanel.add(spacer, BorderLayout.EAST);
        dim = new Dimension();
        dim.setSize(700, 400);
        central.setBorder(new RoundEdgedBorder());
        central.setPreferredSize(dim);
        central.setMaximumSize(dim);
        central.setMinimumSize(dim);
        dim.setSize(50, 50);
    }

    private void formationOfButttons() {
        Font mainFont = new Font("Times New Roman", Font.BOLD, 18);
        Dimension dim = new Dimension();
        dim.setSize(50, 50);
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Apply our own painting effect
                Graphics2D g2d = (Graphics2D) g.create();
                // 50% transparent Alpha
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth() - 8, getHeight() - 8);

                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(0, 0, 250, 255));

        Component spac = Box.createHorizontalStrut((int) (monitorWidth * 0.05));
        panel.add(spac);
        JButton back = new JButton("Назад");
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setFont(mainFont);
        back.setBackground(Color.white);
        back.setPreferredSize(dim);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.goBack();
                /*Main.setMainFrame();*/
            }
        });
        panel.add(back);
        spac = Box.createHorizontalStrut((int) (monitorWidth * 0.48));
        panel.add(spac);
        JButton add = new JButton("Сохранить");
        add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add.setFont(mainFont);
        add.setBackground(Color.white);
        add.setPreferredSize(dim);
        add.setBorderPainted(false);
        add.setFocusPainted(false);
        add.setContentAreaFilled(false);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ((!homeWorkName.getText().equals("Просто нажми сюда :)")) &&
                        (!hwDate.getText().equals("Просто нажми сюда :) (формат даты ДД.ММ.ГГ)")) &&
                        (!hwDescription.getText().equals("Просто нажми сюда :)"))) {
                    if ((work.isSelected()) || (test.isSelected())) {
                        if (work.isSelected()) {
                            HomeWork work = new HomeWork();
                            String name = homeWorkName.getText();
                            String description = hwDescription.getText();
                            String date = hwDate.getText();
                            String[] elementsOfDate = date.trim().split("\\.");
                            if (elementsOfDate.length == 1) {
                                JOptionPane.showMessageDialog(mainPanel, "Неправильно указана дата. Используйте как разделитель точки",
                                        "Неправильно указана дата", JOptionPane.ERROR_MESSAGE);
                            } else {
                                int mounth = Integer.parseInt(fitsZiro(elementsOfDate[1])) - 1;
                                int day = Integer.parseInt(fitsZiro(elementsOfDate[0]));
                                int year = Integer.parseInt(elementsOfDate[2]);
                                if ((mounth > 12) || (day > 31)) {
                                    JOptionPane.showMessageDialog(mainPanel, "Неправильно указана дата!",
                                            "Неправильно указана дата", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    Calendar cal = new GregorianCalendar(year, mounth, day);
                                    Date curentDate = new Date();
                                    Date setedDate = cal.getTime();
                                    if (curentDate.after(setedDate)) {
                                        JOptionPane.showMessageDialog(mainPanel, "Неправильно указана дата. Задание не может иметь дату раньшей нынешней",
                                                "Неправильно указана дата", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        work.setNeedName(name);
                                        work.setNeedDescription(description);
                                        work.setImplementationDate(cal);
                                        work.setStatus("Активно");
                                        if (HomeWork.getHWTreeSet().containsKey(name)) {
                                            JOptionPane.showMessageDialog(mainPanel, "Домашняя работа с таким именем уже существует. Пожалуйста, видоизмените его :)",
                                                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            HomeWork.setHWTreeSet(work, work.getNeedName());
                                            Main.setMainFrame();
                                        }
                                    }
                                }
                            }
                        } else {
                            Test work = new Test();
                            String name = homeWorkName.getText();
                            String description = hwDescription.getText();
                            String date = hwDate.getText();
                            String[] elementsOfDate = date.trim().split("\\.");
                            if (elementsOfDate.length == 1) {
                                JOptionPane.showMessageDialog(mainPanel, "Неправильно указана дата. Используйте как разделитель точки",
                                        "Неправильно указана дата", JOptionPane.ERROR_MESSAGE);
                            } else {
                                int mounth = Integer.parseInt(fitsZiro(elementsOfDate[1])) - 1;
                                int day = Integer.parseInt(fitsZiro(elementsOfDate[0]));
                                int year = Integer.parseInt(elementsOfDate[2]);
                                if ((mounth > 12) || (day > 31)) {
                                    JOptionPane.showMessageDialog(mainPanel, "Неправильно указана дата!",
                                            "Неправильно указана дата", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    Calendar cal = new GregorianCalendar(year, mounth, day);
                                    Date curentDate = new Date();
                                    Date setedDate = cal.getTime();
                                    if (curentDate.after(setedDate)) {
                                        JOptionPane.showMessageDialog(mainPanel, "Неправильно указана дата. Задание не может иметь дату раньшей нынешней",
                                                "Неправильно указана дата", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        work.setNeedName(name);
                                        work.setNeedDescription(description);
                                        work.setImplementationDate(cal);
                                        work.setStatus("Активно");
                                        if (Test.getTestTreeSet().containsKey(name)) {
                                            JOptionPane.showMessageDialog(mainPanel, "Тест с таким именем уже существует. Пожалуйста, видоизмените его :)",
                                                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            Test.setTestTreeSet(work, work.getNeedName());
                                            Main.setMainFrame();
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Тип работы не указан",
                                "Не указан тип работы", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Заполните все поля своими значениями",
                            "Неправильный ввод", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panel.add(add);
        panel.setBackground(Color.WHITE);
        central.add(panel, BorderLayout.SOUTH);
    }

    public static String fitsZiro(String s) {
        int i = s.indexOf("0") + 1;
        if (i == 1) {
            return s.substring(1);
        } else return s;
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
        central.setMaximumSize(new Dimension(700, 400));
        central.setMinimumSize(new Dimension(0, 0));
        central.setPreferredSize(new Dimension(700, 400));
        central.setVisible(true);
        mainPanel.add(central, BorderLayout.CENTER);
        label = new JLabel();
        Font labelFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 28, label.getFont());
        if (labelFont != null) label.setFont(labelFont);
        label.setForeground(new Color(-16777216));
        label.setHorizontalAlignment(0);
        label.setHorizontalTextPosition(0);
        label.setText("Добавление задания");
        central.add(label, BorderLayout.NORTH);
        button = new JPanel();
        button.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        central.add(button, BorderLayout.SOUTH);
        descriptionOfNeed = new JPanel();
        descriptionOfNeed.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        descriptionOfNeed.setBackground(new Color(-1));
        descriptionOfNeed.setForeground(new Color(-20442));
        central.add(descriptionOfNeed, BorderLayout.WEST);
        homeWorkNamePanel = new JPanel();
        homeWorkNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        homeWorkNamePanel.setBackground(new Color(-1));
        homeWorkNamePanel.setForeground(new Color(-1));
        descriptionOfNeed.add(homeWorkNamePanel);
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-12828863));
        Font label1Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-60905));
        label1.setText("Предмет:          ");
        homeWorkNamePanel.add(label1);
        homeWorkName = new JTextPane();
        Font homeWorkNameFont = this.$$$getFont$$$(null, Font.BOLD | Font.ITALIC, 16, homeWorkName.getFont());
        if (homeWorkNameFont != null) homeWorkName.setFont(homeWorkNameFont);
        homeWorkName.setText("Просто нажми сюда :)");
        homeWorkNamePanel.add(homeWorkName);
        homeWorkDate = new JPanel();
        homeWorkDate.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        homeWorkDate.setBackground(new Color(-1));
        homeWorkDate.setForeground(new Color(-1));
        descriptionOfNeed.add(homeWorkDate);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-60905));
        label2.setText(" Дата:                ");
        homeWorkDate.add(label2);
        hwDate = new JTextPane();
        Font hwDateFont = this.$$$getFont$$$(null, Font.BOLD | Font.ITALIC, 16, hwDate.getFont());
        if (hwDateFont != null) hwDate.setFont(hwDateFont);
        hwDate.setText("Просто нажми сюда :) (формат даты ДД.ММ.ГГ)");
        homeWorkDate.add(hwDate);
        homeWorkDescription = new JPanel();
        homeWorkDescription.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        homeWorkDescription.setBackground(new Color(-1));
        homeWorkDescription.setForeground(new Color(-1));
        descriptionOfNeed.add(homeWorkDescription);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-60905));
        label3.setText("Повторить:    ");
        homeWorkDescription.add(label3);
        hwDescription = new JTextPane();
        Font hwDescriptionFont = this.$$$getFont$$$(null, Font.BOLD | Font.ITALIC, 16, hwDescription.getFont());
        if (hwDescriptionFont != null) hwDescription.setFont(hwDescriptionFont);
        hwDescription.setText("Просто нажми сюда :)");
        homeWorkDescription.add(hwDescription);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel1.setBackground(new Color(-1));
        panel1.setForeground(new Color(-1));
        descriptionOfNeed.add(panel1);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-60905));
        label4.setText("Тип задания");
        panel1.add(label4);
        test = new JRadioButton();
        test.setBackground(new Color(-1));
        Font testFont = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 16, test.getFont());
        if (testFont != null) test.setFont(testFont);
        test.setForeground(new Color(-16777216));
        test.setText("Тест");
        panel1.add(test);
        work = new JRadioButton();
        work.setBackground(new Color(-1));
        Font workFont = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 16, work.getFont());
        if (workFont != null) work.setFont(workFont);
        work.setForeground(new Color(-16777216));
        work.setText("Домашняя работа");
        panel1.add(work);
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
