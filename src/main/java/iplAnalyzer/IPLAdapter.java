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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class IPLAdapter
{

   public static final String PREPARED_RUNS_FILE_PATH
         = "./src/test/resources/preparedRunsFile.csv";

   Map<String, PlayerDAO> runsMap;

   public IPLAdapter()
   {
      this.runsMap = new HashMap<>();
   }

   public abstract Map<String, PlayerDAO> loadData(String iplFilePath) throws IPLException;

   public <E> Map<String, PlayerDAO> loadData(Class<E> binderClass, String iplFilePath) throws IPLException
   {
      prepareFile(iplFilePath, PREPARED_RUNS_FILE_PATH);
      try (Reader reader = Files.newBufferedReader(Paths.get(PREPARED_RUNS_FILE_PATH)))
      {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<RunsCsvBinder> csvFileIterator = csvBuilder.getCSVFileIterator(reader, binderClass);
         Iterable<RunsCsvBinder> csvIterable = () -> csvFileIterator;

         if (binderClass.getName().equals("iplAnalyzer.RunsCsvBinder"))
         {
            StreamSupport.stream
                  (csvIterable.spliterator(), false)
                  .map(RunsCsvBinder.class::cast)
                  .forEach(ipl -> runsMap.put(ipl.player, new PlayerDAO(ipl)));
         }
         else if (binderClass.getName().equals("iplAnalyzer.BowlerCsvBinder"))
         {
            StreamSupport.stream
                  (csvIterable.spliterator(), false)
                  .map(BowlerCsvBinder.class::cast)
                  .forEach(ipl -> runsMap.put(ipl.player, new PlayerDAO(ipl)));
         }
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
