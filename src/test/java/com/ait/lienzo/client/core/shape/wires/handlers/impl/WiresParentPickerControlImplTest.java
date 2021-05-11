/*
 *
 *    Copyright (c) 2018 Ahome' Innovation Technologies. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */
package com.ait.lienzo.client.core.shape.wires.handlers.impl;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.PickerPart;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.handlers.WiresLayerIndex;
import com.ait.lienzo.test.LienzoMockitoTestRunner;
import com.ait.tooling.common.api.java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(LienzoMockitoTestRunner.class)
public class WiresParentPickerControlImplTest {

    private static final double START_X = 3;
    private static final double START_Y = 5;

    @Mock
    private WiresLayerIndex index;

    private WiresParentPickerControlImpl tested;
    private Layer layer;
    private WiresManager manager;
    private WiresShape shape;
    private WiresShape parent;
    private WiresShapeLocationControlImpl shapeLocationControl;

    @Before
    public void setup()
    {
        layer = new Layer();
        manager = WiresManager.get(layer);
        shape = new WiresShape(new MultiPath().rect(0, 0, 10, 10));
        shape.setWiresManager(manager);
        parent = new WiresShape(new MultiPath().rect(0, 0, 100, 100));
        parent.setWiresManager(manager);
        shapeLocationControl = spy(new WiresShapeLocationControlImpl(shape));
        tested = new WiresParentPickerControlImpl(shapeLocationControl,
                                                  new Supplier<WiresLayerIndex>() {
                                                      @Override
                                                      public WiresLayerIndex get() {
                                                          return index;
                                                      }
                                                  });
    }

    @Test
    public void testReturnParentAtCertainLocation()
    {
        // Start moving shape.
        tested.onMoveStart(START_X,
                           START_Y);
        assertEquals(manager.getLayer(), tested.getParent());
        // Mock find method to return parent at the following location.
        when(index.findShapeAt(eq((int) (START_X + 10)),
                                eq((int) (START_Y + 10))))
                .thenReturn(new PickerPart(parent, PickerPart.ShapePart.BODY));

        // Move step. Parent is here.
        double dx = 10d;
        double dy = 10d;
        tested.onMove(dx, dy);
        assertEquals(parent, tested.getParent());

        // Move step. Parent no here.
        dx = -10d;
        dy = -10d;
        tested.onMove(dx, dy);
        assertEquals(manager.getLayer(), tested.getParent());

        // Verify the index is never updated.
        verify(index, never()).build(any(WiresLayer.class));
        verify(index, never()).exclude(any(WiresContainer.class));
        verify(index, never()).clear();
    }

    @Test
    public void testDestroy(){
        tested.destroy();
        verify(shapeLocationControl, atLeastOnce()).clear();
        verify(shapeLocationControl).destroy();
    }
}
