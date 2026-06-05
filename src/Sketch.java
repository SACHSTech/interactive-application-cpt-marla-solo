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

    // Goose images
    PImage embdenIdle;
    // PImage embdenRun1;
    // PImage embdenRun2;
    // PImage embdenRun3;
    // PImage embdenRun4;

    // Goose animations
    PImage[] embdenRun = new PImage[4];

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

        // Load goose images from the images/ folder
        embdenIdle = loadImage("images/embden-idle.png");

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
        } else {
            drawTrack();
        }
    }

    public void mouseClicked() {
        if (!isRacing) {
            if (mouseX > 200 && mouseX < 400 && mouseY > 450 && mouseY < 550) {
                playerSpeed++;
                System.out.println("Speed: " + playerSpeed);
            } else if (mouseX > 450 && mouseY > 450) {
                isRacing = true;
            }
        }
    }

    public void drawButtons() {
        fill(255);
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
        // rect(200, 200, 400, 400);  // Bird placeholder
        image(embdenIdle, 300, 300, 200, 200);

        text("Running speed: " + playerSpeed, width / 2, 50);
    }
}
