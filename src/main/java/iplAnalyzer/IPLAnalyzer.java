package iplAnalyzer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IPLAnalyzer
{
   public Reader loadData(String iplFilePath) throws IOException
   {
      Reader reader = Files.newBufferedReader(Paths.get(iplFilePath));
      return reader;
   }
}
