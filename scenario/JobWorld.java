import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Polygon;
import java.awt.color.ColorSpace;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * Write a description of class Jobworld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JobWorld extends ScrollingWorld
{

    private int levelWidth;
    private int levelHeight;
    private int levelMinWidth;
    private int levelMinHeight;
    private Lives life1;
    private Lives life2;
    private Lives life3;
    private int lives;
    private int level;
    private int stars;
    private int starsThisLife;
    private GreenfootSound musicPlayer;

    public JobWorld(int newLives, int newLevel, int newStars, GreenfootSound newMusicPlayer){
        super(800,600,1);
        setPaintOrder(Lives.class,SolidActor.class,Column.class,Wall.class,Statue.class,SmallWall.class);

        level = newLevel;
        stars = newStars;
        setLives(newLives);

        levelWidth = 6000;
        levelHeight = 6000;
        levelMinWidth = -3000;
        levelMinHeight = -3000;
        setScrollingBackground(new GreenfootImage("tile.png"));

        musicPlayer = newMusicPlayer;

        setLevel(level);

        //System.out.println(stars);

    }

    public void gotStar(){
        starsThisLife++;
    }

    /*
     * Tests to see if the music from the old level is the same as the music from the new level.
     * If the music are the same AND it is playing it will return true, meaning that the music shouldn't stop playing.
     * If else it will return false, and the new music will start playing.
     * 
     */
    private boolean testOldMusic(GreenfootSound oldMusicPlayer, GreenfootSound newMusicPlayer){

        if(oldMusicPlayer==null)
            return false;

        if(newMusicPlayer==null){
            return true;
        }

        String tempString = oldMusicPlayer.toString();
        String oldFileName = tempString.substring(tempString.indexOf("file: ")+6,tempString.indexOf("Is playing: ")-3);
        String isPlaying = tempString.substring(tempString.indexOf("Is playing: ")+12, tempString.length());
        String newMusicString = newMusicPlayer.toString();
        String newFileName = newMusicString.substring(newMusicString.indexOf("file: ")+6,newMusicString.indexOf("Is playing: ")-3);

        if(isPlaying.equals("true")&&oldFileName.equals(newFileName))
            return true;

        //return true; 

        return false; //uncomment this and comment out ("return true;") to reactivate music.

    }

    private void changeMusic(GreenfootSound newMusicPlayer){
        GreenfootSound oldMusicPlayer = musicPlayer;

        if(testOldMusic(oldMusicPlayer,newMusicPlayer))
        {
            musicPlayer = oldMusicPlayer;
        }
        else
        {
            if(oldMusicPlayer!=null)
                oldMusicPlayer.stop();
            musicPlayer = newMusicPlayer;

            if(musicPlayer!=null)
                musicPlayer.playLoop();
        }
    }

    public int getLevelWidth(){
        return levelWidth;
    }

    public int getLevelMinWidth(){
        return levelMinWidth;
    }

    public int getLevelHeight(){
        return levelHeight;
    }

    public int getLevelMinHeight(){
        return levelMinHeight;
    }

    public boolean isPlayerInRange(int testX,int testY,int testRange){
        //List<Player> tempList = getObjects(Player.class);
        for (Object obj : getObjects(Character.class))  
        {  
            Character ps = (Character) obj; // sub-casting
            if(Math.sqrt(Math.pow((ps.getExactX()-testX),2)+Math.pow((ps.getExactY()-testY),2))<testRange)
                return true;
        }
        return false;
    }

    public boolean isPlayerBoxInPolygon(int[] xPoints, int[] yPoints, int nPoints){
        Polygon pgon = new Polygon(xPoints,yPoints,nPoints);

        /*
        int[] xPointsPlayer = new int[4];
        int[] yPointsPlayer = new int[4];
         */

        for (Object obj : getObjects(Character.class))  
        {  
            Character ps = (Character) obj; // sub-casting  

            int playerWidth = ps.getWidth();
            int playerHeight = ps.getHeight();
            double playerX = ps.getExactX();
            double playerY = ps.getExactY();

            Rectangle2D.Double playerRect = new Rectangle2D.Double(playerX-playerWidth/2,playerY-playerHeight/2,playerWidth,playerHeight);

            if(pgon.intersects(playerRect))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerInPolygon(int[] xPoints, int[] yPoints, int nPoints){
        Polygon pgon = new Polygon(xPoints,yPoints,nPoints);

        for (Object obj : getObjects(Character.class))  
        {  
            Character ps = (Character) obj; // sub-casting  

            if(pgon.contains(ps.getExactX(),ps.getExactY()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isWallInPolygon(int[] xPoints, int[] yPoints, int nPoints){
        Polygon pgon = new Polygon(xPoints,yPoints,nPoints);

        for (Object obj : getObjects(SolidActor.class))  
        {  
            SolidActor ps = (SolidActor) obj; // sub-casting  

            if(pgon.contains(ps.getX(),ps.getY()))
            {
                return true;
            }
        }
        return false;
    }

    public void playerIsCaught(){
        removeLife();
        starsThisLife = 0;
    }

    public void removeLife(){
        lives-=1;
        if(lives==2||lives==1){
            endGame();
        }
        if(lives==0){
            removeObject(life1); //gameover
            gameLose();
        }
    }    

    public void heartsCheck(){
        if(lives==2){
            removeObject(life3);
        }
        if(lives==1){
            removeObject(life3);
            removeObject(life2);
        }
        if(lives==0){
            removeObject(life3);
            removeObject(life2);
            removeObject(life1); //gameover
            gameLose();
        }
    }    

    public void endGame(){
        GameOver go = new GameOver(lives, level, stars, musicPlayer);
        Greenfoot.setWorld(go);
    }

    public void gameLose(){
        LosingScreen go = new LosingScreen(lives, level, stars, musicPlayer);
        Greenfoot.setWorld(go);
    }

    public void setLives(int newLives){
        lives=newLives;
    }

    public int getLives(){
        return lives;
    }

    public void resetLives(){
        life1 = new Lives();
        addObject(life1, 750, 35);
        life2 = new Lives();
        addObject(life2, 690, 35);
        life3 = new Lives();
        addObject(life3, 630, 35);

    }

    public void setLevel(int newLevel){

        removeObjects(getObjects(null));

        resetLives();
        heartsCheck();

        stars+=starsThisLife;
        starsThisLife = 0;

        level = newLevel;

        //level height, width, & minimums
        //level background;
        //music stuff

        switch(newLevel){
            case 1: {               
                //Lincoln Memorial
                levelWidth=680;
                levelHeight=600;
                levelMinWidth=-360;
                levelMinHeight=-270;

                changeMusic(new GreenfootSound("InGame.mp3"));

                for(int i=0; i<=4; i++){
                    Column column = new Column();
                    addObject(column, 50, 200*i+50);
                    Column column1 = new Column();
                    addObject(column1, 200*i+50, 850);
                }
                for(int i=0; i<=5; i++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 315, 100*i+50);
                }
                for(int i=0; i<=7; i++){
                    if(i!=4&&i!=5){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 315+100*i, 575);
                    }        
                }

                for(int i=0; i <= 10; i++){                     //level boundaries left vertical
                    BlackWall wall = new BlackWall();
                    addObject(wall, -70, -90+(i*100));
                }
                for(int i=0; i <= 10; i++){                     //level boundaries right vertical
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1150, -90+(i*100));
                }
                for(int i=0; i <= 12; i++){                     //level boundaries top horizontal
                    BlackWall wall = new BlackWall();
                    addObject(wall, -70+(i*100), -90);
                }
                for(int i=0; i <= 12; i++){                     //level boundaries bottom horizontal
                    BlackWall wall = new BlackWall();
                    addObject(wall, -70+(i*100), 960);
                }

                Statue statue = new Statue();
                addObject(statue, 790, 136);
                statue.setLocation(762, 120);
                statue.setLocation(768, 118);
                statue.setLocation(767, 120);
                Key key = new Key();
                addObject(key, 773, 45);
                key.setLocation(763, 32);

                SecurityCamera camera1 = new SecurityCamera();
                addObject(camera1, 510, 300);
                camera1.setRotation(270);

                SecurityCamera camera3 = new SecurityCameraLeft();
                addObject(camera3, 930, 300);

                addObject(new BlackWall(), 415, 300);
                addObject(new BlackWall(), 1015, 300);
                addObject(new CameraButton(),1015,800);

                SecurityCamera camera = new SecurityCamera();
                addObject(new Star(), 1015, 700);
                addObject(new Star(), 550, 300);
                addObject(new Star(), 890, 300);

                addObject(new VertWalkEnemy(),170,400);
                Character player = new Character();
                addObject(player, 170, 30);
                

                break;
            }
            case 3:{
                //Obama's House
                levelWidth = 2000;
                levelHeight = 1765;
                levelMinWidth = -275;
                levelMinHeight = -165;

                setScrollingBackground(new GreenfootImage("carpet.png"));
                changeMusic(new GreenfootSound("InGame.mp3"));

                //Top left corner
                for(int j=-2; j<=-1; j++){
                    for(int i=-3; i<=-1; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50+(j*100));
                    }
                }

                for(int j=-3; j<=0; j++){
                    //Top
                    for(int i=0; i<=21; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50+(j*100));
                    }

                    //Left
                    for(int i=0; i<=18; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(j*100),50+(i*100));
                    }      

                }
                for(int j=0; j<=17; j++){
                    //Right
                    for(int i=21; i<=24; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50+(j*100));
                    }
                }
                for(int j=18; j<=21; j++){
                    //Bottom
                    for(int i=1; i<=21; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100),50+(j*100));
                    }      

                }

                //Left-Middle Part
                for(int j=10; j<=11; j++){
                    for(int i=1; i<=11; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50+(j*100));
                    }
                }

                //Top-Middle Part
                for(int i=1; i<=7; i++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 50+(9*100),50+(i*100));
                }
                for(int i=10; i<=13; i++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 50+(i*100),50+(5*100));
                }
                for(int i=10; i<=13; i++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 50+(i*100),50+(6*100));
                }
                BlackWall wall = new BlackWall();
                addObject(wall, 50+(10*100), 50+(7*100));
                BlackWall wall2 = new BlackWall();
                addObject(wall2, 50+(11*100), 50+(7*100));

                BlackWall wall3 = new BlackWall();
                addObject(wall3, 50+(13*100), 50+(1*100));
                BlackWall wall4 = new BlackWall();
                addObject(wall4, 50+(13*100), 50+(2*100));

                //Right middle
                for(int i=14; i<=20; i++){
                    BlackWall wall5 = new BlackWall();
                    addObject(wall5, 50+(i*100),50+(9*100));
                }

                for(int i=12; i<=15; i++){
                    BlackWall wall5 = new BlackWall();
                    addObject(wall5, 50+(4*100),50+(i*100));
                }
                for(int i=6; i<=11; i++){
                    BlackWall wall5 = new BlackWall();
                    addObject(wall5, 50+(i*100),50+(14*100));
                }
                for(int i=15; i<=17; i++){
                    BlackWall wall5 = new BlackWall();
                    addObject(wall5, 50+(11*100),50+(i*100));
                }
                BlackWall wall5 = new BlackWall();
                addObject(wall5, 50+(6*100),50+(15*100));

                for(int i=13; i<=19; i+=3){
                    BlackWall wall6 = new BlackWall();
                    addObject(wall6, 50+(i*100),50+(14*100));
                    BlackWall wall7 = new BlackWall();
                    addObject(wall7, 50+((i+1)*100),50+(14*100));
                }

                Car car1 = new Car();
                addObject(car1, 5*100, 4*100);
                Car car2 = new Car();
                addObject(car2, 5*100, 8*100);
                Cabinet cabinet =new Cabinet();
                addObject(cabinet, 50+2*100, 50+12*100);

                //Desk
                Cabinet cabinet1 =new Cabinet();
                addObject(cabinet1, 50+9*100, 50+15*100);
                Bookshelf bookshelf = new Bookshelf();
                addObject(bookshelf, 50+10*100, 50+16*100);

                //Toilet
                White white = new White();
                addObject(white, 50+11*100,50+1*100);

                //Sink1
                White white1 = new White();
                addObject(white1, 50+10*100,50+4*100);
                White white2 = new White();
                addObject(white2, 50+11*100,50+4*100);

                //Bed
                for(int j=1; j<=5; j++){
                    for(int i=16; i<=18; i++){
                        White white3 = new White();
                        addObject(white3, 50+i*100,50+j*100);
                    }
                }

                //Fridge
                White white3 = new White();
                addObject(white3, 50+12*100,50+16*100);
                White white4 = new White();
                addObject(white4, 50+12*100,50+17*100);

                //Sink2       
                for(int i=15; i<=18; i++){
                    White white5 = new White();
                    addObject(white5, 50+i*100,50+16*100);
                }

                //Couch1
                Cabinet cabinet2 =new Cabinet();
                addObject(cabinet2, 50+16*100, 50+10*100);

                //Couch2
                Cabinet cabinet3 =new Cabinet();
                addObject(cabinet3, 50+16*100, 50+12*100);

                //Couch3
                for(int i=10; i<=12; i++){
                    Bookshelf bookshelf1 = new Bookshelf();
                    addObject(bookshelf1, 50+19*100, 50+i*100);
                }

                Key key = new Key();
                addObject(key, 7*100+50, 15*100+50);   
                addObject(new Star(), 2050, 1050);
                addObject(new Star(), 1250, 150);
                addObject(new Star(), 1650, 1650);

                Character character = new Character();
                addObject(character, 150, 150);

                addObject(new CircleCarEnemy(),500,400);
                addObject(new CircleCarEnemy(),500,800);
                addObject(new DeskEnemy(),500,1500);
                addObject(new SinkEnemy(),1700,1700);
                addObject(new CouchEnemy(),1600,1300);
                addObject(new BedEnemy(),1750,400);

                break;
            }

            case 6:{
                //White House
                levelWidth = 4200;
                levelHeight = 2330;
                levelMinWidth = -275;
                levelMinHeight = -165;

                setScrollingBackground(new GreenfootImage("carpet.png"));
                changeMusic(new GreenfootSound("InGame.mp3"));

                for(int j=-1; j<=1; j++){
                    for(int i=-3; i<=18; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50+(j*100));
                    }
                    for(int i=21; i<=47; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50+(j*100));
                    }
                }
                for(int j=2; j<=27; j++){
                    //Left
                    for(int i=-3; i<=0; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100),50+(j*100));
                    }      
                    //Right
                    for(int i=44; i<=47; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100),50+(j*100));
                    }
                }
                for(int j=23; j<=25; j++){
                    //Bottom
                    for(int i=1; i<=46; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100),50+(j*100));
                    }      

                }

                //Map Walls
                /**for(int i=10; i<=38; i++){
                SmallWall swall = new SmallWall();
                addObject(swall, 125+(i*50),625);
                }
                for(int i=10; i<=38; i++){
                SmallWall swall = new SmallWall();
                addObject(swall, 125+(i*50),575);
                }
                 **/
                for(int i=10; i<=38; i++){
                    for (int j=0; j<=1; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 150+(i*100), 950+(j*100));
                    }
                }
                for(int j=0; j<=9; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1050, 250+(j*100));
                }
                for(int j=12; j<=19; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1050, 250+(j*100));
                }
                for(int j=12; j<=16; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1050, 1450+(j*100));
                }
                for(int j=18; j<=20; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1050, 1650+(j*100));
                }
                for(int i=10; i<=27; i++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 150+(i*100), 1550);
                }
                for(int j=0; j<=6; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1650, 1650+(j*100));
                }
                for(int j=0; j<=2; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3350, 250+(j*100));
                }
                for(int j=4; j<=6; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3350, 250+(j*100));
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3350, 1150);
                }
                for(int j=12; j<=18; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3350, 250+(j*100));
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3250, 1550);
                }
                for(int i=0; i<=9; i++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3450+(i*100), 1550);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3250, 1950);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3150, 1950);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2750, 1650);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2750, 2250);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 3350, 2250);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 4050, 2250);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 4050, 2050);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 4050, 1950);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 4050, 1850);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 4050, 1650);
                }
                for(int j=16; j<=18; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2750, 250+(j*100));
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2850, 1950);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2950, 1950);
                }

                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1050,-50);
                }

                //Oval Office
                for(int j=0; j<=1; j++){
                    for(int i=0; i<=1; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 1950+(i*100), -50+(j*100));
                    }
                }
                for(int j=0; j<=1; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1750, 250+(j*100));
                }
                for(int j=0; j<=1; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2250, 250+(j*100));
                }
                for(int j=3; j<=5; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1750, 250+(j*100));
                }
                for(int j=3; j<=5; j++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2250, 250+(j*100));
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 2150, 750);
                }
                {
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1850, 750);
                }
                for(int i=0; i<=5; i++){
                    BlackWall wall = new BlackWall();
                    addObject(wall, 1750+(i*100), 850);
                }

                //Characters
                {
                    Character character = new Character();
                    addObject(character, 250, 350);
                }
                {
                    Level4Hallway e1 = new Level4Hallway();
                    addObject(e1, 1500, 1100);
                }
                {
                    Level4Hallway2 e2 = new Level4Hallway2();
                    addObject(e2, 1500, 1400);
                }
                {
                    Level4Hallway3 e3 = new Level4Hallway3();
                    addObject(e3, 1500, 1250);
                }
                {
                    Level4Hallway3 e4 = new Level4Hallway3();
                    addObject(e4, 2200, 1250);
                }
                {
                    Level4Hallway3 e5 = new Level4Hallway3();
                    addObject(e5, 2900, 1250);
                }
                {
                    Level4Hallway3 e6 = new Level4Hallway3();
                    addObject(e6, 3200, 1700);
                }
                {
                    Level4Hallway3 e7 = new Level4Hallway3();
                    addObject(e7, 3200, 2100);
                }
                {
                    Level4Hallway3 e8 = new Level4Hallway3();
                    addObject(e8, 2150, 400);
                }
                {
                    Level4KeyCamera e9 = new Level4KeyCamera();
                    addObject(e9, 2150, 50);
                }
                {
                    CameraButton b = new CameraButton();
                    addObject(b, 1550, 800);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 1600, 1600);
                }
                {
                    Bookshelf s1 = new Bookshelf();
                    addObject(s1, 1600, 1700);
                }
                {
                    Bookshelf s2 = new Bookshelf();
                    addObject(s2, 1600, 1800);
                }
                {
                    Bookshelf s3 = new Bookshelf();
                    addObject(s3, 1300, 1700);
                }
                {
                    Bookshelf s4 = new Bookshelf();
                    addObject(s4, 1400, 1700);
                }
                {
                    Bookshelf s5 = new Bookshelf();
                    addObject(s5, 1400, 1800);
                }
                {
                    Bookshelf s6 = new Bookshelf();
                    addObject(s6, 1400, 1900);
                }
                {
                    Bookshelf s7 = new Bookshelf();
                    addObject(s7, 1400, 2000);
                }
                {
                    Level4Camera1 cc = new Level4Camera1();
                    addObject(cc, 1500, 1500);
                }
                {
                    Level4Camera1Button cb = new Level4Camera1Button();
                    addObject(cb, 1900, 1600);
                }
                {
                    Level4RoomPat rp = new Level4RoomPat();
                    addObject(rp, 1300, 1800);
                }
                for(int i=0; i<=3; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 1400+(i*100), 700);
                }
                {
                    CircleCarEnemy cce = new CircleCarEnemy();
                    addObject(cce, 1300, 500);
                }
                {
                    CircleCarEnemy cce = new CircleCarEnemy();
                    addObject(cce, 1300, 500);
                }
                {
                    CircleCarEnemy cce = new CircleCarEnemy();
                    addObject(cce, 1300, 200);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2100, 300);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2200, 300);
                }
                for(int j=0; j<=5; j++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2000, 1600+(j*100));
                }
                for(int i=0; i<=5; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2200+(i*100), 1700);
                }
                for(int j=0; j<=3; j++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2700, 1800+(j*100));
                }
                {
                    CircleCarEnemy cce = new CircleCarEnemy();
                    addObject(cce, 2100, 1900);
                }
                {
                    Level4RoomPat2 rp2 = new Level4RoomPat2();
                    addObject(rp2, 2100, 1600);
                }
                {
                    VertWalkEnemy vwe = new VertWalkEnemy();
                    addObject(vwe, 2500, 1800);
                }
                {
                    CircleCarEnemy cce = new CircleCarEnemy();
                    addObject(cce, 3600, 1600);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4300, 1800);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4400, 1800);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4400, 2000);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4500, 2000);
                }
                {
                    Level4RoomPat3 rp3 = new Level4RoomPat3();
                    addObject(rp3, 3600, 2000);
                }
                {
                    Level4Hallway3 h3 = new Level4Hallway3();
                    addObject(h3, 4400, 1900);
                }
                for(int i=0; i<=3; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4100+(i*100), 1100);
                }
                for(int i=-2; i<=0; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4500+(i*100), 900);
                }
                for(int i=0; i<=7; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 3700+(i*100), 700);
                }
                for(int i=0; i<=3; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 3700+(i*100), 500);
                }
                for(int i=0; i<=3; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 3700+(i*100), 300);
                }
                for(int i=0; i<=2; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4200+(i*100), 500);
                }
                for(int i=0; i<=2; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4200+(i*100), 300);
                }
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 4100, 400);
                }
                {
                    Level4RoomPat2 rp2 = new Level4RoomPat2();
                    addObject(rp2, 3800, 600);
                }
                {
                    Level4RoomPat2 rp2 = new Level4RoomPat2();
                    addObject(rp2, 3900, 200);
                }
                {
                    Level4RoomPat2 rp2 = new Level4RoomPat2();
                    addObject(rp2, 3600, 800);
                }
                for(int j=0; j<=4; j++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 3300, 300+(j*100));
                }
                for(int j=0; j<=4; j++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2600, 300+(j*100));
                }
                for(int i=0; i<=4; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2800+(i*100), 600);
                }
                for(int i=0; i<=4; i++)
                {
                    Bookshelf s = new Bookshelf();
                    addObject(s, 2700+(i*100), 400);
                }
                {
                    Level4RoomPat2 rp2 = new Level4RoomPat2();
                    addObject(rp2, 2700, 700);
                }
                {
                    Level4RoomPat2 rp2 = new Level4RoomPat2();
                    addObject(rp2, 2800, 200);
                }
                {
                    SinkEnemy se = new SinkEnemy();
                    addObject(se, 3600, 1200);
                }
                {
                    VertWalkEnemy vwe = new VertWalkEnemy();
                    addObject(vwe, 4500, 1000);
                }
                addObject(new Key(), 2150, 100);
                
                addObject(new Star(), 4500, 1900);
                addObject(new Star(), 2600, 1800);
                addObject(new Star(), 1300, 1600);
                
                break;
            }
            case 2:{
                //National Archives
                levelWidth = 4200;
                levelHeight = 2330;
                levelMinWidth = -275;
                levelMinHeight = -165;

                setScrollingBackground(new GreenfootImage("tile.png"));
                changeMusic(new GreenfootSound("InGame.mp3"));

                levelWidth = 4200;
                levelHeight = 2330;
                levelMinWidth = -275;
                levelMinHeight = -165;

                setScrollingBackground(new GreenfootImage("bathroom-tile.jpg"));
                changeMusic(new GreenfootSound("InGame.mp3"));

                //Top
                for(int j=-2; j<=0; j++){
                    for(int i=0; i<=34; i++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50 + (i*100), 50 + (j*100));
                    }
                }
                //top left corner
                for(int i=1; i<=6; i++){
                    for(int j=1; j<=1; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=5; i++){
                    for(int j=2; j<=2; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=4; i++){
                    for(int j=3; j<=3; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=3; i++){
                    for(int j=4; j<=4; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=2; i++){
                    for(int j=5; j<=5; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=1; i++){
                    for(int j=6; j<=6; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                //top right
                for(int i=19; i<=26; i++){
                    for(int j=1; j<=1; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=20; i<=26; i++){
                    for(int j=2; j<=2; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=21; i<=26; i++){
                    for(int j=3; j<=3; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=22; i<=26; i++){
                    for(int j=4; j<=4; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=23; i<=26; i++){
                    for(int j=5; j<=5; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=24; i<=26; i++){
                    for(int j=6; j<=6; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=25; i<=26; i++){
                    for(int j=7; j<=12; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                //left
                for(int i=-3; i<=0; i++)
                {
                    for(int j=1; j<=30; j++)
                    {
                        BlackWall wall= new BlackWall();
                        addObject(wall, 50+(i*100), 50+(j*100));
                    }
                }
                //right
                for(int i=27; i<=28 ; i++){
                    for(int j=1; j<=12; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=25; i<=28; i++){
                    for(int j=12; j<=33; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                //bottom right
                for(int i=24; i<=24; i++){
                    for(int j=21; j<=21; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=23; i<=24; i++){
                    for(int j=22; j<=22; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=22; i<=24; i++){
                    for(int j=23; j<=23; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=21; i<=24; i++){
                    for(int j=24; j<=24; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=20; i<=24; i++){
                    for(int j=25; j<=25; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=19; i<=24; i++){
                    for(int j=26; j<=26; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                //bottom
                for(int i=-3; i<=25; i++){
                    for(int j=29; j<=33; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=11; i++){
                    for(int j=27; j<=29; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=14; i<=22; i++){
                    for(int j=27; j<=29; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                //bottom left
                for(int i=1; i<=1; i++){
                    for(int j=21; j<=21; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=2; i++){
                    for(int j=22; j<=22; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=3; i++){
                    for(int j=23; j<=23; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=4; i++){
                    for(int j=24; j<=24; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=5; i++){
                    for(int j=25; j<=25; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }
                for(int i=1; i<=6; i++){
                    for(int j=26; j<=26; j++){
                        BlackWall wall = new BlackWall();
                        addObject(wall, 50+(i*100), 50 + (j*100));
                    }
                }

                //Shelves
                for(int i=8; i<=10; i++){
                    for(int j=1; j<=1; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=3; i<=3; i++){
                    for(int j=6; j<=6; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=4; i<=4; i++){
                    for(int j=5; j<=5; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=2; i<=2; i++){
                    for(int j=7; j<=9; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=2; i<=2; i++){
                    for(int j=12; j<=13; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=2; i<=2; i++){
                    for(int j=16; j<=18; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=3; i<=3; i++){
                    for(int j=19; j<=19; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=5; i<=5; i++){
                    for(int j=20; j<=20; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=7; i<=7; i++){
                    for(int j=22; j<=22; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=9; i<=10; i++){
                    for(int j=23; j<=23; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=15; i<=16; i++){
                    for(int j=23; j<=23; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=18; i<=18; i++){
                    for(int j=22; j<=22; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=20; i<=20; i++){
                    for(int j=21; j<=21; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=22; i<=22; i++){
                    for(int j=19; j<=19; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=23; i<=23; i++){
                    for(int j=16; j<=18; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=22; i<=22; i++){
                    for(int j=11; j<=14; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=23; i<=23; i++){
                    for(int j=7; j<=9; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=22; i<=22; i++){
                    for(int j=6; j<=6; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=21; i<=21; i++){
                    for(int j=5; j<=5; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                //Top Shelves
                for(int i=11; i<=11; i++){
                    for(int j=3; j<=4; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=12; i<=13; i++){
                    for(int j=4; j<=4; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=14; i<=14; i++){
                    for(int j=3; j<=4; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=6; i<=11; i++){
                    for(int j=6; j<=6; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=14; i<=19; i++){
                    for(int j=6; j<=6; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=5; i<=5; i++){
                    for(int j=8; j<=11; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=20; i<=20; i++){
                    for(int j=8; j<=11; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=14; i<=16; i++){
                    for(int j=8; j<=8; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=9; i<=11; i++){
                    for(int j=8; j<=8; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=7; i<=7; i++){
                    for(int j=9; j<=11; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=18; i<=18; i++){
                    for(int j=9; j<=11; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=10; i<=15; i++){
                    for(int j=10; j<=10; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }

                for(int i=9; i<=9; i++){
                    for(int j=12; j<=13; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=11; i<=11; i++){
                    for(int j=12; j<=13; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=14; i<=14; i++){
                    for(int j=12; j<=13; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=16; i<=16; i++){
                    for(int j=12; j<=13; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=12; i<=13; i++){
                    for(int j=13; j<=13; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                //Bottom
                for(int i=5; i<=5; i++){
                    for(int j=14; j<=17; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=20; i<=20; i++){
                    for(int j=14; j<=17; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=7; i<=7; i++){
                    for(int j=14; j<=16; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=18; i<=18; i++){
                    for(int j=14; j<=16; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=9; i<=9; i++){
                    for(int j=15; j<=16; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=16; i<=16; i++){
                    for(int j=15; j<=16; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=11; i<=11; i++){
                    for(int j=15; j<=17; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=14; i<=14; i++){
                    for(int j=15; j<=17; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=8; i<=11; i++){
                    for(int j=19; j<=19; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=14; i<=17; i++){
                    for(int j=19; j<=19; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=9; i<=11; i++){
                    for(int j=21; j<=21; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=14; i<=16; i++){
                    for(int j=21; j<=21; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=9; i<=10; i++){
                    for(int j=223; j<=23; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=15; i<=16; i++){
                    for(int j=23; j<=23; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                //stopped here
                for(int i=7; i<=7; i++){
                    for(int j=2; j<=2; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=6; i<=6; i++){
                    for(int j=3; j<=3; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=12; i<=13; i++){
                    for(int j=1; j<=1; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=15; i<=17; i++){
                    for(int j=1; j<=1; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=18; i<=18; i++){
                    for(int j=2; j<=2; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }
                for(int i=19; i<=19; i++){
                    for(int j=3; j<=3; j++)
                    {
                        Bookshelf b = new Bookshelf();
                        addObject(b, (i*100)+50, (j*100)+150);
                    }
                }

                //MOVING CHARACTERS
                Character c = new Character();
                addObject(c,450,450);

                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        CircleCarEnemy circle = new CircleCarEnemy();
                        addObject(circle, 1000, 1100);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        CircleCarEnemy circle = new CircleCarEnemy();
                        addObject(circle, 1000, 200);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        CircleCarEnemy circle = new CircleCarEnemy();
                        addObject(circle, 800, 2200);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        CircleCarEnemy circle = new CircleCarEnemy();
                        addObject(circle, 1200, 2200);
                    }
                }

                MediumCircle m = new MediumCircle();
                addObject(m, 800, 900);

                LargeCircleEnemy l = new LargeCircleEnemy();
                addObject(l, 600, 700); 

                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 400, 1400);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 400, 800);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 2100, 1400);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 2100, 800);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 1900, 2100);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 600, 2100);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 900, 200);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 1600, 200);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 100, 800);
                    }
                }
                for(int i=10; i<=10; i++){
                    for(int j=2; j<=2; j++)
                    {
                        VertWalkEnemy v = new VertWalkEnemy();
                        addObject(v, 100, 1400);
                    }
                }
                Key k = new Key();
                addObject(k, 1250,1200);                

                Level4Camera1 LC = new Level4Camera1();
                addObject(LC,1250,1000);

                Level4Camera1Button LCB = new Level4Camera1Button();
                addObject(LCB,1250,300);

                SecurityCameraDown SCD = new SecurityCameraDown();
                addObject(SCD, 1250, 100);

                CameraButton CB = new CameraButton();
                addObject(CB,100,1250);

                c.setLocation(1250,2700);
                addObject(new Star(), 400, 2200);
                addObject(new Star(), 2000, 1200);
                addObject(new Star(), 200, 500);
                
                break;
            }
            case 4:{
                //Area 51
                createBorders(2400,2400);
                setScrollingBackground(new GreenfootImage("tile.png"));
                changeMusic(new GreenfootSound("InGame.mp3"));
                createObjectsFromImage("Level6.png",2400,2400);

                break;
            }
            case 5:{
                //Pentagon
                levelWidth = 4200;
                levelHeight = 2330;
                levelMinWidth = -275;
                levelMinHeight = -165;

                setScrollingBackground(new GreenfootImage("carpet.png"));
                changeMusic(new GreenfootSound("InGame.mp3"));

                Key key = new Key();
                addObject(key, 21*100+50, 7*100+50);   
                

                //Outline
                for(int i=0; i<=45;i++){    
                    addObject(new BlackWall(), 50+(100*i),50);
                }
                for(int i=0; i<=45;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+2400);
                }
                for(int i=0; i<=24;i++){    
                    addObject(new BlackWall(), 50,50+(100*i));
                }
                for(int i=0; i<=24;i++){    
                    addObject(new BlackWall(), 50+4500,50+(100*i));
                }

                //Top Left Box
                for(int i=1; i<=9;i++){    
                    addObject(new BlackWall(), 50+(100*9),50+(100*i));
                }
                for(int i=1; i<=17;i++){    
                    if(i!=7&&i!=14){
                        addObject(new BlackWall(), 50+(100*i),50+(100*9));
                    }
                }

                //Vertical Wall
                for(int i=10; i<=13;i++){    
                    addObject(new BlackWall(), 50+(100*5),50+(100*i));
                }

                //The two shape
                for(int i=15; i<=19;i++){    
                    if(i!=16){
                        addObject(new BlackWall(), 50+(100*2),50+(100*i));
                    }
                    if(i!=18){
                        addObject(new BlackWall(), 50+(100*3),50+(100*i));
                    }
                }

                //Bottom Horizontal Wall
                for(int i=5; i<=33;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*18));
                }
                addObject(new BlackWall(), 50+(100*12),50+(100*19));

                //Bottom Left Box
                for(int j=21; j<=23;j++){    
                    for(int i=1; i<=12; i++){
                        addObject(new BlackWall(), 50+(100*i),50+(100*j));
                    }
                }

                //Left C
                for(int i=13; i<=15;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*14));
                    addObject(new BlackWall(), 50+(100*i),50+(100*16));
                }
                addObject(new BlackWall(), 50+(100*13),50+(100*15));

                //Right C
                for(int i=26; i<=28;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*14));
                    addObject(new BlackWall(), 50+(100*i),50+(100*16));
                }

                addObject(new BlackWall(), 50+(100*28),50+(100*15));

                //Upper Wall
                for(int i=15; i<=26;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*1));
                }

                //Top Middle Two Walls
                for(int i=15; i<=29;i++){    
                    if(i!=19&&i!=20&&i!=21&&i!=22&&i!=23){
                        addObject(new BlackWall(), 50+(100*i),50+(100*3));
                    }
                }
                addObject(new BlackWall(), 50+(100*27),50+(100*4));
                addObject(new BlackWall(), 50+(100*28),50+(100*2));
                addObject(new BlackWall(), 50+(100*21),50+(100*2));
                addObject(new BlackWall(), 50+(100*21),50+(100*4));

                //Top Middle Wall
                for(int i=17; i<=25;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*5));
                }

                //Left Middle Wall
                for(int i=5; i<=12;i++){    
                    addObject(new BlackWall(), 50+(100*17),50+(100*i));
                }
                for(int i=10; i<=12;i++){    
                    addObject(new BlackWall(), 50+(100*18),50+(100*i));
                }
                addObject(new BlackWall(), 50+(100*19),50+(100*11));        

                //Right Middle Wall
                for(int i=5; i<=12;i++){    
                    addObject(new BlackWall(), 50+(100*25),50+(100*i));
                }
                for(int i=10; i<=12;i++){    
                    addObject(new BlackWall(), 50+(100*24),50+(100*i));
                }
                addObject(new BlackWall(), 50+(100*23),50+(100*11)); 

                //Bottom Left L shape
                for(int i=5; i<=9;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*15));
                }
                addObject(new BlackWall(), 50+(100*9),50+(100*16)); 

                //Bottom Right Box
                for(int i=19; i<=23;i++){    
                    addObject(new BlackWall(), 50+(100*28),50+(100*i));
                }
                addObject(new BlackWall(), 50+(100*33),50+(100*19)); 
                addObject(new BlackWall(), 50+(100*34),50+(100*19));
                addObject(new BlackWall(), 50+(100*30),50+(100*20)); 
                addObject(new BlackWall(), 50+(100*31),50+(100*20)); 
                for(int i=19; i<=23;i++){    
                    addObject(new BlackWall(), 50+(100*36),50+(100*i));
                }
                addObject(new BlackWall(), 50+(100*35),50+(100*21)); 

                //Bottom Right J
                for(int i=20; i<=23;i++){    
                    addObject(new BlackWall(), 50+(100*41),50+(100*i));
                }
                addObject(new BlackWall(), 50+(100*42),50+(100*20)); 
                addObject(new BlackWall(), 50+(100*43),50+(100*20)); 
                addObject(new BlackWall(), 50+(100*43),50+(100*21)); 

                //Right Side Room
                for(int i=35; i<=44;i++){    
                    if(i!=37){
                        addObject(new BlackWall(), 50+(100*i),50+(100*17));
                    }
                }
                addObject(new BlackWall(), 50+(100*38),50+(100*16)); 
                addObject(new BlackWall(), 50+(100*38),50+(100*18)); 
                for(int i=9; i<=12;i++){    
                    addObject(new BlackWall(), 50+(100*38),50+(100*i));
                }
                for(int i=38; i<=43;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*11));
                }
                for(int i=8; i<=11;i++){    
                    addObject(new BlackWall(), 50+(100*44),50+(100*i));
                }
                for(int i=12; i<=14;i++){    
                    addObject(new BlackWall(), 50+(100*42),50+(100*i));
                }
                addObject(new BlackWall(), 50+(100*43),50+(100*14)); 

                //Top Right Square
                for(int j=1; j<=7;j++){    
                    for(int i=31; i<=44; i++){
                        addObject(new BlackWall(), 50+(100*i),50+(100*j));
                    }
                }
                for(int i=26; i<=35;i++){    
                    if(i!=28){
                        addObject(new BlackWall(), 50+(100*i),50+(100*9));
                    }
                }
                for(int i=31; i<=35;i++){    
                    addObject(new BlackWall(), 50+(100*i),50+(100*8));
                }
                addObject(new BlackWall(), 50+(100*31),50+(100*10)); 
                addObject(new BlackWall(), 50+(100*31),50+(100*11)); 

                //Above the Left C
                addObject(new BlackWall(), 50+(100*14),50+(100*12)); 
                addObject(new BlackWall(), 50+(100*15),50+(100*12)); 

                //Opposite Corner shapes
                addObject(new BlackWall(), 50+(100*7),50+(100*11)); 
                addObject(new BlackWall(), 50+(100*7),50+(100*12)); 
                addObject(new BlackWall(), 50+(100*8),50+(100*11)); 

                addObject(new BlackWall(), 50+(100*10),50+(100*12)); 
                addObject(new BlackWall(), 50+(100*10),50+(100*13)); 
                addObject(new BlackWall(), 50+(100*9),50+(100*13)); 

                //Wall Stuff
                for(int i=6; i<=8;i++){    
                    addObject(new BlackWall(), 50+(100*11),50+(100*i));
                    addObject(new BlackWall(), 50+(100*10),50+(100*i));
                }
                addObject(new BlackWall(), 50+(100*12),50+(100*10));
                addObject(new BlackWall(), 50+(100*12),50+(100*11));

                //Left Table
                addObject(new Bookshelf(), 50+(100*2),50+(100*10)); 
                addObject(new Bookshelf(), 50+(100*3),50+(100*10));

                //Top Left Table 1
                addObject(new Bookshelf(), 50+(100*12),50+(100*2)); 
                addObject(new Bookshelf(), 50+(100*12),50+(100*3)); 
                addObject(new Bookshelf(), 50+(100*12),50+(100*4)); 

                //Top Left Table 2
                addObject(new Bookshelf(), 50+(100*16),50+(100*7)); 
                addObject(new Bookshelf(), 50+(100*16),50+(100*8));

                //Top Right Table
                addObject(new Bookshelf(), 50+(100*29),50+(100*6)); 
                addObject(new Bookshelf(), 50+(100*29),50+(100*7)); 

                //Right Table
                addObject(new Bookshelf(), 50+(100*41),50+(100*9)); 
                addObject(new Bookshelf(), 50+(100*41),50+(100*8)); 
                addObject(new Bookshelf(), 50+(100*42),50+(100*8)); 
                addObject(new Bookshelf(), 50+(100*43),50+(100*8)); 

                //Bottom Right Table 1
                addObject(new Bookshelf(), 50+(100*29),50+(100*22)); 
                addObject(new Bookshelf(), 50+(100*29),50+(100*23)); 

                //Bottom Right Table 2
                for(int i=31; i<=34;i++){
                    addObject(new Bookshelf(), 50+(100*i),50+(100*21)); 
                    addObject(new Bookshelf(), 50+(100*i),50+(100*22)); 
                }

                //Bottom Right Table 3
                addObject(new Bookshelf(), 50+(100*38),50+(100*21)); 
                addObject(new Bookshelf(), 50+(100*39),50+(100*21)); 
                addObject(new Bookshelf(), 50+(100*39),50+(100*22)); 

                /*addObject(new CircleEnemy1(), 50+(100*2),50+(100*2)); 
                addObject(new CircleEnemyLeft2(), 50+(100*6),50+(100*3));
                addObject(new CircleEnemy2(), 50+(100*11),50+(100*1)); 
                addObject(new CircleEnemy3(), 50+(100*4),50+(100*17)); 
                addObject(new CircleEnemy4(), 50+(100*18),50+(100*14)); 
                addObject(new CircleEnemyLeft1(), 50+(100*24),50+(100*14));
                addObject(new CircleEnemy5(), 50+(100*21),50+(100*13)); 
                addObject(new CircleEnemy7(), 50+(100*37),50+(100*20)); 
                addObject(new CircleEnemy8(), 50+(100*37),50+(100*9)); 
                addObject(new CircleEnemy9(), 50+(100*34),50+(100*18)); 
                addObject(new CircleEnemy10(), 50+(100*29),50+(100*12)); 
                addObject(new CircleEnemy11(), 50+(100*28),50+(100*5)); 
                addObject(new CircleEnemy12(), 50+(100*19),50+(100*3)); 
                addObject(new CircleEnemy13(), 50+(100*11),50+(100*12)); 
                */
                addObject(new SecurityCameraRight(), 50+(100*19), 50+(100*11));
                addObject(new SecurityCameraLeft(), 50+(100*23), 50+(100*11));
                addObject(new SecurityCameraLeft(), 50+(100*5), 50+(100*13));
                addObject(new SecurityCameraLeft(), 50+(100*2), 50+(100*18));
                addObject(new SecurityCameraUp(), 50+(100*38), 50+(100*16));
                addObject(new SecurityCameraDown(), 50+(100*38), 50+(100*12));

                addObject(new CameraButton(), 50+(100*42), 50+(100*21));
                
                addObject(new Star(), 400, 400);
                addObject(new Star(), 2750, 250);
                addObject(new Star(), 4350, 1250);

                Character character = new Character();
                addObject(character, 50+(100*18),50+(100*21));

                break;
            }
            default:{
                createBorders(1600,1200);
                createObjectsFromImage("DefaultLevel.png",1600,1200);

                //System.out.println(level);

                break;
            }

        }
    }

    private void createObjectsFromImage(String imageName, int worldWidth, int worldHeight){
        GreenfootImage levelImage = new GreenfootImage(imageName);
        int blockWidth = worldWidth/levelImage.getWidth();
        int blockHeight = worldHeight/levelImage.getHeight();
        int characterX = 100;
        int characterY = 100;

        int laserX1 = 0;
        int laserY1 = 0;
        int laserX2 = 0;
        int laserY2 = 0;

        for(int j = 0; j<levelImage.getWidth(); j++)
        {
            for(int k = 0; k<levelImage.getHeight(); k++)
            {
                Color tempColor = levelImage.getColorAt(j,k);
                int placeX = j*blockWidth+blockWidth/2;
                int placeY = k*blockHeight+blockHeight/2;

                if(Color.BLACK.equals(tempColor))
                {
                    Wall tempWall = new Wall();
                    addObject(tempWall, j*blockWidth+blockWidth/2, k*blockHeight+blockHeight/2);
                    GreenfootImage tempImage = new GreenfootImage(blockWidth,blockHeight);
                    tempImage.fillRect(0,0,blockWidth,blockHeight);
                    tempWall.setImage(tempImage);
                }

                else if(Color.YELLOW.equals(tempColor))
                {
                    //System.out.println(tempColor.toString());
                    addObject(new Key(), j*blockWidth+blockWidth/2, k*blockHeight+blockHeight/2);
                }
                else if(Color.GREEN.equals(tempColor))
                {
                    characterX = j*blockWidth+blockWidth/2;
                    characterY = k*blockHeight+blockHeight/2;
                }
                else if(Color.BLUE.equals(tempColor)){ //Security Camera Up
                    addObject(new SecurityCameraUp(), j*blockWidth+blockWidth/2, k*blockHeight+blockHeight);
                }
                else if(new Color(153,102,51).equals(tempColor)){ //Security Camera down
                    addObject(new SecurityCameraDown(), placeX, placeY);
                }
                else if(new Color(128,0,128).equals(tempColor)){ 
                    addObject(new Teleporter1(), placeX, placeY);
                }
                else if(new Color(255,128,0).equals(tempColor)){ 
                    addObject(new Teleporter2(), placeX, placeY);
                }
                else if(new Color(0,255,255).equals(tempColor)){
                    addObject(new SecurityCameraRight(), placeX, placeY);
                }
                else if(new Color(204,153,102).equals(tempColor)){ 
                    addObject(new SecurityCameraLeft(), placeX, placeY);
                }
                else if(new Color(102,102,204).equals(tempColor)){ 
                    addObject(new Level4Hallway3(), placeX, placeY);
                }
                else if(new Color(102,102,0).equals(tempColor)){ 
                    laserX1 = j*blockWidth;
                    laserY1 = k*blockHeight;
                }
                else if(new Color(0,153,102).equals(tempColor)){ //Security Camera down
                    laserX2 = j*blockWidth+blockWidth;
                    laserY2 = k*blockHeight+blockHeight;
                    //System.out.println(laserX2+"  "+laserY2); //CONSOLE OUTPUT HERE
                }
                else if(new Color(204,0,204).equals(tempColor)){ //Security Camera down
                    addObject(new Star(), placeX, placeY);
                }
                else if(Color.WHITE.equals(tempColor))
                {}
                else{
                    //System.out.print("R:"+tempColor.getRed());
                    //System.out.print("  G:"+tempColor.getGreen());
                    //System.out.print("  B:"+tempColor.getBlue());
                    //System.out.println("\t"+" "+j+"\t"+k);
                }

            }
        }

        //System.out.println(laserX1+"  "+laserY1); 
        if(laserX1!=0||laserY1!=0){
            //System.out.println(laserX1+"  "+laserY1+"   JLAKLJS"); //CONSOLE OUTPUT HERE
            addObject(new LaserEnemy(laserX1,laserY1,laserX2,laserY2),laserX1,laserY1);
        }

        //System.out.println(characterX+"\t"+characterY);
        addObject(new Character(),characterX,characterY);

    }

    private void createBorders(int worldWidth, int worldHeight){
        levelMinHeight = 0;
        levelMinWidth = 0;
        levelWidth = worldWidth;
        levelHeight = worldHeight;
        int SCREEN_WIDTH = 800;
        int SCREEN_HEIGHT = 600;

        GreenfootImage tempImage = new GreenfootImage(worldWidth+SCREEN_WIDTH,SCREEN_HEIGHT/2);
        tempImage.fillRect(0,0,tempImage.getWidth(),tempImage.getHeight());
        Wall tempWall = new Wall();
        tempWall.setImage(tempImage);
        addObject(tempWall,-SCREEN_WIDTH/2+tempImage.getWidth()/2,-SCREEN_HEIGHT/2+tempImage.getHeight()/2);

        tempImage = new GreenfootImage(SCREEN_WIDTH/2,worldHeight);
        tempImage.fillRect(0,0,tempImage.getWidth(),tempImage.getHeight());
        tempWall = new Wall();
        tempWall.setImage(tempImage);
        addObject(tempWall,-SCREEN_WIDTH/2+tempImage.getWidth()/2,0+tempImage.getHeight()/2);

        tempImage = new GreenfootImage(SCREEN_WIDTH/2,worldHeight);
        tempImage.fillRect(0,0,tempImage.getWidth(),tempImage.getHeight());
        tempWall = new Wall();
        tempWall.setImage(tempImage);
        addObject(tempWall,worldWidth+SCREEN_WIDTH/2-tempImage.getWidth()/2,0+tempImage.getHeight()/2);

        tempImage = new GreenfootImage(worldWidth+SCREEN_WIDTH,SCREEN_HEIGHT/2);
        tempImage.fillRect(0,0,tempImage.getWidth(),tempImage.getHeight());
        tempWall = new Wall();
        tempWall.setImage(tempImage);
        addObject(tempWall,-SCREEN_WIDTH/2+tempImage.getWidth()/2,worldHeight+SCREEN_HEIGHT/2-tempImage.getHeight()/2);
    }

    public void advanceLevel(){
        level++;
        stars+=starsThisLife;
        if(level == 7){
            Greenfoot.setWorld(new WinScreen1(stars, musicPlayer));
        }
        else if(starsThisLife>0&&starsThisLife<=3)
            Greenfoot.setWorld(new CollectedScene(lives, level, stars, starsThisLife, musicPlayer));
        else
            Greenfoot.setWorld(new CutScene(lives, level, stars, musicPlayer));
    }

}

