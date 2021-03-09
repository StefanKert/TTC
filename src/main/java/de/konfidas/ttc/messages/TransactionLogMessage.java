package de.konfidas.ttc.messages;

import de.konfidas.ttc.utilities.ByteArrayOutputStream;
import de.konfidas.ttc.exceptions.BadFormatForLogMessageException;
import de.konfidas.ttc.utilities.oid;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

public class TransactionLogMessage extends LogMessage {
    public TransactionLogMessage(byte[] content, String filename) throws BadFormatForLogMessageException {
        super(content, filename);
    }

    @Override
    void parseCertifiedDataType(ByteArrayOutputStream dtbsStream, Enumeration<ASN1Primitive> asn1Primitives) throws IOException, LogMessage.CertifiedDataTypeParsingException {
        super.parseCertifiedDataType(dtbsStream,asn1Primitives);
        if(this.certifiedDataType != oid.id_SE_API_transaction_log){
            throw new LogMessage.CertifiedDataTypeParsingException("Invalid Certified Data Type, expected id_SE_API_transaction_log but found "+this.certifiedDataType.getName(), null);
        }
    }

    @Override
    ASN1Primitive parseCertifiedData(ByteArrayOutputStream dtbsStream, Enumeration<ASN1Primitive> test) throws IOException {
        ASN1Primitive element;
        // Now, we will enter a while loop and collect all the certified data
        element = test.nextElement();
        while (!(element instanceof ASN1OctetString)) {
            // Then, the object identifier for the certified data type shall follow
            this.certifiedData.add(element);
            byte[] elementValue = Arrays.copyOfRange(element.getEncoded(), 2, element.getEncoded().length);
            dtbsStream.write(elementValue);

            element = test.nextElement();
        }
        return element;
    }
}
