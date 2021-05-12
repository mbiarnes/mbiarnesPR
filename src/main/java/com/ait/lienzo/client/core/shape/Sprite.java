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

package com.ait.lienzo.client.core.shape;

import java.util.List;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.Context2D;
import com.ait.lienzo.client.core.config.LienzoCore;
import com.ait.lienzo.client.core.image.ImageLoader;
import com.ait.lienzo.client.core.image.SpriteLoadedHandler;
import com.ait.lienzo.client.core.image.SpriteOnRollHandler;
import com.ait.lienzo.client.core.image.SpriteOnTickHandler;
import com.ait.lienzo.client.core.shape.json.IJSONSerializable;
import com.ait.lienzo.client.core.shape.json.validators.ValidationContext;
import com.ait.lienzo.client.core.shape.json.validators.ValidationException;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.SpriteBehaviorMap;
import com.ait.lienzo.client.core.util.ScratchPad;
import com.ait.lienzo.shared.core.types.ImageSerializationMode;
import com.ait.lienzo.shared.core.types.ShapeType;
import com.ait.tooling.nativetools.client.collection.MetaData;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;

public class Sprite extends Shape<Sprite>
{
    private int                 m_index  = 0;

    private BoundingBox[]       m_frames = null;

    private ImageElement        m_sprite = null;

    private SpriteLoadedHandler m_loaded = null;

    private SpriteOnTickHandler m_ontick = null;

    private SpriteOnRollHandler m_onroll = null;

    private boolean             m_paused = true;

    private boolean             m_inited = false;

    private Timer               m_ticker = null;

    public Sprite(final String url, double rate, SpriteBehaviorMap bmap, String behavior)
    {
        super(ShapeType.SPRITE);

        setURL(url).setTickRate(rate).setSpriteBehaviorMap(bmap).setSpriteBehavior(behavior);

        new ImageLoader(url)
        {
            @Override
            public void onImageElementLoad(final ImageElement elem)
            {
                m_sprite = elem;

                if (null != m_loaded)
                {
                    m_loaded.onSpriteLoaded(Sprite.this);
                }
            }

            @Override
            public void onImageElementError(String message)
            {
                LienzoCore.get().error("Sprite could not load URL " + url + " " + message);
            }
        };
    }

    public Sprite(final ImageResource resource, double rate, SpriteBehaviorMap bmap, String behavior)
    {
        super(ShapeType.SPRITE);

        setURL(resource.getSafeUri().asString()).setTickRate(rate).setSpriteBehaviorMap(bmap).setSpriteBehavior(behavior);

        new ImageLoader(resource)
        {
            @Override
            public void onImageElementLoad(final ImageElement elem)
            {
                m_sprite = elem;

                if (null != m_loaded)
                {
                    m_loaded.onSpriteLoaded(Sprite.this);
                }
            }

            @Override
            public void onImageElementError(String message)
            {
                LienzoCore.get().error("Sprite could not load resource " + resource.getName() + " " + message);
            }
        };
    }

    public Sprite(ImageElement sprite, double rate, SpriteBehaviorMap bmap, String behavior)
    {
        super(ShapeType.SPRITE);

        setURL(sprite.getSrc()).setTickRate(rate).setSpriteBehaviorMap(bmap).setSpriteBehavior(behavior);

        m_sprite = sprite;

        if (null != m_loaded)
        {
            m_loaded.onSpriteLoaded(this);
        }
    }

    public Sprite(JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(ShapeType.SPRITE, node, ctx);
    }

    Sprite load()
    {
        if (isLoaded())
        {
            if (null != m_loaded)
            {
                m_loaded.onSpriteLoaded(this);
            }
        }
        else
        {
            final String url = getURL();

            new ImageLoader(url)
            {
                @Override
                public void onImageElementLoad(final ImageElement elem)
                {
                    m_sprite = elem;

                    if (null != m_loaded)
                    {
                        m_loaded.onSpriteLoaded(Sprite.this);
                    }
                }

                @Override
                public void onImageElementError(String message)
                {
                    LienzoCore.get().error("Sprite could not load URL " + url + " " + message);
                }
            };
        }
        return this;
    }

