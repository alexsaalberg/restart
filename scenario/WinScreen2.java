import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.InputStream;
import java.awt.Color;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WinScreen2 extends World
{

    private int stars;
    private GreenfootSound musicPlayer;

    public WinScreen2(int newStars, GreenfootSound oldMusicPlayer)
    {    

        super(800, 600, 1); 
        stars=newStars;

        musicPlayer = null;

        if(testOldMusic(oldMusicPlayer,musicPlayer))
        {
            musicPlayer = oldMusicPlayer;
        }
        else
        {
            if(oldMusicPlayer!=null){
                oldMusicPlayer.stop();
            }
            if(musicPlayer!=null)
                musicPlayer.play();
        }

        File f = new File("HighScore.txt");

        try{
            Scanner sc = new Scanner(f);
            int i = sc.nextInt();
            if(stars>i)
            {
                GreenfootImage img1 = new GreenfootImage("NEW HIGHSCORE",24, Color.RED, Color.WHITE);
                img1.setColor(Color.RED);
                getBackground().drawImage(img1, 400-img1.getWidth()/2, 480);
                
                f = new File("HighScore.txt");

                FileWriter writer = new FileWriter(f);

                writer.write(i+48);

                writer.close();


                String content = "This is the content to write into file";

                f.delete();

                if (!f.exists()) {
                    f.createNewFile();
                }

                FileWriter fw = new FileWriter(f.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(""+stars,0,(""+stars).length());
                bw.close();

                
                GreenfootImage img2 = new GreenfootImage("Highscore: "+stars+" stars",24, Color.BLACK, Color.WHITE);
                getBackground().drawImage(img2, 400-img2.getWidth()/2, 560);
            }
            else{
            GreenfootImage img2 = new GreenfootImage("Highscore: "+i+" stars",24, Color.BLACK, Color.WHITE);
            getBackground().drawImage(img2, 400-img2.getWidth()/2, 560);
            }
            
            GreenfootImage img3 = new GreenfootImage("Your Score: "+stars+" stars",24, Color.BLACK, Color.WHITE);
            getBackground().drawImage(img3, 400-img3.getWidth()/2, 520);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*
     * Tests to see if the music from the old level is the same as the music from the new level.
     * If the music are the same AND it is playing it will return true, meaning that the music shouldn't stop playing.
     * If else it will return false, and the new music will start playing.
     */
    private boolean testOldMusic(GreenfootSound oldMusicPlayer, GreenfootSound newMusicPlayer){

        if(oldMusicPlayer==null)
            return false;

        if(newMusicPlayer==null){
            return true;
        }

        String tempString = oldMusicPlayer.toString();
        String oldFileName = tempString.substring(tempString.indexOf("file: ")+6,tempString.indexOf("Is playing: ")-3);
        String isPlaying = tempString.substring(tempString.indexOf("Is playing: ")+12, tempString.length());
        String newMusicString = newMusicPlayer.toString();
        String newFileName = newMusicString.substring(newMusicString.indexOf("file: ")+6,newMusicString.indexOf("Is playing: ")-3);

        if(isPlaying.equals("true")&&oldFileName.equals(newFileName))
            return true;

        //return true; 

        return false; //uncomment this and comment out ("return true;") to reactivate music.

    }
}
