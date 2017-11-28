import com.mashape.unirest.http.HttpResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import youTubeEntities.YouTubeEntity;
import youTubeEntities.YouTubeItem;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class YouTubeUI extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private ArrayList<VBox> pages = new ArrayList<>();
    private int pageIndex;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();
        VBox content = new VBox();

        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: #934c93;");
        menu.getStylesheets().add("stylesButtonsMenu.css");
        menu.setMinWidth(WIDTH/4);

        HBox history = new HBox();
        history.setSpacing(10);
        history.setMinHeight(40);
        history.setAlignment(Pos.CENTER_RIGHT);
        history.setStyle("-fx-background-color: #934c93;");
        history.setPadding(new Insets(0, 10, 0, 10));

        VBox greeting = new VBox();
        Text welcome = new Text("Welcome to YouTube application");
        welcome.setStyle("-fx-font: 22px Arial;");
        greeting.setMinWidth(WIDTH - menu.getMinWidth() - 50);
        greeting.setMinHeight(HEIGHT - history.getMinHeight() - 100);
        greeting.setAlignment(Pos.CENTER);
        greeting.getChildren().add(welcome);

        VBox searchingResults = new VBox();
        searchingResults.setPadding(new Insets(10));
        searchingResults.setSpacing(10);
        searchingResults.setStyle("-fx-font: 15px Arial;");
        searchingResults.getChildren().add(greeting);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(WIDTH - menu.getMinWidth());
        scrollPane.setMinHeight(HEIGHT - history.getMinHeight()- 30);
        scrollPane.setContent(searchingResults);

        // Переключение между страницами
        Button prev = ComponentsUI.generateButton("", 50, () -> {
            Platform.runLater(() -> {
                searchingResults.getChildren().clear();
                if (pages.size() == 0) return;
                if (pageIndex <= 0) {
                    searchingResults.getChildren().add(pages.get(pageIndex = 0));
                } else {
                    searchingResults.getChildren().add(pages.get(--pageIndex));
                }
            });
        });
        prev.setGraphic(ComponentsUI.getImage("buttonImages/back.png", 20,20));

        // Переключение между страницами
        Button next = ComponentsUI.generateButton("", 50, () -> {
            Platform.runLater(() -> {
                searchingResults.getChildren().clear();
                if (pages.size() == 0) return;
                if (pageIndex >= pages.size() - 1) {
                    searchingResults.getChildren().add(pages.get(pageIndex = pages.size() - 1));
                } else {
                    searchingResults.getChildren().add(pages.get(++pageIndex));
                }
            });
        });
        next.setGraphic(ComponentsUI.getImage("buttonImages/forward.png", 20,20));
        history.getChildren().addAll(prev, next);

        // Поиск видео по имени
        Button searchMenu = ComponentsUI.generateButton("Search Videos", (int) menu.getMinWidth(),
                () -> {
                searchingResults.getChildren().clear();
                searchingResults.getChildren().add(searchBox(searchingResults));
        });
        searchMenu.getStyleClass().add("button");

        // Расширенный поиск
        Button advancedSearch = ComponentsUI.generateButton("Advanced search", (int) menu.getMinWidth(),
                () -> {
                searchingResults.getChildren().clear();
                searchingResults.getChildren().add(advancedSearchBox(searchingResults));
        });
        searchMenu.getStyleClass().add("button");

        Button clearHistory = ComponentsUI.generateButton("Clear search history", (int) menu.getMinWidth(),
                () -> Platform.runLater(() -> pages.clear()));
        clearHistory.getStyleClass().add("button");

        menu.getChildren().addAll(searchMenu, advancedSearch, clearHistory);
        content.getChildren().addAll(history, scrollPane);
        root.getChildren().addAll(menu, content);
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.show();
    }

    public VBox searchBox(VBox searchingResults){
        VBox searchBox = new VBox();
        searchBox.setSpacing(10);
        searchBox.setStyle("-fx-font: 15px Arial;");
        TextField inputVideoName = new TextField();
        inputVideoName.setMinWidth(360);
        searchBox.getChildren().addAll(new Text("Enter video name:"), inputVideoName,
                searchButton(searchingResults, inputVideoName));
        return searchBox;
    }

    public Button searchButton(VBox results, TextField inputVideoName){
        return ComponentsUI.generateButton("Search", 100, () -> {
            ExecutorService pool = Executors.newFixedThreadPool(5);
            pool.submit(() -> {
                HttpResponse<YouTubeEntity> response = YouTubeClient.getSearchItems(inputVideoName.getText(), 5);
                YouTubeEntity body = response.getBody();
                Platform.runLater(() -> {
                    results.getChildren().clear();
                    results.getChildren().add(searchBox(results));
                    results.getChildren().add(drawYoutubeItems(body, results));
                    pages.add(drawYoutubeItems(body, results));
                    pageIndex++;
                });
            });
            pool.shutdown();
        });
    }

    public VBox advancedSearchBox(VBox searchingResults){
        VBox advancedSearchBox = new VBox();
        advancedSearchBox.setSpacing(10);
        TextField inputVideoName = new TextField();
        TextField inputMaxResults = new TextField();
        TextField inputCountDays = new TextField();
        advancedSearchBox.getChildren().addAll(new Text("Enter video name:"), inputVideoName);
        advancedSearchBox.getChildren().addAll(new Text("Enter the maximum number of results in the issuance:"), inputMaxResults);
        advancedSearchBox.getChildren().addAll( new Text("Enter the number of days after publication:"), inputCountDays);
        advancedSearchBox.getChildren().add(advancedButton(searchingResults, inputVideoName, inputMaxResults, inputCountDays));
        return advancedSearchBox;
    }

    public Button advancedButton(VBox results, TextField inputName, TextField inputMaxResults, TextField inputCountDays){
        return ComponentsUI.generateButton("Search", 100, () -> {
            ExecutorService pool = Executors.newFixedThreadPool(5);
            pool.submit(() -> {
                HttpResponse<YouTubeEntity> response = YouTubeClient.getItemsByDate(inputName.getText(),
                        Integer.parseInt(inputMaxResults.getText()),
                        YouTubeDate.getDateForPublish(Integer.parseInt(inputCountDays.getText())));
                YouTubeEntity body = response.getBody();
                Platform.runLater(() -> {
                    results.getChildren().clear();
                    results.getChildren().add(advancedSearchBox(results));
                    results.getChildren().add(drawYoutubeItems(body, results));
                    pages.add(drawYoutubeItems(body, results));
                    pageIndex++;
                });
            });
            pool.shutdown();
        });
    }

    // Отрисовка элементов поска
    public VBox drawYoutubeItems(YouTubeEntity response, VBox searchingResults){
        VBox page = new VBox();
        page.setSpacing(10);
        page.setMinWidth(searchingResults.getMinWidth());
        ArrayList<YouTubeItem> items = response.getItems();
        for (int i = 0; i < items.size(); i++){
            page.getChildren().add(drawItem(items.get(i), page));
        }
        return page;
    }

    // Отрисовка элемента
    public VBox drawItem(YouTubeItem item, VBox content){
        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().add(ComponentsUI.generateButton("Channel: " + item.getSnippet().getChannelTitle(),
                500,"#e7d4f9", () -> Platform.runLater(() -> getChannelInfo(content, item))));
        box.getChildren().add(new Text("Name video: " + item.getSnippet().getTitle()));
        box.getChildren().add(new Text("Published at: " + YouTubeDate.getStringDateFromISO8601(
                item.getSnippet().getPublishedAt())));

        String url = "https://www.youtube.com/embed/" + item.getId() + "?autoplay=1";
        box.getChildren().add(ComponentsUI.generateImage(item.getSnippet().getThumbnails().getMedium()));
        box.getChildren().add(ComponentsUI.generateButton("View", 100, () -> {
                content.getChildren().clear();
                playVideo(content, url);
        }));
        return box;
    }

    // Воспроизведение видео
    private void playVideo(VBox content, String url){
        content.getChildren().clear();
        WebView webview = new WebView();
        webview.getEngine().load(url);
        webview.setPrefSize(content.getWidth(), content.getHeight());
        content.getChildren().add(webview);
    }

    // Отрисовка иформации канала
    private void getChannelInfo(VBox content, YouTubeItem item){
        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(() -> {
            HttpResponse<YouTubeEntity> channel = YouTubeClient.getChannelInfo(item.getSnippet().getChannelId());
            YouTubeEntity body = channel.getBody();
            HttpResponse<YouTubeEntity> response = YouTubeClient.getChannelPlaylist(item.getSnippet().getChannelId(), 10);
            YouTubeEntity playlist = response.getBody();
            Platform.runLater(() -> drawingChannelInfo(content, body, playlist));
        });
        pool.shutdown();
    }

    private void drawingChannelInfo(VBox content, YouTubeEntity channel, YouTubeEntity playlist){
        content.getChildren().clear();

        ArrayList<YouTubeItem> items = channel.getItems();
        Image image = new Image(items.get(0).getSnippet().getThumbnails().getMedium().getUrl());
        ImageView imageView = new ImageView(image);

        VBox textBox = new VBox();
        textBox.setMaxWidth(content.getMinWidth());
        textBox.getChildren().add(new Text(items.get(0).getSnippet().getDescription()));

        VBox channelInfo = new VBox();
        channelInfo.setSpacing(10);
        channelInfo.getChildren().addAll(new Text(items.get(0).getSnippet().getTitle()), textBox);
        channelInfo.getChildren().add(drawYoutubeItems(playlist,content));
        pages.add(channelInfo);

        content.getChildren().addAll(imageView, new Text(items.get(0).getSnippet().getTitle()), textBox);
        content.getChildren().add(drawYoutubeItems(playlist,content));

    }

}
