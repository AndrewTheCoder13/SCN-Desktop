package Program;

import Program.SchoolNeeds.HomeWork;
import Program.SchoolNeeds.Need;
import Program.SchoolNeeds.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class GettingNeedFrame implements FrameClass {
    private boolean test;
    private JPanel mainPanel;
    private JPanel central;
    private JLabel label;
    private JPanel button;
    private JPanel descriptionOfNeed;
    private JPanel homeWorkNamePanel;
    private JLabel homeWorkName;
    private JPanel homeWorkDate;
    private JLabel hwDate;
    private JPanel homeWorkDescription;
    private JTextArea hwDescription;
    private JCheckBox isEnded;
    private Need need;
    private static int monitorWidth;
    private static int monitorHeight;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public GettingNeedFrame(Need need, boolean test) {
        this.need = need;
        this.test = test;
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
        setingNeed();
    }

    private void initializationOfPanels() {
        descriptionOfNeed.setLayout(new BoxLayout(descriptionOfNeed, BoxLayout.Y_AXIS));
        hwDescription.setAlignmentY(Component.CENTER_ALIGNMENT);
        int heigh = (int) ((int) monitorHeight * 0.1);
        int width = (int) ((int) monitorWidth * 0.1);
        String path = "src/main/resources/pictures/homework.png";
        InputStream in = GettingNeedFrame.class.getResourceAsStream("/pictures/homework.png");
        /*File file = new File(path);*/
        Image img = null;
        try {
            img = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension d = new Dimension();
        d.setSize(width, heigh);
        JLabel labelImage = new JLabel(new ImageIcon(img));
        label.setPreferredSize(d);
        central.add(labelImage, BorderLayout.EAST);
        Font mainFont = new Font("Times New Roman", Font.BOLD, 18);
        if (test) {
            label.setText("Тест");
        } else {
            label.setText("Задание");
        }
        Component spacer = Box.createVerticalStrut(heigh);
        mainPanel.add(spacer, BorderLayout.NORTH);
        spacer = Box.createVerticalStrut(heigh);
        mainPanel.add(spacer, BorderLayout.SOUTH);
        spacer = Box.createHorizontalStrut(width);
        mainPanel.add(spacer, BorderLayout.WEST);
        spacer = Box.createHorizontalStrut(width);
        mainPanel.add(spacer, BorderLayout.EAST);
        Dimension dim = new Dimension();
        dim.setSize(700, 400);
        central.setBorder(new RoundEdgedBorder());
        central.setPreferredSize(dim);
        central.setMaximumSize(dim);
        central.setMinimumSize(dim);
        dim.setSize(50, 50);
        JButton back = new JButton("Назад");
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setFont(mainFont);
        back.setBackground(Color.white);
        back.setPreferredSize(dim);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        if (need.isEnded()) {
            isEnded.setSelected(true);
        }
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isEnded.isSelected()) {
                    if (test) {
                        Test test = Test.getTestTreeSet().get(need.getNeedName());
                        test.setEnded(true);
                        Test.setTestTreeSet(test, test.getNeedName());
                    } else {
                        HomeWork test = HomeWork.getHWTreeSet().get(need.getNeedName());
                        test.setEnded(true);
                        HomeWork.setHWTreeSet(test, test.getNeedName());
                    }
                } else {
                    if (test) {
                        Test test = Test.getTestTreeSet().get(need.getNeedName());
                        test.setEnded(false);
                        Test.setTestTreeSet(test, test.getNeedName());
                    } else {
                        HomeWork test = HomeWork.getHWTreeSet().get(need.getNeedName());
                        test.setEnded(false);
                        HomeWork.setHWTreeSet(test, test.getNeedName());
                    }
                }
                Main.goBack();
                /*Main.setMainFrame();*/
            }
        });
        central.add(back, BorderLayout.SOUTH);
    }

    private void setingNeed() {
        String preMounths = "Января Февраля Марта Апреля Мая Июня Июля Августа Сентября Октбября Ноября Декабря";
        String[] mounths = preMounths.split(" ");
        String s = need.getNeedName();
        homeWorkName.setText(s);
        s = need.getNeedDescription();
        String[] elements = s.split("\\s+");
        StringBuilder finalString = new StringBuilder();
        int count = 0;
        for (int a = 0; a < elements.length; a++) {
            String element = elements[a];
            finalString.append(element + " ");
            count += element.length() + 1;
            if (count > 50) {
                finalString.append("\n");
                count = 0;
            }
        }
        String g = finalString.toString();

        hwDescription.setText(g);
        Calendar date1 = need.getImplementationDate();
        String year = Integer.toString(date1.get(Calendar.YEAR));
        String mounth = mounths[date1.get(Calendar.MONTH)];
        String day = Integer.toString(date1.get(Calendar.DAY_OF_MONTH));
        s = day + " " + mounth + " " + year;
        hwDate.setText(s);
        int heigh = (int) ((int) monitorHeight * 0.1);
        Component spacer = Box.createVerticalStrut(heigh);
        descriptionOfNeed.add(spacer);
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
        label.setText("Label");
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
        descriptionOfNeed.add(homeWorkDate);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-60905));
        label2.setText(" Дата:                ");
        homeWorkDate.add(label2);
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
        descriptionOfNeed.add(homeWorkDescription);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-60905));
        label3.setText("Повторить:    ");
        homeWorkDescription.add(label3);
        hwDescription = new JTextArea();
        Font hwDescriptionFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 16, hwDescription.getFont());
        if (hwDescriptionFont != null) hwDescription.setFont(hwDescriptionFont);
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
        label4.setText("Выполнено:    ");
        panel1.add(label4);
        isEnded = new JCheckBox();
        isEnded.setBackground(new Color(-1));
        Font isEndedFont = this.$$$getFont$$$("Arial Narrow", Font.BOLD | Font.ITALIC, 14, isEnded.getFont());
        if (isEndedFont != null) isEnded.setFont(isEndedFont);
        isEnded.setForeground(new Color(-16777216));
        isEnded.setLabel("Да");
        isEnded.setText("Да");
        panel1.add(isEnded);
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
