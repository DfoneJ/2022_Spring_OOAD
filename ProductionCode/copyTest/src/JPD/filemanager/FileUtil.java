package JPD.filemanager;

import java.io.*;


public class FileUtil {
    public FileUtil() {
    }

    public boolean copy(String fromFileName, String toFileName, boolean override) {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);

        if (!fromFile.exists() || !fromFile.isFile() || !fromFile.canRead()) {
            return false;
        }

        if (toFile.isDirectory()) {
            toFile = new File(toFile, fromFile.getName());
        }

        if (toFile.exists()) {
            if (!toFile.canWrite() || override == false) {
                return false;
            }
        }
        else {
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists() || dir.isFile() || !dir.canWrite()) {
                return false;
            }
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytes_read;
            while ( (bytes_read = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytes_read);
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }
        finally {
            if (from != null) {
                try {
                    from.close();
                }
                catch (IOException e) {
                    System.err.println("Exception when close source file");
                }
            }
            if (to != null) {
                try {
                    to.close();
                }
                catch (IOException e) {
                    System.err.println("Exception when close target file");
                }
            }
        }
    }

    public boolean emptyDirectory(File directory) {
        boolean result = false;
        File[] entries = directory.listFiles();
        for (int i = 0; i < entries.length; i++) {
            if (!entries[i].delete()) {
                result = false;
            }
        }
        return true;
    }

    public boolean emptyDirectory(String directoryName) {
        File dir = new File(directoryName);
        return emptyDirectory(dir);
    }

     public boolean deleteDirectory(String dirName) {
         return deleteDirectory(new File(dirName));
    }

    public static boolean deleteDirectory(File dir) {
        if ( (dir == null) || !dir.isDirectory()) {
            throw new IllegalArgumentException("Argument " + dir +
                                             " is not a directory. ");
        }

        File[] entries = dir.listFiles();
        int sz = entries.length;

        for (int i = 0; i < sz; i++) {
            if (entries[i].isDirectory()) {
                if (!deleteDirectory(entries[i])) {
                    return false;
                }
            }
            else {
                if (!entries[i].delete()) {
                    return false;
                }
            }
        }

        if (!dir.delete()) {
            return false;
        }
        return true;
    }
}
