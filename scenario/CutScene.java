import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.InputStream;


public class CutScene extends World
{

    private int lives;
    private int level;
    private int stars;
    private int frame = 1;
    private GreenfootSound musicPlayer;
    
    public CutScene(int newLives, int newLevel, int newStars, GreenfootSound oldMusicPlayer)
    {    
        
        super(800, 600, 1); 
        lives=newLives;
        level=newLevel;
        stars=newStars;
        
        String imageName = ("CutScene"+level+"-"+frame+".PNG");
                    if(fileExists(imageName))
                    {
                        
                    setBackground(new GreenfootImage(imageName));
                    frame++;
                    }
                    else
                    {
                        System.out.println(imageName);
                    Greenfoot.setWorld(new JobWorld(lives, level, stars, musicPlayer));
                    }
        
        
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
        
    }
    
    public boolean fileExists(String fileName){
    File f = new File("Images/"+fileName);

      if(f.exists()){
          return true;
      }else{
          return false;
        }
    }
    
    public void act(){
        if(Greenfoot.mouseClicked(this)){
                    String imageName = ("CutScene"+level+"-"+frame+".PNG");
                    if(fileExists(imageName))
                    {
                        
                    setBackground(new GreenfootImage(imageName));
                    frame++;
                    }
                    else
                    {
                    Greenfoot.setWorld(new JobWorld(lives, level, stars, musicPlayer));
                    }
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
