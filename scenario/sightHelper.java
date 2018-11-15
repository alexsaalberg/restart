import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.geom.*;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;

/*
var endpoints;      # list of endpoints, sorted by angle
var open = [];      # list of walls, sorted by distance

loop over endpoints:
    remember which wall is nearest
    add any walls that BEGIN at this endpoint to 'walls'
    remove any walls that END at this endpoint from 'walls'
    
    SORT the open list
    
    if the nearest wall changed:
        fill the current triangle and begin a new one
        */
       
       
public class sightHelper extends Actor
{
    private ArrayList<Point2D.Double> endPoints = new ArrayList<Point2D.Double>();
    private ArrayList<Line2D.Double> walls = new ArrayList<Line2D.Double>();
    
    /**
     * Act - do whatever the sightHelper wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    private void pointLoop(){
        
    }
}
