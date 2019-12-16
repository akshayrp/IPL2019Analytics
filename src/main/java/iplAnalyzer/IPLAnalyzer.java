package iplAnalyzer;

import CSVBuilder.CSVBuilderException;
import CSVBuilder.CSVBuilderFactory;
import CSVBuilder.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPLAnalyzer
{
   Map<String, RunDAO> runsMap;
   Map<DataFields, Comparator<RunDAO>> enumMap = null;

   DataFields dataFields;

   public IPLAnalyzer()
   {
      this.runsMap = new HashMap<>();
      this.enumMap = new HashMap<>();
      this.enumMap.put(dataFields.BATTING_AVERAGE, Comparator.comparing(census -> census.avg, Comparator.reverseOrder()));
      this.enumMap.put(dataFields.STRIKE_RATE, Comparator.comparing(census -> census.strikeRate, Comparator.reverseOrder()));

   }

   public Map<String, RunDAO> loadData(String iplFilePath) throws IPLException
   {
      try (Reader reader = Files.newBufferedReader(Paths.get(iplFilePath)))
      {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<RunsCsvBinder> csvFileIterator = csvBuilder.getCSVFileIterator(reader, RunsCsvBinder.class);
         Iterable<RunsCsvBinder> csvIterable = () -> csvFileIterator;

         StreamSupport.stream(csvIterable.spliterator(), false)
               .map(RunsCsvBinder.class::cast).forEach(CsvData -> runsMap
               .put(CsvData.player, new RunDAO(CsvData)));
      }
      catch (IOException e)
      {
         throw new IPLException("Error in File Reading", IPLException.ExceptionType.CANNOT_READ_FILE);
      }
      catch (CSVBuilderException e)
      {
         throw new IPLException("Unable to Build Bean", IPLException.ExceptionType.UNABLE_TO_PARSE);
      }
      return runsMap;
   }

   public String sortData(DataFields field, Map<String, RunDAO> dataMap)
   {
      ArrayList list = dataMap.values()
            .stream()
            .sorted(this.enumMap.get(field))
            .collect(Collectors.toCollection(ArrayList::new));
      String sortedData = new Gson().toJson(list);
      return sortedData;
   }

   public String sortDataOn4sAnd6s(Map<String, RunDAO> dataMap)
   {
      ArrayList list = dataMap.values().stream().collect(Collectors.toCollection(ArrayList::new));
      Collections.sort(list, (Comparator<RunDAO>) (o1, o2) ->
            Integer.valueOf(o1.sixs).compareTo(Integer.valueOf(o2.fours)));
      Collections.reverse(list);
      String sortedData = new Gson().toJson(list);
      return sortedData;
   }


   public void prepareFile(String originalFilePath, String preparedFilePath) throws IPLException
   {
      try (Stream<String> lines = Files.lines(Paths.get(originalFilePath)))
      {
         List<String> replaced = lines
               .map(line -> line.replaceAll("-", "0"))
               .collect(Collectors.toList());
         Files.write(Paths.get(preparedFilePath), replaced);
      }
      catch (IOException e)
      {
         throw new IPLException("Error in File Reading", IPLException.ExceptionType.CANNOT_READ_FILE);
      }
   }
}
