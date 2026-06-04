import processing.core.PApplet;
// import processing.core.PImage;
// import java.util.ArrayList;

/**
 * Template for programs with Processing graphics output.
 * @author Marla K.
 */
public class Sketch extends PApplet {
    boolean isRacing = false;
    float playerSpeed = 1;

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
        textAlign(CENTER);
        noStroke();
    }

    @Override
    public void draw() {
        background(96, 193, 237);

        fill(49, 113, 28);  // Green
        rect(width, height, 0, 350);
        fill(255);
        rect(200, 200, 400, 400);  // Bird placeholder
        rect(200, 450, 400, 550);  // Feed button
        
        fill(0);
        textSize(25);
        text("Click to FEED!", width / 2, 510);
        text("Running speed: " + playerSpeed, width / 2, 50);
    }

    public void mouseClicked() {
        if (!isRacing) {
            if (mouseX > 200 && mouseX < 400 && mouseY > 450 && mouseY < 550) {
                playerSpeed++;
                System.out.println("Speed: " + playerSpeed);
            }
        }
    }
}
