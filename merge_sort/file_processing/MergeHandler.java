package merge_sort.file_processing;

import merge_sort.config.Config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MergeHandler
{
    private final Config config;

    private final ValidFilesHandler validFilesHandler;

    private final int BUFFER_SIZE_LIMIT;

    public MergeHandler(Config c, ArrayList<String> validFiles, int buffSize)
    {
        config = c;
        BUFFER_SIZE_LIMIT = buffSize;
        validFilesHandler = new ValidFilesHandler(config, validFiles, buffSize);
    }

    public void merge() throws Exception
    {
        if (validFilesHandler.inputFilenames.size() > 1)
        {
            try (FileWriter fw = new FileWriter(config.getOutputFileName(), true);
                 BufferedWriter out = new BufferedWriter(fw, BUFFER_SIZE_LIMIT))
            {
                while (validFilesHandler.hasNext())
                {
                    out.write(validFilesHandler.getNext());
                    out.newLine();
                }
                validFilesHandler.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Merge error.");
            }
        }
        else
        {
            try
            {
                validFilesHandler.close();
                Path source = Paths.get(validFilesHandler.inputFilenames.get(0));
                Files.move(source, source.resolveSibling(config.getOutputFileName()));
            }
            catch (Exception e)
            {
                config.getExceptionHandler().exec(new IOException("Could not rename temp file."));
            }
        }
    }
}

