package iplAnalyzer;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class IPLAnalyzer
{
   private IPLAdapter mockedAdapter;
   SortedDataContainer mockedContainer;

   public void setMockObj(IPLAdapter mockAdapter, SortedDataContainer container)
   {
      this.mockedAdapter = mockAdapter;
      this.mockedContainer = container;
   }

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
     // mockedAdapter = PlayerObjectFactory.getPlayer(playerEnum);
      Map<String, PlayerDao> map = null;
      try
      {
         map = mockedAdapter.loadData(iplFilePath);
      }
      catch (IPLException e)
      {
         throw new IPLException("Cannot Load Data",IPLException.ExceptionType.CANNOT_READ_FILE);
      }
      return map;
   }


   public String sortData(SortingEnums field, Map<String, PlayerDao> dataMap)
   {
      ArrayList list = dataMap.values()
            .stream()
            .sorted(mockedContainer.getData(field))
            .collect(Collectors.toCollection(ArrayList::new));
      String sortedData = new Gson().toJson(list);
      return sortedData;
   }
}
