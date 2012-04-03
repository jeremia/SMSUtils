package jeremiaMorling.utils.sms;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import jeremiaMorling.utils.managers.Loc;

/**
 * @author Jeremia
 */
public class SMSLoc extends Loc {
    private static SMSLoc instance;
    
    private static SMSLoc getInstance() {
        if( instance == null )
            instance = new SMSLoc();
        
        return instance;
    }
    
    private SMSLoc() {
        super( "/jeremiaMorling/utils/sms/messages.properties" );
    }
    
    public static String getText( String key ) {
        return getInstance().internalGetText( key );
    }
}