package testex;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.Arrays;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

  @Test
  public void testGetAvailableTypes() throws Exception {
    JokeFetcher jokeFetcher = createJokeFetcher();

    List<String> types = jokeFetcher.getAvailableTypes();

    assertTrue(types.contains("EduJoke"));
    assertTrue(types.contains("ChuckNorris"));
    assertTrue(types.contains("Moma"));
    assertTrue(types.contains("Tambal"));
  }

  @Test
  public void testCheckIfValidToken() throws Exception {
    JokeFetcher jokeFetcher = createJokeFetcher();

    assertTrue(jokeFetcher.isStringValid("Moma"));
    assertFalse(jokeFetcher.isStringValid("invalid"));
  }

  @Test(expected = JokeException.class)
  public void testGetJokesThrows() throws Exception {
    JokeFetcher jokeFetcher = createJokeFetcher();

    jokeFetcher.getJokes("invalid", "timeZone");
  }

  @Mock
  private IDateFormatter dfMock;

  @Mock
  private FetcherFactory factoryMock;

  @Mock
  private YoMammaJokeFetcher yoMammaJokeFetcherMock;

  @Mock
  private ChuckNorrisJokeFetcher chuckNorrisJokeFetcherMock;

  @Mock
  private TambalJokeFetcher tambalJokeFetcherMock;

  @Mock
  private EduJokeFetcher eduJokeFetcherMock;

  @Test()
  public void testShouldWork() throws Exception {
    when(dfMock.getFormattedDate(eq("Europe/Copenhagen"), anyObject())).thenReturn("17 feb. 2017 10:56 AM");
    when(dfMock.getFormattedDate(eq("Europe/Copenhagen"), anyObject())).thenReturn("17 feb. 2017 10:56 AM");

    JokeFetcher jokeFetcher = createJokeFetcher();

    Jokes jokes = jokeFetcher.getJokes("Moma", "Europe/Copenhagen");

    assertEquals("17 feb. 2017 10:56 AM", jokes.getTimeZoneString());
    verify(dfMock, times(1)).getFormattedDate(eq("Europe/Copenhagen"), anyObject());
  }

  private JokeFetcher createJokeFetcher() {
    List<IJokeFetcher> fetchers = Arrays.asList(eduJokeFetcherMock, chuckNorrisJokeFetcherMock, yoMammaJokeFetcherMock,
        tambalJokeFetcherMock);
    when(factoryMock.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetchers);
    List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
    when(factoryMock.getAvailableTypes()).thenReturn(types);

    return new JokeFetcher(this.dfMock, this.factoryMock);
  }
}
