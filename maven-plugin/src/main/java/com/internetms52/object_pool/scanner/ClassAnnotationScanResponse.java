package com.internetms52.object_pool.scanner;

/**
 * 這裡應要放檔案路徑，以及掃描到的annotation的值的內容
 */
public class ClassAnnotationScanResponse {
    String classFilePath;

    public ClassAnnotationScanResponse(String classFilePath) {
        this.classFilePath = classFilePath;
    }

    public String getClassFilePath() {
        return classFilePath;
    }

    public void setClassFilePath(String classFilePath) {
        this.classFilePath = classFilePath;
    }
}
