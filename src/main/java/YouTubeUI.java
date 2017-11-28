import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class YouTubeFx extends Application {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();

        VBox menu = new VBox();
        menu.setPadding(new Insets(10));
        menu.setSpacing(10);

        TextField textField = new TextField();

        Button search = new Button("Search");
        search.setOnAction(event -> {});

        menu.getChildren().addAll(textField, search);
        root.getChildren().addAll(menu);
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.show();
    }

}
