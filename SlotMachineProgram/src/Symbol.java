
import javax.swing.ImageIcon;

public class Symbol implements ISymbol{

    private int value;
    private ImageIcon image;

    // first constructor
    public Symbol() {
        value = 0;
        image = null;
    }

    // second constructor
    public Symbol(int value, ImageIcon image) {
        this.value = value;
        this.image = image;
    }

    // setter and getter methods
    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public ImageIcon getImage() {
        return image;
    }
}
