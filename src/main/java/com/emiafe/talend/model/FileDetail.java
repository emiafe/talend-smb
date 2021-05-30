package com.emiafe.talend.model;

import com.hierynomus.msdtyp.FileTime;

import java.io.Serializable;

public class FileDetail implements Serializable {
    private String fileName;
    private FileTime changeTime;
    private long allocationSize;
    private String shortName;

    public FileDetail(String fileName) {
        this.fileName=fileName;
    }

    public void setChangeTime(FileTime changeTime) {
        this.changeTime = changeTime;
    }

    public FileTime getChangeTime() {
        return changeTime;
    }

    public void setAllocationSize(long allocationSize) {
        this.allocationSize = allocationSize;
    }

    public long getAllocationSize() {
        return allocationSize;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return "FileDetail{" +
                "fileName='" + fileName + '\'' +
                ", changeTime=" + changeTime +
                ", allocationSize=" + allocationSize +
                '}';
    }
}
