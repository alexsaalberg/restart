import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level4Hallway3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level4Hallway3 extends ConeEnemy
{
    private int startPointX;
    private int startPointY;
    
    /**
     * Act - do whatever the Level4Hallway3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        setDirection(getDirection()+3);
    }    
    protected void addedToWorld(World world){
        super.addedToWorld(world);
        setSightLength(250);
        setSightAngle(30);
        startPointY = (int) getScrollingY();
        startPointX = (int) getScrollingX();
    }
}
