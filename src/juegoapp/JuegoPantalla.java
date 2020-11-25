
package juegoapp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class JuegoPantalla implements Runnable{
    
    
    //############# VARIABLES ###############
    
    private boolean run = false;
    public Thread thread;
    
    
    // frame & canvas initailize
    public JFrame frame = new JFrame();
    public Canvas canvas = new Canvas();

    //Dimesions frame and canvas settings
    public int Width = 750, Height = 750;
    Dimension canvasSize = new Dimension(Width,Height);
    public BufferStrategy bs;
    public Graphics g;
    
    //input from canvas
    public Input input = new Input();
   
    //character animation
    public static BufferedImage character1,character2, character3,character4;
    public static BufferedImage[] player = new BufferedImage[4];
    public static String name = "";
    public boolean play = false, wash = false, eat = false, sleep = false;
    
    //blink
    public boolean blink = false;
    private int x,y;
    private final int scale = 1;
    
    //other images
    public static BufferedImage food,bed,shower,smiley,bath,rest,ball,hotdog;
    
    //selection boxes foth other objects
    private final Rectangle boxOne = new Rectangle(20,Height/4*3 +30,148,148);
    private final Rectangle boxTwo = new Rectangle(40+148,Height/4*3 +30,148,148);
    private final Rectangle boxThree = new Rectangle(Width - 148*2 - 40,Height/4*3 +30,148,148);
    private final Rectangle boxFour = new Rectangle(Width - 148 - 20,Height/4*3 +30,148,148);
    private boolean one = false,two = false,three = false,four = false;
    
    
    //color background
    public int tmp = 170;
    public boolean blue = false;
    
    //loading page
    private boolean loading = true;
    private int loadingCount = 0;
    private int flashing = 0;
    private boolean loadBar = false;
    
    //main menu
    public static boolean menu = true;
    
    //pause
    private boolean pause = false;
      
    //help
    private boolean help = false;
    private final Rectangle menuHelp = new Rectangle(Width / 2 - 100, Height/12 * 11 + 20, 200, 50);
    private final Rectangle back = new Rectangle(50 , Height/8 * 7 + 25, 200, 50);
    
    //functions
    public static int clean = 950,hunger = 950,sleepy = 950,hapiness = 950;
    public static final int maxClean = 950,maxHunger = 950,maxSleep = 950,maxHappiness = 950;
    private Rectangle pet = new Rectangle();



    //mouse
    private boolean left,right,draw;
    
    //save and load 
    static List<String> inputs = new ArrayList<>();
    public static String loc;
    
    //############# MAIN ###############
    
    public static void Play() throws IOException{
        JuegoPantalla juegopantalla = new JuegoPantalla();
        
        
        character1 = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/main.png"));
        character2 = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/blink.png"));
        character3 = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/left.png"));
        character4 = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/right.png"));
        food = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/food.png"));
        shower = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/soap.png"));
        bed = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/bed.png"));
        smiley = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/smiley.png"));
        hotdog = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/hotdog.png"));
        ball = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/ball.png"));
        bath = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/bath.png"));
        rest = ImageIO.read(new File("/Users/camilafazzio/NetBeansProjects/JuegoApp/Imagenes/sleep.png"));
        
        
        //path to save file
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Pou" + File.separator + "Save";
        File customDir = new File(path);
        if(customDir.exists()){
            System.out.println("save file exists");
            menu = false;
            load(path);
        }else if(customDir.mkdirs()){
            System.out.println("save file made");
            newSave(path);
            
        }else{
            System.out.println("error finding or making file");
        }
        loc = path;
        
        
         
        
        
        juegopantalla.createDisplay();
        
        juegopantalla.start();
    }
    
     
    //############# LOAD ###############
    public static void load(String path) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(path + "/Save.txt"));
        
        while(scanner.hasNext()){
            if(scanner.hasNextLine()){
                inputs.add(scanner.nextLine());
            }else{
                scanner.nextLine();
            }
        }
        fill(); 
    }
    
    //############# NEWSAVE ###############
    public static void newSave(String path) throws FileNotFoundException, UnsupportedEncodingException{
        System.out.println("here");
        PrintWriter writer = new PrintWriter(path + "/Save.txt", "UTF-8");
        writer.close();
         
    }
    
    
    
    //############# SAVE ###############
    public static void save(String name, int hunger,int sleepy, int happy, int clean) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(loc + "/Save.txt", "UTF-8");
        
        writer.println(name);
        writer.println(hunger);
        writer.println(happy);
        writer.println(clean);
        writer.println(sleepy);
        writer.close();
    }
    
    
    
    
    
    
    //############# CREATE DISPLAY ###############
    
    public void createDisplay(){
        
        //frame
        frame = new JFrame("Java Juego");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame.setLocation(dim.width/2 - Width/2,dim.height/2 - Height);
        frame.setSize(Width,Height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        
        
        //canvas
        canvas = new Canvas();
        canvas.setPreferredSize(canvasSize);
        canvas.setMaximumSize(canvasSize);
        canvas.setMinimumSize(canvasSize);
        canvas.setFocusable(false);
        
        
        frame.add(canvas);
        canvas.addMouseListener(new CustomListener());
        frame.addKeyListener(input);
        frame.pack();
        run = true;
        
        
        player[0] = character1;
        player[1] = character2;
        player[2] = character3;
        player[3] = character4;
        
        run();

        
    }
    
    
    
    //############# UPDATE ###############
    
    public void update(){
        
        if(input.escape && pause == false && loading == false && menu == false){
            pause = true;
        }
        if(loading == false && menu == false && input.test){
            
        }
        if(loading == true && input.test){
            loading = false;
        }
        if(hunger < 0){
            hunger = 0;
        }
        if(clean < 0){
            clean = 0;
        }
        if(sleepy < 0){
            sleepy = 0;
        }
        if(hapiness < 0){
            hapiness = 0;
        }
        
        
        
    }


    //############# RENNDER ###############
    public void render() throws FileNotFoundException, UnsupportedEncodingException{
        save(name, hunger, sleepy, hapiness,clean);
        
        //glowing background
        if(tmp == 255){
            blue = false;
        }
        if(tmp == 170){
            blue = true;
        }
        if(blue == true){
            tmp += 1;
        }
        if(blue == false){
            tmp -= 1;
        }
        Color background = new Color(3,200,tmp);
        
        x = Width/2 -(character1.getWidth() * scale / 2);
        y = Width/2 -(character1.getHeight() * scale / 2);
        pet = new Rectangle(x,y,character1.getWidth(), character1.getHeight());
        
        //bufferedStrategy
        bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
         
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, Width, Height); 
        
        //background
        g.setColor(background);
        g.fillRect(0, 0, Width, Height);
        g.setFont(new Font("Ariel", Font.BOLD, 30));
        
        //loading page
        if(loading == true){
            loadingCount++;
            flashing ++;
            g.setColor(Color.yellow);
            if(loadBar == false){
                g.fillRect(12, 400, loadingCount * 3, 90);
            }
            g.setColor(Color.black);
            g.drawRect(9, 395, Width-20, 100);
            if(flashing > 50){
                g.drawString("LOADING . . .", Width/ 2 - 70, 350);
            }
            if(flashing > 100){
                //g.drawString("LOADING...", Width/ 2 - 50, 350);
                flashing = 0;
            }
            if(loadingCount > 242){
                loadBar = true;
                g.setColor(Color.green);
                g.fillRect(12, 400, 245 * 3, 90);
                
            }
            if(loadingCount > 300){
                loading = false;
                
            }
            
        }else if (loading == false){
            
        //menu page
            if(menu == true){
                g.setColor(Color.white);
                g.fillRect(150, 150, 400, 400);
                g.setColor(Color.black);
                g.drawString("Enter a Name : " + name , 180, 200);
                g.setFont(new Font("Ariel", Font.BOLD, 18));
                g.drawString(" 10 characters max" , 275, 230);
                g.drawString(" Press enter to continue " , 255, 280);
                g.drawRect(415, 180, 120, 23);
                
                //help button
                g.setColor(new Color(255,100,0));
                g.fillRect(Width/2 - 100, Height / 12 * 11, 200, 50);
                g.setColor(Color.red);
                g.fillRect(Width/2 - 95, Height / 12 * 11 + 5, 190, 40);
                g.setColor(Color.black);
                g.setFont(new Font("Ariel", Font.BOLD, 25));
                g.drawString("HELP" , Width/2 - 27, Height / 12 * 11 + 32);
                
                if(onScreen() == true){
                    if(menuHelp.contains(frame.getMousePosition()) && left == true){
                        help = true;
                    }
                }
                
            }
            
        //main page
            if(menu == false && pause == false){
                //show pets name
                g.setFont(new Font("Ariel", Font.BOLD, 25));
                g.setColor(Color.black);
                //pou animation 
                if(blink == true || sleep == true) {
                    g.drawString(name, pet.x + pet.width/2, pet.y - 10);
                    g.drawImage(player[1], x, y, 300, 300, null);
                } else if(wash == true){
                    g.drawString(name, pet.x + pet.width/2, pet.y - 10);
                    g.drawImage(player[2], x, y, 300, 300, null);
                } else if (eat == true){
                    g.drawString(name, pet.x + pet.width/2, pet.y - 10);
                    g.drawImage(player[3], x, y, 300, 300, null);
                }else if (play == true){
                    g.drawString(name, pet.x + pet.width/2, pet.y - 10);
                    g.drawImage(player[0], x, y, 300, 300, null);
                }else {
                    g.drawString(name, pet.x + pet.width/2, pet.y - 10);
                    g.drawImage(player[0], x, y, 300, 300, null);
                }
                
                //stat Bars
                g.setColor(Color.black);
                g.drawImage(food,10,15, 40, 40, null);
                g.drawRect(20, 70, 28, 480);
                g.setColor(Color.red);
                g.fillRect(22, 72, 25, hunger/2);

                g.setColor(Color.black);
                g.drawImage(bed,65,15, 40, 40, null);
                g.drawRect(70, 70, 28, 480);
                g.setColor(Color.cyan);
                g.fillRect(72, 72, 25, hapiness / 2);

                g.setColor(Color.black);
                g.drawImage(shower,Width - 55,15, 40, 40, null);
                g.drawRect(Width - 48, 70, 28, 480);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(Width - 46, 72, 25, clean / 2);

                g.setColor(Color.black);
                g.drawImage(smiley,Width - 105,15, 40, 40, null);
                g.drawRect(Width - 98, 70, 28, 480);
                g.setColor(Color.orange);
                g.fillRect(Width - 96, 72, 25, sleepy /2);
                
                //object option bar
                g.setColor(Color.yellow);
                g.fillRoundRect(0, Height/4*3, Width, Height/4*3,50,50);

                g.setColor(Color.red);
                g.drawImage(hotdog, boxOne.x, boxOne.y, boxOne.width, boxOne.height,null);
                g.drawImage(ball, boxTwo.x, boxTwo.y, boxTwo.width, boxTwo.height,null);
                g.drawImage(bath, boxThree.x, boxThree.y, boxThree.width, boxThree.height,null);
                g.drawImage(rest, boxFour.x, boxFour.y, boxFour.width, boxFour.height,null);
                if(onScreen() == true){
                if(boxOne.contains(frame.getMousePosition()) && two == false && three == false && four == false && left == true){
                    one = true;
                    draw = true;
                }
                if(boxTwo.contains(frame.getMousePosition()) && one == false && three == false && four == false && left == true){
                    two = true;
                    draw = true;
                }
                if(boxThree.contains(frame.getMousePosition()) && two == false && one == false && four == false && left == true){
                    three = true;
                    draw = true;
                }
                if(boxFour.contains(frame.getMousePosition()) && two == false && three == false && one == false && left == true){
                    four = true;
                    draw = true;
                }
                if(pet.contains(frame.getMousePosition()) && draw == true && one == true){
                    
                    eat();
                    draw = false;
                }
                if(pet.contains(frame.getMousePosition()) && draw == true && two == true){
                    
                    play();
                    draw = false;
                }
                if(pet.contains(frame.getMousePosition()) && draw == true && three == true){
                    
                    wash();
                    draw = false;
                }
                if(pet.contains(frame.getMousePosition()) && draw == true && four == true){
                    
                    sleep();
                    draw = false;
                }
                
                if(draw && one){
                    g.drawImage(hotdog, frame.getMousePosition().x - 25, frame.getMousePosition().y - 25, 60, 50, null);
                }
                if(draw && two){
                    g.drawImage(ball, frame.getMousePosition().x - 25, frame.getMousePosition().y - 25, 50, 50, null);
                }
                if(draw && three){
                    g.drawImage(bath, frame.getMousePosition().x - 25, frame.getMousePosition().y - 25, 50, 50, null);
                }
                if(draw && four){
                    g.drawImage(rest, frame.getMousePosition().x - 25, frame.getMousePosition().y - 25, 40, 50, null);
                }
            
                }
                
                
            }
        //pause page    
            if(pause == true && help == false){
                System.out.println("Paused");
            }
        //help page    
            if(help == true){
                g.setColor(Color.orange);
                g.fillRect(15, 15, Width - 30, Height - 30);
                g.setColor(Color.red);
                g.fillRect(50, Height/8 * 7, 190, 50);
                g.setColor(Color.white);
                g.fillRect(55, Height/8 * 7 + 5, 180, 40);
                g.setColor(Color.black);
                g.drawString("BACK", 100, Height/8 * 7 + 35);
                g.drawString("Keep your friend alive", 40 , 50);
                
                if(onScreen() == true){
                    if(back.contains(frame.getMousePosition()) && left == true){
                        help = false;
                    }
                }
                
                
            }
            
        }
        
        bs.show();
        
        g.dispose();
        
        
    }
    
    //################### START FUNCTION #######################
    public void start(){
        if(run == false){
            return;
        }else {
            run = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    
    
    
    
    //############# RUN ###############
    @Override
    public void run(){
        long time;
        long lastTime = System.currentTimeMillis();
        long eventTime = System.currentTimeMillis();
        int fps = 60;
        int b = 0;
        int p = 0, w = 0, s= 0, e = 0, counter = 0;
        
        while(run){
            time = System.currentTimeMillis();
            if((double)(time - lastTime)/ 1000 > (double)1/fps){
                update();
                input.update();
                try {
                    render();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JuegoApp.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(JuegoApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            if(blink == true){
                b++;
            }
            if(b == 10){
                blink = false;
                b = 0;
            }
            if(play == true){
                p++;
            }
            if(p == 8){
                play = false;
                p = 0;
            }
            if(eat == true){
                e++;
            }
            if(e == 15){
                eat = false;
                e = 0;
            }
            if(sleep == true){
                s++;
            }
            if(s == 75){
                sleep = false;
                s = 0;
            }
            if(wash == true){
                w++;
            }
            if(w == 50){
                wash = false;
                w = 0;
            }

            lastTime = time;

            }

            if((double)(time - eventTime) / 1000 > 1 && menu == false && loading == false && help == false){
                counter++;
                eventTime = time;
                hunger -= 1;
                hapiness -=3;
                if(counter == 1){
                    sleepy -=1;
                }
                if(counter == 3){
                    clean -=4;
                    if(eat == false && wash == false && sleep == false){
                    blink();
                    counter = 0;
                    }
                }

            }        
        
        }
    
    }
    
    
    //################### BLINK FUNCTION #######################
    public void blink(){
        blink = true;
        System.out.println("blink");
    }
    
    //################### ACTIONS FUNCTIONS #######################
    public void sleep(){
        System.out.println("zzz");
        sleep = true;
        sleepy += 350;
        hunger -= 150;
        hapiness -= 100;
        if(sleepy > maxSleep){
            sleepy = maxSleep;
        }
    }
    
    public void eat(){
        System.out.println("yum");
        eat = true;
        hunger += 50;
        sleepy += 5;
        if(hunger > maxHunger){
            hunger = maxHunger;
        }
    }
    
    public void play(){
        System.out.println("fun");
        play = true;
        hapiness += 90;
        hunger -= 10;
        clean -= 40;
        sleepy -= 25;
        if(hapiness > maxHappiness){
            hapiness = maxHappiness;
        }
    }
    
    public void wash(){
        System.out.println("clean");
        wash = true;
        clean += 75;
        hapiness += 50;
        if(clean > maxClean){
            clean = maxClean;
        }
    }
    
    
    
    
    //################### ONSCREEN FUNCTION #######################
    public boolean onScreen(){
        boolean ret = false;
        if(MouseInfo.getPointerInfo().getLocation().x > frame.getX() && MouseInfo.getPointerInfo().getLocation().x < frame.getX()+ Width &&
                    MouseInfo.getPointerInfo().getLocation().y > frame.getY() && MouseInfo.getPointerInfo().getLocation().y < frame.getY() + Height && frame.isFocused() == true){
            ret = true;
        }
        return ret;
    }
    
    //################### FILL FUNCTION #######################
    public static void fill(){
        if(inputs.size() == 1){
            if(check(0) == true){
                name = "unknown";
            }else{
                name = inputs.get(0);
            }
        }
        
        if(inputs.size() == 2){
            if(check(0) == true){
                name = "unknown";
            }else{
                name = inputs.get(0);
            }
            if(check(1) == true){
                hunger = maxHunger;
            }else{
                hunger = Integer.parseInt(inputs.get(1));
            }
        }
        
        if(inputs.size() == 3){
            if(check(0) == true){
                name = "unknown";
            }else{
                name = inputs.get(0);
            }
            if(check(1) == true){
                hunger = maxHunger;
            }else{
                hunger = Integer.parseInt(inputs.get(1));
            }
            if(check(2) == true){
                hapiness = maxHappiness;
            }else{
                hapiness = Integer.parseInt(inputs.get(2));
            }
        }
        
        if(inputs.size() == 4){
            if(check(0) == true){
                name = "unknown";
            }else{
                name = inputs.get(0);
            }
            if(check(1) == true){
                hunger = maxHunger;
            }else{
                hunger = Integer.parseInt(inputs.get(1));
            }
            if(check(2) == true){
                hapiness = maxHappiness;
            }else{
                hapiness = Integer.parseInt(inputs.get(2));
            }if(check(3) == true){
                clean = maxClean;
            }else{
                clean = Integer.parseInt(inputs.get(3));
            }
        }
            
        if(inputs.size() == 5){
            if(check(0) == true){
                name = "unknown";
            }else{
                name = inputs.get(0);
            }
            if(check(1) == true){
                hunger = maxHunger;
            }else{
                hunger = Integer.parseInt(inputs.get(1));
            }
            if(check(2) == true){
                hapiness = maxHappiness;
            }else{
                hapiness = Integer.parseInt(inputs.get(2));
            }if(check(3) == true){
                clean = maxClean;
            }else{
                clean = Integer.parseInt(inputs.get(3));
            }if(check(4) == true){
                sleepy = maxSleep;
            }else{
                sleepy = Integer.parseInt(inputs.get(4));
            }
        }
        
    }
    
    //################### CHECK FUNCTION #######################
    public static boolean check(int i){
        boolean placeHolder = false;
        if(inputs.get(i).equals("")){
            placeHolder = true;
        }
        return placeHolder;
    }
    
    
    
    //################### CUSTOM LISTENER MOUSE FUNCTION #######################
    

    private class CustomListener implements MouseListener{
        
        public CustomListener(){
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent me) {
            if(me.getButton() == MouseEvent.BUTTON1){
                left = true;
            }
            if(me.getButton() == MouseEvent.BUTTON3){
                right = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            left = false;
            right = false;
            draw = false;
            
            one = false;
            two = false;
            three = false;
            four = false; 
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        /**
        @Override
        public void mouseClicked(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent me) {
            if(me.getButton() == MouseEvent.BUTTON1){
                left = true;
            }
            if(me.getButton() == MouseEvent.BUTTON3){
                right = true;
            }

            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            left = false;
            right = false;
            draw = false;
            
            one = false;
            two = false;
            three = false;
            four = false;

            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        */
     
    
    }
    
}
