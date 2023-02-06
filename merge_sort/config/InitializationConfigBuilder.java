package merge_sort.config;

import merge_sort.enumTypes.DataType;
import merge_sort.enumTypes.SortOrder;

import java.io.File;
import java.util.regex.Pattern;

public class InitializationConfigBuilder extends Config
{
    InitializationConfigBuilder(ExceptionHandler eh)
    {
        exceptionHandler = eh;
    }

    public InitializationConfig build()
    {
        return new InitializationConfig(this);
    }

    public void setArg(String arg)
    {
        if (arg.charAt(0) == '-')
        {
            setFlagArg(arg.substring(1));
        }
        else
        {
            setFileNameArg(arg);
        }
    }

    private void setFlagArg(String arg) throws IllegalArgumentException
    {
        for (int c = 0; c < arg.length(); c++)
        {
            switch (Character.toLowerCase(arg.charAt(c)))
            {
                case 'a' -> sortOrder = SortOrder.ASCENDING;
                case 'd' -> sortOrder = SortOrder.DESCENDING;
                case 'i' -> dataType = DataType.INTEGER;
                case 's' -> dataType = DataType.STRING;
                case '-' ->
                {

                }
                default ->
                {
                    if (!isIgnoreErrors())
                    {
                        throw new IllegalArgumentException("Illegal flag: \"" + arg + "\"");
                    }
                }
            }
        }
    }

    private void setFileNameArg(String arg) throws IllegalArgumentException
    {
        String filenameRegex;
        if (isIgnoreErrors())
        {
            filenameRegex = "^[\\w\\-. ]+$";
        }
        else
        {
            filenameRegex = "^\\w+.\\w+$";
        }

        if (!Pattern.matches(filenameRegex, arg))
        {
            throw new IllegalArgumentException("Illegal filename argument: \"" + arg + "\"");
        }

        if (outputFileName == null)
        {
            if (new File(arg).exists())
            {
                throw new IllegalArgumentException("File \"" + arg + "\" already exists. Overwriting is not allowed.");
            }
            outputFileName = arg;
        }
        else
        {
            if (new File(arg).exists())
            {
                inputFileNames.add(arg);
            }
        }
    }

}
