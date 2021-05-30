package com.emiafe.talend.processor;

import com.emiafe.talend.dataset.ConnectionDataset;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.File;
import java.io.Serializable;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset" }),
    @GridLayout.Row({ "path","file-path" })

})
@Documentation("TODO fill the documentation for this configuration")
public class SmbFilePutConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    ConnectionDataset dataset;

    @Option
    @Documentation("")
    private String path;

//    @Option
@Option("file-path")
@Documentation("")
    File file;

    public String getPath() {
        return path;
    }

//    public String getFilename() {
//        return filename;
//    }

    public SmbFilePutConfiguration setPath(String path) {
        this.path = path;
        return this;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /*public SmbFilePutConfiguration setFilename(String filename) {
        this.filename = filename;
        return this;
    }*/

    public ConnectionDataset getDataset() {
        return dataset;
    }

    public SmbFilePutConfiguration setDataset(ConnectionDataset dataset) {
        this.dataset = dataset;
        return this;
    }

}