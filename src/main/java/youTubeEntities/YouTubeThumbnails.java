package youTubeEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeThumbnails {
    private YouTubeThumbnailImage medium;
    private YouTubeThumbnailImage high;

    public YouTubeThumbnailImage getMedium() {
        return medium;
    }

    public void setMedium(YouTubeThumbnailImage medium) {
        this.medium = medium;
    }

    public YouTubeThumbnailImage getHigh() {
        return high;
    }

    public void setHigh(YouTubeThumbnailImage high) {
        this.high = high;
    }

}
