package com.emiafe.talend.processor;

import com.emiafe.talend.dataset.ConnectionDataset;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset" }),
    @GridLayout.Row({ "path","filename" })

})
@Documentation("TODO fill the documentation for this configuration")
public class SmbFileDeleteConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private ConnectionDataset dataset;

    @Option
    @Documentation("")
    private String path;

    @Option
    @Documentation("")
    private String filename;

    public String getPath() {
        return path;
    }

    public String getFilename() {
        return filename;
    }

    public SmbFileDeleteConfiguration setPath(String path) {
        this.path = path;
        return this;
    }

    public SmbFileDeleteConfiguration setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public ConnectionDataset getDataset() {
        return dataset;
    }

    public SmbFileDeleteConfiguration setDataset(ConnectionDataset dataset) {
        this.dataset = dataset;
        return this;
    }



}