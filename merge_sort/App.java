package merge_sort;

import merge_sort.config.ExceptionHandler;
import merge_sort.config.InitializationConfig;
import merge_sort.file_processing.Handler;

public class App
{
    // true - drop incorrect data and try to continue or try to skip exception, false - end the program immediately
    static final boolean IGNORE_ERRORS = true;

    public static void main(String[] args)
    {
        try
        {
            InitializationConfig config = InitializationConfig.getConfig(args, new ExceptionHandler(IGNORE_ERRORS));
            if (!config.isReady())
            {
                throw new IllegalArgumentException("Unable to run the program. Invalid argument list.");
            }
            new Handler(config).mergeSort();
            System.out.println("Success! See result in: \"" + config.getOutputFileName() + "\"");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
