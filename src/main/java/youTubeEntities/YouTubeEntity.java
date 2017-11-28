package youTubeEntities;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeEntity {
    private ArrayList<YouTubeItem> items = new ArrayList<>();

    public ArrayList<YouTubeItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<YouTubeItem> items) {
        this.items = items;
    }
}
