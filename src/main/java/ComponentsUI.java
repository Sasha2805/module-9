import entities.youTubeSearch.ThumbnailImage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ComponentsUI {

    public static Button generateButton(String name, int minWidth, Runnable runnable){
        Button button = new Button(name);
        button.setMaxWidth(minWidth);
        button.setOnAction(event -> runnable.run());
        return button;
    }

    public static Button generateButton(String name, int minWidth, int X, int Y, Runnable runnable){
        Button button = new Button(name);
        button.setMaxWidth(minWidth);
        button.setTranslateX(X);
        button.setTranslateY(Y);
        button.setOnAction(event -> runnable.run());
        return button;
    }

    public static Button generateButton(String name, int minWidth, int X, int Y, String color, Runnable runnable){
        Button button = generateButton(name, minWidth, X, Y, runnable);
        button.setStyle("-fx-background-color:" + color);
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setPadding(new Insets(10,5,10,5));
        return button;
    }

    public static Text generateText(String str, int translateX, int translateY){
        Text text = new Text();
        text.setText(str);
        text.setTranslateX(translateX);
        text.setTranslateY(translateY);
        return text;
    }

    public static ImageView generateImage(ThumbnailImage image, int X, int Y) {
        if (image == null) return null;
        ImageView imageView = new ImageView(new Image(image.getUrl()));
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
        imageView.setTranslateX(X);
        imageView.setTranslateY(Y);
        return imageView;
    }

}

