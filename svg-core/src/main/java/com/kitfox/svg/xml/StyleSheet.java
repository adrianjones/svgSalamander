/*
 * SVG Salamander
 * Copyright (c) 2004, Mark McKay
 * Copyright (c) 2017, Adrian Jones
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 *   - Redistributions of source code must retain the above 
 *     copyright notice, this list of conditions and the following
 *     disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials 
 *     provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 * 
 * Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 * projects can be found at http://www.kitfox.com
 *
 * StyleSheet code implemented by Adrian Jones
 */
package com.kitfox.svg.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * SVG StyleSheet
 */
public class StyleSheet {
    private static final String STYLE_NAME = "stylesheet";
    
	private final Map<StyleSheetRule,Map<String,String>> ruleMap = new HashMap<>();

	/**
	 * Parses a string to a {@link StyleSheet}
	 * @param srcString - source content as a string
	 * @return {@link StyleSheet}
	 */
    public static StyleSheet parseSheet( String srcString) {
    	final StyleSheet sheet = new StyleSheet();
    	srcString = srcString.replace( "\n", ""); 				// remove new line characters
    	
    	String[] stylesSections = srcString.split( "\\}"); 		// split the sections 
    	for (String styleSection : stylesSections) {
    		String[] styleItems = styleSection.split( "\\{");	// split key / values
    		if ( styleItems.length == 2) {
    			String key = styleItems[0];
				StyleSheetRule rule = (key.startsWith( ".") ? new StyleSheetRule( STYLE_NAME, null, key.substring(1)) :
															  new StyleSheetRule( STYLE_NAME, key, null));
    			String values = styleItems[1];
				Map<String,String> map = new HashMap<>();
				for (String value : values.split(";")) { 		// split each value
					String[] valuepair = value.split( ":");		// finally split style / value
					map.put(valuepair[0], valuepair[1]);
				}
				sheet.addStyleRule( rule, map);
    		}
		}
        return sheet;
    }
    
    public static <T extends StyleSheet> T cloneSheet( StyleSheet source, T destination) {
    	for (Entry<StyleSheetRule, Map<String, String>> entry : source.getStyles().entrySet()) {
        	destination.addStyleRule(entry.getKey(), entry.getValue());
		}
    	return destination;
    }

    public final void addStyleRule(StyleSheetRule rule, Map<String,String> values) {
        ruleMap.put(rule, values);
    }
    
    public final Map<StyleSheetRule, Map<String, String>> getStyles() {
    	return ruleMap;
    }
        
    /**
     * Copies the current style into the passed style attribute. 
     *
     * @param attrib - Attribute to write style data to. Must have it's name set
     * to the name of the style being queried.
     * @param tagName - tag name for the element style
     * @param cssClass - css class name for the element style
     * @return <i>true</i> if the style was applied to the style attribute.
     */
    public final boolean getStyle( StyleAttribute attrib, String tagName, String cssClass) {
        final String attribName = attrib.getName();

        String value = getValue( attribName, tagName, cssClass);		//Try with tag an class name
        
        if ( value == null) {	    
            value = getValue( attribName, null, cssClass);				//Try again using just class name
        }
        
        if ( value == null) {
            value = getValue( attribName, tagName, null);				//Try again using just tag name
        }
                
        if ( value != null) {
            attrib.setStringValue(value);
            return true;
        }
        
        return false;
    }
    
    /**
     * Finds and returns the style value.
     * @param attribName - attribute name.
     * @param tagName - tag name for the element style
     * @param cssClass - css class name for the element style
     * @return value or <i>null</i>
     */
    private final String getValue( String attribName, String tagName, String cssClass) {
        StyleSheetRule rule = new StyleSheetRule( STYLE_NAME, tagName, cssClass);
        Map<String,String> values = ruleMap.get(rule);
		if (values != null) {
        	return values.get( attribName);
        }
		return null;
    }
}
