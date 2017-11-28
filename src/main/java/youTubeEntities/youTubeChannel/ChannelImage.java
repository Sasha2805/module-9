package youTubeEntities.youTubeChannel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThumbnailChannelImage {
    private ThumbnailMedium medium;

    public ThumbnailMedium getMedium() {
        return medium;
    }

    public void setMedium(ThumbnailMedium medium) {
        this.medium = medium;
    }

}
