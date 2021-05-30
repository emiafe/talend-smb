package com.emiafe.talend.processor;

import com.emiafe.talend.service.SmbService;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.processor.*;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.connection.Connection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.Serializable;

import static org.talend.sdk.component.api.component.Icon.IconType.CUSTOM;


@Version(1) // default version is 1, if some configuration changes happen between 2 versions you can add a migrationHandler
@Icon(value = CUSTOM, custom = "file") // icon is located at src/main/resources/icons/CompanyInput.svg
@Processor(name = "FilePut")
@Documentation("Esta componente e para fazer upload dos ficheiros")
public class SmbFilePut implements Serializable {
    private final SmbFilePutConfiguration configuration;
    @Connection
    private SmbService service;

    public SmbFilePut(
            @Option("configuration") final SmbFilePutConfiguration configuration
             , final SmbService service
    ) {
        this.configuration = configuration;
        this.service = service;
    }


    @PostConstruct
    public void init() throws IOException {
        // this method will be executed once for the whole component execution,
        // this is where you can establish a connection for instance
        // Note: if you don't need it you can delete it
        service.refreshConn(configuration.getDataset());
    }

    @BeforeGroup
    public void beforeGroup() {
        // if the environment supports chunking this method is called at the beginning if a chunk
        // it can be used to start a local transaction specific to the backend you use
        // Note: if you don't need it you can delete it
    }

    @ElementListener
    public void onNext(
            @Input final Record defaultInput,
            @Output final OutputEmitter<Record> defaultOutput) {
        // this is the method allowing you to handle the input(s) and emit the output(s)
        // after some custom logic you put here, to send a value to next element you can use an
        // output parameter and call emit(value).
        if(defaultInput!=null)
            defaultOutput.emit(defaultInput);
    }

//    @AfterGroup
    public void afterGroup() throws IOException {
        // symmetric method of the beforeGroup() executed after the chunk processing
        // Note: if you don't need it you can delete it
//        service.write(configuration.getPath(),configuration.getFilename());
    }

    @PreDestroy
    public void release() throws IOException {
        // this is the symmetric method of the init() one,
        // release potential connections you created or data you cached
        // Note: if you don't need it you can delete it
        service.write(configuration.getPath(),configuration.getFile());
    }
}