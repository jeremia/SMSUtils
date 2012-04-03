/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.sms.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import jeremiaMorling.utils.managers.DM;
import jeremiaMorling.utils.vector.IClonable;
import jeremiaMorling.utils.vector.IComparable;
import jeremiaMorling.utils.vector.ISerializable;

/**
 *
 * @author Jeremia
 */
public class SelectedPhoneNumber extends PhoneNumber implements ISerializable {
    private String phoneNumber;
    private String name;
    
    public SelectedPhoneNumber() {}
    
    public SelectedPhoneNumber( String phoneNumber ) {
        super( phoneNumber );
        this.phoneNumber = phoneNumber;
    }
    
    public SelectedPhoneNumber( String name, String phoneNumber, int attributes ) {
        super( calculateText( name, phoneNumber ), attributes );
        this.phoneNumber = phoneNumber;
        setName( name );
    }
    
    private static String calculateText( String name, String phoneNumber ) {
        if( name != null )
            return name;
        else
            return phoneNumber;
    }
    
    public void setName( String name ) {
        if( name == null || name.equals( "" ) )
            this.name = null;
        else {
            this.name = name;
            setText( name );
        }
    }
    
    public String getName() {
        if( name == null )
            return "";
        else
            return name;
    }
    
    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
        if( name == null || name.equals( "" ) )
            setText( phoneNumber );
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getText() {
        if( name != null )
            return name;
        else
            return phoneNumber;
    }

    public int compareTo( Object itemToCompare ) throws IllegalArgumentException {
        return -1;
    }

    public IClonable clone() {
        SelectedPhoneNumber clone = new SelectedPhoneNumber( name, phoneNumber, attributes );
        return clone;
    }

    public void toSerialFormat( DataOutputStream dataOutputStream ) {
        try {
            dataOutputStream.writeUTF( getPhoneNumber() );
            dataOutputStream.writeInt( getAttributes() );
            dataOutputStream.writeUTF( getName() );
        } catch( Exception e ) {
            DM.error( e, "SelectedPhoneNumber: toSerialFormat()" );
        }
    }

    public ISerializable fromSerialFormat( DataInputStream dataInputStream ) {
        try {
            setPhoneNumber( dataInputStream.readUTF() );
            setAttributes( dataInputStream.readInt() );
            setName( dataInputStream.readUTF() );
        } catch( Exception e ) {
            DM.error( e, "SelectedPhoneNumber: fromSerialFormat()" );
        }
        
        return this;
    }
    
    public boolean equals( Object o ) {
        if( !(o instanceof SelectedPhoneNumber) )
            return false;
        
        SelectedPhoneNumber phoneNumberToCompare = (SelectedPhoneNumber)o;
        if( getPhoneNumber().equals( phoneNumberToCompare.getPhoneNumber() ) )
            return true;
        else
            return false;
    }
}
