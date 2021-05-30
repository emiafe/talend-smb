package com.emiafe.talend.service;

import com.emiafe.talend.dataset.ConnectionDataset;
import com.emiafe.talend.model.FileDetail;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.exception.ComponentException;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.connection.CloseConnection;
import org.talend.sdk.component.api.service.connection.CloseConnectionObject;
import org.talend.sdk.component.api.service.connection.CreateConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
public class SmbService extends CloseConnectionObject{
    private Session session;
    private SMBClient client;
    private ConnectionDataset configuration;

    @CreateConnection
    public Object createConn(@Option("configuration") final ConnectionDataset dataset) throws ComponentException, IOException {
        client = new SMBClient();
        this.configuration =dataset;
        Connection connection = client.connect(configuration.getHost());
        AuthenticationContext ac = new AuthenticationContext(configuration.getUsername(), configuration.getPassword().toCharArray(), configuration.getDomain());
        session = connection.authenticate(ac);
        return this;
    }
//    @HealthCheck
//    public HealthCheckStatus testConnection(ConnectionDataset dataset) {
//
//        if (datastoreA == null || datastoreA.getUsername().equals("invalidtest")) {
//            return new HealthCheckStatus(HealthCheckStatus.Status.KO, "Connection not ok, datastore can't be null");
//        }
//        return new HealthCheckStatus(HealthCheckStatus.Status.OK, "Connection ok");
//    }

    public void refreshConn(final ConnectionDataset configuration) throws IOException {
        if (session == null
                || session.getConnection() == null
                || !session.getConnection().isConnected()) {
            System.out.println(this + ">> connecting");
            createConn(configuration);
        }
    }

    @Override
    public boolean close() throws ComponentException {
        try {
            session.close();
        } catch (IOException e) {
            throw new ComponentException(e.getMessage());
        }
        return true;
    }

    @CloseConnection
    public CloseConnectionObject closeConn() {
        return this;
        /*
        return new CloseConnectionObject() {
            @Override
            public boolean close() throws ComponentException {
                try {
                    session.close();
                } catch (IOException e) {
                    throw new ComponentException("Service not close");
                }
                return false;
            }
        };
        */
    }

    public Collection<FileDetail> smbv3ListFiles(String path, String searchPattern) {
        List<FileDetail> files = new LinkedList<>();
        String path_dir = path;
        String dir = "";
        int index = path.indexOf("\\");
        if (index > 1) {
            path_dir = path.substring(0, index);
            dir = path.substring(index + 1);

        }
        DiskShare share = (DiskShare) session.connectShare(path_dir);
        for (FileIdBothDirectoryInformation f : share.list(dir, searchPattern)) {
            FileDetail file = new FileDetail(f.getFileName());
            file.setChangeTime(f.getChangeTime());
            file.setAllocationSize(f.getAllocationSize());
            file.setShortName(f.getShortName());
            //System.out.println("File : " + f.getFileName());
            if (!file.getFileName().isEmpty() && !file.getFileName().equals("..") && !file.getFileName().equals("."))
                files.add(file);
        }
        return files;

    }

    public void write(byte[] bytes, String path, String filename) throws IOException {
        String path_dir = path;
        String dir = "";
        int index = path.indexOf("\\");
        if (index > 1) {
            path_dir = path.substring(0, index);
            dir = path.substring(index + 1);
            if (!dir.isEmpty() && !dir.endsWith("\\")) {
                dir = dir + "\\";
            }
        }
        DiskShare share = (DiskShare) session.connectShare(path_dir);
        File f = share.openFile(dir + filename,
                new HashSet<>(Arrays.asList(AccessMask.MAXIMUM_ALLOWED)),
                new HashSet<>(Arrays.asList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
                SMB2ShareAccess.ALL,
                SMB2CreateDisposition.FILE_OVERWRITE_IF,
                new HashSet<>(Arrays.asList(SMB2CreateOptions.FILE_DIRECTORY_FILE))
        );
        OutputStream os = f.getOutputStream();
        os.write(bytes);
        os.close();
    }

    public void del(String path, String filename) throws IOException {
        String path_dir = path;
        String dir = "";
        int index = path.indexOf("\\");
        if (index > 1) {
            path_dir = path.substring(0, index);
            dir = path.substring(index + 1);
            if (!dir.isEmpty() && !dir.endsWith("\\")) {
                dir = dir + "\\";
            }
        }
        DiskShare share = (DiskShare) session.connectShare(path_dir);
        share.rm(dir + filename);
    }


    public void write(String path, String filename) throws IOException {
        java.io.File f = new java.io.File(filename);
        write(f, path, f.getName());
    }

    public void write(String path, java.io.File f) throws IOException {
        write(f, path, f.getName());
    }

    public void write(String path, String file, String newFile) throws IOException {
        java.io.File f = new java.io.File(file);
        write(f, path, newFile);
    }

    private void write(java.io.File f, String path, String newFile) throws IOException {
        byte[] bytes = new byte[(int) f.length()];
        // funny, if can use Java 7, please uses Files.readAllBytes(path)
        try (FileInputStream fis = new FileInputStream(f)) {
            fis.read(bytes);
            write(bytes, path, newFile);
        }
    }



}