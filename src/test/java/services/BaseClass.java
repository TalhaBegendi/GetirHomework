package services;
import com.google.common.io.Resources;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class BaseClass {


    public static JSONObject readJsonFile(String jsonFileName) throws IOException {

        URL jsonFile = Resources.getResource(jsonFileName+".json");
        String json = Resources.toString(jsonFile, Charset.defaultCharset());
        JSONObject jsonObject = new JSONObject( json );
        return jsonObject;
    }
}


