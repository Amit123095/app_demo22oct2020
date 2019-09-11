package com.mindyourlovedone.healthcare.DropBox;

import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.SharedFileMetadata;

/*
Created by Nikita on 7-9-19
 */
public class DropBoxFileItem {
    int shared = 0;
    SharedFileMetadata sharefmd;

    public int getShared() {
        return shared;
    }

    public void setShared(int shared) {
        this.shared = shared;
    }

    public SharedFileMetadata getSharefmd() {
        return sharefmd;
    }

    public void setSharefmd(SharedFileMetadata sharefmd) {
        this.sharefmd = sharefmd;
    }

    public Metadata getFilemd() {
        return filemd;
    }

    public void setFilemd(Metadata filemd) {
        this.filemd = filemd;
    }

    Metadata filemd;
}
