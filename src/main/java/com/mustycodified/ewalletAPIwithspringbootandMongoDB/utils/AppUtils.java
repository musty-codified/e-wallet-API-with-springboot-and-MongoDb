package com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AppUtils {
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    public String getFormattedNumber(final String number){
        String trimmedNumber = number.trim();
        String formattedNumber = null;
        if(trimmedNumber.startsWith("0"))
            formattedNumber = "+234" + trimmedNumber.substring(1);
        else if(trimmedNumber.startsWith("234"))
            formattedNumber = "+" + number;
        else if (!number.startsWith("+")
                && Integer.parseInt(String.valueOf(number.charAt(0))) > 0) {
            formattedNumber = "+234" + number;
        }
        return formattedNumber;
    }

    public String getString( Object o){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(o);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;

        }
    }
    private String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i=0; i<length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public  String generateSerialNumber(String prefix) {
        Random rand = new Random();
        long x = (long)(rand.nextDouble()*100000000000000L);
        return  prefix + String.format("%014d", x);
    }

    public boolean validImage(String fileName)
    {
        String regex = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
        Pattern p = Pattern.compile(regex);
        if (fileName == null) {
            return false;
        }
        Matcher m = p.matcher(fileName);
        return m.matches();
    }

    public boolean validEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public Long generateOTP(){
        Random rnd = new Random();
        Long number = (long) rnd.nextInt(999999);
        return  number;
    }

    public  Object getObject(String content, Class cls){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content,cls);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public ObjectMapper getMapper(){
        ObjectMapper mapper= new ObjectMapper();
        return mapper;
    }


    public void isEmailDomainValid(String email) {
        try {
            String domain = email.substring(email.lastIndexOf('@') + 1);
            int result = doDNSLookup(domain);
            LOGGER.info("Domain: " + domain);
            LOGGER.info("Result of domain: " + result);
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public int doDNSLookup(String hostName) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext( env );
        Attributes attrs = ictx.getAttributes( hostName, new String[] { "MX" });
        Attribute attr = attrs.get( "MX" );
        if( attr == null ) return( 0 );
        return( attr.size() );
    }
}
