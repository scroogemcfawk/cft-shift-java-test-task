package merge_sort.config;

public class InitializationConfig extends Config
{
    protected InitializationConfig(InitializationConfigBuilder cb)
    {
        exceptionHandler = cb.exceptionHandler;
        sortOrder = cb.sortOrder;
        dataType = cb.dataType;
        outputFileName = cb.outputFileName;
        inputFileNames = cb.inputFileNames;
    }

    public static InitializationConfig getConfig(String[] args, ExceptionHandler eh)
    {
        InitializationConfigBuilder cb = new InitializationConfigBuilder(eh);
        for (var arg: args)
        {
            cb.setArg(arg);
        }
        return cb.build();
    }

    public boolean isReady()
    {
        return (inputFileNames.size() > 0);
    }

}
