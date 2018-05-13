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
        String textHidden=padding(textLength)+MessageToBinary(text);
        while(textHidden.length()%3!=0){
            textHidden+="0";
        }
        return textHidden;
    }
    public BufferedImage getEmbedImage(String text){
        text=getTextHidden(text);
        char[] binHidden =text.toCharArray();
        int count=0;
        for (int x=0;x<image.getWidth();x++){
            for (int y=0;y<image.getHeight();y++){
                int rgb=image.getRGB(x, y);
                rgb = (binHidden[count++] == '0')?(rgb & 0xFFFEFFFF):(rgb | 0x00010000); //Replace LSB Red value
                rgb = (binHidden[count++] == '0')?(rgb & 0xFFFFFEFF):(rgb | 0x00000100); //Replace LSB Green value
                rgb = (binHidden[count++] == '0')?(rgb & 0xFFFFFFFE):(rgb | 0x00000001); //Replace LSB Blue value
                image.setRGB(x, y, rgb);
                if (count==binHidden.length) break;
            }
            if (count==binHidden.length) break;
        }
        return image;
    }
}
