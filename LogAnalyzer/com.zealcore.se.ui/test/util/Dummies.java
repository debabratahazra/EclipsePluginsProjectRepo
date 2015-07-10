package util;

import com.zealcore.se.ui.internal.ArtifactID;

public final class Dummies {

    private Dummies() {}

    public static final ArtifactID ARTIFACTID = new ArtifactID() {
        @Override
        public String getClassName() {
            return "DummyClassName";
        }

        @Override
        public String getId() {
            return "DummyArtifactName";
        }

        // @Override
        // public String getLogSession() {
        // return "DummyLogSessionName";
        // }
    };
}
