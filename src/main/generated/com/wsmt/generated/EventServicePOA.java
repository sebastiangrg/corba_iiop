package com.wsmt.generated;


/**
* com/wsmt/generated/EventServicePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from EventService.idl
* Thursday, June 4, 2020 7:18:18 PM EEST
*/

public abstract class EventServicePOA extends org.omg.PortableServer.Servant
 implements com.wsmt.generated.EventServiceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("add", new java.lang.Integer (0));
    _methods.put ("delete", new java.lang.Integer (1));
    _methods.put ("update", new java.lang.Integer (2));
    _methods.put ("getAll", new java.lang.Integer (3));
    _methods.put ("filter", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Agenda/EventService/add
       {
         String title = in.read_string ();
         String description = in.read_string ();
         long date = in.read_longlong ();
         this.add (title, description, date);
         out = $rh.createReply();
         break;
       }

       case 1:  // Agenda/EventService/delete
       {
         long id = in.read_longlong ();
         this.delete (id);
         out = $rh.createReply();
         break;
       }

       case 2:  // Agenda/EventService/update
       {
         long id = in.read_longlong ();
         String title = in.read_string ();
         String description = in.read_string ();
         long date = in.read_longlong ();
         this.update (id, title, description, date);
         out = $rh.createReply();
         break;
       }

       case 3:  // Agenda/EventService/getAll
       {
         com.wsmt.generated.Event $result[] = null;
         $result = this.getAll ();
         out = $rh.createReply();
         com.wsmt.generated.EventListHelper.write (out, $result);
         break;
       }

       case 4:  // Agenda/EventService/filter
       {
         String filter = in.read_string ();
         com.wsmt.generated.Event $result[] = null;
         $result = this.filter (filter);
         out = $rh.createReply();
         com.wsmt.generated.EventListHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Agenda/EventService:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public EventService _this() 
  {
    return EventServiceHelper.narrow(
    super._this_object());
  }

  public EventService _this(org.omg.CORBA.ORB orb) 
  {
    return EventServiceHelper.narrow(
    super._this_object(orb));
  }


} // class EventServicePOA
