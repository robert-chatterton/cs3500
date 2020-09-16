package cs3500.marblesolitaire;

import java.io.InputStreamReader;

/**
 * The application class.
 */
public final class MarbleSolitaire {
  /**
   * The main method, runs the program.
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;

    cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel model;
    boolean english = false;
    boolean european = false;
    boolean triangle = false;
    int size = -1;
    int holeRow = -1;
    int holeCol = -1;

    for ( int i = 0; i < args.length; i++ ) {
      String s = args[i];
      switch (s) {
        case "english":
          english = true;
          european = false;
          triangle = false;
          break;
        case "european":
          english = false;
          european = true;
          triangle = false;
          break;
        case "triangle":
          english = false;
          european = false;
          triangle = true;
          break;
        case "-size":
          size = getSize(i, args);
          i++;
          break;
        case "-hole":
          holeRow = getRow(i, args);
          holeCol = getCol(i, args);
          i += 2;
          break;
        default: throw new IllegalArgumentException("invalid command");
      }
    }

    model = createModel(english, european, triangle, size, holeRow, holeCol);

    helper(model, new cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl(rd, ap));
  }

  private static int getSize(int idx, String[] args) {
    int size;
    try {
      size = Integer.parseInt(args[idx + 1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid size");
    }

    return size;
  }

  private static int getRow(int idx, String[] args) {
    int row;
    try {
      row = Integer.parseInt(args[idx + 1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid row");
    }

    return row;
  }

  private static int getCol(int idx, String[] args) {
    int col;
    try {
      col = Integer.parseInt(args[idx + 2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid col");
    }

    return col;
  }

  private static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel
          createModel(boolean eng, boolean eur, boolean tri, int size, int row, int col) {
    if (eng) {
      if (size != -1 && row != -1) {
        return new cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl(size, row, col);
      } else if (row != -1) {
        return new cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl(row, col);
      } else if (size != -1) {
        return new cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl(size);
      } else {
        return new cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl();
      }
    } else if (eur) {
      if (size != -1 && row != -1) {
        return new cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl(size, row, col);
      } else if (row != -1) {
        return new cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl(row, col);
      } else if (size != -1) {
        return new cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl(size);
      } else {
        return new cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl();
      }
    } else if (tri) {
      if (size != -1 && row != -1) {
        return new cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl(size, row, col);
      } else if (row != -1) {
        return new cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl(row, col);
      } else if (size != -1) {
        return new cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl(size);
      } else {
        return new cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl();
      }
    }

    return null;
  }

  private static void helper(
          cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel model,
          cs3500.marblesolitaire.controller.MarbleSolitaireController controller) {
    controller.playGame(model);
  }
}
