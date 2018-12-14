package portfolio.model;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the JsonBuilderReader interface.
 * Extension Change: Add PortfolioBuilder class, @12.06.
 */
public class PortfolioBuilder implements JsonBuilderReader {

  @Override
  public void saveStrategy(Map<String, Double> ratioMap,
                           Date startDate, Date endDate, int interval, double amount,
                           double comm) {
    String fileName = "Strategy-" + startDate.toString() + "-" + endDate.toString() + "-"
            + interval + ".JSON";
    JSONObject json = new JSONObject();
    json.put("startDate", startDate.toString());
    json.put("endDate", endDate.toString());
    json.put("interval", interval);
    json.put("amount", amount);
    json.put("comm", comm);

    JSONArray arr = new JSONArray();
    for (String sym : ratioMap.keySet()) {
      String item = sym + ":" + ratioMap.get(sym);
      arr.put(item);
    }
    json.put("ratioMap", arr);
    try (FileWriter file = new FileWriter(fileName)) {
      file.write(json.toString());
    } catch (IOException e) {
      throw new IllegalArgumentException("Incorrect input");
    }
  }

  @Override
  public void saveStrategy(Map<String, Double> ratioMap, Date startDate,
                           int interval, double amount, double comm) {
    String fileName = startDate.toString() + "-" + interval + ".JSON";
    JSONObject json = new JSONObject();
    json.put("startDate", startDate.toString());
    json.put("interval", interval);
    json.put("amount", amount);
    json.put("comm", comm);

    JSONArray arr = new JSONArray();
    for (String sym : ratioMap.keySet()) {
      String item = sym + ":" + ratioMap.get(sym);
      arr.put(item);
    }
    json.put("ratioMap", arr);
    try (FileWriter file = new FileWriter(fileName)) {
      file.write(json.toString());
    } catch (IOException e) {
      throw new IllegalArgumentException("Incorrect input");
    }
  }

  @Override
  public void applyStrategyFromJosn(StockModel model, String portfolioName, String path)
          throws IOException, JSONException {
    JSONObject json = readJsonFromUrl(path);

    int interval = json.getInt("interval");
    double amount = json.getDouble("amount");
    double comm = json.getDouble("comm");
    Date startDate = Date.valueOf(json.getString("startDate"));
    Map<String, Double> ratioMap = new HashMap<>();
    JSONArray arr = json.getJSONArray("ratioMap");
    int len = arr.length();
    for (int i = 0; i < len; i++) {
      String str = arr.getString(i);
      String[] inputs = str.split(":");
      ratioMap.put(inputs[0], Double.parseDouble(inputs[1]));
    }
    //System.out.println("Portfolio: " + portfolioName + " amount: "
           // + amount + " date: " + startDate.toString() + " comm: " + comm);
    if (json.has("endDate")) {
      Date endDate = Date.valueOf(json.getString("endDate"));
      model.highLevelFixedValueTrade(portfolioName, ratioMap, startDate, endDate, interval, amount,
              comm, false);
    } else {
      model.highLevelFixedValueTrade(portfolioName, ratioMap, startDate, interval, amount,
              comm, false);
    }
  }

  @Override
  public void save(String portfolioName, Portfolio pf)
          throws IllegalArgumentException {
    JSONObject json = new JSONObject();
    json.put("syms", pf.getStockNames());
    json.put("stocks", pf.examinePortfolio());

    String filename = portfolioName + ".JSON";
    try (FileWriter file = new FileWriter(filename)) {
      file.write(json.toString());
    } catch (IOException e) {
      throw new IllegalArgumentException("Incorrect input");
    }
  }

  @Override
  public Portfolio build(String path) throws IOException, JSONException {
    JSONObject json = readJsonFromUrl(path);

    String syms = json.getString("syms");
    String stocks = json.getString("stocks");

    Set<String> symbolTickers = new HashSet<>();
    if (!syms.equals("")) {
      String[] symsArr = syms.split(" ");
      for (String sym : symsArr) {
        symbolTickers.add(sym);
      }
    }
    Portfolio pf = new PortfolioImp(symbolTickers);

    String[] stockOwnershipItems = stocks.split("\n");

    //buy individual identical stock into the portfolio again
    for (String stockOwner : stockOwnershipItems) {
      if (stockOwner.length() < 5) {
        continue;
      }
      String[] inputs = stockOwner.split(",");
      if (inputs.length != 6) {
        continue;
      }
      //String stocksym = inputs[0].substring(inputs[0].length() - 4);
      String stocksym = inputs[0].split(":")[2].trim();
      double share = Double.parseDouble(inputs[1].split(": ")[1]);
      double comm = Double.parseDouble(inputs[4].split(": ")[1]);

      String[] dateInput = inputs[5].split(": ")[1].split("/");
      String dateStr = "20" + dateInput[2] + "-" + dateInput[0] + "-" + dateInput[1];
      Date buydate = Date.valueOf(dateStr);

      pf.buyStockByShare(stocksym, buydate, share, comm);
    }

    return pf;
  }

  private static JSONObject readJsonFromUrl(String path) throws IOException, JSONException {
    File file = new File(path);
    String content = FileUtils.readFileToString(file, "utf-8");
    JSONObject json = new JSONObject(content);
    return json;
  }
}
