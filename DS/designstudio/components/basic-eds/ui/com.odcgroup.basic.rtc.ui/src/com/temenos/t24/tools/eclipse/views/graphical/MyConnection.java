package com.temenos.t24.tools.eclipse.views.graphical;


/** This object represents arrow / Edge of the graph which connects two nodes. */ 
public class MyConnection {

    final MyNode source;
    final MyNode destination;

    public MyConnection(MyNode source, MyNode destination) {
        this.source = source;
        this.destination = destination;
    }

    public MyNode getSource() {
        return source;
    }

    public MyNode getDestination() {
        return destination;
    }
}
