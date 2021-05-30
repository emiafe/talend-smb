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
@Icon(value = CUSTOM, custom = "rm") // icon is located at src/main/resources/icons/CompanyInput.svg
@Processor(name = "FileDelete")
@Documentation("Esta componente e para fazer upload dos ficheiros")
public class SmbFileDelete implements Serializable {
    private final SmbFileDeleteConfiguration configuration;
    @Connection
    private SmbService service;

    public SmbFileDelete(
            @Option("configuration") final SmbFileDeleteConfiguration configuration
            , final SmbService service
    ) {
        this.configuration = configuration;
        this.service = service;
    }

    @PostConstruct
    public void init() throws IOException {
        service.refreshConn(configuration.getDataset());
    }


    @ElementListener
    public void onNext(
            @Input final Record defaultInput,
            @Output final OutputEmitter<Record> defaultOutput) {
        if(defaultInput!=null)
            defaultOutput.emit(defaultInput);
    }

//    @AfterGroup
    @PreDestroy
    public void run() throws IOException {
        service.del(configuration.getPath(),configuration.getFilename());
    }


}