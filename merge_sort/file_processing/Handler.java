package merge_sort.file_processing;

import merge_sort.config.RuntimeHelper;
import merge_sort.sort.Sort;
import merge_sort.config.Config;
import merge_sort.enumTypes.DataType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Handler
{
    private final Config config;

    private final ArrayList<String> validFileNames = new ArrayList<>();
    private int BUFFER_SIZE_LIMIT;
    private String[] strBuffer;
    private Integer[] intBuffer;
    int countOfBuffers = 0;
    private int countOfLinesInBuffer = 0;

    private void initBuffer()
    {
        if (config.getDataType() == DataType.INTEGER)
        {
            BUFFER_SIZE_LIMIT = (int) (RuntimeHelper.freeMemory() / 16 / 3);
            intBuffer = new Integer[BUFFER_SIZE_LIMIT];
        }
        else
        {
            BUFFER_SIZE_LIMIT = (int) (RuntimeHelper.freeMemory() / 512 / 3);
            strBuffer = new String[BUFFER_SIZE_LIMIT];
        }
        System.out.println("Buffer size: " + BUFFER_SIZE_LIMIT + " bytes.");
    }

    public Handler(Config c)
    {
        config = c;
        initBuffer();
    }

    public void mergeSort() throws Exception
    {
        try
        {
            loadValidateSplit();
            new MergeHandler(config, validFileNames, BUFFER_SIZE_LIMIT).merge();
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        finally
        {
            removeTemporaryFiles();
        }
    }

    private void freeBuffer()
    {
        // freeing memory?
        intBuffer = null;
        strBuffer = null;
    }

    private void loadValidateSplit() throws Exception
    {
        Sort sort = new Sort(config);
        // rewrite all input files into new valid files
        for (String i: config.getInputFileNames())
        {
            // TODO: check if file is already sorted before loading
            try (FileReader fr = new FileReader(i); BufferedReader in = new BufferedReader(fr))
            {
                String line;
                while ((line = in.readLine()) != null) {
                    if (config.getDataType() == DataType.INTEGER)
                    {
                        // invalid or out of boundary values are discarded
                        intBuffer[countOfLinesInBuffer] = Integer.parseInt(line);
                    }
                    else
                    {
                        if (line.isBlank() || line.contains(" ") || line.length() > 255)
                        {
                            throw new IllegalArgumentException();
                        }
                        strBuffer[countOfLinesInBuffer] = line;
                    }
                    countOfLinesInBuffer++;
                    if (countOfLinesInBuffer >= BUFFER_SIZE_LIMIT)
                    {
                        if (config.getDataType() == DataType.INTEGER)
                        {
                            sort.sortInt(intBuffer, 0, countOfLinesInBuffer - 1);
                        }
                        else
                        {
                            sort.sortStr(strBuffer, 0, countOfLinesInBuffer - 1);
                        }
                        writeResetBuffer();
                    }
                }
            }
            catch (Exception e)
            {
                config.getExceptionHandler().exec(new IOException("Validation error."));
            }
        }
        if (countOfBuffers > 1) {
            if (config.getDataType() == DataType.INTEGER)
            {
                sort.sortInt(intBuffer, 0, countOfLinesInBuffer - 1);
            }
            else
            {
                sort.sortStr(strBuffer, 0, countOfLinesInBuffer - 1);
            }
        }
        writeResetBuffer();
        freeBuffer();
    }

    private void writeResetBuffer() throws Exception
    {

        String outputFilename = ".temp" + System.currentTimeMillis() + "" + countOfBuffers++;
        try (FileWriter fr = new FileWriter(outputFilename); BufferedWriter out = new BufferedWriter(fr, BUFFER_SIZE_LIMIT))
        {
            for (int i = 0; i < countOfLinesInBuffer; i++)
            {
                if (config.getDataType() == DataType.INTEGER)
                {
                    out.write(intBuffer[i] + "");
                }
                else
                {
                    out.write(strBuffer[i]);
                }
                out.newLine();
            }
            validFileNames.add(outputFilename);
        }
        catch (FileNotFoundException e)
        {
            config.getExceptionHandler().exec(new NumberFormatException("Could not write temporary file."));
        }
        countOfLinesInBuffer = 0;
    }

    private void removeTemporaryFiles()
    {
        for (var f: validFileNames)
        {
            while (true)
            {
                try
                {
                    Files.deleteIfExists(Paths.get(f));
                    break;
                }
                catch (IOException e)
                {
                    System.err.println("Could not remove temporary file: \"" + f + "\", remove manually.");
                }
            }
        }
    }


}
