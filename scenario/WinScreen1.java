import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.InputStream;


public class WinScreen1 extends World
{

    private int stars;
    private GreenfootSound musicPlayer;
    
    public WinScreen1(int newStars, GreenfootSound oldMusicPlayer)
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
        
    }
    
    public boolean fileExists(String fileName){
    File f = new File("Images/"+fileName);

      if(f.exists()){
          return true;
      }else{
          return false;
        }
    }
    
        public void act() 
    {
        if(Greenfoot.mouseClicked(this))   
        {
            Greenfoot.setWorld(new WinScreen2(stars ,musicPlayer));  
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
