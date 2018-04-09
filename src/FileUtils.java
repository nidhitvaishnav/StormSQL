import java.io.File;
import java.io.FileNotFoundException;

public class FileUtils {

    public static boolean deleteRecursive(File path) throws FileNotFoundException{
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean successFlag = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
            	successFlag = successFlag && FileUtils.deleteRecursive(f);
            }
        }
        return successFlag && path.delete();
    }
}