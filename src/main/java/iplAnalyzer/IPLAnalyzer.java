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
      BATSMAN, ALL_ROUNDER, BOWLER
   }

   PlayerEnum player;

   public IPLAnalyzer()
   {
      this.enumMap = new HashMap<>();
      sort();
   }

   public void sort()
   {
      this.enumMap.put(sortingBasedOn.BATTING_AVERAGE, Comparator.comparing(ipl -> ipl.battingAvg, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.BATTING_STRIKE_RATE, Comparator.comparing(ipl -> ipl.battingStrikeRate, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.SORT_ON_4s_AND_6s, new ComparatorFor4sAnd6sRuns().reversed());
      this.enumMap.put(sortingBasedOn.BEST_STRIKE_RATE_WITH_6s_4s, new ComparatorFor4sAnd6sRuns().reversed().thenComparing(compare -> compare.battingStrikeRate));
      Comparator<PlayerDao> comparingAverageForStrikeRate = Comparator.comparing(ipl -> ipl.battingAvg, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.BATTING_STRIKE_RATE_AND_AVERAGE, comparingAverageForStrikeRate.thenComparing(compare -> compare.battingAvg, Comparator.reverseOrder()));
      Comparator<PlayerDao> comparingRunsForAverage = Comparator.comparing(ipl -> ipl.run, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_RUNS, comparingRunsForAverage.thenComparing(compare -> compare.battingStrikeRate, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.BOWLING_AVERAGE, Comparator.comparing(ipl -> ipl.bowlingAvg));
      this.enumMap.put(sortingBasedOn.BOWLING_STRIKE_RATE, Comparator.comparing(ipl -> ipl.bowlingStrikeRate));
      this.enumMap.put(sortingBasedOn.BOWLING_ECONOMY, Comparator.comparing(ipl -> ipl.economy));
      Comparator<PlayerDao> comparingStrikeRateForWickets = new ComparatorFor4And5Wickets().reversed();
      this.enumMap.put(sortingBasedOn.SORT_ON_4w_AND_5w, comparingStrikeRateForWickets.thenComparing(ipl -> ipl.bowlingStrikeRate));
      Comparator<PlayerDao> comparingBowlerAverageForStrikeRate = Comparator.comparing(ipl -> ipl.battingStrikeRate);
      this.enumMap.put(sortingBasedOn.BOWLING_STRIKE_RATE_AND_AVERAGE, comparingBowlerAverageForStrikeRate.thenComparing(ipl -> ipl.bowlingAvg));
      Comparator<PlayerDao> comparingBowlerMaxWicketsForAverage = new ComparatorFor4And5Wickets();
      this.enumMap.put(sortingBasedOn.WICKETS_AND_AVERAGE, comparingBowlerMaxWicketsForAverage.thenComparing(ipl -> ipl.bowlingAvg));
      this.enumMap.put(sortingBasedOn.ALL_ROUNDER_IN_AVERAGE,new ComparatorForAllRounderInAverage().reversed());
      this.enumMap.put(sortingBasedOn.ALL_ROUNDER,new ComparatorForAllRounder().reversed());

   }

   public Map<String, PlayerDao> getSortedData(PlayerEnum playerEnum, String...iplFilePath) throws IPLException
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
