/*
   Copyright (c) 2014,2015 Ahome' Innovation Technologies. All rights reserved.

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

import com.ait.lienzo.client.core.shape.json.IFactory;
import com.ait.lienzo.client.core.shape.json.validators.ValidationContext;
import com.ait.lienzo.client.core.shape.json.validators.ValidationException;
import com.ait.lienzo.client.core.types.ImageData;
import com.ait.lienzo.shared.core.types.ImageFilterType;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class EmbossImageDataFilter extends AbstractImageDataFilter<EmbossImageDataFilter>
{
    public EmbossImageDataFilter()
    {
        super(ImageFilterType.EmbossImageDataFilterType);
    }

    protected EmbossImageDataFilter(JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(ImageFilterType.EmbossImageDataFilterType, node, ctx);
    }

    @Override
    public final boolean isTransforming()
    {
        return true;
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
        filter_(data, FilterCommonOps.getLength(source), source.getWidth(), FilterCommonOps);

        return source;
    }

    private final native void filter_(JavaScriptObject data, int length, int width, ImageDataFilterCommonOps fops)
    /*-{
        for(var i = 0; i < length; i++) {
            if(i < (length - width * 4)) {
                if(((i + 1) % 4) !== 0) {
                    if(((i + 4) % (width * 4)) == 0) {
                        data[  i  ] = data[i - 4];
                        data[i + 1] = data[i - 3];
                        data[i + 2] = data[i - 2];
                        data[i + 3] = data[i - 1];
                    } else {
                        data[i] = 255/2 + 2 * data[i] - data[i + 4] - data[i + width * 4];
                    }
                }
            } else {
                if(((i + 1) % 4) !== 0) {
                    data[i] = data[i - width * 4];
                }
            }
        }
        fops.filterLuminosity(data, length);
    }-*/;

    @Override
    public IFactory<EmbossImageDataFilter> getFactory()
    {
        return new EmbossImageDataFilterFactory();
    }

    public static class EmbossImageDataFilterFactory extends ImageDataFilterFactory<EmbossImageDataFilter>
    {
        public EmbossImageDataFilterFactory()
        {
            super(ImageFilterType.EmbossImageDataFilterType);
        }

        @Override
        public EmbossImageDataFilter create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            return new EmbossImageDataFilter(node, ctx);
        }
    }
}
