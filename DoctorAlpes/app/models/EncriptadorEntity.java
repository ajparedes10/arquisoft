package models;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Entity;
import com.avaje.ebean.Model;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by cc.novoa11 on 07/04/2017.
 */

@Entity
public class EncriptadorEntity extends Model {
    public static final String ALGORITMO ="MD5";

    private String mensajeCode;

    private byte[] hashMensaje;

    private String mensajeDecode;

    public EncriptadorEntity()
    {
        this.mensajeCode ="NO NAME";
    }

    public EncriptadorEntity(String codificado, byte[] hashMensaje)
    {
        setMensajeCodificado(codificado);
        setHashMensaje(hashMensaje);
    }
    public EncriptadorEntity(String decodificado)
    {
        setMensajeDesencriptado(decodificado);
        mensajeCode=encriptar(decodificado);
        hashMensaje=getHashMensaje(mensajeCode);
    }

    public String getMensajeCodificado()
    {

        return mensajeCode;
    }

    public void setMensajeCodificado(String codificado)
    {
        this.mensajeCode = codificado;
        mensajeDecode= desencriptar(codificado);
    }

    public String encriptar(String texto)
    {

        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";

        try
        {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return base64EncryptedString;
    }

    public String desencriptar(String textoEncriptado){

        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64EncryptedString;
    }


    public boolean validar()
    {
        return true; //new String(getHashMensaje(mensajeCodificado)).equals(new String(hashMensaje));
    }

    public byte[] getHashMensaje(String mensaje)
    {
        try
        {
            byte[] buffer= mensaje.getBytes();
            MessageDigest md5 = MessageDigest.getInstance(ALGORITMO);
            md5.update(buffer);
            return md5.digest();
        }
        catch (Exception e)
        {
            return null;
        }
    }


    public byte[] getHashMensaje()
    {
        return hashMensaje;
    }

    public void setHashMensaje(byte[] hashMensaje)
    {
        this.hashMensaje = hashMensaje;
    }

    public String getMensajeDesencriptado()
    {
        return mensajeDecode;
    }

    public void setMensajeDesencriptado(String mensajeDesencriptado)
    {
        this.mensajeDecode = mensajeDesencriptado;
    }
}
