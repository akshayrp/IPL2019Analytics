package iplAnalyzer;

import CSVBuilder.CSVBuilderException;
import CSVBuilder.CSVBuilderFactory;
import CSVBuilder.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class AllRounderAdapter extends IPLAdapter
{

   Map<String, PlayerDao> map1;

   public AllRounderAdapter()
   {
      this.map1 = new HashMap<>();
   }

   @Override
   public Map<String, PlayerDao> loadData(String... iplFilePath) throws IPLException
   {
      map1 = super.loadData(BatsManCsvBinder.class, iplFilePath[0]);
      this.loadBowlerData(iplFilePath[1]);
      return map1;
   }

   private void loadBowlerData(String iplFilePath) throws IPLException
   {
      prepareFile(iplFilePath, PREPARED_FILE_PATH);
      try (Reader reader = Files.newBufferedReader(Paths.get(PREPARED_FILE_PATH)))
      {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<BowlerCsvBinder> csvFileIterator = csvBuilder.getCSVFileIterator(reader, BowlerCsvBinder.class);
         Iterable<BowlerCsvBinder> csvIterable = () -> csvFileIterator;
         StreamSupport.stream
               (csvIterable.spliterator(), false)
               .map(BowlerCsvBinder.class::cast).filter(csvPlayer -> map1.get(csvPlayer.player) != null)
               .forEach(mergedData -> {
                  map1.get(mergedData.player).bowlingAvg = mergedData.avg;
                        map1.get(mergedData.player).wickets = mergedData.wickets;
               });
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (CSVBuilderException e)
      {
         e.printStackTrace();
      }
   }
}
