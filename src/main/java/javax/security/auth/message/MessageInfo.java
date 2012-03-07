package javax.security.auth.message;

import java.util.Map;

//$Id$

/**
 *  A message processing runtime uses this interface to pass messages and 
 *  message processing state to authentication contexts for processing by 
 *  authentication modules. 
 *  
 *  This interface encapsulates a request message object and a response 
 *  message object for a message exchange.
 *  
 *  This interface may also be used to associate additional context in the 
 *  form of key/value pairs, with the encapsulated messages. Every implementation 
 *  of this interface should provide a zero argument constructor, and a constructor 
 *  which takes a single Map argument. Additional constructors may also be provided. 
 *  @author Anil.Saldhana@redhat.com
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  Jul 10, 2007 
 *  @version $Revision$
 */
public interface MessageInfo
{
   /**
    * Get (a reference to) the Map object of this MessageInfo. Operations performed 
    * on the acquired Map must effect the Map within the MessageInfo.
    * @return the Map object of this MessageInfo. This method never returns null. 
    * If a Map has not been associated with the MessageInfo, this method instantiates 
    * a Map, associates it with this MessageInfo, and then returns it.
    */
   Map getMap();
   
   /**
    * Get the request message object from this MessageInfo.
    * @return An object representing the request message, or null if no request message 
    * is set within the MessageInfo.
    */
   Object getRequestMessage();
   
   /**
    * Get the response message object from this MessageInfo.
    * @return an object representing the response message, or null if no response message 
    * is set within the MessageInfo.
    */
   Object getResponseMessage();
   
   /**
    * Set the request message object in this MessageInfo.
    * @param request An object representing the request message.
    */
   void setRequestMessage(Object request);
   
   /**
    * Set the response message object in this MessageInfo.
    * @param response An object representing the response message.
    */
   void setResponseMessage(Object response);
}
