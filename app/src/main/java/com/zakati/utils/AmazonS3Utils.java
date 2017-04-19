package com.zakati.utils;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

/**
 * Created by techahead on 18/5/16.
 */
public class AmazonS3Utils {

    public static final String BASE_S3_URL = "https://s3.amazonaws.com/";
    private final static String COGNITO_POOL_ID="us-east-1:e6333a0e-0a83-45a9-a514-202e8357b593";
    private static  CognitoCachingCredentialsProvider sCredProvider;
    public final static String AMAZON_BUCKET="ondemandbucket/driver";

    /**
     * Gets an instance of CognitoCachingCredentialsProvider which is
     * constructed using the given Context.
     *
     * @param context An Context instance.
     * @return A default credential provider.
     */
    public static CognitoCachingCredentialsProvider getCredProvider(Context context) {
        if (sCredProvider == null) {
            sCredProvider = new CognitoCachingCredentialsProvider(
                    context.getApplicationContext(),
                    COGNITO_POOL_ID,
                    Regions.US_EAST_1);
        }

        return sCredProvider;
    }




//    TransferUtility transferUtility = new TransferUtility(getS3(context), APPLICATION_CONTEXT);
//    TransferObserver observer = transferUtility.upload(
//            MY_BUCKET,     /* The bucket to upload to */
//            OBJECT_KEY,    /* The key for the uploaded object */
//            MY_FILE        /* The file where the data to upload exists */
//    );


    public static  AmazonS3 getS3(Context context){
        AmazonS3 s3 = new AmazonS3Client(getCredProvider(context));
        s3.setRegion(Region.getRegion(Regions.US_EAST_1));
        return s3;
    }


    public static TransferObserver upload(TransferUtility tu, String key, File file, TransferListener listener){

        TransferObserver observer=tu.upload(AMAZON_BUCKET,key,file);
         observer.setTransferListener(listener);
        return  observer;

    }





   public static void listener(final Context mContext, TransferObserver to){

       to.setTransferListener(new TransferListener() {
           @Override
           public void onStateChanged(int id, TransferState state) {

           }

           @Override
           public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {


           }

           @Override
           public void onError(int id, Exception ex) {

           }
       });

   }
}
