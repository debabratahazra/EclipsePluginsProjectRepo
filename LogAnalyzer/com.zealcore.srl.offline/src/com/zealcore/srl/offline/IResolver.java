package com.zealcore.srl.offline;

import java.io.File;

interface IResolver {
    void resolve(Blackbox box, AbstractTypedMessage message,
            File externalResource);
}
