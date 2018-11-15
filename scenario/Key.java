import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Key here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Key extends CollectableActor
{
    /**
     * Act - do whatever the Key wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    public void collected(){
        JobWorld tempWorld = (JobWorld) getWorld();
        if(tempWorld.getObjects(Key.class).size()==1){
            tempWorld.advanceLevel();
        }
        else{
            tempWorld.removeObject(this);
        }
    }
}
