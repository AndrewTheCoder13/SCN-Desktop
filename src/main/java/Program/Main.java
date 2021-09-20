package Program;

import Program.LoandingClasses.Loader;
import Program.SchoolNeeds.HomeWork;
import Program.SchoolNeeds.Need;
import Program.SchoolNeeds.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class Main {
    public static JFrame mainFrame = new JFrame("SchoolOrganizer LOADING...");
    public static Test nearTest;
    public static HomeWork randomWork;
    public static TreeMap<String, HomeWork> hwForTomorow = new TreeMap<>();
    public static TreeMap<String, Test> testForTomorow = new TreeMap<>();
    private static String needsForAdd;
    private static File needFile = new File("src/main/resources/needs.json");
    private static GettingNeedFrame gettingNeedFrame;
    private static CreateNeed createNeed;
    private static ListFrame listFrame;
    private static ArrayList<FrameClass> lastFrameOpened = new ArrayList<>();
    private static Timer timer;


    public static void main(String[] args) {
        try {
            URL url = Main.class.getResource("");
            needFile =  new File(url.toURI().getPath() + "needs.json");
            loadingFrame();
        HomeWork.maidTreeMap();
        Test.maidTreeMap();
        needsForAdd = Loader.parseNeeds();
        intializationOfNeeds(needsForAdd);
        Thread.sleep(5000);
        loadingFrameClose();
        getNeedsForTomorow();
        randomWork = searchWork();
        nearTest = searchNearTest();
        mainFrameOpen();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void loadingFrame() throws IOException {
        mainFrame.setSize(570, 570);
        mainFrame.setLocationRelativeTo(null);
        String path = "src/main/resources/pictures/Banner.png";
        InputStream in = Main.class.getResourceAsStream("/pictures/Banner.png");
        Image img = ImageIO.read(in);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setHorizontalAlignment(0);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for(int i = 0; i < 256; i++){
                    Color color = new Color(0, 0, 0, i);
                    label.setForeground(color);
                }
            }
        };
        long delay = 100L;
        java.util.Timer time = new java.util.Timer();
        in = Main.class.getResourceAsStream("/pictures/icon2.png");
        ImageIcon icon = new ImageIcon(ImageIO.read(in));
        mainFrame.setIconImage(icon.getImage());
        mainFrame.setIconImage(icon.getImage());
        mainFrame.getContentPane().setBackground(Color.ORANGE);
        mainFrame.getContentPane().add(label);
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(MAXIMIZED_BOTH);
        time.schedule(timerTask, delay);
    }

    private static void intializationOfNeeds(String needs) throws ParseException {

        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(needs);
            jsonArray.forEach(need -> {
                JSONObject needObject = (JSONObject) need;
                boolean test = (boolean) needObject.get("test");
                if(test){
                    String name = (String) needObject.get("name");
                    String description = (String) needObject.get("description");
                    String date = (String) needObject.get("date");
                    addToTest(name, description, date);
                } else {
                    String name = (String) needObject.get("name");
                    String description = (String) needObject.get("description");
                    String date = (String) needObject.get("date");
                    boolean importance = (boolean) needObject.get("importance");
                    addHomeWork(name, description, date, importance);
                }
            });
        } catch (Exception e){

        }
    }

    private static void addToTest(String name, String description, String date){
        Test test = new Test();
        test.setNeedName(name);
        test.setNeedDescription(description.trim());
        String[] elementsOfDate = date.trim().split("\\.");
        int mounth = Integer.parseInt(fitsZiro(elementsOfDate[1])) - 1;

        int day = Integer.parseInt(elementsOfDate[0]);
        int year = Integer.parseInt(elementsOfDate[2]);
        Calendar cal = new GregorianCalendar(year, mounth, day);
        test.setImplementationDate(cal);
        String status = getStatus(cal);
        test.setStatus(status);
        test.setImportant(true);
        Test.setTestTreeSet(test, name);
    }

    private static void addHomeWork(String name, String description, String date, boolean importance){
        HomeWork homeWork = new HomeWork();
        homeWork.setNeedName(name);
        homeWork.setNeedDescription(description.trim());
        String[] elementsOfDate = date.trim().split("\\.");
        int mounth = Integer.parseInt(fitsZiro(elementsOfDate[1])) - 1;
        String day1 = fitsZiro(elementsOfDate[0]);
        int day = Integer.parseInt(day1);
        int year = Integer.parseInt(elementsOfDate[2]);
        Calendar cal = new GregorianCalendar(year, mounth, day);
        homeWork.setImplementationDate(cal);
        homeWork.setImportant(importance);
        String status = getStatus(cal);
        homeWork.setStatus(status);
        HomeWork.setHWTreeSet(homeWork, name);
    }

    private static String fitsZiro(String s){
        int i = s.indexOf("0") + 1;
        if(i == 1){
            return s.substring(1);
        } else return s;
    }

    private static String getStatus(Calendar cal){
        Calendar calendar = Calendar.getInstance();
        if(cal.before(calendar)){
            return "Просрочено";
        } else {
            return "Активно";
        }
    }

    private  static void loadingFrameClose() throws IOException{
        mainFrame.setTitle("SchoolOrganaiser");
        mainFrame.getContentPane().removeAll();
    }

    private static void getNeedsForTomorow(){
        TreeMap<String, HomeWork> h = HomeWork.getHWTreeSet();
        TreeMap<String, Test> t = Test.getTestTreeSet();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String[] s = date.toString().split(" ");
        int day = Integer.parseInt(s[2]);
        for(String key : h.keySet()){
            HomeWork work = h.get(key);
            Calendar calendar1 = work.getImplementationDate();
            Date date1 = calendar1.getTime();
            String[] s1 = date1.toString().split(" ");
            int day1 = Integer.parseInt(s1[2]);
            if( ((day1-day) == 1) || ((day1 == 1) && (day == 31)) || ((day1 == 1) && (day == 30)) ||
                    ((day1 == 1) && (day == 29)) || ((day1 == 1) && (day == 28))){
                hwForTomorow.put(key, work);
            }
        }
        for(String key : t.keySet()){
            Test test = t.get(key);
                testForTomorow.put(key, test);
        }
    }

    private static HomeWork searchWork(){
        TreeMap<String, HomeWork> map = HomeWork.getHWTreeSet();
        int i = 1;
        HomeWork random = new HomeWork();
        for(String s : map.keySet()){
            random = map.get(s);
            Date date = new Date();
            int day = Integer.parseInt(date.toString().split(" ")[2]);
            Calendar cal = random.getImplementationDate();
            Date date2 = cal.getTime();
            int day2 = Integer.parseInt(date2.toString().split(" ")[2]);
            if( (day2-day) != 1){
                random = new HomeWork();
                continue;
            }
        }
        if(random.getNeedName() == null){
            return null;
        }
        return random;
    }

    private static Test searchNearTest(){
        TreeMap<String, Test> map = Test.getTestTreeSet();
        Test min = null;
        List<Test> needs = new ArrayList<>();
        for(String s : map.keySet()){
            needs.add(map.get(s));
        }
        int i = 0;
        for(Test o : needs){
            if((i == 0) && (o.getStatus().equals("Активно"))){
                min = o;
                i++;
                continue;
            }
            if (i!=0) {
                if ((o.getImplementationDate().before(min.getImplementationDate())) && (o.getStatus().equals("Активно"))) {
                    min = o;
                }
            }
        }
        return min;
    }

    public static void mainFrameOpen(){
        MainFrame frame = null;
        try {
            frame = new MainFrame(nearTest, randomWork, hwForTomorow ,testForTomorow);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastFrameOpened.add(frame);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JSONArray array = new JSONArray();
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(needFile);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                needFile.delete();
                try {
                    needFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                TreeMap<String, HomeWork> homeWorkTreeMap = HomeWork.getHWTreeSet();
                for(String key : homeWorkTreeMap.keySet()) {
                   HomeWork work = homeWorkTreeMap.get(key);
                   if( (work.getStatus().equals("Активно")) && (!work.isEnded())){
                       JSONObject need = new JSONObject();
                       String[] preDate = work.getImplementationDate().getTime().toString().split(" ");
                       Date date = work.getImplementationDate().getTime();
                       Calendar cal = Calendar.getInstance();
                       cal.setTime(date);
                       String date2 = preDate[2] + "." + (cal.get(Calendar.MONTH) + 1 ) + "." + preDate[5];
                       need.put("test", false);
                       need.put("importance", work.isImportant());
                       need.put("date", date2);
                       need.put("description", work.getNeedDescription().trim());
                       need.put("name", work.getNeedName().trim());
                       array.add(need);
                   }
                }
                TreeMap<String, Test> testTreeMap = Test.getTestTreeSet();
                for(String key : testTreeMap.keySet()) {
                    JSONObject need = new JSONObject();
                    Test work = testTreeMap.get(key);
                    if( (work.getStatus().equals("Активно")) && (!work.isEnded()) ){
                        String[] preDate = work.getImplementationDate().getTime().toString().split(" ");
                        Date date = work.getImplementationDate().getTime();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        String date2 = preDate[2] + "." + (cal.get(Calendar.MONTH) + 1 ) + "." + preDate[5];
                        need.put("test", true);
                        need.put("importance", work.isImportant());
                        need.put("date", date2);
                        need.put("description", work.getNeedDescription().trim());
                        need.put("name", work.getNeedName().trim());
                        array.add(need);
                    }
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonElement je = JsonParser.parseString(array.toJSONString());
                writer.write(gson.toJson(je));
                writer.flush();
                writer.close();
            }
        });
        mainFrame.setContentPane(frame.getMainPanel());
         mainFrame.revalidate();
         mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         Dimension minimumSize = new Dimension();
         minimumSize.setSize(893, 563);
         mainFrame.setMinimumSize(minimumSize);
         mainFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
         mainFrame.setBackground(new Color(255,177,37));

    }

    public static void gettingNeedFrame(Need need, boolean test){
        gettingNeedFrame = new GettingNeedFrame(need, test);
        lastFrameOpened.add(gettingNeedFrame);
        mainFrame.setContentPane(gettingNeedFrame.getMainPanel());
        mainFrame.revalidate();
    }

    public static void setMainFrame(){
        randomWork = searchWork();
        nearTest = searchNearTest();
        getNeedsForTomorow();
        MainFrame frame = null;
        try {
            frame = new MainFrame(nearTest, randomWork, hwForTomorow, Test.getTestTreeSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastFrameOpened.add(frame);
        mainFrame.setContentPane(frame.getMainPanel());
        mainFrame.revalidate();
    }

    public static void setAddFrame(){
        createNeed = new CreateNeed();
        lastFrameOpened.add(createNeed);
        mainFrame.setContentPane(createNeed.getMainPanel());
        mainFrame.revalidate();
    }

    public static void setListFrame(){
        listFrame = new ListFrame();
        lastFrameOpened.add(listFrame);
        mainFrame.setContentPane(listFrame.getMainPanel());
        mainFrame.revalidate();
    }

    public static void goBack(){
        int length = lastFrameOpened.size() - 1;
        lastFrameOpened.remove(length);
        length--;
        FrameClass frameClass = lastFrameOpened.get(length);
        String s = frameClass.toString().substring(8, frameClass.toString().indexOf("@"));
        if(s.equals("MainFrame")){
            setMainFrame();
        }
        if(s.equals("ListFrame")){
            setListFrame();
        }
        if(s.equals("CreateNeed")){
            setAddFrame();
        }
    }

}

