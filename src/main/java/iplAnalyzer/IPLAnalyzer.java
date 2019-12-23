package iplAnalyzer;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class IPLAnalyzer
{
   public enum PlayerEnum
   {
      BATSMAN, ALL_ROUNDER, BOWLER
   }

   PlayerEnum player;
   SortedDataContainer sortContainer;

   public IPLAnalyzer()
   {
      sortContainer = new SortedDataContainer();
      sortContainer.sort();
   }

   public Map<String, PlayerDao> getSortedData(PlayerEnum playerEnum, String... iplFilePath) throws IPLException
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
            .sorted(SortedDataContainer.enumMap.get(field))
            .collect(Collectors.toCollection(ArrayList::new));
      String sortedData = new Gson().toJson(list);
      return sortedData;
   }
}
