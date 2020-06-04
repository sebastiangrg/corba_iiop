package com.wsmt.server;

import com.wsmt.generated.EventService;
import com.wsmt.generated.EventServicePOATie;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA_2_3.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

public class Server {
    private static final String HOST = "localhost";
    private static final String PORT = "3000";
    private static final String NAME = "EventService";

    public static void main(String[] _args) throws InvalidName, AdapterInactive, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed {
        String[] arguments = {"-ORBInitialHost", HOST, "-ORBInitialPort", PORT};
        ORB orb = (ORB) ORB.init(arguments, null);
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();

        // service implementation
        EventServiceImpl eventServiceImpl = new EventServiceImpl();
        EventServicePOATie tie = new EventServicePOATie(eventServiceImpl, rootPOA);
        EventService href = tie._this(orb);

        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        NameComponent[] path = ncRef.to_name(NAME);
        ncRef.rebind(path, href);
        System.out.println("Java IDL waiting: " + HOST + ":" + PORT + "/" + NAME);
        orb.run();
    }
}
