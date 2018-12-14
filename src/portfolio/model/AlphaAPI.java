package portfolio.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AlphaAPI implements StockInforAPI {
  private static Map<String, String> symToInfor = new HashMap<>();

  @Override
  public double getLatestPrice(String sym) throws IllegalArgumentException {

    String latestInfor = getData(sym, getTodayDate());
    String[] inputLines = latestInfor.split("\n|\r");
    for (String str: inputLines) {
      if (str.length() < 3 || !Character.isDigit(str.charAt(0))) {
        continue;
      }
      return getPriceFromLine(str);
    }
    return 0.0;
  }

  @Override
  public double getPriceofDate(String sym, Date targetDate) throws IllegalArgumentException {

    String input = getData(sym, targetDate);

    for (String str: input.split("\n|\r")) {
      if (str.length() < 3 || !Character.isDigit(str.charAt(0))) {
        continue;
      }
      String[] infor = str.split(",");
      Date lineDate = Date.valueOf(infor[0]);
      if (targetDate.before(lineDate)) {
        continue;
      } else if (targetDate.after(lineDate)) {
        throw new IllegalArgumentException("not a open market date");
      } else {
        return getPriceFromLine(str);
      }
    }
    throw new IllegalArgumentException("no data for such a date of the stock");
  }

  @Override
  public Date getLastOpenDate(Date targetDate) {
    String nasdaqSym = "DJIA";
    //URL thisURL = getURL(nasdaqSym);
    String latestInfor = getData(nasdaqSym, targetDate);
    String[] inputLines = latestInfor.split("\n|\r");

    for (String str: inputLines) {
      if (str.length() < 9 || str.startsWith("times")) {
        continue;
      }
      String[] infor = str.split(",");
      Date lineDate = Date.valueOf(infor[0]);
      if (targetDate.before(lineDate)) {
        continue;
      } else {
        return lineDate;
      }
    }
    throw new IllegalArgumentException("no data for such a date of the stock");
  }

  @Override
  public Date getNextOpenDate(Date targetDate) {
    String nasdaqSym = "DJIA";
    //URL thisURL = getURL(nasdaqSym);
    String latestInfor = getData(nasdaqSym, targetDate);
    String[] inputLines = latestInfor.split("\n|\r");
    Date cache = targetDate;

    for (String str: inputLines) {
      if (str.length() < 3 || str.startsWith("times")) {
        continue;
      }
      String[] infor = str.split(",");
      Date lineDate = Date.valueOf(infor[0]);
      if (targetDate.before(lineDate)) {
        cache = lineDate;
        continue;
      } else if (targetDate.after(lineDate)) {
        return cache;
      } else {
        return lineDate;
      }
    }
    throw new IllegalArgumentException("no data for such a date of the stock");
  }

  /**
   * The methods returns a URL object for the API stock price information query.
   *
   * @param stockSymbol a String as the stock ticker symbol
   * @return a date object as the closest date before the given date which is an open market date
   */
  private URL getURL(String stockSymbol) {
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    return url;
  }

  /**
   * The methods returns a BufferedReader object converted from the queried information by the API.
   *
   * @param url the String as the ticker symbol the the price
   * @return a BufferedReader object as the query price information
   * @throws IllegalArgumentException when API provided information not correct
   * @throws IllegalArgumentException when given date is not open for market trade
   */
  private String getStr(URL url) throws IllegalArgumentException {
    StringBuilder output = new StringBuilder();
    try {
      //System.out.println("SLEEP");
      //System.out.println(url.toString());
      Thread.sleep(20000);

    } catch (InterruptedException ex) {
    }
    try {
      InputStream in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char)b);
      }
      return output.toString();
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for ");
    }
  }

  /**
   * The methods returns a double value as the latest price of the API.
   *
   * @param line the String as the line of the right line of the stock information
   * @return a double value as the price of the stock
   */
  private double getPriceFromLine(String line) {
    if (line == null || line.length() == 0) {
      return 0.0;
    }
    String[] infor = line.split(",");
    return Double.parseDouble(infor[infor.length - 2]);
  }

  private Date getTodayDate() {
    Calendar todayCal = Calendar.getInstance();

    todayCal.clear(Calendar.HOUR);
    todayCal.clear(Calendar.MINUTE);
    todayCal.clear(Calendar.SECOND);
    return new Date(todayCal.getTimeInMillis());
  }



  private String getData(String sym, Date date) {
    if (symToInfor.containsKey(sym)) {


      String lastQuery = symToInfor.get(sym);
      //System.out.println(" SYm:  " + sym);
      String dateStr = null;
      for (String str: lastQuery.split("\n|\r")) {
        if (str.length() < 3 || !Character.isDigit(str.charAt(0))) {
          continue;
        }
        String[] strArr = str.split(",");
        dateStr = strArr[0];
        //System.out.println(" date:  " + dateStr);
        break;
      }
      //System.out.println("TargetDate" + date.toString());
      if (date.toString().equals(dateStr) || date.before(Date.valueOf(dateStr))) {
        //System.out.println(sym + " GOOD!");
        return lastQuery;
      }
    }
    URL thisURL = getURL(sym);
    String latestInfor = getStr(thisURL);
    symToInfor.put(sym, latestInfor);
    return latestInfor;
  }
}
