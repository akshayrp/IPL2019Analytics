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

public class IPLAnalyzer
{
   Map<String,RunsCsvBinder> runsMap = new HashMap<>();
   public int loadData(String iplFilePath) throws IPLException
   {
      int playersCount = 0;
      try (Reader reader = Files.newBufferedReader(Paths.get(iplFilePath)))
      {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator csvFileIterator = csvBuilder.getCSVFileIterator(reader, RunsCsvBinder.class);
         Iterable csvIterable = () -> csvFileIterator;
         playersCount = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
      }
      catch (IOException e)
      {
         throw new IPLException("Error in File Reading", IPLException.ExceptionType.CANNOT_READ_FILE);
      }
      catch (CSVBuilderException e)
      {
         throw new IPLException("Unable to Build Bean", IPLException.ExceptionType.UNABLE_TO_PARSE);
      }
      return playersCount;
   }
}
