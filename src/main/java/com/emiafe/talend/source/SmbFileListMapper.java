package com.emiafe.talend.source;

import com.emiafe.talend.service.SmbService;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.*;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.connection.Connection;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import java.io.Serializable;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.talend.sdk.component.api.component.Icon.IconType.CUSTOM;

//
// this class role is to enable the work to be distributed in environments supporting it.
//
@Version(1) // default version is 1, if some configuration changes happen between 2 versions you can add a migrationHandler
@Icon(value = CUSTOM, custom = "file-storage") // icon is located at src/main/resources/icons/CompanyInput.svg
@PartitionMapper(name = "FileList")
@Documentation("TODO fill the documentation for this mapper")
public class SmbFileListMapper implements Serializable {
    private final SmbFileListMapperConfiguration configuration;
    private final RecordBuilderFactory recordBuilderFactory;
    @Connection
    private SmbService service;

    public SmbFileListMapper(@Option("configuration") final SmbFileListMapperConfiguration configuration,
                             final SmbService service,
                             final RecordBuilderFactory recordBuilderFactory
    ) {
        this.configuration = configuration;
        this.service = service;
        this.recordBuilderFactory = recordBuilderFactory;
    }

    @Assessor
    public long estimateSize() {
        // this method should return the estimation of the dataset size
        // it is recommended to return a byte value
        // if you don't have the exact size you can use a rough estimation
        return 1L;
    }

    @Split
    public List<SmbFileListMapper> split(@PartitionSize final long bundles) {
        // overall idea here is to split the work related to configuration in bundles of size "bundles"
        //
        // for instance if your estimateSize() returned 1000 and you can run on 10 nodes
        // then the environment can decide to run it concurrently (10 * 100).
        // In this case bundles = 100 and we must try to return 10 CompanyInputMapper with 1/10 of the overall work each.
        //
        // default implementation returns this which means it doesn't support the work to be split
        return singletonList(this);
    }

    @Emitter
    public SmbFileListSource createWorker() {
        // here we create an actual worker,
        // you are free to rework the configuration etc but our default generated implementation
        // propagates the partition mapper entries.
        return new SmbFileListSource(configuration, service, recordBuilderFactory);
    }
}