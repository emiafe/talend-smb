package com.emiafe.talend.source;

import com.emiafe.talend.model.FileDetail;
import com.emiafe.talend.service.SmbService;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.connection.Connection;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

@Documentation("TODO fill the documentation for this source")
public class SmbFileListSource implements Serializable {
    private final SmbFileListMapperConfiguration configuration;
    @Connection
    private SmbService service;
    private final RecordBuilderFactory builderFactory;
    private Iterator<FileDetail> iterator;
    private Collection<FileDetail> list;

    public SmbFileListSource(
            @Option("configuration") final SmbFileListMapperConfiguration configuration,
                        final SmbService service,
                        final RecordBuilderFactory builderFactory) {
        this.configuration = configuration;
        this.service = service;
        this.builderFactory = builderFactory;
    }

    @PostConstruct
    public void init() throws IOException {
        service.refreshConn(configuration.getDataset());
        list= service.smbv3ListFiles(configuration.getPath(),configuration.getSearchPattern());
        iterator = list.iterator();
    }

    @Producer
    public FileDetail next() {
        if(iterator.hasNext())
            return  iterator.next();
        else
            return null;
    }

    @PreDestroy
    public void release() {
        // this is the symmetric method of the init() one,
        // release potential connections you created or data you cached
    }
}