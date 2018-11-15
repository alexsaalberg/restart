import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SecurityCamera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SecurityCamera extends ConeEnemy
{
    /**
     * Act - do whatever the SecurityCamera wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int angle1 = 245;
    private int angle2 = 295;
    
    public void act() 
    {
        super.act();
        if(getDirection()==angle1)
        smoothSetDirection(angle2);
        else if(getDirection()==angle2)
        smoothSetDirection(angle1);
    }    
    
    public void setAngle1(int newAngle1){
        angle1 = newAngle1;
    }
    
    public void setAngle2(int newAngle2){
        angle2 = newAngle2;
    }
    
    protected void addedToWorld(World world){
            super.addedToWorld(world);
            GreenfootImage img = getImage();
            img.rotate(90);
            setSightLength(240);
            setSightAngle(80);
            setDirection(angle1);
            setTurnSpeed(1);
    }
    
    
}
