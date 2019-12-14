package iplAnalyzer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IPLAnalyzer
{
   public Reader loadData(String iplFilePath) throws IPLException
   {
      Reader reader = null;
      try
      {
         reader = Files.newBufferedReader(Paths.get(iplFilePath));
      }
      catch (IOException e)
      {
         throw new IPLException("Error in File Reading",IPLException.ExceptionType.CANNOT_READ_FILE);
      }
      return reader;
   }
}
