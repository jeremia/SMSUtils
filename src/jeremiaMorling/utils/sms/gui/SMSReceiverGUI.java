/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jeremiaMorling.utils.sms.gui;

import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordStoreException;
import jeremiaMorling.utils.vector.ListItem;
import jeremiaMorling.utils.displayUtils.displays.SortableList;
import jeremiaMorling.utils.managers.DM;
import jeremiaMorling.utils.sms.SMSLoc;
import jeremiaMorling.utils.sms.model.PhoneNumber;
import jeremiaMorling.utils.sms.model.SMSReceiverList;
import jeremiaMorling.utils.sms.model.SelectedPhoneNumber;

/**
 * 
 *
 * @author Jeremia Mörling
 */
public abstract class SMSReceiverGUI extends SortableList implements CommandListener {
    private static final int ENTER_PHONE_NUMBER_INDEX = 0;
    private static final int CONTACTS_LOOK_UP_INDEX = 1;
    
    private static final int MAX_HISTORY_SIZE = 15;
    
    public SMSReceiverGUI() throws RecordStoreException, IOException {
        super( SMSLoc.getText( "receiver" ), List.IMPLICIT );

        appendTopItem( new ListItem( SMSLoc.getText( "enterPhoneNumber" ) ) );
        appendTopItem( new ListItem( SMSLoc.getText( "contactsLookUp" ) ) );
        
        setSelectedIndex( CONTACTS_LOOK_UP_INDEX, true );
        
        try {
            setItemsAndRefresh( SMSReceiverList.loadPersisted() );
        } catch( Exception e ) {
            DM.error( e, "SMSReceiverGUI: SMSReceiverGUI(), setItems" );
        }
        
        setSelectCommand( new Command( SMSLoc.getText( "select" ), Command.OK, 0 ) );
        addCommand( new Command( SMSLoc.getText( "cancel" ), Command.CANCEL, 1 ) );
        setCommandListener( this );
    }

    private void phoneNumberFromHistory() {
        DM.pop();
        SelectedPhoneNumber receiver = (SelectedPhoneNumber) getSelectedItem();
        phoneNumberSelected( receiver );
    }

    public void commandAction( Command c, Displayable d ) {
        try {
            switch( c.getCommandType() ) {
                case Command.OK:
                    switch( getSelectedIndex() ) {
                        case ENTER_PHONE_NUMBER_INDEX:
                            DM.add( new EnterPhoneNumberGUI( this ) );
                            break;
                        case CONTACTS_LOOK_UP_INDEX:
                            DM.add( new SearchContactGUI( this ) );
                            break;
                        default:
                            phoneNumberFromHistory();
                    }
                    break;
                case Command.CANCEL:
                    DM.back();
                    break;
            }
        } catch( Exception e ) {
            DM.error( e, "SMSReceiverGUI: commandAction()" );
        }
    }

    public void phoneNumberSelected( SelectedPhoneNumber selectedPhoneNumber ) {
        sendSMS( selectedPhoneNumber.getPhoneNumber() );
        saveSMSReceiverHistory( selectedPhoneNumber );
        PhoneNumber.unloadIcons();
    }
    
    private void saveSMSReceiverHistory( SelectedPhoneNumber selectedPhoneNumber ) {
        try {
            SMSReceiverList smsReceiverHistory = (SMSReceiverList)getItems();
            smsReceiverHistory.removeElement( selectedPhoneNumber );
            if( smsReceiverHistory.size() == MAX_HISTORY_SIZE )
                smsReceiverHistory.removeLastElement();
            smsReceiverHistory.insertElementFirst( selectedPhoneNumber );
            smsReceiverHistory.persist();
        } catch( Exception e ) {
            DM.error( e, "SMSReceiverGUI: saveSMSReceiverHistory()" );
        }
    }
    
    protected abstract void sendSMS( String phoneNumber );
}
