
import java.util.*;
import java.util.List;
import java.awt.*;       // Using AWT's layouts
import java.awt.event.*; // Using AWT's event classes and listener interfaces
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
//MyWorker class to implement spinning mechanism using SwingWorker
public class MyWorker extends SwingWorker<Boolean, Symbol> {
    //JComponents
    private JButton lblImg;
    private JTextField imgVal;
    //constructor
    public MyWorker(JButton lblImg, JTextField imgVal) {
        this.lblImg = lblImg;
        this.imgVal = imgVal;
    }

    // doInBackground() will simulate the spinning of the reels until worker has be stopped
    protected Boolean doInBackground() throws Exception {
        Reel reel = new Reel();
        Symbol firstImg;
        int symVal;
        do {
            firstImg = reel.spin();
            symVal = firstImg.getValue();
            publish(firstImg, symVal);
            Thread.sleep(100);
        } while (!isCancelled());
        return true;
    }
    
    protected void done() {
        boolean status;
        try {
            //Retrieves the return value of doInBackground.
            status = get();
            System.out.println("Completed with status: " + status);
        } catch (InterruptedException | CancellationException e) {
            // This is thrown when the thread is interrupted
            //it will return the message saying the game is done
            System.out.println("Game Finished");
        } catch (ExecutionException ex) {
            Logger.getLogger(MyWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //publish()will return the image as well as the value of the symbol published
    private void publish(Symbol firstImg, int symVal) {
        lblImg.setIcon(firstImg.getImage());
        imgVal.setText(Integer.toString(symVal));
    }

}
