import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InfoScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InfoScreen extends World
{

    /**
     * Constructor for objects of class InfoScreen.
     * 
     */
    public InfoScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
       
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this))   
        {
            Greenfoot.setWorld(new CutScene(3,1,0,null));  
        }
    }
}
