package com.zealcore.srl.offline;

import java.io.PrintStream;

interface IPrinter extends ITransformer {

    void setOut(PrintStream out);
}
