/*
 * Copyright (c) 2019 Ahome' Innovation Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ait.lienzo.test.stub.overlays;

import com.ait.lienzo.test.annotation.StubClass;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Stub for class <code>com.ait.lienzo.client.core.types.Point2D$Point2DJSO</code>.
 */
@StubClass("com.ait.lienzo.client.core.types.DragBounds$DragBoundsJSO")
public class DragBoundsJSO extends JavaScriptObject
{
    public static DragBoundsJSO make()
    {
        return new DragBoundsJSO();
    }

    public static DragBoundsJSO make(double x1, double y1, double x2, double y2)
    {
        return new DragBoundsJSO(x1, y1, x2, y2);
    }

    private double x1;

    private double y1;

    private double x2;

    private double y2;

    protected DragBoundsJSO()
    {
    }

    protected DragBoundsJSO(double x1, double y1, double x2, double y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isX1()
    {
        return true;
    }

    public boolean isX2()
    {
        return true;
    }

    public boolean isY1()
    {
        return true;
    }

    public boolean isY2()
    {
        return true;
    }

    public void setX1(double x1)
    {
        this.x1 = x1;
    }

    public double getX1()
    {
        return x1;
    }

    public void setX2(double x2)
    {
        this.x2 = x2;
    }

    public double getX2()
    {
        return x2;
    }

    public void setY1(double y1)
    {
        this.y1 = y1;
    }

    public double getY1()
    {
        return y1;
    }

    public void setY2(double y2)
    {
        this.y2 = y2;
    }

    public double getY2()
    {
        return y2;
    }
}
