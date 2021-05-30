package com.emiafe.talend.source;

import com.emiafe.talend.dataset.ConnectionDataset;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset" }),
    @GridLayout.Row({ "path","searchPattern" }),
})
@Documentation("TODO fill the documentation for this configuration")
public class SmbFileListMapperConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private ConnectionDataset dataset;

    @Option
    @Documentation("")
    private String path;

    @Option
    @Documentation("")
    private String searchPattern;

    public String getPath() {
        return path;
    }

    public SmbFileListMapperConfiguration setPath(String path) {
        this.path = path;
        return this;
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public SmbFileListMapperConfiguration setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
        return this;
    }

    public ConnectionDataset getDataset() {
        return dataset;
    }

    public SmbFileListMapperConfiguration setDataset(ConnectionDataset dataset) {
        this.dataset = dataset;
        return this;
    }


}