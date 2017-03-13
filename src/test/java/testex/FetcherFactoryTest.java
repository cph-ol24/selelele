package testex;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FetcherFactoryTest {

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
    FetcherFactory factory = new FetcherFactory();

    List<IJokeFetcher> result = factory.getJokeFetchers("eduprog,ChuckNorris,Moma,Tambal");

    assertEquals(4, result.size());
  }
}
