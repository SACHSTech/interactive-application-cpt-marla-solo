import processing.core.PApplet;
import processing.core.PImage;
// import java.util.ArrayList;

/**
 * Template for programs with Processing graphics output.
 * @author Marla K.
 */
public class Sketch extends PApplet {
    float playerSpeed = 1;
    boolean playerWin;
    float canadaPos;
    int embdenFrame = 0;
    int canadaFrame = 0;
    float finishLine;

    // Each number corresponds to a place/screen (3 in total). 1: home, 2: race track, 3: results screen.
    int place = 1;

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

        // Home
        if (place == 1) {
            drawHome();
            image(embdenIdle, width / 2, height / 2, 150, 150);
        } 
        
        // Start race
        else if (place == 2) {
            drawTrack();
            progressBar();
            endTrack();
            animateGoose(embdenRun, width / 2, height * (float)0.6);
            animateGoose(canadaRun, canadaPos, height / 3);
        }

        // Race results
        else if (place == 3) {
            drawResults();
        }

        drawText();
    }

    public void mouseClicked() {
        if (place == 1) {
            // "Click to FEED!" button
            if (mouseX > width / 3 && mouseX < 2 * (width / 3) && mouseY > 3 * (height / 4) && mouseY < height) {
                playerSpeed++;
            } 
            
            // "RACE!" button
            else if (mouseX > 3 * (width / 4) && mouseY > 3 * (height / 4)) {
                place = 2;
                finishLine = 1000;
                canadaPos = width / 2;
            }
        }
    }

    public void keyPressed() {
        // Resign from race when X is typed
        if (key == 'x' && place == 2) {
            place = 1;
        }

        // Exit results screen
        if (key == 'x' && place == 3) {
            place = 1;
        }
    }

    // Draws all text to the screen before and during the race
    public void drawText() {
        textSize(20);  
        fill(0);

        // HOME
        if (place == 1) {
            text("Click to FEED!", width / 2, (height / 30) * 27);
            text("RACE!", 27 * (width / 30), 27 * (height / 30));
            text("Running speed: " + playerSpeed, width / 2, height / 8);
        }

        // RACE
        if (place == 2) {
            text("Press X to leave the race.", width / 2, height / 14);
        }

        // RESULTS
        if (place == 3) {
            text("Press X to go back.", width / 2, height * (float)0.95);
            textSize(50);

            if (playerWin) {
                text("YOU WIN!", width / 2, height * (float)0.8);
            } else {
                text("YOU LOST :(", width / 2, height * (float)0.8);
            }
        }
    }

    // Draws the background for the race track
    public void drawTrack() {
        fill(49, 113, 28);  // Green
        rect(width, height, 0, height / 10);  // Grass
        fill(118, 151, 27);  // Light green
        rect(0, height / (float)4.8, width, height / (float)1.26);  // Track 
        fill(17, 68, 21);   // Dark green
        rect(width, height, 0, 9 * (height / 10));  // Side
    }

    // Draws the background and buttons for the menu
    public void drawHome() {
        // Background
        fill(49, 113, 28);  // Green
        rect(width, height, 0, height / 2);  // Grass

        // Buttons
        fill(151, 118, 139);  // Muted purple
        rect(width / 3, height, 2 * (width / 3), 3 * (height / 4));  // Feed button
        rect(3 * (width / 4), 3 * (height / 4), width, height);  // Race button
    }

    /**
     * Draws a running goose animation at about 6 frames per second.
     * @param gooseRun the array of running frames (4 in total)
     * @param gooseX goose location on the x axis
     * @param gooseY goose location on the y axis
     */
    public void animateGoose(PImage[] gooseRun, float gooseX, float gooseY) {
        float canadaSpeed;

        if (gooseRun == embdenRun) {
            // Switch the running frame once every 10 frames
            if (frameCount % 10 == 0) {
                // Restart animation at the last frame
                if (embdenFrame == 3) {
                    embdenFrame = -1;
                }

                embdenFrame++;
            }
            image(gooseRun[embdenFrame], gooseX, gooseY, 70, 70);
        }
        
        // Move the canada goose's position depending on both its own and the player's speed
        if (gooseRun == canadaRun) {
            canadaSpeed = random(50, 70);
            canadaPos += (canadaSpeed - playerSpeed) / 50;
            if (frameCount % 10 == 0) {
                // Restart animation at the last frame
                if (canadaFrame == 3) {
                    canadaFrame = -1;
                }

                canadaFrame++;
            }   

            image(gooseRun[canadaFrame], gooseX, gooseY, 85, 70);
        }
    }

    /**
     * Draws a finish line off-screen that progressively moves
     * to the left depending on the player's speed.
     * The game ends when either goose crosses the finish line.
     */
    public void endTrack() {
        noStroke();
        finishLine -= playerSpeed / 50;

        fill(180, 62, 62);  // Red
        rect(finishLine, height / 10, finishLine - 20, 9 * (height / 10));  // Finish line

        // Player wins
        if (finishLine < width / 2) {
            place = 3;
            playerWin = true;
        } 
        // Canada goose wins
        else if (finishLine < canadaPos) {
            place = 3;
            playerWin = false;
        }
    }

    /**
     * Draws a progress bar to the screen that displays each goose's
     * distance from the finish line using colour-coded pointers
     */
    public void progressBar() {
        float playerProgress;
        float canadaProgress;

        strokeWeight(2);
        stroke(100);
        fill(150);

        rect(width / 20, height - 17, width * (float)0.95, height - 12);  // Progress bar

        // Calculate x value of goose pointers on the progress bar
        playerProgress = ((width * (float)0.95) - ((finishLine - width / 2) / (float)1.3));
        canadaProgress = ((width * (float)0.95) - ((finishLine - canadaPos) / (float)1.3));

        // Canada goose pointer
        stroke(17, 13, 11);
        fill(90, 74, 67);
        rect(canadaProgress, height - 2, canadaProgress + 10, height * (float)0.95);

        // Embden (player) goose pointer
        stroke(255, 134, 28);
        fill(255);
        rect(playerProgress, height - 2, playerProgress + 10, height * (float)0.95);
    }

    /**
     * Displays a message depending on who reaches the finish line first.
     * @param playerWin is true when the player wins
     */
    public void drawResults() {
        // Grass
        fill(118, 151, 27);
        rect(0, height / 5, width, height);

        // Win screen
        if (playerWin) {
            image(embdenIdle, width / 2, height / 2);
        }

        // Lose screen
        else {
            image(canadaRun[1], width / 2, height / 2);
        }
    }
}