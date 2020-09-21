package cs3500.hw01.publication;

/**
 * This class represents a webpage.
 */
public class Webpage implements Publication {
  private final String title;
  private final String url;
  private final String retrieved;

  /**
   * Constructor for a Webpage.
   * @param title      - This String is the title of the Webpage.
   * @param url        - This String is the url of the Webpage.
   * @param retrieved  - This String is the date when this Webpage was accessed.
   */
  public Webpage(String title, String url, String retrieved) {
    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  /**
   * The newWebpage() method creates a new webpage and cites it immediately.
   * @param title the title
   * @param url the url
   * @param retrieved the date this Webpage was visited
   * @param style the style, either "apa" or "mla"
   * @return a String citation
   */
  public String newWebpage(String title, String url, String retrieved, String style) {
    Webpage wb = new Webpage(title, url, retrieved);
    if (style.equals("apa")) {
      return wb.citeApa();
    } else if (style.equals("mla")) {
      return wb.citeMla();
    } else {
      throw new IllegalArgumentException("not a style");
    }
  }

  /**
   * The citeApa() method displays the information of this Webpage as an APA-style citation.
   * @return String of the APA-style citation.
   */
  @Override
  public String citeApa() {
    return this.title + ". Retrieved " + this.retrieved + ", from " + this.url + ".";
  }

  /**
   * The citeMLA() method displays the information of this Webpage as an MLA-style citation.
   * @return String of the MLA-style citation.
   */
  @Override
  public String citeMla() {
    return "\"" + this.title + ".\" Web. " + this.retrieved + " <" + this.url + ">.";
  }


}
