/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.model;

import javax.microedition.pim.Contact;
import jeremiaMorling.utils.vector.ListVector;
import jeremiaMorling.utils.pim.PIMWrapper;

/**
 *
 * @author Jeremia
 */
public class ContactWrapper extends PIMWrapper {
    public ContactWrapper( Contact contact ) {
        super( contact );
        setText( getNameFromContact() );
    }
    
    private String getNameFromContact() {
        String[] names = getStringArray( Contact.NAME );
        if( names == null || names.length == 0 )
            return "";
        
        StringBuffer name = new StringBuffer();
        if( names.length >= 2 && names[Contact.NAME_GIVEN] != null ) {
            name.append( names[Contact.NAME_GIVEN] );
        }
        if( names[Contact.NAME_FAMILY] != null ) {
            if( name.length() > 0 )
                name.append( " " );
            name.append( names[Contact.NAME_FAMILY] );
        }
        
        return name.toString();
    }
    
    public String getName() {
        return getText();
    }
    
    public ListVector getPhoneNumbers() {
        int nrOfPhoneNumbers = pimItem.countValues( Contact.TEL );
        ListVector phoneNumbers = new ListVector( nrOfPhoneNumbers );
        for( int i=0; i<nrOfPhoneNumbers; i++ ) {
            PhoneNumber phoneNumberToAdd = new PhoneNumber( (Contact)pimItem, i );
            if( phoneNumberToAdd.isPreferredNumber() )
                phoneNumbers.insertElementAt( phoneNumberToAdd, 0 );
            else
                phoneNumbers.addElement( phoneNumberToAdd );
        }
        
        return phoneNumbers;
    }
}
