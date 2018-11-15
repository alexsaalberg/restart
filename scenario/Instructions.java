import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instructions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instructions extends World
{

    /**
     * Constructor for objects of class Instructions.
     * 
     */
    private StartScreen startscreen;
    
    public Instructions(StartScreen s)
    {    
        super(800, 600, 1); 
        startscreen =s;
    }
    
     public void act()
    {
        //Restart the game if the user clicks the mouse anywhere in the screen
        if(Greenfoot.mouseClicked(this))   
        {
            Greenfoot.setWorld(startscreen);  
        }
    }
}
