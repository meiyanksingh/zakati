package com.zakati.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ta.R;

import java.util.List;


/**
 * Created by Rahil on 8/9/15.
 */
public class DialogUtil {

    private static final String TAG = DialogUtil.class.getSimpleName();

    public static ProgressDialog showProgressDialog(Context mContext) {
        try {
            if (mContext != null && !((Activity) mContext).isFinishing()) {
                ProgressDialog mProgressDialog = ProgressDialog.show(mContext,
                        "", mContext.getString(R.string.please_wait));
                return mProgressDialog;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ProgressDialog(mContext);
    }

    public static void showProgressDialog(@NonNull ProgressDialog progressDialog) {
        try {
            if (progressDialog != null) {
                Activity activity = (Activity) progressDialog.getContext();
                if (activity != null && !activity.isFinishing()) {
                    if (!progressDialog.isShowing()) progressDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog(@NonNull ProgressDialog progressDialog) {
        try {
            if (progressDialog.isShowing()) progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /* public static ProgressDialog getProgressDialog(Context mContext) {
         if (mContext != null) {
             ProgressDialog mProgressDialog = new ProgressDialog(mContext);
             mProgressDialog.setMessage(mContext.getString(R.string.please_wait));
             return mProgressDialog;
         }

         return null;
     }
 */
    public static void showNoNetworkToast(Context context) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        String msg = context.getResources().getString(R.string.no_network_msg);
        showToastShortLength(context, msg);
    }

    public static Snackbar showRetrySnackBar(@NonNull View anyView, String msg, final View.OnClickListener retryListener) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        try {
            final Snackbar snackBar = Snackbar.make(anyView, msg, Snackbar.LENGTH_INDEFINITE);
            snackBar.setActionTextColor(Color.WHITE);
            View view = snackBar.getView();
            TextView tv = (TextView)
                    view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackBar.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                    retryListener.onClick(v);
                }
            });
            snackBar.show();
            return snackBar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Snackbar showNoNetworkSnackBar(@NonNull View anyView, final View.OnClickListener listener) {
        String msg = anyView.getContext().getResources().getString(R.string.no_network_msg);
        return showRetrySnackBar(anyView, msg, listener);
    }

    public static Snackbar showNoNetworkSnackBar(@NonNull View anyView) {
        return showSnackBar(anyView, R.string.no_network_msg);
    }

    public static Snackbar showSnackBar(View anyView, String msg) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        final Snackbar snackBar = Snackbar.make(anyView, msg, Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.WHITE);
        View view = snackBar.getView();
        TextView tv = (TextView)
                view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
        return snackBar;
    }

    public static Snackbar showSnackBar(View anyView, int msg) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        Resources res = anyView.getContext().getResources();
        return showSnackBar(anyView, res.getString(msg));
    }


    public static void showToast(@NonNull Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(@NonNull Context context, int msg) {
        Toast.makeText(context.getApplicationContext(), context.getResources().getString(msg), Toast.LENGTH_LONG).show();
    }

    public static void showToastShortLength(@NonNull Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showTryAgainToast(@NonNull Context context) {
        String msg = context.getString(R.string.server_error);
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    public static Snackbar showTryAgainSnackBar(@NonNull View anyView, final View.OnClickListener listener) {
        String msg = anyView.getContext().getResources().getString(R.string.server_error);
        return showRetrySnackBar(anyView, msg, listener);
    }

    public static Snackbar showTryAgainSnackBar(@NonNull View anyView) {
        String msg = anyView.getContext().getResources().getString(R.string.server_error);
        return showSnackBar(anyView, msg);
    }


    public static android.support.v7.app.AlertDialog getOkCancelDialog(final Context context, int msg, AlertDialog.OnClickListener yesListner) {
        return getOkCancelDialog(context, context.getResources().getString(msg), yesListner);
    }

    public static android.support.v7.app.AlertDialog getOkCancelDialog(final Context context, String msg, AlertDialog.OnClickListener yesListner) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setPositiveButton(context.getString(R.string.text_yes), yesListner);
        builder.setNegativeButton(context.getString(R.string.text_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.setMessage(msg);
        return builder.create();

    }

    public static android.support.v7.app.AlertDialog getOkCancelDialog(final Context context, AlertDialog.OnClickListener yesListner, String msg) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setPositiveButton(context.getString(R.string.ok), yesListner);
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.setMessage(msg);
        return builder.create();

    }

    public static android.support.v7.app.AlertDialog getOkDialog(final Context context, int msg, AlertDialog.OnClickListener yesListner) {
        return getOkDialog(context, context.getResources().getString(msg), yesListner);
    }

    public static android.support.v7.app.AlertDialog getOkDialog(final Context context, String msg, AlertDialog.OnClickListener yesListner) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setPositiveButton(context.getString(R.string.ok), yesListner);
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.setMessage(msg);
        return builder.create();

    }

    public static void modifyDialogBounds2(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = (int) (dialog.getContext().getResources().getDisplayMetrics().widthPixels * 0.8);
        lp.height = (int) (dialog.getContext().getResources().getDisplayMetrics().heightPixels * 0.30);
        window.setAttributes(lp);
    }

    public static android.support.v7.app.AlertDialog getOkDialog(final Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final android.support.v7.app.AlertDialog alert = builder1.create();
        alert.setMessage(msg);
        return builder1.create();
    }


    public static android.support.v7.app.AlertDialog getSingleChoiceDialog(Context context, int title, int items, int checkedItem, final DialogInterface.OnClickListener listener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

    public static android.support.v7.app.AlertDialog getSingleChoiceDialog(Context context, List<Object> items, int checkedItem, final DialogInterface.OnClickListener listener) {
        int size = items.size();
        String[] itemArray = new String[size];
        for (int i = 0; i < size; i++) {
            itemArray[i] = items.get(i).toString();
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        //builder.setTitle(context.getResources().getString(R.string.lbl_select_lang));

        builder.setSingleChoiceItems(itemArray, checkedItem, listener);
        return builder.create();
    }

    public static <T> android.support.v7.app.AlertDialog getListDialog(Context context, List<T> items, String title, final DialogInterface.OnClickListener listener) {
        int size = items.size();
        String[] itemArray = new String[size];
        for (int i = 0; i < size; i++) {
            itemArray[i] = items.get(i).toString();
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context/*, R.style.AppDialog*/);
        builder.setTitle(title);
        builder.setItems(itemArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

    public static android.support.v7.app.AlertDialog getListDialog(Context context, int itemsRes, String title, final DialogInterface.OnClickListener listener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context/*, R.style.AppDialog*/);
        builder.setTitle(title);
        builder.setItems(itemsRes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

    public static void closeAlertDialog(android.support.v7.app.AlertDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void closeDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static android.support.v7.app.AlertDialog getOkCancelDialog(Context context, int msg, int style, final DialogInterface.OnClickListener listener) {
        return getOkCancelDialog(context, msg, style, "Ok", "Cancel", listener);
    }

    public static android.support.v7.app.AlertDialog getOkCancelDialog(Context context, int msg, int style, String positiveBtn, String negetiveBtn, final DialogInterface.OnClickListener listener) {
        //android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context, style);
        builder.setCancelable(true);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setNegativeButton(positiveBtn, null);
        builder.setPositiveButton(negetiveBtn, listener);

        builder.setMessage(msg);
        //builder.setCustomTitle()
        final android.support.v7.app.AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        listener.onClick(dialog, 1);

                    }
                });
            }
        });
        return alertDialog;
    }

    public static android.support.v7.app.AlertDialog getOkCancelDialog(final Context context, String msg, String yesText, String noText, AlertDialog.OnClickListener yesListner) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context/*, R.style.AppDialog*/);
        builder.setMessage(msg);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setPositiveButton(yesText, yesListner);
        builder.setNegativeButton(noText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.setMessage(msg);
        return builder.create();

    }

   /* public static Dialog getFullScreenDialog(Context context, int layoutId) {
        Dialog dialog = new Dialog(context, R.style.full_screen_dialog);
        //dialog.setContentView(R.layout.dialog_edit_comment);
        dialog.getWindow().setContentView(layoutId);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }*/


    /*public static android.support.v7.app.AlertDialog getDialogBuilder(Context context, int titleResId, int viewResId, final DialogInterface.OnClickListener yesListener*//*,final DialogInterface.OnClickListener radioListener*//*) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setView(viewResId);
        builder.setTitle(titleResId);
        //this option has been removed
        //builder.setSingleChoiceItems(R.array.Audio_setting_gender, 0,radioListener);


        builder.setPositiveButton(R.string.Audio_dialog_Save, yesListener);
        builder.setNegativeButton(R.string.Audio_dialog_Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }*/

    /*public static android.support.v7.app.AlertDialog getDialogBox(final Context context, int titleResId, final DialogInterface.OnClickListener yesListener) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(titleResId);
        builder.setPositiveButton(R.string.Okay, yesListener);
        return builder.create();
    }*/

    public static void showImagePickerDialog(Context context, final DialogInterface.OnClickListener callback) {
        final String[] items = context.getResources().getStringArray(R.array.choose_image_options);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(R.string.add_image);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface Dialog, int pos) {
                if (pos == 0 || pos == 1) {
                    callback.onClick(Dialog, pos);
                } else if (pos == 2) {
                    Dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    public static android.support.v7.app.AlertDialog showOkCancelDialog(Context context, String message, String ok, String cancel, final DialogInterface.OnClickListener dialogListener) {
        android.support.v7.app.AlertDialog dialog = null;
        if (context != null && context instanceof Activity && !((Activity) context).isFinishing()) {
            final android.support.v7.app.AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogListener.onClick(dialogInterface, i);
                    dialogInterface.cancel();
                }
            });
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });
            dialog = builder.create();
            builder.show();


        }
        return dialog;
    }


    public static void showOkDialog(Context context, String title, String message) {
        if (context != null && context instanceof Activity && !((Activity) context).isFinishing()) {

            final android.support.v7.app.AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(context);

            if (!TextUtils.isEmpty(title))
                builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(context.getString(android.R.string.ok), null);

            builder.show();
        }
    }

    public static void invisibleView(View v) {
        v.setVisibility(View.INVISIBLE);
    }


    public static void closeDialogFragment(DialogFragment dialogFragment) {
        if (dialogFragment != null) dialogFragment.dismiss();
    }

}
