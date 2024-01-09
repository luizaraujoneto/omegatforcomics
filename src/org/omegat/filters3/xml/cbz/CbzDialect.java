/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2009 Alex Buloichik

               Home page: https://www.omegat.org/
               Support center: https://omegat.org/support

 This file is part of OmegaT.

 OmegaT is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OmegaT is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>.
 **************************************************************************/

package org.omegat.filters3.xml.cbz;

import java.util.regex.Pattern;

//import org.omegat.filters3.Attribute;
import org.omegat.filters3.Attributes;
import org.omegat.filters3.xml.DefaultXMLDialect;

/**
 * XML dialect declaration for CBZ filter.
 *
 * @author Luiz Araujo (luizaraujoneto@gmail.com)
 */
public class CbzDialect extends DefaultXMLDialect {
    public static final Pattern ROOT_PATTERN = Pattern.compile("Page");

    public CbzDialect() {
        defineConstraint(CONSTRAINT_ROOT, ROOT_PATTERN);
        defineIntactTags(new String[] { "Order", "Position" });
        defineParagraphTags(new String[] { "Content" });
    }

    @Override
    public Boolean validateIntactTag(String tag, Attributes atts) {
//        if (atts != null) {
//            for (int i = 0; i < atts.size(); i++) {
//                Attribute oneAttribute = atts.get(i);
//                if (oneAttribute.getName().equalsIgnoreCase("translatable")) {
//                    return "false".equalsIgnoreCase(oneAttribute.getValue());
//                } else if (oneAttribute.getName().equalsIgnoreCase("translate")) {
//                    return "false".equalsIgnoreCase(oneAttribute.getValue());
//                }
//            }
//        }
        return false;
    }
}
