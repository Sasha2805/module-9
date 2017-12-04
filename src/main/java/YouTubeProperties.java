import java.io.*;
import java.util.Properties;

public class YouTubeProperties {
    public static void writePropertiesToFile(String filePath, String youTubeURL, String apiKey){
        Properties properties = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream(filePath);
            properties.setProperty("YouTubeURL", youTubeURL);
            properties.setProperty("API_KEY", apiKey);
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Properties loadPPropertiesFromFile(String filePath){
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(filePath);
            properties.load(input);
            return properties;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
