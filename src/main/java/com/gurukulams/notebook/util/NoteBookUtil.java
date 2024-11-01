package com.gurukulams.notebook.util;

import com.gurukulams.notebook.DataManager;
import com.gurukulams.notebook.service.AnnotationService;
import com.gurukulams.notebook.store.AnnotationStore;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;

public class NoteBookUtil {

    /**
     * Notebooks Data Storage Directory.
     */
    private static final String DATA_NOTEBOOK = "./data/notebooks/";
    /**
     * File Extension of H2.
     */
    private static final String H2_DB_EXT = ".mv.db";

    /**
     * NoteBook Util.
     */
    private static NoteBookUtil noteBookUtil;

    /**
     * gets NoteBookUtil.
     * @return noteBookUtil
     */
    public static NoteBookUtil getNoteBookUtil() {
        if (noteBookUtil == null) {
            noteBookUtil = new NoteBookUtil();
        }
        return noteBookUtil;
    }

    /**
     * Get Annotation Store for User.
     *
     * @return getAnnotationService(userName)
     */
    public AnnotationStore getAnnotationStore()
            throws SQLException, IOException {
        return new DataManager()
                .getAnnotationStore();
    }

    /**
     * Get DataSource Store for User.
     * @param userName
     * @return ds
     * @throws IOException
     * @throws SQLException
     */
    public static DataSource getDataSource(final String userName)
            throws IOException, SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        String dbFile = DATA_NOTEBOOK + userName;
        ds.setURL("jdbc:h2:" + dbFile);
        ds.setUser(userName);
        ds.setPassword(userName);
        if (!new File(dbFile + H2_DB_EXT).exists()) {
            Reader reader = new InputStreamReader(AnnotationService.class
                    .getModule()
                    .getResourceAsStream("db/ddl/notes.sql"));
            RunScript.execute(ds.getConnection(), reader);
        }
        return ds;
    }

}
