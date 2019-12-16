package iplAnalyzer;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class IPLAnalyzer
{
   Map<SortingEnums, Comparator<PlayerDAO>> enumMap = null;
   SortingEnums sortingBasedOn;

   public enum PlayerEnum
   {
      BATSMAN, BOWLER
   }

   PlayerEnum player;

   public IPLAnalyzer()
   {
      this.enumMap = new HashMap<>();
      this.enumMap.put(sortingBasedOn.BATTING_AVERAGE, Comparator.comparing(ipl -> ipl.avg, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.STRIKE_RATE, Comparator.comparing(ipl -> ipl.strikeRate, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.SORT_ON_4s_AND_6s, new ComparatorFor4sAnd6s().reversed());
      this.enumMap.put(sortingBasedOn.BEST_STRIKE_RATE_WITH_6s_4s, new ComparatorFor4sAnd6s().reversed().thenComparing(compare -> compare.strikeRate));
      Comparator<PlayerDAO> comparingAverageForStrikeRate = Comparator.comparing(ipl -> ipl.avg, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_AVERAGE, comparingAverageForStrikeRate.thenComparing(compare -> compare.strikeRate, Comparator.reverseOrder()));
      Comparator<PlayerDAO> comparingRunsForAverage = Comparator.comparing(ipl -> ipl.run, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_RUNS, comparingRunsForAverage.thenComparing(compare -> compare.avg, Comparator.reverseOrder()));
   }

   public Map<String, PlayerDAO> getSortedData(PlayerEnum player, String iplFilePath) throws IPLException
   {
      this.player = player;
      IPLAdapter obj = PlayerObjectFactory.getPlayer(player);
      Map<String, PlayerDAO> map = obj.loadData(iplFilePath);
      return map;
   }


   public String sortData(SortingEnums field, Map<String, PlayerDAO> dataMap)
   {
      ArrayList list = dataMap.values()
            .stream()
            .sorted(this.enumMap.get(field))
            .collect(Collectors.toCollection(ArrayList::new));
      String sortedData = new Gson().toJson(list);
      return sortedData;
   }


}
