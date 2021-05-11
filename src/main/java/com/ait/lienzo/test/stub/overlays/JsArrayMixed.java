/*
 * Copyright (c) 2017 Ahome' Innovation Technologies. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ait.lienzo.test.annotation.StubClass;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * In-memory array implementation stub for class <code>com.google.gwt.core.client.JsArrayMixed</code>.
 *
 * @author Roger Martinez
 * @since 1.0
 *
 */
@StubClass("com.google.gwt.core.client.JsArrayMixed")
public class JsArrayMixed extends JavaScriptObject
{
    private final List<Object> list = new ArrayList<Object>();

    protected JsArrayMixed()
    {
    }

    public boolean getBoolean(final int index)
    {
        return (Boolean) list.get(index);
    }

    public double getNumber(final int index)
    {
        return (Double) list.get(index);
    }

    @SuppressWarnings("unchecked")
    public <T extends JavaScriptObject> T getObject(final int index)
    {
        return (T) list.get(index);
    }

    public String getString(final int index)
    {
        return (String) list.get(index);
    }

    public final String join()
    {
        return join(",");
    }

    public String join(final String separator)
    {
        return StringUtils.join(list, separator);
    }

    public int length()
    {
        return list.size();
    }

    public void push(final boolean value)
    {
        list.add(value);
    }

    public void push(final double value)
    {
        list.add(value);
    }

    public void push(final JavaScriptObject value)
    {
        list.add(value);
    }

    public void push(final String value)
    {
        list.add(value);
    }

    public void set(final int index, final boolean value)
    {
        list.set(index, value);
    }

    public void set(final int index, final double value)
    {
        list.set(index, value);
    }

    public void set(final int index, final JavaScriptObject value)
    {
        list.set(index, value);
    }

    public void set(final int index, final String value)
    {
        list.set(index, value);
    }

    public void setLength(final int newLength)
    {
        // Does not make sense here.
    }

    public boolean shiftBoolean()
    {
        return doShift();
    }

    public double shiftNumber()
    {
        return doShift();
    }

    public <T extends JavaScriptObject> T shiftObject()
    {
        return doShift();
    }

    public String shiftString()
    {
        return doShift();
    }

    public void unshift(final boolean value)
    {
        doUnShift(value);
    }

    public void unshift(final double value)
    {
        doUnShift(value);
    }

    public void unshift(final JavaScriptObject value)
    {
        doUnShift(value);
    }

    public void unshift(final String value)
    {
        doUnShift(value);
    }

    @SuppressWarnings("unchecked")
    private <T> T doShift()
    {
        final T t = (T) list.get(0);

        list.remove(0);

        return t;
    }

    private void doUnShift(final Object value)
    {
        list.add(0, value);
    }
}
