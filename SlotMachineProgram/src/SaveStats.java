
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveStats {
    //global variables
    private int games;
    private int wins;
    private int losses;
    private int creditsWon;
    //constructor
    public SaveStats(int games, int wins, int losses, int creditsWon) {
        this.games = games;
        this.wins = wins;
        this.losses = losses;
        this.creditsWon = creditsWon;
    }
    //input()will retrieve the values of each games and will be saved in a txt file
    public void input(int games, int wins, int losses, int creditsWon) {
        //this retrieves the current date and time in the specified format
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        Date date = new Date();
        try {
            //opens the file with the name as the current date and time
            FileWriter fw = new FileWriter(sdf.format(date).concat(".txt"));
            BufferedWriter bw = new BufferedWriter(fw);
            //writes it into the file
            bw.write("Games Played: " + games + "\n");
            bw.write("Wins: " + wins + "\n");
            bw.write("Losses: " + losses + "\n");
            bw.write("Average Credits Won Per Game: " + creditsWon + "\n");
            bw.flush();
            System.out.println("Done");
            //closes the file
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    } 
}
