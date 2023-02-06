package merge_sort.file_processing;

import merge_sort.config.Config;
import merge_sort.sort.Comparator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ValidFilesHandler
{
    Config config;
    ArrayList<String> inputFilenames;

    int BUFFER_SIZE_LIMIT;

    private final ArrayList<FileWrapper> files = new ArrayList<>();

    ValidFilesHandler(Config c, ArrayList<String> vfn, int buffSize)
    {
        config = c;
        inputFilenames = vfn;
        BUFFER_SIZE_LIMIT = buffSize;
        initFiles();
    }

    public void close()
    {
        for (var f: files)
        {
            try
            {
                f.br.close();
            }
            catch (IOException ignored)
            {
            }
        }
    }

    private void initFiles()
    {
        for (var f: inputFilenames)
        {
            try
            {
                files.add(new FileWrapper(f));
            }
            catch (Exception e)
            {
                if (!config.isIgnoreErrors())
                {
                    throw new RuntimeException("Internal error. Could not open temporary file.");
                }
            }
        }
    }

    public boolean hasNext() throws IOException
    {
        for (var f: files)
        {
            if (f.isReady())
            {
                return true;
            }
        }
        return false;
    }

    public String getNext() throws IOException
    {
        String value = null;
        int fileIndex = -1;
        for (int i = 0; i < files.size(); i++)
        {
            if (files.get(i).isReady())
            {
                value = files.get(i).peek();
                fileIndex = i;
                break;
            }
        }
        if (value == null)
        {
            throw new RuntimeException("Internal error. Unexpected null");
        }
        for (int i = 0; i < files.size(); i++)
        {
            try
            {
                String peekValue = files.get(i).peek();
                if (peekValue != null)
                {
                    if (!Comparator.inOrderStatic(value, peekValue, config))
                    {
                        value = peekValue;
                        fileIndex = i;
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println("File " + inputFilenames.get(i)  + " is not ready");
            }
        }
        return files.get(fileIndex).pop();
    }

    private class FileWrapper
    {
        String fileName;
        BufferedReader br;

        boolean isCurrent = false;

        String value;

        FileWrapper(String filename) throws Exception
        {
            fileName = filename;
            br = new BufferedReader(new FileReader(filename), BUFFER_SIZE_LIMIT * 2 / inputFilenames.size());
        }

        boolean isReady()
        {
            try
            {
                if (peek() == null)
                {
                    return false;
                }
            }
            catch (IOException e)
            {
                return false;
            }
            return true;
        }


        String peek() throws IOException
        {
            if (!isCurrent)
            {
                load();
            }
            return value;
        }

        private void load() throws IOException
        {
            value = br.readLine();
            isCurrent = true;
        }

        private String pop()
        {
            isCurrent = false;
            return value;
        }
    }
}
