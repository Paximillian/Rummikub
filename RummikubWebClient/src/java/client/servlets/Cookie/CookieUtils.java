/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.servlets.Cookie;

import javax.servlet.http.Cookie;

/**
 *
 * @author yafita870
 */
public class CookieUtils {
    
    public static String CookieMap(Cookie[] cookis ,String key )
    {
        for (Cookie cooki : cookis) {
            String str = cooki.getName();
            if(str.equals(key))
            {
                return cooki.getValue();
            }
        }
        
        return "error";
    }
}
