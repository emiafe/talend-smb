package com.emiafe.talend.dataset;

import com.emiafe.talend.datastore.ConnectionDatastore;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@DataSet("ConnectionDataset")
@GridLayout({
        @GridLayout.Row({ "datastore" })
})
@Documentation("A Dataset configuration containing a simple datastore")
public class ConnectionDataset implements Serializable {
    @Option
    @Documentation("Datastore")
    private ConnectionDatastore datastore;

    public ConnectionDatastore getDatastore() {
        return datastore;
    }

    public ConnectionDataset setDatastore(ConnectionDatastore datastore) {
        this.datastore = datastore;
        return this;
    }


    public String getHost() {
        return datastore.getHost();
    }


    public String getDomain() {
        return datastore.getDomain();
    }

    public String getUsername() {
        return datastore.getUsername();
    }

    public String getPassword() {
        return datastore.getPassword();
    }
}