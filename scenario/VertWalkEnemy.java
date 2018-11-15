import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class vertWalkEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VertWalkEnemy extends ConeEnemy
{
    /**
     * Act - do whatever the vertWalkEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int walkDistance = 300;
    private int startPoint;
    private boolean movingDown = true;
    
    public void act() 
    {
        super.act();
        if(!isTurning()){
            
        if(movingDown){
        directionMove(getDirection(), 3);
        }
        else{
        directionMove(getDirection(), 3);
        }
        if(movingDown&&getScrollingY()>startPoint+walkDistance)
        {
            movingDown = false;
            smoothSetDirection(180);
        }
        else if(!movingDown&&getScrollingY()<startPoint)
        {
            movingDown = true;
            smoothSetDirection(0);
        }
        
        }
        // Add your action code here.

    }   
    
    private void directionMove(int direction, int speed)
    {
        
        int moveX = (int)Math.sin(Math.toRadians(direction));
        int moveY = (int)Math.cos(Math.toRadians(direction));
        setLocation(getX()+speed*moveX,getY()+speed*moveY);
    }
    
    protected void addedToWorld(World world){
        super.addedToWorld(world);
        setSightLength(250);
        setSightAngle(30);
        startPoint = (int) getScrollingY();
    }
}
