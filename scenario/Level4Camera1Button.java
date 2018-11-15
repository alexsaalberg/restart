import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class CameraButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level4Camera1Button extends CollectableActor
{
    /**
     * Act - do whatever the CameraButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    
    public void collected(){
        World tempWorld = getWorld();
        tempWorld.removeObjects(tempWorld.getObjects(Level4Camera1.class));
        List<Actor> tempList = tempWorld.getObjects(GuardSight.class);
        for (Actor temp : tempList) {
        temp.setLocation(10000,10000);
    }
        tempWorld.removeObject(this);
    
    }
}
