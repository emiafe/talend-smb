package com.emiafe.talend.datastore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@DataStore("ConnectionDatastore")
@GridLayout({
        // The generated component layout will display one configuration entry per line.
        // Customize it as much as needed.
        @GridLayout.Row({"host","domain"}),
        @GridLayout.Row({"username", "password"}),
})
@Documentation("A Datastore made of an API URL, a username, and a password. The password is marked as Credential.")
public class ConnectionDatastore implements Serializable {
    @Option
    @Documentation("")
    private String host;

    @Option
    @Documentation("")
    private String username;

    @Option
    @Credential
    @Documentation("")
    private String password;

    @Option
    @Documentation("")
    private String domain;

    public String getHost() {
        return host;
    }

    public ConnectionDatastore setHost(String host) {
        this.host = host;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public ConnectionDatastore setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ConnectionDatastore setPassword(String password) {
        this.password = password;
        return this;
    }
}
