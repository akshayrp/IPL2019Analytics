package iplAnalyzer;

import CSVBuilder.CSVBuilderException;
import CSVBuilder.CSVBuilderFactory;
import CSVBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class IPLAnalyzer
{
   public int loadData(String iplFilePath) throws IPLException
   {
      int playersCount=0;
      try( Reader reader = Files.newBufferedReader(Paths.get(iplFilePath)))
      {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator csvFileIterator = csvBuilder.getCSVFileIterator(reader,RunsCsvBinder.class);
         while(csvFileIterator.hasNext())
         {
            playersCount++;
            csvFileIterator.next();
         }
      }
      catch (IOException e)
      {
         throw new IPLException("Error in File Reading",IPLException.ExceptionType.CANNOT_READ_FILE);
      }
      catch (CSVBuilderException e)
      {
         e.printStackTrace();
      }
      return playersCount;
   }
}
