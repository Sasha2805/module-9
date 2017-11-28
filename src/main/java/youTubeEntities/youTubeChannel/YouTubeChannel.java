package entities.youTubeChannel;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeChannel {
    private ArrayList<YouTubeChannelItem> items = new ArrayList<>();

    public ArrayList<YouTubeChannelItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<YouTubeChannelItem> items) {
        this.items = items;
    }

}
