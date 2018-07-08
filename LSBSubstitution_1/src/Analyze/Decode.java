/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyze;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author tuans
 */
public class Decode {
    BufferedImage image;
    public Decode(){}
    public Decode(BufferedImage xImage){
        this.image=xImage;
    }
    public String getLSBits(int pixel_rgb) {
        Color c = new Color(pixel_rgb);
        String result = "";
        String redInB = Integer.toBinaryString(c.getRed());
        String greenInB = Integer.toBinaryString(c.getGreen());
        String blueInB = Integer.toBinaryString(c.getBlue());
        
        result += String.valueOf(redInB.charAt(redInB.length() - 1));
        result += String.valueOf(greenInB.charAt(greenInB.length() - 1));
        result += String.valueOf(blueInB.charAt(blueInB.length() - 1));
        return result;
    }
    //Construct character from its Binary String
    public char BinToChar(String bin_string) {
        byte r = 0;
        int n = bin_string.length();
        int i = 0;
        int pow = 1;
        for(char c : bin_string.toCharArray()) {
            if(c == '1') {
                i = 0;
                pow = 1;
                while(i < n - 1) {
                    pow *= 2;
                    i++;
                }
                r += pow;
            }
            n--;
        }
        return (char)r;
    }
    public String getTextDecoded(){
        //Integer lengthText=null;
        String text="";
        String lastText="";
        int count=0,textLength=-1;
        Boolean breakPoint=false;
        Boolean findStart=false;
        for (int x=0;x<image.getWidth();x++){
            for (int y=0;y<image.getHeight();y++){
                text+=getLSBits(image.getRGB(x, y));
                count+=3;
                textLength=count/8;
                if (text.length()>=9){
                    if (findStart==false){
                        String lastGroup=text.substring(text.length()-9);
                        if (lastGroup.equals("001111111")){
                            findStart=true;
                            text="";
                        }
                    }
                    else{
                        String lastGroup=text.substring(text.length()-9);
                        if (lastGroup.equals("001111111")){
                            breakPoint=true;
                            //text=lastText;
                            break;
                        }
                    }
                    
                }
                if (breakPoint) break;
//                if (count==9){
//                    textLength=Integer.parseInt(text.substring(0, 8),2)+1;
//                }
//                if (textLength>0 && count>textLength*8) break;
            }
            if (breakPoint) break;
            //if (textLength>0 && count>textLength) break;
        }
        System.out.println(text);
        String result="";
        textLength--;

        if (text.length()>=9){
            text=text.substring(0,text.length()-9);
            
            for (int i=0;i<text.length();i+=8){
                if (i>=text.length() || i+8>text.length()) break;
                result+=BinToChar(text.substring(i, i+8));
            }
        }
        if (result==""||findStart==false)
            result="<<No hidden message found>>";
        return result;
    }
}
