package merge_sort.config;

public class RuntimeHelper
{
    static private final long CONST_SIZE = 1_000_000;
    static public long freeMemory() {
//        return CONST_SIZE;
        return (Runtime.getRuntime().maxMemory() - (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }
}
