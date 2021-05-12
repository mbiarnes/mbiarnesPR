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

package com.ait.lienzo.client.core.event;

import java.util.List;

import com.google.gwt.event.dom.client.TouchEvent;

public class NodeTouchMoveEvent extends AbstractNodeTouchEvent<TouchEvent<?>, NodeTouchMoveHandler>
{
    private static final Type<NodeTouchMoveHandler> TYPE = new Type<NodeTouchMoveHandler>();

    public static final Type<NodeTouchMoveHandler> getType()
    {
        return TYPE;
    }

    public NodeTouchMoveEvent(final TouchEvent<?> event, final List<TouchPoint> touches)
    {
        super(event, touches);
    }

    @Override
    public final Type<NodeTouchMoveHandler> getAssociatedType()
    {
        return TYPE;
    }

    @Override
    protected void dispatch(final NodeTouchMoveHandler handler)
    {
        handler.onNodeTouchMove(this);
    }
}
