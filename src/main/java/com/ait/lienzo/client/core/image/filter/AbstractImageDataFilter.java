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
import com.ait.lienzo.client.core.event.AttributesChangedHandler;
import com.ait.lienzo.client.core.event.IAttributesChangedBatcher;
import com.ait.lienzo.client.core.shape.Attributes;
import com.ait.lienzo.client.core.shape.json.AbstractFactory;
import com.ait.lienzo.client.core.shape.json.validators.ValidationContext;
import com.ait.lienzo.client.core.shape.json.validators.ValidationException;
import com.ait.lienzo.shared.core.types.ImageFilterType;
import com.ait.tooling.nativetools.client.NObjectJSO;
import com.ait.tooling.nativetools.client.collection.MetaData;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public abstract class AbstractImageDataFilter<T extends AbstractImageDataFilter<T>> implements ImageDataFilter<T>
{
    private final MetaData        m_meta;

    private final Attributes      m_attr;

    private final ImageFilterType m_type;

    protected AbstractImageDataFilter(final ImageFilterType type)
    {
        m_type = type;

        m_attr = new Attributes(this);

        m_meta = new MetaData();

        setActive(true);
    }

    protected AbstractImageDataFilter(final ImageFilterType type, final JSONObject node, final ValidationContext ctx) throws ValidationException
    {
        m_type = type;

        if (null == node)
        {
            m_attr = new Attributes(this);

            m_meta = new MetaData();

            return;
        }
        JSONValue aval = node.get("attributes");

        if (null == aval)
        {
            m_attr = new Attributes(this);
        }
        else
        {
            JSONObject aobj = aval.isObject();

            if (null == aobj)
            {
                m_attr = new Attributes(this);
            }
            else
            {
                JavaScriptObject ajso = aobj.getJavaScriptObject();

                if (null == ajso)
                {
                    m_attr = new Attributes(this);
                }
                else
                {
                    m_attr = new Attributes(ajso, this);
                }
            }
        }
        JSONValue mval = node.get("meta");

        if (null == mval)
        {
            m_meta = new MetaData();
        }
        else
        {
            JSONObject mobj = mval.isObject();

            if (null == mobj)
            {
                m_meta = new MetaData();
            }
            else
            {
                JavaScriptObject mjso = mobj.getJavaScriptObject();

                if (null == mjso)
                {
                    m_meta = new MetaData();
                }
                else
                {
                    NObjectJSO jso = mjso.cast();

                    m_meta = new MetaData(jso);
                }
            }
        }
    }

    @Override
    public boolean isTransforming()
    {
        return false;
    }

    @Override
    public boolean isActive()
    {
        return getAttributes().isActive();
    }

    @Override
    public void setActive(final boolean active)
    {
        getAttributes().setActive(active);
    }

    @Override
    public final ImageFilterType getType()
    {
        return m_type;
    }

    @SuppressWarnings("unchecked")
    protected final T cast()
    {
        return (T) this;
    }

    public final MetaData getMetaData()
    {
        return m_meta;
    }

    public final Attributes getAttributes()
    {
        return m_attr;
    }

    @Override
    public String toJSONString()
    {
        return toJSONObject().toString();
    }

    @Override
    public JSONObject toJSONObject()
    {
        JSONObject object = new JSONObject();

        object.put("type", new JSONString(getType().getValue()));

        if (false == getMetaData().isEmpty())
        {
            object.put("meta", new JSONObject(getMetaData().getJSO()));
        }
        object.put("attributes", new JSONObject(getAttributes().getJSO()));

        return object;
    }

    public final T setAttributesChangedBatcher(final IAttributesChangedBatcher batcher)
    {
        m_attr.setAttributesChangedBatcher(batcher);

        return cast();
    }

    public HandlerRegistration addAttributesChangedHandler(final Attribute attribute, final AttributesChangedHandler handler)
    {
        return m_attr.addAttributesChangedHandler(attribute, handler);
    }

    public final T cancelAttributesChangedBatcher()
    {
        m_attr.cancelAttributesChangedBatcher();

        return cast();
    }

    protected static abstract class ImageDataFilterFactory<T extends ImageDataFilter<T>> extends AbstractFactory<T>
    {
        protected ImageDataFilterFactory(final ImageFilterType type)
        {
            super(type.getValue());

            addAttribute(Attribute.ACTIVE, true);
        }
    }
}
