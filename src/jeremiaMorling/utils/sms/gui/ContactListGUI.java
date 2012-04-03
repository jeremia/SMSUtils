/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.gui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.pim.PIMException;
import jeremiaMorling.utils.displayUtils.displays.AlertDisplay;
import jeremiaMorling.utils.displayUtils.displays.SortableList;
import jeremiaMorling.utils.managers.DM;
import jeremiaMorling.utils.managers.ResourceManager;
import jeremiaMorling.utils.sms.SMSLoc;
import jeremiaMorling.utils.sms.model.ContactListWrapper;
import jeremiaMorling.utils.sms.model.ContactWrapper;
import jeremiaMorling.utils.vector.IComparable;

/**
 *
 * @author Jeremia
 */
public class ContactListGUI extends SortableList implements CommandListener, Runnable {
    private SMSReceiverGUI phoneNumberReceiver;
    
    private Thread contactLoadingThread;
    private boolean isContactsLoading = false;
    private boolean waitingForContactsLoading = false;
    private IComparable textToFind;
    
    public ContactListGUI( SMSReceiverGUI phoneNumberReceiver ) throws PIMException {
        super( SMSLoc.getText( "contacts" ), IMPLICIT );
        this.phoneNumberReceiver = phoneNumberReceiver;
        
        setSelectCommand( new Command( SMSLoc.getText( "select" ), Command.OK, 0 ) );
        addCommand( new Command( SMSLoc.getText( "back" ), Command.BACK, 1 ) );
        setCommandListener( this );
        
        contactLoadingThread = new Thread( this );
        contactLoadingThread.start();
    }
    
    public void run() {
        try {
            isContactsLoading = true;
            ContactListWrapper contactListWrapper = new ContactListWrapper();
            setItems( contactListWrapper );
            refresh();
            isContactsLoading = false;
            
            if( waitingForContactsLoading ) {
                super.find( textToFind );
                DM.add( this, true );
                waitingForContactsLoading = false;
            }
            //sort();
        } catch( PIMException ex ) {
            DM.error( ex, "ContactListGUI: run()" );
        }
    }
    
    public void findAndShow( IComparable textToFind ) {
        if( isContactsLoading ) {
            this.textToFind = textToFind;
            waitingForContactsLoading = true;
            DM.add( new AlertDisplay(
                        SMSLoc.getText( "waitForContactsToLoad.title" ),
                        SMSLoc.getText( "waitForContactsToLoad.text" ),
                        ResourceManager.getImage( "/pictures/Wait64.png" ) ) );
        } else {
            super.find( textToFind );
            DM.add( this );
        }
    }

    public void commandAction( Command c, Displayable d ) {
        switch( c.getCommandType() ) {
            case Command.OK:
                DM.add(
                    new SelectPhoneNumberGUI(
                        (ContactWrapper)getSelectedItem(),
                        phoneNumberReceiver
                    )
                );
                break;
            case Command.BACK:
                DM.back();
                break;
        }
    }
}
