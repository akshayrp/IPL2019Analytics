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

   public static final String PREPARED_FILE_PATH
         = "/home/admin1/IdeaProjects/IPL2019Analytics/src/test/resources/preparedRunsFile.csv";

   public IPLAdapter()
   {}
   public abstract Map<String, PlayerDao> loadData(String...iplFilePath) throws IPLException;

   public <E> Map<String, PlayerDao> loadData(Class<E> binderClass, String iplFilePath) throws IPLException
   {
      Map<String, PlayerDao> runsMap = new HashMap<>();
      prepareFile(iplFilePath, PREPARED_FILE_PATH);
      try (Reader reader = Files.newBufferedReader(Paths.get(PREPARED_FILE_PATH)))
      {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, binderClass);
         Iterable<E> csvIterable = () -> csvFileIterator;

         if (binderClass.getName().equals("iplAnalyzer.BatsManCsvBinder"))
         {
            StreamSupport.stream
                  (csvIterable.spliterator(), false)
                  .map(BatsManCsvBinder.class::cast)
                  .forEach(batsMan -> runsMap.put(batsMan.player, new PlayerDao(batsMan)));
         }
         else if (binderClass.getName().equals("iplAnalyzer.BowlerCsvBinder"))
         {
            StreamSupport.stream
                  (csvIterable.spliterator(), false)
                  .map(BowlerCsvBinder.class::cast)
                  .forEach(bowler -> runsMap.put(bowler.player, new PlayerDao(bowler)));
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
