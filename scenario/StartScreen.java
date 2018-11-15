import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartScreen extends World
{

    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {  
        super(800, 600, 1); 
    }

    public void act()
    {
        //Restart the game if the user clicks the mouse anywhere in the screen
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(Greenfoot.mouseClicked(this)&&mi.getX()>250&&mi.getX()<450&&mi.getY()>375&&mi.getY()<425)   
        {
            Greenfoot.setWorld(new InfoScreen());  
        }
        if(Greenfoot.mouseClicked(this)&&mi.getX()>250&&mi.getX()<475&&mi.getY()>450&&mi.getY()<500)   
        {
            Greenfoot.setWorld(new Instructions(this));  
        }
    }


    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
}
