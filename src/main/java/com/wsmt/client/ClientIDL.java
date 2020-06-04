package com.wsmt.client;

import com.wsmt.generated.Event;
import com.wsmt.generated.EventService;
import com.wsmt.generated.EventServiceHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

public class ClientIDL {
    private static final String HOST = "localhost";
    private static final String PORT = "3000";
    private static final String NAME = "EventService";

    // prints an event
    private static void printEvent(Event event) {
        System.out.println(MessageFormat.format("Event id: {0}, title: {1}, description: {2}, date: {3}",
                event.id, event.title, event.description, new Date(event.date).toGMTString()));
    }

    public static void main(String[] _args) throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound {
        String[] arguments = {"-ORBInitialPort", "" + PORT, "-ORBInitialHost", HOST};
        ORB orb = ORB.init(arguments, null);
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // service interface
        EventService proxy = EventServiceHelper.narrow(ncRef.resolve_str(NAME));
        System.out.println("Java IDL Client: " + HOST + ":" + PORT + "/" + NAME);

        // add an event
        proxy.add("New title", "Some description here", new Date().getTime());

        // filter events
        Event[] events = proxy.filter("script");
        Arrays.asList(events).forEach(ClientIDL::printEvent);

        // delete an event
        long id = events[0].id;
        proxy.delete(id);
    }
}
