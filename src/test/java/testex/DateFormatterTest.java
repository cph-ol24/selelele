package testex;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DateFormatterTest {

  @Test
  public void assertWork() throws Exception {
    DateFormatter dateFormatter = new DateFormatter();

    Date date = new Date(2015, 3, 4, 12, 55);
    String response = dateFormatter.getFormattedDate("Europe/Kiev", date);

    assertEquals("04 Apr 3915 01:55 PM", response);
  }

  @Test(expected=JokeException.class)
  public void assertThrowsWhenInvalidTimeZone() throws Exception {
    DateFormatter dateFormatter = new DateFormatter();

    dateFormatter.getFormattedDate("InvalidTimezone", new Date());
  }
}