    @Override
    public BoundingBox getBoundingBox()
    {
        double wide = 0;

        double high = 0;

        for (int i = 0; i < m_frames.length; i++)
        {
            BoundingBox bbox = m_frames[i];

            wide = Math.max(wide, bbox.getWidth());

            high = Math.max(high, bbox.getHeight());
        }
        return new BoundingBox(0, 0, wide, high);
    }

    public final String getURL()
    {
        return getAttributes().getURL();
    }

    public final Sprite setURL(String url)
    {
        if ((null == url) || (url.trim().isEmpty()))
        {
            throw new NullPointerException("url is null or empty");
        }
        getAttributes().setURL(url);

        return this;
    }

    public final double getTickRate()
    {
        return getAttributes().getTickRate();
    }

    public final Sprite setTickRate(double rate)
    {
        getAttributes().setTickRate(rate);

        if (isPlaying())
        {
            pause();

            play();
        }
        return this;
    }

    public final SpriteBehaviorMap getSpriteBehaviorMap()
    {
        return getAttributes().getSpriteBehaviorMap();
    }

    public final Sprite setSpriteBehaviorMap(SpriteBehaviorMap bmap)
    {
        if (bmap == null)
        {
            throw new NullPointerException("SpriteBehaviorMap is null");
        }
        getAttributes().setSpriteBehaviorMap(bmap);

        String behavior = getSpriteBehavior();

        if ((null != behavior) && (false == behavior.trim().isEmpty()))
        {
            m_index = 0;

            m_frames = bmap.getFramesForBehavior(behavior);
        }
        return this;
    }

    public final String getSpriteBehavior()
    {
        return getAttributes().getSpriteBehavior();
    }

    public final Sprite setSpriteBehavior(String behavior)
    {
        if ((null == behavior) || (behavior.trim().isEmpty()))
        {
            throw new NullPointerException("behavior is null or empty");
        }
        getAttributes().setSpriteBehavior(behavior);

        SpriteBehaviorMap bmap = getSpriteBehaviorMap();

        if (null != bmap)
        {
            m_index = 0;

            m_frames = bmap.getFramesForBehavior(behavior);
        }
        return this;
    }

    public final Sprite setSerializationMode(ImageSerializationMode mode)
    {
        getAttributes().setSerializationMode(mode);

        return this;
    }

    public final ImageSerializationMode getSerializationMode()
    {
        return getAttributes().getSerializationMode();
    }

    public final Sprite setAutoPlay(boolean play)
    {
        getAttributes().setAutoPlay(play);

        return this;
    }

    public final boolean isAutoPlay()
    {
        return getAttributes().isAutoPlay();
    }

    public final Sprite play()
    {
        if (false == isPlaying())
        {
            if ((null != m_frames) && (null != m_sprite) && (m_index < m_frames.length))
            {
                final Layer layer = getLayer();

                if (null != layer)
                {
                    final Sprite sprite = this;

                    final int repeat = (int) (1000.0 / Math.min(Math.max(getTickRate(), 0.001), 60.0));

                    m_paused = false;

                    m_ticker = new Timer()
                    {
                        @Override
                        public void run()
                        {
                            boolean draw = true;

                            if ((++m_index) >= m_frames.length)
                            {
                                m_index = 0;

                                if (null != m_onroll)
                                {
                                    draw = m_onroll.onSpriteRoll(sprite);
                                }
                            }
                            if (draw)
                            {
                                if (null != m_ontick)
                                {
                                    draw = m_ontick.onSpriteTick(sprite);
                                }
                                if (draw)
                                {
                                    layer.batch();
                                }
                            }
                        }
                    };
                    m_ticker.scheduleRepeating(repeat);
                }
            }
        }
        return this;
    }

    public final Sprite onTick(SpriteOnTickHandler handler)
    {
        m_ontick = handler;

        return this;
    }

