package merge_sort.config;

public class ExceptionHandler
{
    private boolean ignoreErrors;

    public ExceptionHandler(boolean ie)
    {
        ignoreErrors = ie;
    }

    public boolean isIgnoreErrors()
    {
        return ignoreErrors;
    }

    public void exec(Exception e) throws Exception
    {
        if (ignoreErrors)
        {
            throw new Exception(e);
        }
    }
}
