package cs3500.marblesolitaire.model.hw04;

import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

import static org.junit.Assert.assertEquals;

/**
 * For testing the EuropeanSolitaireModel.
 */
public class EuropeanSolitaireModelImplTest {

  @Test
  public void testValidConstructors() {
    MarbleSolitaireModel standard = new EuropeanSolitaireModelImpl();
    MarbleSolitaireModel cons2 = new EuropeanSolitaireModelImpl(1, 1);
    MarbleSolitaireModel cons3 = new EuropeanSolitaireModelImpl(5);
    MarbleSolitaireModel cons4 = new EuropeanSolitaireModelImpl(5, 6, 7);

    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", standard.getGameState());
    assertEquals(36, standard.getScore());
    assertEquals("    O O O\n"
            + "  _ O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", cons2.getGameState());
    assertEquals(36, cons2.getScore());
    assertEquals("        O O O O O\n" +
            "      O O O O O O O\n" +
            "    O O O O O O O O O\n" +
            "  O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O _ O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "  O O O O O O O O O O O\n" +
            "    O O O O O O O O O\n" +
            "      O O O O O O O\n" +
            "        O O O O O", cons3.getGameState());
    assertEquals(128, cons3.getScore());
    assertEquals("        O O O O O\n" +
            "      O O O O O O O\n" +
            "    O O O O O O O O O\n" +
            "  O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O _ O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "  O O O O O O O O O O O\n" +
            "    O O O O O O O O O\n" +
            "      O O O O O O O\n" +
            "        O O O O O", cons4.getGameState());
    assertEquals(128, cons4.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructors() {
    MarbleSolitaireModel badCons2 = new EuropeanSolitaireModelImpl(0, 0);
    MarbleSolitaireModel badCons3 = new EuropeanSolitaireModelImpl(4);
    MarbleSolitaireModel badCons4 = new EuropeanSolitaireModelImpl(4, 6, 7);
    MarbleSolitaireModel badCons4_2 = new EuropeanSolitaireModelImpl(5, 0, 0);
  }

}