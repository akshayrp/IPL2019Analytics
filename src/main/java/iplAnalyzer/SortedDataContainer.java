package iplAnalyzer;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SortedDataContainer
{
   static Map<SortingEnums, Comparator<PlayerDao>> enumMap;

   public SortedDataContainer()
   {
      this.enumMap = new HashMap<>();
   }

   public Comparator<PlayerDao> getData(SortingEnums field)
   {
      return enumMap.get(field);
   }

   public void sort()
   {
      enumMap.put(SortingEnums.BATTING_AVERAGE,
            Comparator.comparing(ipl -> ipl.battingAvg, Comparator.reverseOrder()));
      enumMap.put(SortingEnums.BATTING_STRIKE_RATE,
            Comparator.comparing(ipl -> ipl.battingStrikeRate, Comparator.reverseOrder()));
      enumMap.put(SortingEnums.SORT_ON_4s_AND_6s,
            new ComparatorFor4sAnd6sRuns().reversed());
      enumMap.put(SortingEnums.BEST_STRIKE_RATE_WITH_6s_4s,
            new ComparatorFor4sAnd6sRuns().reversed().thenComparing(compare -> compare.battingStrikeRate));
      Comparator<PlayerDao> comparingAverageForStrikeRate
            = Comparator.comparing(ipl -> ipl.battingAvg, Comparator.reverseOrder());
      enumMap.put(SortingEnums.BATTING_STRIKE_RATE_AND_AVERAGE,
            comparingAverageForStrikeRate.thenComparing(compare -> compare.battingAvg, Comparator.reverseOrder()));
      Comparator<PlayerDao> comparingRunsForAverage
            = Comparator.comparing(ipl -> ipl.run, Comparator.reverseOrder());
      enumMap.put(SortingEnums.STRIKE_RATE_AND_RUNS,
            comparingRunsForAverage.thenComparing(compare -> compare.battingStrikeRate, Comparator.reverseOrder()));
      enumMap.put(SortingEnums.BOWLING_AVERAGE,
            Comparator.comparing(ipl -> ipl.bowlingAvg));
      enumMap.put(SortingEnums.BOWLING_STRIKE_RATE,
            Comparator.comparing(ipl -> ipl.bowlingStrikeRate));
      enumMap.put(SortingEnums.BOWLING_ECONOMY,
            Comparator.comparing(ipl -> ipl.economy));
      Comparator<PlayerDao> comparingStrikeRateForWickets
            = new ComparatorFor4And5Wickets().reversed();
      enumMap.put(SortingEnums.SORT_ON_4w_AND_5w,
            comparingStrikeRateForWickets.thenComparing(ipl -> ipl.bowlingStrikeRate));
      Comparator<PlayerDao> comparingBowlerAverageForStrikeRate
            = Comparator.comparing(ipl -> ipl.battingStrikeRate);
      enumMap.put(SortingEnums.BOWLING_STRIKE_RATE_AND_AVERAGE,
            comparingBowlerAverageForStrikeRate.thenComparing(ipl -> ipl.bowlingAvg));
      Comparator<PlayerDao> comparingBowlerMaxWicketsForAverage
            = new ComparatorFor4And5Wickets();
      enumMap.put(SortingEnums.WICKETS_AND_AVERAGE,
            comparingBowlerMaxWicketsForAverage.thenComparing(ipl -> ipl.bowlingAvg));
      enumMap.put(SortingEnums.ALL_ROUNDER_IN_AVERAGE,
            new ComparatorForAllRounderInAverage().reversed());
      enumMap.put(SortingEnums.ALL_ROUNDER,
            new ComparatorForAllRounder().reversed());

   }
}
