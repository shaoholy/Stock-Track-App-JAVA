package portfolio.controller.command;

/**
 * Interface for all the menu commands.
 */
public interface ICommand {

  /**
   * Run the specific menu command.(eg: View all Stock Portfolios, Create Stock Portfolio, ect.)
   * Return true if this command runs successfully;
   * Return false if user quit Portfolio in the process.
   *
   * @return true if this command runs successfully; false if user quit Portfolio in the process.
   */
  boolean goCommand();
}
