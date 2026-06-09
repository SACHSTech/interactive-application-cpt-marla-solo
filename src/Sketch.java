import processing.core.PApplet;
import processing.core.PImage;
// import java.util.ArrayList;

/**
 * Template for programs with Processing graphics output.
 * @author Marla K.
 */
public class Sketch extends PApplet {
    boolean isRacing = false;
    boolean playerWin;
    float playerSpeed = 1;
    float canadaSpeed;
    float canadaPos;
    int embdenFrame = 0;
    int canadaFrame = 0;
    float finishLine;
    float playerProgress;

    // Goose image/animations
    PImage embdenIdle;
    PImage[] embdenRun = new PImage[4];
    PImage[] canadaRun = new PImage[4];

    // Misc. images
    PImage tree;
    PImage bush;
    PImage dandelions;

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    @Override
    public void settings() {
        size(600, 600, P2D); 
    }

    @Override
    public void setup() {
        rectMode(CORNERS);
        imageMode(CENTER);
        textAlign(CENTER);
        noStroke();

        // Load images from the images/ folder
        embdenIdle = loadImage("images/embden-idle.png");
        tree = loadImage("images/tree.png");
        bush = loadImage("images/bush.png");
        dandelions = loadImage("images/dandelions.png");

        // Load goose running animations
        for (int i = 0; i < 4; i++) {
            embdenRun[i] = loadImage("images/embden-run" + i + ".png");
            canadaRun[i] = loadImage("images/canada-run" + i + ".png");
        }
    }

    @Override
    public void draw() {
        background(96, 193, 237);
        noStroke();

        if (!isRacing) {
            drawHome();
        } 
        
        // Start race
        else {
            drawTrack();
            endTrack();
            animateGoose(embdenRun, width / 2, height * (float)0.6, 70, 70);
            animateGoose(canadaRun, canadaPos, height / 3, 85, 70);
        }

        drawText();
    }

    public void mouseClicked() {
        if (!isRacing) {
            if (mouseX > width / 3 && mouseX < 2 * (width / 3) && mouseY > 3 * (height / 4) && mouseY < height) {
                playerSpeed++;
                System.out.println("Speed: " + playerSpeed);
            } 
            
            // "RACE!" button
            else if (mouseX > 3 * (width / 4) && mouseY > 3 * (height / 4)) {
                isRacing = true;
                finishLine = 1000;
                canadaPos = width / 2;
            }
        }
    }

    public void drawText() {
        textSize(20);
        fill(0);

        // HOME
        if (!isRacing) {
            text("Click to FEED!", width / 2, (height / 30) * 27);
            text("RACE!", 27 * (width / 30), 27 * (height / 30));
            text("Running speed: " + playerSpeed, width / 2, height / 8);
        }

        // RACE
        if (isRacing) {
            text("Press X to leave the race.", width / 2, height / 14);
        }
    }

    public void drawTrack() {
        fill(49, 113, 28);  // Green
        rect(width, height, 0, height / 10);  // Grass
        fill(118, 151, 27);
        rect(0, height / (float)4.8, width, height / (float)1.26);  // Track 
        fill(17, 68, 21); 
        rect(width, height, 0, 9 * (height / 10));  // Side

        progressBar();
    }

    public void drawHome() {
        // Background
        fill(49, 113, 28);  // Green
        rect(width, height, 0, height / 2);  // Grass

        // Buttons
        fill(151, 118, 139);  // Muted purple
        rect(width / 3, height, 2 * (width / 3), 3 * (height / 4));  // Feed button
        rect(3 * (width / 4), 3 * (height / 4), width, height);  // Race button

        image(embdenIdle, width / 2, height / 2, 150, 150);
    }

    public void animateGoose(PImage[] gooseRun, float gooseX, float gooseY, float gooseWidth, float gooseHeight) {
        

        if (gooseRun == embdenRun) {
            // Switch the running frame once every 10 frames
            if (frameCount % 10 == 0) {
                // Restart animation at the last frame
                if (embdenFrame == 3) {
                    embdenFrame = -1;
                }

                embdenFrame++;
            }
            image(gooseRun[embdenFrame], gooseX, gooseY, gooseWidth, gooseHeight);
        }
        
        // Move the canada goose' position depending on the player's speed
        if (gooseRun == canadaRun) {
            canadaSpeed = random(50, 70);
            canadaPos += (canadaSpeed - playerSpeed) / 50;
            System.out.println(gooseX);
            if (frameCount % 10 == 0) {
                // Restart animation at the last frame
                if (canadaFrame == 3) {
                    canadaFrame = -1;
                }

                canadaFrame++;
                
            }   
            image(gooseRun[canadaFrame], gooseX, gooseY, gooseWidth, gooseHeight);
        }
    }

    public void endTrack() {
        noStroke();
        finishLine -= playerSpeed / 50;
        System.out.println("Finish line x: " + finishLine);

        fill(180, 62, 62);  // Red
        rect(finishLine, height / 10, finishLine - 20, 9 * (height / 10));  // Finish line

        if (finishLine < width / 2) {
            playerWin = true;
            System.out.println("You win!");
            isRacing = false;
        } else if (finishLine < canadaPos) {
            playerWin = false;
            System.out.println("You lost!");
            isRacing = false;
        }
    }

    public void progressBar() {
        stroke(0);
        strokeWeight(5);
        fill(100);
        rect(width / 20, height, width * (float)0.95, height * (float)0.95);

        stroke(255, 134, 28);
        fill(255);

        // Calculate x value of goose pointers on the progress bar
        playerProgress = ((width * (float)0.95) - ((finishLine - width / 2) / (float)1.3));

        rect(playerProgress, height - 2, playerProgress + 10, height * (float)0.95);
    }

    public void keyPressed() {
        // Resign from race when X is typed
        if (key == 'x' && isRacing == true) {
            isRacing = false;
        }
    }
}