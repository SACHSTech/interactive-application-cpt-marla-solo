import processing.core.PApplet;
import processing.core.PImage;
// import java.util.ArrayList;

/**
 * Template for programs with Processing graphics output.
 * @author Marla K.
 */
public class Sketch extends PApplet {
    boolean isRacing = false;
    float playerSpeed = 1;
    int gooseFrame = 0;
    float finishLine;

    // Goose image/animations
    PImage embdenIdle;
    PImage[] embdenRun = new PImage[4];

    // Misc. images
    // PImage tree;

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    @Override
    public void settings() {
        size(600, 600); 
    }

    @Override
    public void setup() {
        rectMode(CORNERS);
        imageMode(CENTER);
        textAlign(CENTER);
        noStroke();

        // Load images from the images/ folder
        embdenIdle = loadImage("images/embden-idle.png");
        // tree = loadImage("images/tree.png");

        // Load goose running animations
        for (int i = 0; i < 4; i++) {
            embdenRun[i] = loadImage("images/embden-run" + i + ".png");
        }
    }

    @Override
    public void draw() {
        background(96, 193, 237);
        if (!isRacing) {
            drawHome();
        } 
        
        // Start race
        else {
            drawTrack();
            endTrack();
            animateGoose(embdenRun, 300, 300, 70);
            
        }
    }

    public void mouseClicked() {
        if (!isRacing) {
            if (mouseX > 200 && mouseX < 400 && mouseY > 450 && mouseY < 550) {
                playerSpeed++;
                System.out.println("Speed: " + playerSpeed);
            } 
            
            // "RACE!" button
            else if (mouseX > 450 && mouseY > 450) {
                isRacing = true;
                finishLine = 1000;
            }
        }
    }

    public void drawButtons() {
        fill(151, 118, 139);
        rect(200, 450, 400, 550);  // Feed button
        rect(450, 450, width, height);  // Race button

        fill(0);
        textSize(27);
        text("Click to FEED!", width / 2, 510);
        text("RACE!", 525, 535);
    }

    public void drawTrack() {
        fill(49, 113, 28);  // Green
        rect(width, height, 0, 50);  // Grass
        fill(118, 151, 27);
        rect(0, 125, width, 475);  // Track 
        fill(17, 68, 21); 
        rect(width, height, 0, 550);  // Side
    }

    public void drawHome() {
        fill(49, 113, 28);  // Green
        rect(width, height, 0, 350);  // Grass
        drawButtons();
        image(embdenIdle, 300, 300, 200, 200);

        text("Running speed: " + playerSpeed, width / 2, 50);
    }

    public void animateGoose(PImage[] gooseRun, float gooseX, float gooseY, float gooseSize) {
        // Switch the running frame once every 15 frames
        if (frameCount % 15 == 0) {
            // Restart animation at the last frame
            if (gooseFrame == 3) {
                gooseFrame = -1;
            }

            gooseFrame++;
        }

        image(gooseRun[gooseFrame], gooseX, gooseY, gooseSize, gooseSize);
    }

    public void endTrack() {
        finishLine -= playerSpeed / 50;
        System.out.println(finishLine);

        fill(180, 62, 62);  // Red
        rect(finishLine, 50, finishLine - 20, 550);  // Finish line

        if (finishLine < width / 2) {
            isRacing = false;
        }
    }
}