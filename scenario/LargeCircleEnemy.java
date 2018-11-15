import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LargeCircleEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LargeCircleEnemy extends ConeEnemy
{
    protected int walkDistanceX = 1300;
    protected int walkDistanceY = 1300;
    protected int startPointX;
    protected int startPointY;
    private boolean movingDown = false;
    private boolean movingUp = false;
    private boolean movingRight = true;
    private boolean movingLeft = false;
    /**
     * Act - do whatever the LargeCircleEnemy wants to do. This method is called whenever
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
            movingDown = false;
            movingLeft = true;
            smoothSetDirection(90);
        }else if(movingLeft&&getScrollingX()<startPointX){
            movingLeft = false;
            movingUp = true;
            smoothSetDirection(180);
        }else if(movingUp&&getScrollingY()<startPointY){
            movingUp =false;
            movingRight= true;
            smoothSetDirection(270);
        }else if(movingRight&&getScrollingX()>startPointX+walkDistanceX){
            movingRight =false;
            movingDown= true;
            smoothSetDirection(0);
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
        setDirection(270);
        startPointY = (int) getScrollingY();
        startPointX = (int) getScrollingX();
    }
}
