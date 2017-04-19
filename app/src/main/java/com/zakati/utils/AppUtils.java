package com.zakati.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.ta.R;
import com.ta.models.general.GeneralResp;
import com.ta.ui.mkloader.MKLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ta.utils.Utils.hideView;
import static com.ta.utils.Utils.showView;


/**
 * Created by Rahil on 13/11/15.
 */
public class AppUtils {

    /*public enum CoachMarkType {
        HOME
    }

    public static void showCoachMarks(final View view, final CoachMarkType listType) {
        boolean isAlreadyShown = false;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });
        switch (listType) {

            case HOME:
                isAlreadyShown = PreferenceUtil.getGlobalSharedPrefBooleanData(view.getContext(), PreferenceUtil.KEY_HOME_COACH_MARK);
                PreferenceUtil.setGlobalSharedPrefBooleanData(view.getContext(), PreferenceUtil.KEY_HOME_COACH_MARK, true);
                break;
        }

        if (!isAlreadyShown) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);

    }*/


    public static <T> void enqueueCall(final View parentView, final MKLoader progressWheel, final Call<T> call, final Callback<T> callback) {


        progressWheel.start();


        if (progressWheel.getId() == R.id.toolbar_loader) {
            parentView.setEnabled(false);
        } else {
            parentView.setEnabled(true);
            hideView(parentView);
        }
        enqueueCall(call, new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                progressWheel.stop();

                showView(parentView);
                parentView.setEnabled(true);
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                parentView.setEnabled(true);
                progressWheel.stop();
                callback.onFailure(call, t);
            }
        });
    }

    public static <T> void enqueueCall(final View parentView, final MKLoader progressWheel, int page, final Call<T> call, final Callback<T> callback) {

        enqueueCall(parentView, progressWheel, call, callback);
        if (page > 1) {
            parentView.setEnabled(true);
            parentView.setVisibility(View.VISIBLE);
            progressWheel.stop();
        }
    }

    public static <T> void enqueueCall(final View parentView, final int pageNo, final MKLoader progressWheel, final MKLoader toolbarProgressWheel, final Call<T> call, final Callback<T> callback) {
        if (pageNo == 1) {
            progressWheel.start();
            parentView.setVisibility(View.GONE);
        } else {
            toolbarProgressWheel.start();
            parentView.setVisibility(View.VISIBLE);
        }
        enqueueCall(call, new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                parentView.setVisibility(View.VISIBLE);
                progressWheel.stop();
                toolbarProgressWheel.stop();
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (pageNo == 1) {
                    parentView.setVisibility(View.GONE);
                }
                progressWheel.stop();
                toolbarProgressWheel.stop();
                callback.onFailure(call, t);

            }
        });
    }

    //T extends Comparable<? super T
    public static <T> void enqueueCall(final Call<T> call, final Callback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {

                        try {
                            callback.onFailure(call, new Throwable(response.errorBody().string()));
                        } catch (IOException | NullPointerException e) {
                            callback.onFailure(call, new Throwable("Unknown"));
                            e.printStackTrace();
                        }
                    } else
                        callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Response Failed Code : " + response.code()));
                }


            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(call, t);

            }
        });
    }

    public static <T extends GeneralResp> void enqueueCall(final MenuItem menuItem, final MKLoader loader, final Call<T> call, final Callback<T> callback) {

        menuItem.setVisible(false);
        loader.start();
        enqueueCall(call, new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                loader.stop();
                menuItem.setVisible(true);
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {

                loader.stop();
                menuItem.setVisible(true);
                callback.onFailure(call, t);
            }
        });

    }


    public static void setProfileImage(ImageView imageView, String url) {
    }

    public static void setImage(ImageView imageView, String url) {
    }


   /* public static void openWebViewActivity(Context context, String url) {
        Intent webUrlIntent = new Intent(context, WebViewActivity.class);
        webUrlIntent.putExtra(AppConstants.EXTRA_URL, url);
        context.startActivity(webUrlIntent);
    }*/

    public static void openPlayStore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    public static <T extends GeneralResp> void enqueueCall(View viewTobeHide, int pageNo, MKLoader progress, Call<T> call, Callback<T> callback) {
        if (pageNo == 1) {
            enqueueCall(viewTobeHide, progress, call, callback);
        } else {
            enqueueCall(call, callback);
        }


    }









    public static void setupImage(final SimpleDraweeView simpleDraweeView, final String uri) {
        simpleDraweeView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                simpleDraweeView.getViewTreeObserver().removeOnPreDrawListener(this);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                        .setResizeOptions(new ResizeOptions(simpleDraweeView.getWidth(), simpleDraweeView.getHeight()))
                        .setRotationOptions(RotationOptions.autoRotate())
                        .build();
                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setOldController(simpleDraweeView.getController())
                        .setImageRequest(request)
                        .build();
                simpleDraweeView.setController(controller);
                //  simpleDraweeView.setImageURI(uri);
                return true;
            }
        });
    }

    public static long getTimeStamp(String date) {

        //2017-02-09 11:43:45
        Date localTime = null;
        try {
            localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(date);
            System.out.println("TimeStamp is " + localTime.getTime());
            return localTime.getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void showInImageViewer(Context context, List<String> url, int pos) {

        ImageViewer imageViewer = new ImageViewer.Builder(context, url)
                .setStartPosition(pos)
                //.hideStatusBar(false)
                .setBackgroundColorRes(android.R.color.black)
                //.setBackgroundColor(color)
                .setImageMargin(context, R.dimen.dp5)
                //.setImageChangeListener(changeListener)
                //.setOnDismissListener(getDismissListener())
                //.setCustomDraweeHierarchyBuilder(hierarchyBuilder)

                //.setOverlayView(view)
                .build();
        imageViewer.show();


    }

   /* public static void showNotification(Context ctx, Notification data) {


        Intent intent = AppUtils.getIntentForPush(ctx, data);

        if (intent == null) return;

        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher))
                .setTicker(data.getAlert())
                .setContentTitle(ctx.getString(R.string.app_name))
                .setContentText(data.getAlert())
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(android.app.Notification.PRIORITY_HIGH);
        }
        android.app.Notification notification = builder.build();


        //startForeground(Integer.parseInt(mPushObj.getData().getMsgId()), notification);


        NotificationManager notificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify((int) System.currentTimeMillis(), notification);


    }*/

    public static void shareImageFromUrl(final Context ctx, String url) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();

        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setRequestPriority(Priority.HIGH)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();

        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchDecodedImage(imageRequest, ctx);

        try {
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                public void onNewResultImpl(@Nullable Bitmap bitmap) {
                    if (bitmap == null) {
                        Lg.d("AppUtil", "Bitmap data source returned success, but bitmap null.");
                        return;
                    }

                    Uri contentUri = getUriFromBitmap(ctx, bitmap);
                    if (contentUri != null) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                        shareIntent.setDataAndType(contentUri, ctx.getContentResolver().getType(contentUri));
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        ctx.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                    }
                }

                @Override
                public void onFailureImpl(DataSource dataSource) {
                    // No cleanup required here
                }
            }, CallerThreadExecutor.getInstance());
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
        }
    }

    public static Uri getUriFromBitmap(Context ctx, Bitmap bitmap) {

        try {
            File cachePath = new File(ctx.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = null; // overwrites this image every time
            stream = new FileOutputStream(cachePath + "/image.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            File imagePath = new File(ctx.getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
            Uri contentUri = FileProvider.getUriForFile(ctx, "com.soqek.fileprovider", newFile);
            return contentUri;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void shareImageFromView(View view) {

        Context ctx = view.getContext();
        Uri contentUri = getUriFromBitmap(ctx, getBitmapFromView(view));
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, ctx.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            ctx.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }


    }

    public static Bitmap getBitmapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        v.destroyDrawingCache();
        return v.getDrawingCache();
    }

}
