/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2014 Alex Buloichik
               Home page: https://www.omegat.org/
               Support center: https://omegat.org/support

 This file is part of OmegaT.

 OmegaT is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OmegaT is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>.
 **************************************************************************/

package org.omegat.core.team2.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import tokyo.northside.logging.ILogger;
import tokyo.northside.logging.LoggerFactory;

import org.omegat.core.team2.IRemoteRepository2;
import org.omegat.core.team2.ProjectTeamSettings;
import org.omegat.core.team2.RemoteRepositoryFactory;

import gen.core.project.RepositoryDefinition;
import gen.core.project.RepositoryMapping;

/**
 * HTTP/HTTPS repository connection implementation.
 *
 * It can be used as read-only repository for retrieve sources, external TMX,
 * glossaries, etc. Since HTTP protocol doesn't support multiple files, each URL
 * should be mapped to separate file, i.e. directory mapping is not supported.
 *
 * @author Alex Buloichik (alex73mail@gmail.com)
 */
public class HTTPRemoteRepository implements IRemoteRepository2 {
    private static final ILogger LOGGER = LoggerFactory.getLogger(HTTPRemoteRepository.class);

    private RepositoryDefinition config;
    private File baseDirectory;

    /**
     * Plugin loader.
     */
    public static void loadPlugins() {
        RemoteRepositoryFactory.addRepositoryConnector("http", HTTPRemoteRepository.class);
    }

    /**
     * Plugin unloader.
     */
    public static void unloadPlugins() {
    }

    @Override
    public void init(RepositoryDefinition repo, File dir, ProjectTeamSettings teamSettings) throws Exception {
        LOGGER.atDebug().log("Initialize HTTP remote repository");
        config = repo;
        baseDirectory = dir;
    }

    /**
     * Use SHA-1 as file version.
     */
    @Override
    public String getFileVersion(String file) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.reset();

        // calculate SHA-1
        byte[] buffer = new byte[8192];
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        try {
            while (true) {
                int len = in.read(buffer);
                if (len < 0) {
                    break;
                }
                sha1.update(buffer, 0, len);
            }
        } finally {
            in.close();
        }

        // out as hex
        Formatter formatter = new Formatter();
        try {
            for (byte b : sha1.digest()) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } finally {
            formatter.close();
        }
    }

    @Override
    public void switchToVersion(String version) throws Exception {
        if (version != null) {
            throw new RuntimeException("Not supported");
        }

        LOGGER.atDebug().log("Update to latest");
        // retrieve all mapped files
        Properties etags = loadETags();
        for (RepositoryMapping m : config.getMapping()) {
            String url = config.getUrl() + m.getRepository();
            File out = new File(baseDirectory, m.getRepository());
            retrieve(etags, m.getRepository(), url, out);
        }
        saveETags(etags);
    }

    @Override
    public void addForCommit(String path) throws Exception {
        LOGGER.atDebug().log("Cannot add files for commit for HTTP repositories. Skipping \"{}\".", path);
    }

    @Override
    public void addForDeletion(String path) throws Exception {
        LOGGER.atDebug().log("Cannot add files for deletion for HTTP repositories. Skipping \"{}\".", path);
    }

    @Override
    public File getLocalDirectory() {
        return baseDirectory;
    }

    @Override
    public String[] getRecentlyDeletedFiles() throws Exception {
        return new String[0];
    }

    @Override
    public String commit(String[] onVersions, String comment) throws Exception {
        LOGGER.atDebug().log("Commit not supported for HTTP repositories.");

        return null;
    }

    /**
     * Load all ETags.
     */
    protected Properties loadETags() throws Exception {
        Properties props = new Properties();
        File f = new File(baseDirectory, ".etags");
        if (f.exists()) {
            FileInputStream in = new FileInputStream(f);
            try {
                props.load(in);
            } finally {
                in.close();
            }
        }
        return props;
    }

    /**
     * Save all ETags.
     */
    protected void saveETags(Properties props) throws Exception {
        FileOutputStream out = new FileOutputStream(new File(baseDirectory, ".etags"));
        try {
            props.store(out, null);
        } finally {
            out.close();
        }
    }

    /**
     * Retrieve remote URL with non-modified checking by ETag. If server doesn't
     * support ETag, file will be always retrieved.
     */
    protected void retrieve(Properties etags, String file, String url, final File out) throws Exception {
        String etag = etags.getProperty(file);
        LOGGER.atDebug().setMessage("Retrieve {} into {} with ETag={}").addArgument(url)
                .addArgument(out::getAbsolutePath).addArgument(etag).log();

        out.getParentFile().mkdirs();
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        try {
            if (etag != null) {
                // use ETag if we know it
                conn.setRequestProperty("If-None-Match", etag);
            }
            switch (conn.getResponseCode()) {
            case HttpURLConnection.HTTP_OK:
                etag = conn.getHeaderField("ETag");
                LOGGER.atDebug().log("Retrieve {}: 200 with ETag={}", url, etag);
                break;
            case HttpURLConnection.HTTP_NOT_MODIFIED:
                // not modified - just return
                LOGGER.atDebug().log("Retrieve {}: not modified", url);
                return;
            default:
                throw new RuntimeException("HTTP response code: " + conn.getResponseCode());
            }

            // load into .tmp file
            File temp = new File(out.getAbsolutePath() + ".tmp");
            InputStream in = conn.getInputStream();
            try {
                FileUtils.copyInputStreamToFile(in, temp);
            } finally {
                in.close();
            }

            // rename into file
            if (out.exists()) {
                if (!out.delete()) {
                    throw new IOException();
                }
            }
            if (!temp.renameTo(out)) {
                throw new IOException();
            }
            try {
                etags.setProperty(file, etag);
            } catch (Exception ex) {
                // Etags are optionnal, we eat the exception is there is none
            }
        } catch (UnknownHostException | SocketException e) {
            throw new NetworkException(e);
        } finally {
            conn.disconnect();
        }

        LOGGER.atDebug().log("Retrieve {} finished", url);
    }
}
