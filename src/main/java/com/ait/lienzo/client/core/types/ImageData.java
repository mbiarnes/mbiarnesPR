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

package com.ait.lienzo.client.core.types;

import com.ait.lienzo.client.core.Context2D;
import com.ait.lienzo.client.core.util.ScratchPad;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Object that holds image data and a size.
 *
 * @see <a href="http://www.w3.org/TR/2dcontext/#imagedata">HTML Canvas 2D ImageData</a>
 */
public final class ImageData extends JavaScriptObject
{
    /**
     * Offsets for each color use RGBA ordering.
     */
    public static final int         OFFSET_RED   = 0;

    public static final int         OFFSET_GREEN = 1;

    public static final int         OFFSET_BLUE  = 2;

    public static final int         OFFSET_ALPHA = 3;

    private static final ScratchPad SCRATCH      = new ScratchPad(1, 1);

    protected ImageData()
    {
    }

    /**
     * ImageData can't be cloned or deep-copied, it's an internal data structure and has some CRAZY crap in it, this is cheeeeeezy, but hey, it works, and it's portable!!!
     */
    public final ImageData copy()
    {
        final Context2D context = new ScratchPad(getWidth(), getHeight()).getContext();

        context.putImageData(this, 0, 0);

        return context.getImageData(0, 0, getWidth(), getHeight());
    }

    public final ImageData create()
    {
        return SCRATCH.getContext().createImageData(this);
    }

    /**
     * Returns the alpha value at position (x,y).
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the alpha value at position (x,y), or 0 if not in the image
     * @see #setAlphaAt(int, int, int)
     * @see #getColorAt(int, int, int)
     */
    public final int getAlphaAt(final int x, final int y)
    {
        return getColorAt(x, y, OFFSET_ALPHA);
    }

    /**
     * Returns the blue value at position (x,y).
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the blue value at position (x,y), or 0 if not in the image
     * @see #setBlueAt(int, int, int)
     * @see #getColorAt(int, int, int)
     */
    public final int getBlueAt(final int x, final int y)
    {
        return getColorAt(x, y, OFFSET_BLUE);
    }

    /**
     * Returns a canvas pixel array of size width * height * 4.
     *
     * @return a {@link CanvasPixelArray} object
     */
    public final native CanvasPixelArray getData()
    /*-{
		return this.data;
    }-*/;

    /**
     * Returns the green value at position (x,y).
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the green value at position (x,y), or 0 if not in the image
     * @see #setGreenAt(int, int, int)
     * @see #getColorAt(int, int, int)
     */
    public final int getGreenAt(final int x, final int y)
    {
        return getColorAt(x, y, OFFSET_GREEN);
    }

    /**
     * Returns the height of this image data object.
     *
     * @return the image height as an int
     */
    public final native int getHeight()
    /*-{
		return this.height;
    }-*/;

    /**
     * Returns the red value at position (x,y).
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the red value at position (x,y), or 0 if not in the image
     * @see #setRedAt(int, int, int)
     * @see #getColorAt(int, int, int)
     */
    public final int getRedAt(final int x, final int y)
    {
        return getColorAt(x, y, OFFSET_RED);
    }

    /**
     * Returns the width of this image data object.
     *
     * @return the image width as an int
     */
    public final native int getWidth()
    /*-{
		return this.width;
    }-*/;

    /**
     * Sets the alpha value at position (x,y).
     * 
     * @param alpha the alpha value
     * @param x the x coordinate
     * @param y the y coordinate
     * @see #getAlphaAt(int, int)
     * @see #getColorAt(int, int, int)
     */
    public final void setAlphaAt(final int alpha, final int x, final int y)
    {
        setColorAt(alpha, x, y, OFFSET_ALPHA);
    }

    /**
     * Sets the blue value at position (x,y).
     * 
     * @param blue the blue value
     * @param x the x coordinate
     * @param y the y coordinate
     * @see #getBlueAt(int, int)
     * @see #getColorAt(int, int, int)
     */
    public final void setBlueAt(final int blue, final int x, final int y)
    {
        setColorAt(blue, x, y, OFFSET_BLUE);
    }

    /**
     * Sets the green value at position (x,y).
     * 
     * @param green the green value
     * @param x the x coordinate
     * @param y the y coordinate
     * @see #getGreenAt(int, int)
     * @see #getColorAt(int, int, int)
     */
    public final void setGreenAt(final int green, final int x, final int y)
    {
        setColorAt(green, x, y, OFFSET_GREEN);
    }

    /**
     * Sets the red value at position (x,y).
     * 
     * @param red the red value
     * @param x the x coordinate
     * @param y the y coordinate
     * @see #getRedAt(int, int)
     * @see #getColorAt(int, int, int)
     */
    public final void setRedAt(final int red, final int x, final int y)
    {
        setColorAt(red, x, y, OFFSET_RED);
    }

    /**
     * Returns the color value at position (x,y) with the specified offset.
     * 
     * Colors are stored in RGBA format, where the offset determines the color
     * channel (R, G, B, or A). The values are stored in row-major order. If the
     * specified location is not in the image, 0 is returned.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param offset the color offset
     * @return the color value at position (x,y), or 0 if not in the image
     * @see #setColorAt(int, int, int, int)
     */
    private native int getColorAt(int x, int y, int offset)
    /*-{
		return this.data[4 * (x + y * this.width) + offset] || 0;
    }-*/;

    /**
     * Sets the color value at position (x,y) with the specified offset.
     * 
     * Colors are stored in RGBA format, where the offset determines the color
     * (R, G, B, or A.) The values are stored in row-major order.
     * 
     * @param color the color (in the range 0...255)
     * @param x the x coordinate
     * @param y the y coordinate
     * @param offset the color offset
     * @see #getColorAt(int, int, int)
     */
    private native void setColorAt(int color, int x, int y, int offset)
    /*-{
		this.data[4 * (x + y * this.width) + offset] = color;
    }-*/;
}
