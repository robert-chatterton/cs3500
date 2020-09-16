package cs3500.marblesolitaire.model.hw04;

import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

import static org.junit.Assert.assertEquals;

/**
 * For testing TriangleSolitaireModel.
 */
public class TriangleSolitaireModelImplTest {

  @Test
  public void testValidConstructors() {
    MarbleSolitaireModel standard = new TriangleSolitaireModelImpl();
    MarbleSolitaireModel cons2 = new TriangleSolitaireModelImpl(1, 1);
    MarbleSolitaireModel cons3 = new TriangleSolitaireModelImpl(6);
    MarbleSolitaireModel cons4 = new TriangleSolitaireModelImpl(7, 3, 2);

    assertEquals("    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O", standard.getGameState());
    assertEquals(14, standard.getScore());
    assertEquals("    O\n" +
            "   O _\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O", cons2.getGameState());
    assertEquals(14, cons2.getScore());
    assertEquals("     _\n" +
            "    O O\n" +
            "   O O O\n" +
            "  O O O O\n" +
            " O O O O O\n" +
            "O O O O O O", cons3.getGameState());
    assertEquals(20, cons3.getScore());
    assertEquals("      O\n" +
            "     O O\n" +
            "    O O O\n" +
            "   O O _ O\n" +
            "  O O O O O\n" +
            " O O O O O O\n" +
            "O O O O O O O", cons4.getGameState());
    assertEquals(27, cons4.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructors() {
    MarbleSolitaireModel badCons2 = new TriangleSolitaireModelImpl(2, 3);
    MarbleSolitaireModel badCons3 = new TriangleSolitaireModelImpl(-2);
    MarbleSolitaireModel badCons4 = new TriangleSolitaireModelImpl(-2, 2, 1);
    MarbleSolitaireModel badCons4_2 = new TriangleSolitaireModelImpl(7, 2, 3);
  }
}