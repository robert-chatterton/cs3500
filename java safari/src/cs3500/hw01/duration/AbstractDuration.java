package cs3500.hw01.duration;

/**
 * Abstract base class for implementations of {@link Duration}.
 */
abstract class AbstractDuration implements Duration {
  /**
   * Constructs a {@link Duration} in a manner selected by concrete subclasses
   * of this class.
   *
   * @param inSeconds the length in seconds
   * @return the new {@code Duration}
   */
  protected abstract Duration fromSeconds(long inSeconds);

  @Override
  public String toString() {
    return asHms();
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Duration)) {
      return false;
    }

    return ((Duration) that).inSeconds() == this.inSeconds();
  }

  @Override
  public int hashCode() {
    return Long.hashCode(inSeconds());
  }

  @Override
  public int compareTo(Duration that) {
    return Long.compare(this.inSeconds(), that.inSeconds());
  }

  @Override
  public Duration plus(Duration that) {
    return fromSeconds(this.inSeconds() + that.inSeconds());
  }

  /**
   * Converts an unpacked hours-minutes-seconds duration to its length in
   * seconds.
   *
   * @param hours   the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @return the duration in seconds
   */
  protected static long inSeconds(int hours, int minutes, int seconds) {
    return 3600 * hours + 60 * minutes + seconds;
  }

  /**
   * Formats an unpacked hours-minutes-seconds duration in the same {@code
   * H:MM:SS} format that {@link Duration#asHms()} returns. Assumes that
   *
   * @param hours   the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @return formatted duration
   * @throws IllegalArgumentException if any argument is negative
   */
  protected static String asHms(int hours, int minutes, int seconds) {
    return String.format("%d:%02d:%02d", hours, minutes, seconds);
  }

  /**
   * Ensures that the hours, minutes, and seconds are all non-negative. Is
   * factoring this out overkill? Or should we also factor out the {@code
   * inSeconds < 0} check in the two unary constructors? Discuss.
   *
   * @param hours   the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @throws IllegalArgumentException if any argument is negative
   */
  protected static void ensureHms(int hours, int minutes, int seconds) {
    if (hours < 0 || minutes < 0 || seconds < 0) {
      throw new IllegalArgumentException("must be non-negative");
    }
  }

  /**
   * Returns the number of whole hours in the given number of seconds.
   *
   * @param inSeconds the total number of seconds
   * @return the number of hours
   * @throws ArithmeticException if the correct result cannot fit in an {@code
   *                             int}.
   */
  protected static int hoursOf(long inSeconds) {
    if (inSeconds / 3600 > Integer.MAX_VALUE) {
      throw new ArithmeticException("result cannot fit in type");
    }

    return (int) (inSeconds / 3600);
  }

  /**
   * Returns the number of whole minutes in the given number of seconds, less
   * the number of whole hours.
   *
   * @param inSeconds the total number of seconds
   * @return the number of remaining minutes
   */
  protected static int minutesOf(long inSeconds) {
    return (int) (inSeconds / 60 % 60);
  }

  /**
   * Returns the number of seconds remaining after all full minutes are removed
   * from the given number of seconds.
   *
   * @param inSeconds the total number of seconds
   * @return the number of remaining seconds
   */
  protected static int secondsOf(long inSeconds) {
    return (int) (inSeconds % 60);
  }

  /**
   * Method implementation of format().
   * @param template the template.
   * @return String, the resulting string based off of the template given.
   */
  @Override
  public String format(String template) {
    if (template.equals("")) {
      return template;
    }
    String toReturn = "";
    int hours = hoursOf(this.inSeconds());
    int minutes = minutesOf(this.inSeconds());
    int seconds = (int) this.inSeconds() % 60;

    char lastChar = template.charAt(0);

    for (int i = 1; i < template.length(); i++) {
      char current = template.charAt(i);
      char nextChar = Character.MIN_VALUE; // empty char
      if (i != template.length() - 1) {
        nextChar = template.charAt(i + 1);
      }
      // if the previous character was a '%', check if it is an identifier
      if (lastChar == '%') {
        if (Character.toLowerCase(current) == 'h') {
          if (current == 'H' && hours < 10) {
            toReturn = toReturn + '0' + Long.toString(hours);
          } else {
            toReturn = toReturn + Long.toString(hours);
          }
        } else if (Character.toLowerCase(current) == 'm') {
          if (current == 'M' && minutes < 10) {
            toReturn = toReturn + '0' + Long.toString(minutes);
          } else {
            toReturn = toReturn + Long.toString(minutes);
          }
        } else if (Character.toLowerCase(current) == 's') {
          if (current == 'S' && seconds < 10) {
            toReturn = toReturn + '0' + Long.toString(seconds);
          } else {
            toReturn = toReturn + Long.toString(seconds);
          }
        } else if (current == 't') {
          toReturn = toReturn + this.inSeconds();
        } else if (current == '%'
                && Character.toLowerCase(nextChar) != 'h'
                && Character.toLowerCase(nextChar) != 'm'
                && Character.toLowerCase(nextChar) != 's'
                && nextChar != 't') {
          toReturn = toReturn + current;
          i++;
          if (i <= template.length() - 1) {
            lastChar = template.charAt(i);
          }
        } else {
          throw new IllegalArgumentException("malformed template");
        }
      } else if (current != '%') {
        toReturn = toReturn + current;
      }
      lastChar = current;
    }
    return toReturn;
  }
}
