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
   Map<SortingEnums, Comparator<RunDAO>> enumMap = null;

   SortingEnums sortingBasedOn;

   public static final String PREPARED_RUNS_FILE_PATH
         = "./src/test/resources/preparedRunsFile.csv";

   public IPLAnalyzer()
   {
      this.runsMap = new HashMap<>();
      this.enumMap = new HashMap<>();
      this.enumMap.put(sortingBasedOn.BATTING_AVERAGE, Comparator.comparing(ipl -> ipl.avg, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.STRIKE_RATE, Comparator.comparing(ipl -> ipl.strikeRate, Comparator.reverseOrder()));
      this.enumMap.put(sortingBasedOn.SORT_ON_4s_AND_6s, new ComparatorFor4sAnd6s().reversed());
      this.enumMap.put(sortingBasedOn.BEST_STRIKE_RATE_WITH_6s_4s, new ComparatorFor4sAnd6s().reversed().thenComparing(compare -> compare.strikeRate));
      Comparator<RunDAO> comparingAverageForStrikeRate = Comparator.comparing(ipl -> ipl.avg, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_AVERAGE, comparingAverageForStrikeRate.thenComparing(compare -> compare.strikeRate, Comparator.reverseOrder()));
      Comparator<RunDAO> comparingRunsForAverage = Comparator.comparing(ipl -> ipl.run, Comparator.reverseOrder());
      this.enumMap.put(sortingBasedOn.STRIKE_RATE_AND_RUNS, comparingRunsForAverage.thenComparing(compare -> compare.avg, Comparator.reverseOrder()));
   }

   public Map<String, RunDAO> loadData(String iplFilePath) throws IPLException
   {
      prepareFile(iplFilePath, PREPARED_RUNS_FILE_PATH);
      try (Reader reader = Files.newBufferedReader(Paths.get(PREPARED_RUNS_FILE_PATH)))
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

   public String sortData(SortingEnums field, Map<String, RunDAO> dataMap)
   {
      ArrayList list = dataMap.values()
            .stream()
            .sorted(this.enumMap.get(field))
            .collect(Collectors.toCollection(ArrayList::new));
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
