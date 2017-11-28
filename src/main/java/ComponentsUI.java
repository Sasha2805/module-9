import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import youTubeEntities.YouTubeThumbnailImage;

public final class ComponentsUI {

    public static Button generateButton(String name, int minWidth, Runnable runnable){
        Button button = new Button(name);
        button.setMaxWidth(minWidth);
        button.setOnAction(event -> runnable.run());
        return button;
    }

    public static Button generateButton(String name, int minWidth, String color, Runnable runnable){
        Button button = generateButton(name, minWidth, runnable);
        button.setStyle("-fx-background-color:" + color);
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setPadding(new Insets(10,5,10,5));
        return button;
    }

    public static ImageView generateImage(YouTubeThumbnailImage image) {
        if (image == null) return null;
        ImageView imageView = new ImageView(new Image(image.getUrl()));
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
        return imageView;
    }

    public static ImageView getImage(String url, int width, int height){
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

}

