/*
   Copyright (c) 2017 Ahome' Innovation Technologies. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.ait.lienzo.client.core.image.filter;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.shape.json.validators.ValidationContext;
import com.ait.lienzo.client.core.shape.json.validators.ValidationException;
import com.ait.lienzo.shared.core.types.ImageFilterType;
import com.google.gwt.json.client.JSONObject;

public abstract class AbstractValueImageDataFilter<T extends AbstractValueImageDataFilter<T>> extends AbstractImageDataFilter<T>
{
    public AbstractValueImageDataFilter(final ImageFilterType type, final double value)
    {
        super(type);

        setValue(value);
    }

    protected AbstractValueImageDataFilter(final ImageFilterType type, final JSONObject node, final ValidationContext ctx) throws ValidationException
    {
        super(type, node, ctx);
    }

    public final double getValue()
    {
        return Math.max(Math.min(getAttributes().getValue(), getMaxValue()), getMinValue());
    }

    public final T setValue(final double value)
    {
        getAttributes().setValue(Math.max(Math.min(value, getMaxValue()), getMinValue()));

        return cast();
    }

    public abstract double getMinValue();

    public abstract double getMaxValue();

    public abstract double getRefValue();

    protected static abstract class ValueImageDataFilterFactory<T extends AbstractValueImageDataFilter<T>> extends ImageDataFilterFactory<T>
    {
        protected ValueImageDataFilterFactory(final ImageFilterType type)
        {
            super(type);

            addAttribute(Attribute.VALUE, true);
        }
    }
}
