package portfolio.view;

import portfolio.controller.Features;

/**
 * This represent the view interface.
 * Display results to user.
 * Does not care how the results were produced, when to respond to user action.
 * Extension Change: Add setFeatures function, @12.06.
 *
 */
public interface IView {


  /**
   * Update message that shows to users.
   */
  void update(String message);


  /**
   * Get the set of feature callbacks that the view can use.
   * @param f the set of feature callbacks as a Features object
   */
  void setFeatures(Features f);


}
