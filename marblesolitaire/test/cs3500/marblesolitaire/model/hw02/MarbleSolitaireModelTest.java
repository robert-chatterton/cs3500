package cs3500.marblesolitaire.model.hw02;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Testing class.
 */
public class MarbleSolitaireModelTest {

  // testing constructors
  @Test
  public void testConstructors() {
    MarbleSolitaireModel classicM3 = new MarbleSolitaireModelImpl();
    MarbleSolitaireModel customM3 = new MarbleSolitaireModelImpl(0, 2);
    MarbleSolitaireModel customArm = new MarbleSolitaireModelImpl(5);
    MarbleSolitaireModel customEverything = new MarbleSolitaireModelImpl(5, 0, 4);
    assertFalse(classicM3.isGameOver());
  }

  @Test
  public void testGetGameState() {
    MarbleSolitaireModel m3 = new MarbleSolitaireModelImpl();
    String game = m3.getGameState();
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", game);

    MarbleSolitaireModel m5 = new MarbleSolitaireModelImpl(5);
    String game5 = m5.getGameState();
    assertEquals("        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O", game5);
  }

  @Test
  public void testScore() {
    MarbleSolitaireModel m3 = new MarbleSolitaireModelImpl();
    assertEquals(32, m3.getScore());
  }

  @Test
  public void testMove() {
    MarbleSolitaireModel m3 = new MarbleSolitaireModelImpl(0, 2);
    assertEquals("    _ O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", m3.getGameState());
    m3.move(2, 2, 0, 2);
    assertEquals("    O O O\n"
            + "    _ O O\n"
            + "O O _ O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", m3.getGameState());
    m3.move(2, 0, 2, 2);
    assertEquals("    O O O\n"
            + "    _ O O\n"
            + "_ _ O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", m3.getGameState());
    m3.move(2, 3, 2, 1);
    assertEquals("    O O O\n"
            + "    _ O O\n"
            + "_ O _ _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", m3.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    MarbleSolitaireModel m3 = new MarbleSolitaireModelImpl();
    m3.move(0, 0, 2, 0);
  }

}
