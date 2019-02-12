package com.example.skr.fresco_demo;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.img_icon)
    SimpleDraweeView imgIcon;
    @BindView(R.id.icon_radius)
    Button iconRadius;
    @BindView(R.id.icon_circle)
    Button iconCircle;
    @BindView(R.id.icon_bili)
    Button iconBili;
    @BindView(R.id.icon_jianjin)
    Button iconJianjin;
    @BindView(R.id.icon_cipan)
    Button iconCipan;
    @BindView(R.id.icon_gif)
    Button iconGif;
    @BindView(R.id.icon_listener)
    Button iconListener;
    @BindView(R.id.icon_okhttp)
    Button iconOkhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Uri uri = Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg");
        imgIcon.setImageURI(uri);

    }


    @OnClick({R.id.icon_radius, R.id.icon_circle, R.id.icon_bili, R.id.icon_jianjin, R.id.icon_cipan, R.id.icon_gif, R.id.icon_listener, R.id.icon_okhttp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_radius:
                RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
                roundingParams.setBorder(Color.RED,1.0f);
                roundingParams.setRoundAsCircle(false);
                roundingParams.setCornersRadius(10);
                imgIcon.getHierarchy().setRoundingParams(roundingParams);
                break;
            case R.id.icon_circle:
                RoundingParams roundingParams1 = RoundingParams.fromCornersRadius(5f);
                roundingParams1.setBorder(Color.RED,1.0f);
                roundingParams1.setRoundAsCircle(true);
                imgIcon.getHierarchy().setRoundingParams(roundingParams1);
                break;
            case R.id.icon_bili:
                imgIcon.setAspectRatio(1.2f);
                break;
            case R.id.icon_jianjin:
                Uri uri = Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg");
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setProgressiveRenderingEnabled(true)
                        .build();
                AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(imgIcon.getController())
                        .build();
                imgIcon.setController(controller);
                break;
            case R.id.icon_cipan:
                DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                        .setBaseDirectoryName("image_ss1")
                        .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                        .build();
                //设置磁盘缓存的配置,生成配置文件
                ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                        .setMainDiskCacheConfig(diskCacheConfig)
                        .build();
                Fresco.initialize(this,config);
                break;
            case R.id.icon_gif:
                AbstractDraweeController controller1 = Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setUri(Uri.parse("http://img.gaoxiaogif.cn/GaoxiaoGiffiles/images/2015/11/14/jie%2Czanshiqinshengdema.gif"))
                        .build();
                imgIcon.setController(controller1);
                break;
            case R.id.icon_listener:
                BaseControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        if (imageInfo == null) {
                            return;
                        }
                        QualityInfo qualityInfo = imageInfo.getQualityInfo();
                        FLog.d("Final image received!Size %d x %d","Quality level %d," +
                                        " good enough: %s, full quality: %s",
                                imageInfo.getHeight(),
                                imageInfo.getWidth(),
                                qualityInfo.getQuality(),
                                qualityInfo.isOfFullQuality(),
                                qualityInfo.isOfGoodEnoughQuality());
                    }
                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        FLog.e(getClass(), throwable, "Error loading %s", id);
                    }

                };
                Uri uri1 = null;
                DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                        .setControllerListener(controllerListener)
                        .setUri(uri1)
                        // other setters
                        .build();
                imgIcon.setController(controller2);

            break;
            case R.id.icon_okhttp:
                break;
        }
    }

}
