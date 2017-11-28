package youTubeEntities.youTubeChannel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import youTubeEntities.YouTubeItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeChannel extends YouTubeItem {
    private ChannelImage medium;

    public ChannelImage getMedium() {
        return medium;
    }

    public void setMedium(ChannelImage medium) {
        this.medium = medium;
    }
}
