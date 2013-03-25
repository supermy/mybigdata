package org.supermy.batch.esb;

import java.io.File;
import java.util.Comparator;

public class FileCreationTimeComparator implements Comparator<File>{

    public int compare(File file1, File file2) {
        return Long.valueOf(file2.lastModified()).compareTo(
                Long.valueOf(file1.lastModified()));
//        return file1.getName().compareToIgnoreCase(file2.getName());
    }
    
}