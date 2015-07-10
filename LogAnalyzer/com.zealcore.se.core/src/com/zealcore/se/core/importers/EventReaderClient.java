package com.zealcore.se.core.importers;

import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.TypeDescription;

public interface EventReaderClient {

    public void eventRead(GenericEventInfo event);

    public void eventRead(GenericArtifactInfo event);

    public void typeDescRead(TypeDescription typeDesc);

}
