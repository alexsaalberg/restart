import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MediumCircle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MediumCircle extends ConeEnemy
{
    protected int walkDistanceX = 900;
        protected int walkDistanceY = 900;
        protected int startPointX;
        protected int startPointY;
        private boolean movingDown = true;
        private boolean movingUp = false;
        private boolean movingRight = false;
        private boolean movingLeft = false;
    /**
     * Act - do whatever the MediumCircle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        if(!isTurning()){
            directionMove(getDirection(), 3);
            //System.out.println(getTargetDirection());
            //System.out.println(getDirection());
        }
        if(movingDown&&getScrollingY()>startPointY+walkDistanceY){
            movingRight = true;
            movingDown = false;
            smoothSetDirection(270);
            
        }else if(movingLeft&&getScrollingX()<startPointX){
            movingLeft = false;
            movingDown = true;
            smoothSetDirection(0);
        
        }else if(movingUp&&getScrollingY()<startPointY){
            movingUp =false;
            movingLeft= true;
            smoothSetDirection(90);
        
        }else if(movingRight&&getScrollingX()>startPointX+walkDistanceX){
            movingRight =false;
            movingUp= true;
            smoothSetDirection(180);
        
        }
       
        // Add your action code here.
    }    
    protected void directionMove(int direction, int speed)
    {
        
        int moveX = -1*(int)Math.sin(Math.toRadians(direction));
        int moveY = (int)Math.cos(Math.toRadians(direction));
        setLocation(getX()+speed*moveX,getY()+speed*moveY);
    }
    
    protected void addedToWorld(World world){
        super.addedToWorld(world);
        setSightLength(250);
        setSightAngle(30);
        startPointY = (int) getScrollingY();
        startPointX = (int) getScrollingX();
    }
}
