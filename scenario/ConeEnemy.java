import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Base class for enemies using a cone sight. Draws a triangle from the center of the actor using the parameters length and angle.
 * 
 * @AlexSaalberg 
 * 
 */
public class ConeEnemy extends Enemy
{
    /**
     * Act - do whatever the vigilantMan wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    
    private int sightLength = 200; //(In pixels/coordinate units) How far the lines of the sight go from the enemy.
    private int sightAngle = 60; //(In Degrees) How wide the sight is.
    private boolean turning = false;
    private int direction = 0;
    private int targetDirection = 0;
    private int turnSpeed = 6; //Degrees per step; 
    private GuardSight guardSight;
    
    public void act() 
    {
        if(updateGuardSight(sightLength))
        {
            JobWorld tempWorld = (JobWorld) getWorld();
            tempWorld.playerIsCaught();
        }
        if(isTurning())
        smoothTurn();
    }    
    
    public void setTurning(boolean newTurning){
        turning = newTurning;
    }
    
    public boolean isTurning(){
        return turning;
    }
    
    public void setTargetDirection(int newTargetDirection){
        targetDirection = newTargetDirection;
    }
    
    public int getTargetDirection(){
        return targetDirection;
    }
    
    public void setTurnSpeed(int newSpeed){
        turnSpeed = newSpeed;
    }
    
    public int getDirection(){
        return direction;
    }
    public void setSightLength(int newSightLength){
        sightLength=newSightLength;
    }

    public void setSightAngle(int newSightAngle){
        sightAngle=newSightAngle;
    }
    
    public int getSightAngle(){
        return sightAngle;
    }
    
    public int getSightLength(){
        return sightLength;
    }
    
    public GuardSight getGuardSight(){
        return guardSight;
    }
    
    public void setSightDirection(int r){
        direction = r;
    }
    
    public void smoothSetDirection(int newDirection){
        turning = true;
        setTargetDirection(newDirection);
        smoothTurn();
    }
    
    public void setDirection(int newDirection){
        direction = newDirection;
    }
    
    //@AlexSaalberg
    public void smoothTurn(){
        
        if(direction<0)
        direction+=360;
        direction = direction%360;
        
        int deltaDirection = targetDirection - direction;
        
        //Test if within one turn
        if(Math.abs(deltaDirection)<=turnSpeed){
            direction = targetDirection;
            turning = false;
            return;
        }
        
        if(deltaDirection<0)
        deltaDirection+=360;
        
        if(deltaDirection>=180)
        direction-=turnSpeed; //Turn left; (counter-clockwise)
        else
        {
        direction+=turnSpeed; //Turn right; (clockwise)
        }
        
    }
    
    /*
     * Changes the img of the guardSight object to accurately display the sight of this enemy in this step.
     * @AlexSaalberg
     */
    public boolean updateGuardSight(int length){
        if(length <= 0)
        return false;
        
        
        //Calculates the points of the triangle that is this enemies sight, using it's sightLength and sightAngle.
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        xPoints[0] = (int) getExactX();
        yPoints[0] = (int) getExactY();
        xPoints[1] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()+sightAngle/2)));
        yPoints[1] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()+sightAngle/2)));
        xPoints[2] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()-sightAngle/2)));
        yPoints[2] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()-sightAngle/2)));
        
        JobWorld tempWorld = (JobWorld) getWorld();
        if(detectWall(length)){
            return updateGuardSight(length-10);
        }
        else{
        boolean temp = tempWorld.isPlayerInPolygon(xPoints,yPoints,3);    
            
        //Draws a triangle using the points just calculated.
        xPoints[0] = length;
        yPoints[0] = length;
        xPoints[1] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()+sightAngle/2)));
        yPoints[1] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()+sightAngle/2)));
        xPoints[2] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()-sightAngle/2)));
        yPoints[2] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()-sightAngle/2)));
        
        GreenfootImage img = new GreenfootImage(length*2,length*2);
        img.setColor(Color.blue);
        img.fillPolygon(xPoints,yPoints,3);
        img.setTransparency(60);
        guardSight.setImage(img);
        guardSight.setLocation(getX(),getY());
        
        return temp;
        
        }
        
        
    }
    
    /*
     * Detects whether the player is within the tirangle that is this enemies sight, which is determined using the sightAngle and sightDistance variables.
     * @AlexSaalberg
     */
    public boolean detectPlayer(int length){
        //Calculates the points of the triangle that is this enemies sight, using it's sightLength and sightAngle.
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        xPoints[0] = (int) getExactX();
        yPoints[0] = (int) getExactY();
        xPoints[1] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()+sightAngle/2)));
        yPoints[1] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()+sightAngle/2)));
        xPoints[2] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()-sightAngle/2)));
        yPoints[2] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()-sightAngle/2)));
        
        //Uses a method in the Jobworld class to detect if the player is within the triangle.
        JobWorld tempWorld = (JobWorld) getWorld();
        return tempWorld.isPlayerInPolygon(xPoints,yPoints, 3);
    }
    
    //@AlexSaalberg
    public boolean detectWall(int length){
        //Calculates the points of the triangle that is this enemies sight, using it's sightLength and sightAngle.
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        xPoints[0] = (int) getExactX();
        yPoints[0] = (int) getExactY();
        xPoints[1] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()+sightAngle/2)));
        yPoints[1] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()+sightAngle/2)));
        xPoints[2] = xPoints[0] + (int) (length*Math.sin(Math.toRadians(-getDirection()-sightAngle/2)));
        yPoints[2] = yPoints[0] + (int) (length*Math.cos(Math.toRadians(-getDirection()-sightAngle/2)));
        
        //Uses a method in the Jobworld class to detect if the player is within the triangle.
        JobWorld tempWorld = (JobWorld) getWorld();
        return tempWorld.isWallInPolygon(xPoints,yPoints, 3);
    }
    
    //@AlexSaalberg
    protected void addedToWorld(World world){
        
        //Create guardSight, an object that will display the image corresponding to this enemies sight.
        guardSight = new GuardSight();
        
        //Add guardsight to world
        World tempWorld = getWorld();
        tempWorld.addObject(guardSight,getX(),getY());
        
    }
}
