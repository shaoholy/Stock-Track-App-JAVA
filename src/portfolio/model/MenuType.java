package portfolio.model;

/**
 * Type for the three types of suits in a game of Freecell.
 * clubs (♣), diamonds (♦), hearts (♥), and spades (♠),
 * Hearts and diamonds are colored red; clubs and spades are colored black.
 * Extension Changes : Add STRATEGY MenuType, @11.25.
 */
public enum MenuType {

  VIEWALL("1. View all Stock Portfolios"),
  EXAMONE("2. Examine one Stock Portfolio"),
  CREATE("3. Create Stock Portfolio"),
  BUYSTOCK("4. Buy Stock"),
  TOTALCOST("5. Show total Cost Basis"),
  TOTALVALUE("6. Show total Value"),
  STRATEGY("7. Apply Strategy"), //add this one
  SAVE("8. Save Portfolio"),
  ADD("9. Add Stock"),
  GRAPH("10. Draw Graph"),

  QUIT("Quit");


  private String message;

  /**
   * Construct a SuitType enum.
   *
   * @param message the character representation of SuitType.
   */
  MenuType(String message) {
    this.message = message;
  }

  /**
   * Return the character representation of SuitType.
   *
   * @return the character representation of SuitType.
   */
  public String getMessage() {
    return this.message;
  }
}
