package de.konfidas.ttc.messages.systemlogs;

import de.konfidas.ttc.exceptions.BadFormatForLogMessageException;
import de.konfidas.ttc.messages.SystemLogMessage;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;

import java.io.IOException;


/**
 * Diese Klasse repräsentiert eine deregisterClient Message. Dabei werden in der Methode
 * parseSystemOperationDataContent die folgenden Elemente aus systemOperationData geparst
 * // ╔═══════════════════════╤══════╤═══════════════════════════════════════════════════════════════╤════════════╗
 * // ║ Data field            │ Tag  │ Data Type                                                     │ Mandatory? ║
 * // ╠═══════════════════════╪══════╪═══════════════════════════════════════════════════════════════╪════════════╣
 * // ║ client                │ 0x81 │ PrintableString                                               │ m          ║
 * // ╚═══════════════════════╧══════╧════════════════════════════════════════════════════════════════════════════╝
 */
public class DeregisterClientLogMessage extends SystemLogMessage {
    ASN1Primitive clientId;



    public DeregisterClientLogMessage(byte[] content, String filename) throws BadFormatForLogMessageException {
        super(content, filename);
    }


    @Override
    protected void parseSystemOperationDataContent(ASN1InputStream stream) throws SystemLogParsingException, IOException {

        try{
            clientId = stream.readObject();
    }
        catch (Exception ex){
            throw new SystemLogParsingException("Fehler beim Parsen des systemOperationDataContent",ex);
        }
    }


}