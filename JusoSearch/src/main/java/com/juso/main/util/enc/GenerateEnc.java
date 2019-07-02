package com.juso.main.util.enc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateEnc {
	public static void main(String[] args) {
//		generateEnc("qewr111");
	}

	public static String getEnc(String resource) {
        String encStr = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
            sh.update(resource.getBytes()); 
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer(); 
            
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            encStr = sb.toString();
            
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace(); 
            encStr = null; 
        }
//        System.out.println("암호환 된 문장 : "+encStr);
        return encStr;
	}
	
	
}
