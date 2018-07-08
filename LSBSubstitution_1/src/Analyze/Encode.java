/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyze;

import java.awt.image.BufferedImage;

/**
 *
 * @author tuans
 */
public class Encode {
    BufferedImage image;
    public Encode(){
    }
    public Encode(BufferedImage xImage){
        this.image=xImage;
    }
    public String padding(int b) {
        String bin = Integer.toBinaryString(b); 
        while( bin.length() < 8 ) {
            bin = "0" + bin;
        }
        return bin;
    }
    public String MessageToBinary(String msg) {
        String result = "";
        
        for(char c : msg.toCharArray()) {
            result += padding(c);
        }
        //Padding secret to its length is common multiple by 3
        while(result.length() % 3 != 0)
        {
            result += "0";
        }
        return result;
    }
    public String getTextHidden(String text){
        int textLength=text.length();
        //String textHidden=padding(textLength)+MessageToBinary(text);
        String textHidden=MessageToBinary(text);
        textHidden="001111111"+textHidden;
        textHidden+="001111111";
        
        while(textHidden.length()%3!=0){
            textHidden+="0";
        }
        
        return textHidden;
    }
    public BufferedImage getEmbedImage(String text){
        text=getTextHidden(text);
        System.out.println(text);
        char[] binHidden =text.toCharArray();
        int count=0,x=0,y=0;
        int ok=0;
        for (x=0;x<image.getWidth();x++){
            for (y=0;y<image.getHeight();y++){
                int rgb=image.getRGB(x, y);
                if(ok==0){
                    System.out.println(Integer.toBinaryString(rgb));
                    ok=1;
                }
                rgb = (binHidden[count++] == '0')?(rgb & 0xFFFEFFFF):(rgb | 0x00010000); //Replace LSB Red value
                rgb = (binHidden[count++] == '0')?(rgb & 0xFFFFFEFF):(rgb | 0x00000100); //Replace LSB Green value
                rgb = (binHidden[count++] == '0')?(rgb & 0xFFFFFFFE):(rgb | 0x00000001); //Replace LSB Blue value
                image.setRGB(x, y, rgb);
                if(ok==1){
                    System.out.println(Integer.toBinaryString(rgb));
                    ok=2;
                }
                if (count==binHidden.length) break;
            }
            if (count==binHidden.length) break;
        }
        return image;
    }
}