    public final Sprite onRoll(SpriteOnRollHandler handler)
    {
        m_onroll = handler;

        return this;
    }

    public final Sprite pause()
    {
        m_paused = true;

        if (null != m_ticker)
        {
            m_ticker.cancel();
        }
        return this;
    }

    public final boolean isPlaying()
    {
        return (false == m_paused);
    }

    @Override
    protected boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        if ((null != m_frames) && (null != m_sprite) && (m_index < m_frames.length))
        {
            final BoundingBox bbox = m_frames[m_index];

            if (null != bbox)
            {
                if (false == m_inited)
                {
                    m_inited = true;

                    if (attr.isAutoPlay())
                    {
                        play();
                    }
                }
                if (context.isSelection())
                {
                    final String color = getColorKey();

                    if (null != color)
                    {
                        context.save();

                        context.setFillColor(color);

                        context.fillRect(0, 0, bbox.getWidth(), bbox.getHeight());

                        context.restore();
                    }
                }
                else
                {
                    context.save();

                    context.setGlobalAlpha(alpha);

                    context.drawImage(m_sprite, bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight(), 0, 0, bbox.getWidth(), bbox.getHeight());

                    context.restore();
                }
            }
        }
        return false;
    }

    public final Sprite onLoaded(SpriteLoadedHandler handler)
    {
        m_loaded = handler;

        if (null != m_sprite)
        {
            m_loaded.onSpriteLoaded(this);
        }
        return this;
    }

    public final int getTick()
    {
        return m_index;
    }

    public final boolean isLoaded()
    {
        return (m_sprite != null);
    }

    @Override
    public JSONObject toJSONObject()
    {
        JSONObject attr = new JSONObject(getAttributes().getJSO());

        if (getSerializationMode() == ImageSerializationMode.DATA_URL)
        {
            String url = getURL();

            if (false == url.startsWith("data:"))
            {
                attr.put("url", new JSONString(ScratchPad.toDataURL(m_sprite)));
            }
        }
        JSONObject object = new JSONObject();

        object.put("type", new JSONString(getShapeType().getValue()));

        if (hasMetaData())
        {
            final MetaData meta = getMetaData();

            if (false == meta.isEmpty())
            {
                object.put("meta", new JSONObject(meta.getJSO()));
            }
        }
        object.put("attributes", attr);

        return object;
    }

    @Override
    public List<Attribute> getBoundingBoxAttributes()
    {
        return asAttributes(Attribute.URL, Attribute.SPRITE_BEHAVIOR_MAP, Attribute.SPRITE_BEHAVIOR);
    }

    public static class SpriteFactory extends ShapeFactory<Sprite>
    {
        public SpriteFactory()
        {
            super(ShapeType.SPRITE);

            addAttribute(Attribute.URL, true);

            addAttribute(Attribute.TICK_RATE, true);

            addAttribute(Attribute.SPRITE_BEHAVIOR_MAP, true);

            addAttribute(Attribute.SPRITE_BEHAVIOR, true);

            addAttribute(Attribute.AUTO_PLAY);

            addAttribute(Attribute.SERIALIZATION_MODE);
        }

        @Override
        public Sprite create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            return new Sprite(node, ctx);
        }

        @Override
        public boolean isPostProcessed()
        {
            return true;
        }

        @Override
        public void process(IJSONSerializable<?> node, ValidationContext ctx) throws ValidationException
        {
            if (false == (node instanceof Sprite))
            {
                return;
            }
            Sprite self = (Sprite) node;

            if (false == self.isLoaded())
            {
                self.load();

                self.onLoaded(new SpriteLoadedHandler()
                {
                    @Override
                    public void onSpriteLoaded(Sprite sprite)
                    {
                        if (sprite.isLoaded() && sprite.isVisible())
                        {
                            Layer layer = sprite.getLayer();

                            if ((null != layer) && (null != layer.getViewport()))
                            {
                                layer.batch();
                            }
                        }
                    }
                });
            }
        }
    }
}
