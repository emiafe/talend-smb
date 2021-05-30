package com.emiafe.talend.service;

import com.emiafe.talend.datastore.ConnectionDatastore;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@GridLayout({
    @GridLayout.Row({ "datastore" })
})
@Documentation("")
public class SmbServiceConfiguration implements Serializable {
    @Option
    @Documentation("")
    private ConnectionDatastore datastore;

    public ConnectionDatastore getDatastore() {
        return datastore;
    }

    public SmbServiceConfiguration setDatastore(ConnectionDatastore datastore) {
        this.datastore = datastore;
        return this;
    }
}