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
import jeremiaMorling.utils.managers.DM;
import jeremiaMorling.utils.sms.SMSLoc;
import jeremiaMorling.utils.sms.model.SelectedPhoneNumber;

/**
 *
 * @author Jeremia
 */
public class EnterPhoneNumberGUI extends TextBox implements CommandListener {
    private SMSReceiverGUI phoneNumberReceiver;
    
    private static final int MAX_LENGTH = 20;
    
    public EnterPhoneNumberGUI(
            SMSReceiverGUI phoneNumberReceiver ) {
        super( SMSLoc.getText( "enterPhoneNumber" ), "", MAX_LENGTH, TextField.PHONENUMBER );
        
        this.phoneNumberReceiver = phoneNumberReceiver;
        
        addCommand( new Command( SMSLoc.getText( "ok" ), Command.OK, 0 ) );
        addCommand( new Command( SMSLoc.getText( "back" ), Command.BACK, 1 ) );
        setCommandListener( this );
    }

    public void commandAction( Command c, Displayable d ) {
        switch( c.getCommandType() ) {
            case Command.OK:
                DM.pop( 2 );
                phoneNumberReceiver.phoneNumberSelected( new SelectedPhoneNumber( getString() ) );
                break;
            case Command.BACK:
                DM.back();
                break;
        }
    }
}
