import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import youTubeEntities.YouTubeEntity;
import java.io.IOException;

public class YouTubeClient {
    private static final String YOU_TUBE = "https://www.googleapis.com/youtube/v3/{method}";
    private static final String API_KEY = "AIzaSyC728PQEXC4smd3BE0sRwZOh8Cu0NrWnSg";

    private YouTubeClient() {}

    static {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static HttpResponse<YouTubeEntity> getSearchItems(String nameVideo, int maxResults){
        try {
            return Unirest.get(YOU_TUBE)
                    .routeParam("method", "search")
                    .queryString("q", nameVideo)
                    .queryString("maxResults", maxResults)
                    .queryString("part", "snippet")
                    .queryString("key", API_KEY)
                    .asObject(YouTubeEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse<YouTubeEntity> getItemsByDate(String nameVideo, int maxResults, String date){
        try {
            return Unirest.get(YOU_TUBE)
                    .routeParam("method", "search")
                    .queryString("q", nameVideo)
                    .queryString("maxResults", maxResults)
                    .queryString("part", "snippet")
                    .queryString("publishedAfter", date)
                    .queryString("key", API_KEY)
                    .asObject(YouTubeEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse<YouTubeEntity> getChannelInfo(String channelID){
        try {
            return Unirest.get(YOU_TUBE)
                    .routeParam("method", "channels")
                    .queryString("id", channelID)
                    .queryString("part", "snippet")
                    .queryString("key", API_KEY)
                    .asObject(YouTubeEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse<YouTubeEntity> getChannelPlaylist(String channelID, int maxResults){
        try {
            return Unirest.get(YOU_TUBE)
                    .routeParam("method", "playlists")
                    .queryString("maxResults", maxResults)
                    .queryString("channelId", channelID)
                    .queryString("part", "snippet")
                    .queryString("key", API_KEY)
                    .asObject(YouTubeEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
