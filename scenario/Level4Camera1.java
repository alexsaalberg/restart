 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SecurityCamera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level4Camera1 extends ConeEnemy
{
    /**
     * Act - do whatever the SecurityCamera wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        // Add your action code here.
    }    
    
    protected void addedToWorld(World world){
            super.addedToWorld(world);
            setSightLength(200);
            setSightAngle(90);
    }
    
    
}
