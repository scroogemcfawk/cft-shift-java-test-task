package merge_sort.sort;

import merge_sort.config.Config;

public class Sort
{
    Config config;

    public Sort(Config c) {
        config = c;
    }
    // Unfortunately, works using aftereffects of mutable arrays :(
    public void sortInt(Integer[] buffer, int left, int right)
    {
        if (left > right)
        {
            throw new IllegalArgumentException("Invalid sort boundaries");
        }
        if (right - left > 1)
        {
            int mid = left + (right - left) / 2;
            sortInt(buffer, left, mid);
            sortInt(buffer, mid + 1, right);
            mergeInt(buffer, left, right);
            return;
        }
        if (right - left == 1)
        {
            if (!Comparator.inOrderStatic(buffer[left], buffer[right], config))
            {
                Integer temp = buffer[left];
                buffer[left] = buffer[right];
                buffer[right] = temp;
            }
        }
    }

    public void sortStr(String[] buffer, int left, int right)
    {
        if (left > right)
        {
            throw new IllegalArgumentException("Invalid sort boundaries");
        }
        if (right - left > 1)
        {
            int mid = left + (right - left) / 2;
            sortStr(buffer, left, mid);
            sortStr(buffer, mid + 1, right);
            mergeStr(buffer, left, right);
            return;
        }
        if (right - left == 1)
        {
            if (!Comparator.inOrderStatic(buffer[left], buffer[right], config))
            {
                String temp = buffer[left];
                buffer[left] = buffer[right];
                buffer[right] = temp;
            }
        }
    }

    void mergeInt(Integer[] buffer, int left, int right)
    {
        Integer[] temp = new Integer[right - left + 1];
        int mid = left + (right - left) / 2;
        int lp = left;
        int rp = mid + 1;
        int c = 0;
        while (lp <= mid && rp <= right)
        {
            if (Comparator.inOrderStatic(buffer[lp], buffer[rp], config))
            {
                temp[c++] = buffer[lp++];
            }
            else
            {
                temp[c++] = buffer[rp++];
            }
        }
        while (lp <= mid)
        {
            temp[c++] = buffer[lp++];
        }
        while (rp <= right)
        {
            temp[c++] = buffer[rp++];
        }
        if (c >= 0)
        {
            System.arraycopy(temp, 0, buffer, left, c);
        }
    }

    void mergeStr(String[] buffer, int left, int right)
    {
        String[] temp = new String[right - left + 1];
        int mid = left + (right - left) / 2;
        int lp = left;
        int rp = mid + 1;
        int c = 0;
        while (lp <= mid && rp <= right)
        {
            if (Comparator.inOrderStatic(buffer[lp], buffer[rp], config))
            {
                temp[c++] = buffer[lp++];
            }
            else
            {
                temp[c++] = buffer[rp++];
            }
        }
        while (lp <= mid)
        {
            temp[c++] = buffer[lp++];
        }
        while (rp <= right)
        {
            temp[c++] = buffer[rp++];
        }
        if (c >= 0)
        {
            System.arraycopy(temp, 0, buffer, left, c);
        }
    }
}
