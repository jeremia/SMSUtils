/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.gui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.pim.PIMException;
import jeremiaMorling.utils.managers.DM;
import jeremiaMorling.utils.sms.SMSLoc;
import jeremiaMorling.utils.vector.ComparableString;

/**
 *
 * @author Jeremia
 */
public class SearchContactGUI extends TextBox implements CommandListener {
    private ContactListGUI contactListGUI;
    
    private static final int MAX_SIZE = 100;
    
    public SearchContactGUI(
            SMSReceiverGUI phoneNumberReceiver ) throws PIMException {
        super( SMSLoc.getText( "searchContact" ), "", MAX_SIZE, TextField.ANY );
        
        contactListGUI = new ContactListGUI( phoneNumberReceiver );
        
        addCommand( new Command( SMSLoc.getText( "ok" ), Command.OK, 0 ) );
        addCommand( new Command( SMSLoc.getText( "back" ), Command.BACK, 1 ) );
        setCommandListener( this );
    }

    public void commandAction( Command c, Displayable d ) {
        switch( c.getCommandType() ) {
            case Command.OK:
                contactListGUI.findAndShow( new ComparableString( getString() ) );
                break;
            case Command.BACK:
                DM.back();
                break;
        }
    }
}
