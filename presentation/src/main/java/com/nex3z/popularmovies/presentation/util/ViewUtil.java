package com.nex3z.popularmovies.presentation.util;

import android.net.Uri;
import android.util.TypedValue;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nex3z.popularmovies.presentation.app.App;

public class ViewUtil {

    private ViewUtil() {}

    public static float dpToPx(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                App.getContext().getResources().getDisplayMetrics());
    }

    public static void loadProgressiveImage(SimpleDraweeView view, String url) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .build();
        view.setController(controller);
    }

}
