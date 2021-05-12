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
import com.ait.lienzo.client.core.types.ImageData;
import com.ait.lienzo.shared.core.types.ImageFilterType;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.json.client.JSONObject;

public abstract class AbstractConvolveImageDataFilter<T extends AbstractConvolveImageDataFilter<T>> extends AbstractImageDataFilter<T>
{
    protected AbstractConvolveImageDataFilter(final ImageFilterType type, final double... matrix)
    {
        super(type);

        setMatrix(matrix);
    }

    protected AbstractConvolveImageDataFilter(final ImageFilterType type, final FilterConvolveMatrix matrix)
    {
        super(type);

        setMatrix(matrix);
    }

    protected AbstractConvolveImageDataFilter(final ImageFilterType type, final JSONObject node, final ValidationContext ctx) throws ValidationException
    {
        super(type, node, ctx);
    }

    public final T setMatrix(final double... matrix)
    {
        getAttributes().setMatrix(matrix);

        return cast();
    }

    public final T setMatrix(final FilterConvolveMatrix matrix)
    {
        getAttributes().setMatrix(matrix);

        return cast();
    }

    public final FilterConvolveMatrix getMatrix()
    {
        return getAttributes().getMatrix();
    }

    @Override
    public final boolean isTransforming()
    {
        return true;
    }

    @Override
    public ImageData filter(ImageData source, final boolean copy)
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
        final FilterConvolveMatrix matrix = getMatrix();

        if (matrix.size() < 1)
        {
            return source;
        }
        final ImageData result = source.create();

        FilterCommonOps.doFilterConvolve(data, result.getData(), matrix, source.getWidth(), source.getHeight());

        return result;
    }

    protected static abstract class ConvolveImageDataFilterFactory<T extends AbstractConvolveImageDataFilter<T>> extends ImageDataFilterFactory<T>
    {
        protected ConvolveImageDataFilterFactory(final ImageFilterType type)
        {
            super(type);

            addAttribute(Attribute.MATRIX, true);
        }
    }
}
