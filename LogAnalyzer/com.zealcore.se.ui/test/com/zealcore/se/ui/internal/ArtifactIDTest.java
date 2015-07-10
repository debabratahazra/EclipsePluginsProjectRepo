package com.zealcore.se.ui.internal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.LogFile;

public class ArtifactIDTest {

    private static final String ARTIFACT_NAME = "Something";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public final void testHashCode() {
        final IArtifact artifact = new AbstractArtifact() {};
        artifact.setLogFile(new LogFile() {});

        artifact.setName(ArtifactIDTest.ARTIFACT_NAME);

        final ArtifactID subject = ArtifactID.valueOf(artifact);

        Assert.assertEquals(subject.hashCode(), artifact.hashCode());
    }

    @Test
    public final void testEqualsObject() {
        final IArtifact artifact = new AbstractArtifact() {};
        artifact.setLogFile(new LogFile() {});

        artifact.setName(ArtifactIDTest.ARTIFACT_NAME);

        final ArtifactID subject = ArtifactID.valueOf(artifact);

        Assert.assertEquals(subject, artifact);
        Assert.assertEquals(artifact, subject);

    }

}
