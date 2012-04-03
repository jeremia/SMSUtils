/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.model;

import javax.microedition.lcdui.Image;
import javax.microedition.pim.Contact;
import jeremiaMorling.utils.managers.ResourceManager;
import jeremiaMorling.utils.vector.IClonable;
import jeremiaMorling.utils.vector.ListItem;

/**
 *
 * @author Jeremia
 */
public class PhoneNumber extends ListItem {
    //protected String phoneNumber;
    protected int attributes = NO_ATTRIBUTES_SET;
    
    private static Image mobile;
    private static Image mobilePrivate;
    private static Image mobileWork;
    private static Image home;
    private static Image work;
    private static Image fax;
    private static Image other;
    private static Image unknown;
    
    public static final int NO_ATTRIBUTES_SET = -1;
    
    public static void loadIcons() {
        mobile = ResourceManager.getImage( "/pictures/Mobile24.png" );
        mobilePrivate = ResourceManager.getImage( "/pictures/MobilePrivate24.png" );
        mobileWork = ResourceManager.getImage( "/pictures/MobileWork24.png" );
        home = ResourceManager.getImage( "/pictures/Home24.png" );
        work = ResourceManager.getImage( "/pictures/Work24.png" );
        fax = ResourceManager.getImage( "/pictures/Fax24.png" );
        other = ResourceManager.getImage( "/pictures/Other24.png" );
        unknown = ResourceManager.getImage( "/pictures/Unknown24.png" );
    }
    
    public static void unloadIcons() {
        mobile = null;
        mobilePrivate = null;
        mobileWork = null;
        home = null;
        work = null;
        fax = null;
        other = null;
        unknown = null;
    }
    
    protected PhoneNumber() {}
    
    protected PhoneNumber( String phoneNumber ) {
        super( phoneNumber );
    }
    
    protected PhoneNumber( String phoneNumber, int attributes ) {
        this( phoneNumber );
        setAttributes( attributes );
    }
    
    public PhoneNumber( Contact contact, int index ) {
        this( contact.getString( Contact.TEL, index ), contact.getAttributes( Contact.TEL, index ) );
    }
    
    public String getPhoneNumber() {
        return getText();
    }
    
    public int getAttributes() {
        return attributes;
    }
    
    public void setAttributes( int attributes ) {
        this.attributes = attributes;
        updateIcon();
    }

    private void updateIcon() {
        if( attributes == NO_ATTRIBUTES_SET ) {
            setIcon( unknown );
        } else if( hasAttribute( Contact.ATTR_MOBILE ) ) {
            if( hasAttribute( Contact.ATTR_HOME ) ) {
                setIcon( mobilePrivate );
            } else if( hasAttribute( Contact.ATTR_WORK ) ) {
                setIcon( mobileWork );
            } else {
                setIcon( mobile );
            }
        } else if( hasAttribute( Contact.ATTR_HOME ) ) {
            setIcon( home );
        } else if( hasAttribute( Contact.ATTR_WORK ) ) {
            setIcon( work );
        } else if( hasAttribute( Contact.ATTR_FAX ) ) {
            setIcon( fax );
        } else {
            setIcon( other );
        }
    }
    
    private boolean hasAttribute( int attribute ) {
        return ((attributes & attribute) != 0);
    }

    public IClonable clone() {
        return new PhoneNumber( getPhoneNumber(), attributes );
    }
    
    public boolean isPreferredNumber() {
        return hasAttribute( Contact.ATTR_PREFERRED );
    }
}
