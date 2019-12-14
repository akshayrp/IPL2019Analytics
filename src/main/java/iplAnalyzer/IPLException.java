package iplAnalyzer;

import java.io.IOException;

public class IPLException extends Throwable
{
   enum ExceptionType
   {
      CANNOT_READ_FILE, UNABLE_TO_PARSE
   }

   ExceptionType type;

   public IPLException(String message, ExceptionType type)
   {
      super(message);
      this.type = type;
   }
}
