/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.model;

import java.util.Enumeration;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import jeremiaMorling.utils.vector.ListVector;

/**
 *
 * @author Jeremia
 */
public class ContactListWrapper extends ListVector {
    private ContactList contactList;
    
    public ContactListWrapper() throws PIMException {
        contactList = (ContactList)PIM.getInstance().openPIMList( PIM.CONTACT_LIST, PIM.READ_ONLY );
        refresh();
    }
    
    public void refresh() throws PIMException {
        removeAllElements();
        
        Enumeration contacts = contactList.items();
        while( contacts.hasMoreElements() ) {
            Contact contact = (Contact)contacts.nextElement();
            addElement( new ContactWrapper( contact ) );
        }
    }
}