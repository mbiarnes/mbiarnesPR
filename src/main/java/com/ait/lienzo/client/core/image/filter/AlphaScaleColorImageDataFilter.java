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
import com.ait.lienzo.client.core.shape.json.IFactory;
import com.ait.lienzo.client.core.shape.json.validators.ValidationContext;
import com.ait.lienzo.client.core.shape.json.validators.ValidationException;
import com.ait.lienzo.client.core.types.ImageData;
import com.ait.lienzo.shared.core.types.IColor;
import com.ait.lienzo.shared.core.types.ImageFilterType;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class AlphaScaleColorImageDataFilter extends AbstractRGBImageDataFilter<AlphaScaleColorImageDataFilter>
{
    public AlphaScaleColorImageDataFilter(int r, int g, int b)
    {
        super(ImageFilterType.AlphaScaleColorImageDataFilterType, r, g, b);
    }

    public AlphaScaleColorImageDataFilter(int r, int g, int b, boolean invert)
    {
        super(ImageFilterType.AlphaScaleColorImageDataFilterType, r, g, b);

        setInverted(invert);
    }

    public AlphaScaleColorImageDataFilter(IColor color)
    {
        super(ImageFilterType.AlphaScaleColorImageDataFilterType, color);
    }

    public AlphaScaleColorImageDataFilter(IColor color, boolean invert)
    {
        super(ImageFilterType.AlphaScaleColorImageDataFilterType, color);

        setInverted(invert);
    }

    public AlphaScaleColorImageDataFilter(String color)
    {
        super(ImageFilterType.AlphaScaleColorImageDataFilterType, color);
    }

    public AlphaScaleColorImageDataFilter(String color, boolean invert)
    {
        super(ImageFilterType.AlphaScaleColorImageDataFilterType, color);

        setInverted(invert);
    }

    protected AlphaScaleColorImageDataFilter(JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(ImageFilterType.AlphaScaleColorImageDataFilterType, node, ctx);
    }

    public AlphaScaleColorImageDataFilter setInverted(boolean inverted)
    {
        getAttributes().setInverted(inverted);

        return this;
    }

    public boolean isInverted()
    {
        return getAttributes().isInverted();
    }

    @Override
    public ImageData filter(ImageData source, boolean copy)
    {
        if (null == source)
        {
            return null;
        }
        if (copy)
        {
            source = source.copy();
        }
        if (false == isActive())
        {
            return source;
        }
        final CanvasPixelArray data = source.getData();

        if (null == data)
        {
            return source;
        }
        filter_(data, FilterCommonOps.getLength(source), getR(), getG(), getB(), isInverted());

        return source;
    }

    private final native void filter_(JavaScriptObject data, int length, int r, int g, int b, boolean invert)
    /*-{
        for (var i = 0; i < length; i += 4) {
            var v = ((data[i] * 0.21) + (data[i + 1] * 0.72) + (data[i + 2] * 0.07));
            data[  i  ] = r;
            data[i + 1] = g;
            data[i + 2] = b;
            if (true == invert) {
                data[i + 3] = (v + 0.5) | 0;
            } else {
                data[i + 3] = 255 - ((v + 0.5) | 0);
            }
        }
    }-*/;

    @Override
    public IFactory<AlphaScaleColorImageDataFilter> getFactory()
    {
        return new AlphaScaleColorImageDataFilterFactory();
    }

    public static class AlphaScaleColorImageDataFilterFactory extends RGBImageDataFilterFactory<AlphaScaleColorImageDataFilter>
    {
        public AlphaScaleColorImageDataFilterFactory()
        {
            super(ImageFilterType.AlphaScaleColorImageDataFilterType);

            addAttribute(Attribute.INVERTED);
        }

        @Override
        public AlphaScaleColorImageDataFilter create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            return new AlphaScaleColorImageDataFilter(node, ctx);
        }
    }
}
