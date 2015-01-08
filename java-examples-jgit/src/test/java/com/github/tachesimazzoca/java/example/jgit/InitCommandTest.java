package com.github.tachesimazzoca.java.example.jgit;

import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class InitCommandTest {
    private static final String TEMP_DIR_NAMESPACE = "jgit";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testInitGitDir() throws Exception {
        File dir = tempFolder.newFolder(TEMP_DIR_NAMESPACE);
        new InitCommand().setDirectory(dir).call();
        Repository repo = new FileRepositoryBuilder().findGitDir(dir).build();
        assertEquals("The directory should be dir + /.git",
                new File(dir, ".git"), repo.getDirectory());
    }

    @Test
    public void testInitBareGitDir() throws Exception {
        File dir = tempFolder.newFolder(TEMP_DIR_NAMESPACE);
        new InitCommand().setDirectory(dir).setBare(true).call();
        Repository repo = new FileRepositoryBuilder().findGitDir(dir).build();
        assertEquals("If the repository is bare, the directory should be the same as the root",
                dir, repo.getDirectory());
    }
}
