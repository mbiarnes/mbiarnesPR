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

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

public class NodeMouseUpEvent extends AbstractNodeMouseEvent<MouseEvent<?>, NodeMouseUpHandler>
{
    private static final Type<NodeMouseUpHandler> TYPE = new Type<NodeMouseUpHandler>();

    public static final Type<NodeMouseUpHandler> getType()
    {
        return TYPE;
    }

    public NodeMouseUpEvent(final MouseUpEvent event)
    {
        super(event);
    }

    @Override
    public final Type<NodeMouseUpHandler> getAssociatedType()
    {
        return TYPE;
    }

    @Override
    protected void dispatch(final NodeMouseUpHandler handler)
    {
        handler.onNodeMouseUp(this);
    }
}
