package iplAnalyzer;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class IPLAnalyzer
{
   Map<SortingEnums, Comparator<PlayerDao>> enumMap = null;
   SortingEnums sortingBasedOn;

   public enum PlayerEnum
   {
      BATSMAN, BOWLER
   }

   PlayerEnum player;

   public IPLAnalyzer()
   {
      this.enumMap = new HashMap<>();
      sort();

   }

   public void sort()
   {
      this.enumMap.put(sortingBasedOn.BATTING_AVERAGE, Comparator.comparing(ipl -> ipl.avg, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.BATTING_STRIKE_RATE, Comparator.comparing(ipl -> ipl.strikeRate, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.SORT_ON_4s_AND_6s, new ComparatorFor4sAnd6sRuns().reversed());
      this.enumMap.put(sortingBasedOn.BEST_STRIKE_RATE_WITH_6s_4s, new ComparatorFor4sAnd6sRuns().reversed().thenComparing(compare -> compare.strikeRate));
      Comparator<PlayerDao> comparingAverageForStrikeRate = Comparator.comparing(ipl -> ipl.avg, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_AVERAGE, comparingAverageForStrikeRate.thenComparing(compare -> compare.strikeRate, Comparator.reverseOrder()));
      Comparator<PlayerDao> comparingRunsForAverage = Comparator.comparing(ipl -> ipl.run, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_RUNS, comparingRunsForAverage.thenComparing(compare -> compare.avg, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.BOWLING_AVERAGE, Comparator.comparing(ipl -> ipl.avg));
      this.enumMap.put(sortingBasedOn.BOWLING_STRIKE_RATE, Comparator.comparing(ipl -> ipl.strikeRate));
      this.enumMap.put(sortingBasedOn.BOWLING_ECONOMY, Comparator.comparing(ipl -> ipl.economy));
      Comparator<PlayerDao> comparingStrikeRateForWickets = new ComparatorFor4And5Wickets().reversed();
      this.enumMap.put(sortingBasedOn.SORT_ON_4w_AND_5w, comparingStrikeRateForWickets.thenComparing(Comparator.comparing(ipl -> ipl.strikeRate)));
      Comparator<PlayerDao> comparingBowlerAverageForStrikeRate = Comparator.comparing(ipl -> ipl.avg);
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_AVERAGE, comparingBowlerAverageForStrikeRate.thenComparing(Comparator.comparing(ipl -> ipl.strikeRate)));

   }

   public Map<String, PlayerDao> getSortedData(PlayerEnum playerEnum, String iplFilePath) throws IPLException
   {
      this.player = playerEnum;
      IPLAdapter player = PlayerObjectFactory.getPlayer(playerEnum);
      Map<String, PlayerDao> map = player.loadData(iplFilePath);
      return map;
   }

   public String sortData(SortingEnums field, Map<String, PlayerDao> dataMap)
   {
      ArrayList list = dataMap.values()
            .stream()
            .sorted(this.enumMap.get(field))
            .collect(Collectors.toCollection(ArrayList::new));
      String sortedData = new Gson().toJson(list);
      return sortedData;
   }
}
