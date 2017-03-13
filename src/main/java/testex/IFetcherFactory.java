
package testex;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.response.ExtractableResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import testex.IDateFormatter;

public interface IFetcherFactory {
  List<String> getAvailableTypes();
  List<IJokeFetcher> getJokeFetchers(String jokesToFetch);
}
