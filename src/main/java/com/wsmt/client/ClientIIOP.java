package com.wsmt.client;

import com.wsmt.generated.Event;
import com.wsmt.generated._EventServiceStub;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA_2_3.portable.ObjectImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class ClientIIOP {
    private static final String HOST = "localhost";
    private static final String PORT = "3000";
    private static final String NAME = "EventService";

    // prints an event
    private static void printEvent(Event event) {
        System.out.println(MessageFormat.format("Event id: {0}, title: {1}, description: {2}, date: {3}",
                event.id, event.title, event.description, new Date(event.date).toGMTString()));
    }

    public static void main(String[] args) throws NamingException {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
        props.setProperty("java.naming.provider.url", "iiop://" + HOST + ":" + PORT);
        Context ctx = new InitialContext(props);
        Object ref = ctx.lookup(NAME);

        // service interface
        Delegate delegate = ((ObjectImpl) ref)._get_delegate();
        _EventServiceStub proxy = new _EventServiceStub();
        proxy._set_delegate(delegate);
        System.out.println("Java RMI-IIOP Client: " + HOST + ":" + PORT + "/" + NAME);

        // add an event
        proxy.add("Title 1", "Some description here", new Date().getTime());

        // filter events
        Event[] events = proxy.filter("script");
        Arrays.asList(events).forEach(ClientIIOP::printEvent);

        // delete an event
        long id = events[0].id;
        proxy.delete(id);
    }
}
