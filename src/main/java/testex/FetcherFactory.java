
package testex;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.response.ExtractableResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import testex.IDateFormatter;

public class FetcherFactory implements IFetcherFactory {

    private final List<String> availableTypes = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {
        List<IJokeFetcher> fetchers = new ArrayList<IJokeFetcher>();

        for (String token : jokesToFetch.split(",")) {
            switch (token.toLowerCase()) {
            case "eduprog":
                fetchers.add(new EduJokeFetcher());
                break;
            case "moma":
                fetchers.add(new YoMammaJokeFetcher());
                break;
            case "chucknorris":
                fetchers.add(new ChuckNorrisJokeFetcher());
                break;
            case "tambal":
                fetchers.add(new TambalJokeFetcher());
                break;
            }
        }

        return fetchers;
    }
}
