package cs3500.hw01.publication;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test class for Webpage: unit tests to ensure that Webpages can be
 * cited correctly and otherwise behave correctly.
 */
public class WebpageTest {

  // normal Webpage examples
  Publication testPage1 = new Webpage("test webpage", "www.testwebpage.org",
          "August 11, 2019");
  Publication testPage2 = new Webpage("CS3500: Object-Oriented Design",
          "http://www.ccs.neu.edu/course/cs3500/", "May 4th, 2020");

  // empty string Webpage example
  Publication blank = new Webpage("", "", "");

  /**
   * Testing the citeApa() method.
   */
  @Test
  public void testCiteApa() {
    assertEquals("test webpage. Retrieved August 11, 2019, from www.testwebpage.org.",
            testPage1.citeApa());
    assertEquals("CS3500: Object-Oriented Design. Retrieved May 4th, 2020, "
                    + "from http://www.ccs.neu.edu/course/cs3500/.", testPage2.citeApa());
    assertEquals(". Retrieved , from .", blank.citeApa());
  }

  /**
   * Testing the citeMla() method.
   */
  @Test
  public void testCiteMla() {
    assertEquals("\"test webpage.\" Web. August 11, 2019 <www.testwebpage.org>.",
            testPage1.citeMla());
    assertEquals("\"CS3500: Object-Oriented Design.\" Web. May 4th, 2020 "
            + "<http://www.ccs.neu.edu/course/cs3500/>.", testPage2.citeMla());
    assertEquals("\".\" Web.  <>.", blank.citeMla());
  }

}