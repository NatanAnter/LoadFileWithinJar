import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class GameSense extends JPanel {
    public static final String[] MUSIC_FOLDERS_NAMES = {"gameBackgroundMusic", "gameBoosMusic", "homeBackgroundMusic", "homeWinnerMusic"};
    public static final String MUSIC_LOCATION = "resources/music";
    private List<List<Sound>> MUSIC;
    public static final String IMAGES_JAR_LOCATIONS = "/resources/images/";
    public static final String  IMAGES_LOCATIONS= "resources\\images\\";
    public static final String IMAGE_NAME = "redWon.png";
    public BufferedImage image;
    public GameSense() {
        try {
            this.setBackground(Color.GREEN);
            String protocol = this.getClass().getResource(this.getClass().getName()+".class").getProtocol();
            System.out.println(protocol);
            if(protocol.equals("jar")){
                JOptionPane.showConfirmDialog(null, "great, we know that you are using jar!" ,"running within jar", JOptionPane.YES_NO_OPTION);
                loadImageFromJar();
                this.MUSIC = loadSoundsFromJar(MUSIC_LOCATION, MUSIC_FOLDERS_NAMES);

            } else if (protocol.equals("file")) {
                System.out.println("great U R using the IDE");
                loadImage();
                this.MUSIC = loadSoundsFromIntellij(MUSIC_LOCATION, MUSIC_FOLDERS_NAMES);
            }
        }
        catch (Exception ignored){}
        try {
            MUSIC.get(0).get(0).play();

        }catch (Exception ignored){}

    }
    public void loadImageFromJar(){
        BufferedImage img = null;
        try {
            URL url = getClass().getResource(IMAGES_JAR_LOCATIONS + IMAGE_NAME);
            System.out.println(url);
            InputStream inputStream = getClass().getResourceAsStream(IMAGES_JAR_LOCATIONS + IMAGE_NAME);
            System.out.println(inputStream);
            JOptionPane.showConfirmDialog(null, url ,String.valueOf(inputStream), JOptionPane.YES_NO_OPTION);

            img = ImageIO.read(inputStream);

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, IMAGES_JAR_LOCATIONS + IMAGE_NAME + "  " + e, "didnt find image", JOptionPane.YES_NO_OPTION);
        }
        this.image = img;
    }
    public void loadImage(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(IMAGES_LOCATIONS + IMAGE_NAME));

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, IMAGES_JAR_LOCATIONS + IMAGE_NAME + "  " + e, "didnt find image", JOptionPane.YES_NO_OPTION);
        }
        this.image = img;
    }
    public List<List<Sound>> loadSoundsFromIntellij(String folderName, String[] foldersNames) {
        List<List<Sound>> allMusic = new ArrayList<>();
        for (String innerFolderName : foldersNames) {
            File innerFolder = new File(folderName + "\\" + innerFolderName);
            if (innerFolder.isDirectory()) {
                List<Sound> musicLevel = new ArrayList<>();
                for (File musicFile : Objects.requireNonNull(innerFolder.listFiles())) {
                    if (musicFile.isFile()) {
                        musicLevel.add(new Sound(musicFile.getPath()));
                    }
                }
                allMusic.add(musicLevel);
            }
        }
        return allMusic;
    }
    public List<List<Sound>> loadSoundsFromJar(String folderName, String[] foldersNames){
        ////not working
        List<List<Sound>> allMusic = new ArrayList<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            for (String innerFolderName : foldersNames) {
                InputStream folderStream = classLoader.getResourceAsStream(folderName + "/" + innerFolderName);
                if (folderStream != null) {
                    List<Sound> musicLevel = new ArrayList<>();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(folderStream, StandardCharsets.UTF_8));
                    String musicFileName;
                    while ((musicFileName = reader.readLine()) != null) {
                        musicLevel.add(new Sound(musicFileName));
                    }

                    allMusic.add(musicLevel);
                }
            }
        }catch (Exception e){
            System.out.println("Error in loading sound: " + e);
        }
        return allMusic;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        AffineTransform at = AffineTransform.getTranslateInstance(Window.WINDOW_WIDTH / 2.0 - 100 / 2.0, Window.WINDOW_HEIGHT / 10.0);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(image, at, null);


    }
}