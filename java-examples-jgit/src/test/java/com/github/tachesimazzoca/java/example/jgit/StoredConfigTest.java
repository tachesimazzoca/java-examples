package com.github.tachesimazzoca.java.example.jgit;

import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class StoredConfigTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testSave() throws Exception {
        Repository repoA = createTempRepository();
        StoredConfig a = repoA.getConfig();
        a.setString(ConfigConstants.CONFIG_USER_SECTION, null, "name", "JGit User1");
        a.setString(ConfigConstants.CONFIG_USER_SECTION, null, "email", "user1@exampel.net");
        a.save();

        Repository repoB = new FileRepositoryBuilder()
                .findGitDir(repoA.getDirectory()).build();
        StoredConfig b = repoB.getConfig();
        assertEquals(
                a.getString(ConfigConstants.CONFIG_USER_SECTION, null, "name"),
                b.getString(ConfigConstants.CONFIG_USER_SECTION, null, "name"));
        assertEquals(
                a.getString(ConfigConstants.CONFIG_USER_SECTION, null, "email"),
                b.getString(ConfigConstants.CONFIG_USER_SECTION, null, "email"));
    }

    private Repository createTempRepository() throws
            IOException, GitAPIException {
        File dir = tempFolder.newFolder("jgit");
        new InitCommand().setDirectory(dir).call();
        return new FileRepositoryBuilder().findGitDir(dir).build();
    }
}
