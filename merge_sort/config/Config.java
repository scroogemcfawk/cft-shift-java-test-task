package merge_sort.config;

import merge_sort.enumTypes.DataType;
import merge_sort.enumTypes.SortOrder;

import java.util.ArrayList;

public abstract class Config
{
    protected ExceptionHandler exceptionHandler;
    protected SortOrder sortOrder = SortOrder.ASCENDING;
    protected DataType dataType = DataType.STRING;
    protected ArrayList<String> inputFileNames = new ArrayList<>();
    protected String outputFileName;


    public boolean isIgnoreErrors()
    {
        return exceptionHandler.isIgnoreErrors();
    }

    public SortOrder getSortOrder()
    {
        return sortOrder;
    }

    public DataType getDataType()
    {
        return dataType;
    }

    public ArrayList<String> getInputFileNames()
    {
        return inputFileNames;
    }

    public ExceptionHandler getExceptionHandler()
    {
        return exceptionHandler;
    }

    public String getOutputFileName()
    {
        return outputFileName;
    }


}
