/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.gui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import jeremiaMorling.utils.displayUtils.displays.SortableList;
import jeremiaMorling.utils.managers.DM;
import jeremiaMorling.utils.sms.SMSLoc;
import jeremiaMorling.utils.sms.model.ContactWrapper;
import jeremiaMorling.utils.sms.model.PhoneNumber;
import jeremiaMorling.utils.sms.model.SelectedPhoneNumber;

/**
 *
 * @author Jeremia
 */
public class SelectPhoneNumberGUI extends SortableList implements CommandListener {
    private ContactWrapper contact;
    private SMSReceiverGUI phoneNumberReceiver;
    
    public SelectPhoneNumberGUI(
            ContactWrapper contact,
            SMSReceiverGUI phoneNumberReceiver ) {
        super( contact.getName(), List.IMPLICIT );
        this.contact = contact;
        this.phoneNumberReceiver = phoneNumberReceiver;
        
        setItemsAndRefresh( contact.getPhoneNumbers() );
        
        setSelectCommand( new Command( SMSLoc.getText( "select" ), Command.OK, 0 ) );
        addCommand( new Command( SMSLoc.getText( "back" ), Command.BACK, 1 ) );
        setCommandListener( this );
    }

    public void commandAction( Command c, Displayable d ) {
        switch( c.getCommandType() ) {
            case Command.OK:
                DM.pop( 4 );
                PhoneNumber selectedPhoneNumber = (PhoneNumber)getSelectedItem();
                phoneNumberReceiver.phoneNumberSelected( new SelectedPhoneNumber(
                        contact.getName(),
                        selectedPhoneNumber.getPhoneNumber(),
                        selectedPhoneNumber.getAttributes() ) );
                break;
            case Command.BACK:
                DM.back();
                break;
        }
    }
}
