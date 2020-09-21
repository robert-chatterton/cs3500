// Robert Chatterton

package cs3500.hw01.duration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests for the format method of {@link Duration}s. 
 Add your tests to this class to assure that your format
 method works properly
 */
public abstract class AbstractDurationFormatTest {
  @Test
  public void noPaddingTest() {
    assertEquals("4 hours, 0 minutes, and 9 seconds",
            hms(4, 0, 9)
                    .format("%h hours, %m minutes, and %s seconds"));
  }

  @Test
  public void paddingTest() {
    assertEquals("4:05:07",
            hms(4, 5, 7).format("%h:%M:%S"));
  }

  // ADD MORE TESTS HERE. 
  // THE ABOVE TEST NAMES ARE MERE PLACEHOLDERS. NAME YOUR TESTS MEANINGFULLY. 
  // IF YOU NAME YOUR TESTS SIMILAR TO ABOVE, YOU WILL LOSE POINTS!
  // Your tests must only use hms(...) and sec(...) to construct new Durations
  // and must *not* directly say "new CompactDuration(...)" or
  // "new HmsDuration(...)"

  /**
   * Testing some "normal" examples.
   */
  @Test
  public void regularFormatTest() {
    assertEquals("3:42:09", hms(3, 42, 9).format("%h:%M:%S"));
    assertEquals("03:42:9", hms(3,42,9).format("%H:%m:%s"));
  }

  /**
   * Testing when the template is an empty string.
   */
  @Test
  public void blankTest() {
    assertEquals("", sec(3764).format(""));
  }

  /**
   * Testing with full zeroes, with and without capital letters in the template.
   */
  @Test
  public void zeroTest() {
    assertEquals("00:00:00", hms(0,0,0).format("%H:%M:%S"));
    assertEquals("0 hours, 0 minutes, and 0 seconds", hms(0,0,0)
            .format("%h hours, %m minutes, and %s seconds"));
    assertEquals("00:00:00", sec(0).format("%H:%M:%S"));
    assertEquals("0 hours, 0 minutes, and 0 seconds", sec(0)
            .format("%h hours, %m minutes, and %s seconds"));
  }

  /**
   * Testing with multiple copies of the same data.
   */
  @Test
  public void copyTest() {
    assertEquals("5:05:05:5", hms(5,9,2)
            .format("%h:%H:%H:%h"));
    assertEquals("5:09:9:2:02", hms(5,9,2)
            .format("%h:%M:%m:%s:%S"));
    assertEquals("5:05:05:5", sec(18542).format("%h:%H:%H:%h"));
    assertEquals("5:09:9:2:02", sec(18542).format("%h:%M:%m:%s:%S"));
  }

  /**
   * Checking for malformed templates.
   */
  @Test(expected = IllegalArgumentException.class)
  public void malformedTemplateTest() {
    assertEquals("malformed template",
            hms(2,3,15).format("%%h%"));
    assertEquals("malformed template",
            hms(2,3,15).format("%%h%:%%%M:%%s"));
    assertEquals("malformed template",
            sec(2900).format("%g:%q:%P"));
    assertEquals("malformed template",
            sec(2000).format("%T"));
  }

  /**
   * Checking proper use of '%%' in the templates.
   */
  @Test
  public void percentTemplateTest() {
    assertEquals("%2000", sec(2000).format("%%%t"));
    assertEquals("2000%", sec(2000).format("%t%%"));
  }

  /*
    Leave this section alone: It contains two abstract methods to
    create Durations, and concrete implementations of this testing class
    will supply particular implementations of Duration to be used within 
    your tests.
   */
  /**
   * Constructs an instance of the class under test representing the duration
   * given in hours, minutes, and seconds.
   *
   * @param hours the hours in the duration
   * @param minutes the minutes in the duration
   * @param seconds the seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration hms(int hours, int minutes, int seconds);

  /**
   * Constructs an instance of the class under test representing the duration
   * given in seconds.
   *
   * @param inSeconds the total seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration sec(long inSeconds);

  /**
   * Concrete class for testing HmsDuration implementation of Duration.
   */
  public static final class HmsDurationTest extends AbstractDurationFormatTest {
    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new HmsDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new HmsDuration(inSeconds);
    }
  }

  /**
   * Concrete class for testing CompactDuration implementation of Duration.
   */
  public static final class CompactDurationTest extends AbstractDurationFormatTest {
    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new CompactDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new CompactDuration(inSeconds);
    }
  }
}
