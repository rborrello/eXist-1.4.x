/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2008-2009 The eXist Project
 *  http://exist-db.org
 *  
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 *  $Id$
 */
package org.exist.xslt.expression;

import org.exist.interpreter.ContextAtExist;
import org.exist.xquery.XPathException;
import org.exist.xquery.util.ExpressionDumper;
import org.exist.xquery.value.Item;
import org.exist.xquery.value.Sequence;
import org.exist.xslt.XSLContext;
import org.w3c.dom.Attr;

/**
 * <!-- Category: instruction -->
 * <xsl:output-character
 *   character = char
 *   string = string />
 * 
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 *
 */
public class OutputCharacter extends SimpleConstructor {
	
	private String character = null;
	private String string = null;
	
	public OutputCharacter(XSLContext context) {
		super(context);
	}
	
	public void setToDefaults() {
		character = null;
		string = null;
	}

	public void prepareAttribute(ContextAtExist context, Attr attr) throws XPathException {
		String attr_name = attr.getNodeName();
		if (attr_name.equals(CHARACTER)) {
			character = attr.getValue();
		} else if (attr_name.equals(STRING)) {
			string = attr.getValue();
		}
	}
	
    public Sequence eval(Sequence contextSequence, Item contextItem) throws XPathException {
    	throw new RuntimeException("eval(Sequence contextSequence, Item contextItem) at "+this.getClass());
	}

	/* (non-Javadoc)
     * @see org.exist.xquery.Expression#dump(org.exist.xquery.util.ExpressionDumper)
     */
    public void dump(ExpressionDumper dumper) {
        dumper.display("<xsl:output-character");
        
        if (character != null)
        	dumper.display(" character = "+character.toString());
        if (string != null)
        	dumper.display(" string = "+string.toString());

        dumper.display("> ");

        super.dump(dumper);

        dumper.display("</xsl:output-character>");
    }
    
    public String toString() {
    	StringBuffer result = new StringBuffer();
    	result.append("<xsl:output-character");
        
    	if (character != null)
        	result.append(" character = "+character.toString());    
    	if (string != null)
        	result.append(" string = "+string.toString());    

        result.append("> ");    

        result.append(super.toString());    

        result.append("</xsl:output-character>");
        return result.toString();
    }    
}
