/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.model;

import java.io.IOException;
import javax.microedition.rms.RecordStoreException;
import jeremiaMorling.utils.managers.DM;
import jeremiaMorling.utils.vector.ListVector;

/**
 *
 * @author Jeremia
 */
public class SMSReceiverList extends ListVector {
    private static final String SMS_RECEIVER_HISTORY = "smsReceiverHistory";
    
    public void addElement( Object o ) {
        if( !(o instanceof SelectedPhoneNumber) )
            throw new IllegalArgumentException( "o has to be of type SelectedPhoneNumber, but was of type " + o.getClass().getName() );
        
        super.addElement( o );
    }
    
    public SelectedPhoneNumber getSelectedPhoneNumber( int index ) {
        return (SelectedPhoneNumber)elementAt( index );
    }
    
    public void persist() throws RecordStoreException, IOException {
        persist( SMS_RECEIVER_HISTORY );
    }
    
    public static SMSReceiverList loadPersisted() throws RecordStoreException, IOException, InstantiationException, IllegalAccessException {
        SMSReceiverList result = new SMSReceiverList();
        result.loadPersisted( SMS_RECEIVER_HISTORY, SelectedPhoneNumber.class );
        return result;
    }
}
