package com.vingroup.vinidpay_sdk;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

import com.vinid.paysdk.VinIDPayParams;
import com.vinid.paysdk.VinIDPaySdk;
import com.vinid.paysdk.utils.EnvironmentMode;
import com.vinid.paysdk.utils.VinIDPayConstants;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class VinidpaySdkDelegate implements PluginRegistry.ActivityResultListener {

    private final Activity activity;
    private MethodChannel.Result pendingResult;

    VinidpaySdkDelegate(Activity activity) {
        this.activity = activity;
    }

    void proceedPayment(MethodCall call, MethodChannel.Result result) {

        pendingResult = result;
        String id = call.argument("id");
        String sign = call.argument("sign");
        boolean sandboxMode = call.argument("sandboxMode");

        VinIDPayParams params = new VinIDPayParams.Builder()
                .setOrderId(id)
                .setSignature(sign)
                .build();

        if (sandboxMode) {
            new VinIDPaySdk.Builder()
                    .setVinIDPayParams(params)
                    .setEnvironmentMode(EnvironmentMode.DEV)
                    .build()
                    .startPaymentForResult(activity, VinIDPayConstants.REQUEST_CODE_VINIDPAY_PAY);
        } else {
            new VinIDPaySdk.Builder()
                    .setVinIDPayParams(params)
                    .build()
                    .startPaymentForResult(activity, VinIDPayConstants.REQUEST_CODE_VINIDPAY_PAY);
        }

    }

    void isVinIdAppInstalled(MethodCall call, MethodChannel.Result result) {

        boolean sandboxMode = call.argument("sandboxMode");
        boolean status;
        if (sandboxMode) {
            status = VinIDPaySdk.Companion.isVinIdAppInstalled(activity, EnvironmentMode.DEV);
        } else {
            status = VinIDPaySdk.Companion.isVinIdAppInstalled(activity, EnvironmentMode.PRODUCTION);
        }

        result.success(status);

    }

    void openVinIDInstallPage(MethodCall call) {

        boolean sandboxMode = call.argument("sandboxMode");

        if (sandboxMode) {
            VinIDPaySdk.Companion.openVinIDInstallPage(activity, EnvironmentMode.DEV);
        } else {
            VinIDPaySdk.Companion.openVinIDInstallPage(activity, EnvironmentMode.PRODUCTION);
        }

    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == VinIDPayConstants.REQUEST_CODE_VINIDPAY_PAY) {
            switch (resultCode) {
                case RESULT_OK:
                    String transactionStatus = data.getStringExtra(VinIDPayConstants.EXTRA_RETURN_TRANSACTION_STATUS);

                    switch (transactionStatus) {
                        case VinIDPayConstants.TRANSACTION_SUCCESS:
                            pendingResult.success("payment successful!");
                            break;
                        case VinIDPayConstants.TRANSACTION_ABORT:
                            pendingResult.success("user aborted payment");
                            break;
                        case VinIDPayConstants.TRANSACTION_FAIL:
                            pendingResult.success("payment failed");
//                    int errorCode = data.getIntExtra(
//                            VinIDPayConstants.EXTRA_RETURN_ERROR_CODE,
//                            0
//                    );
//                    pendingResult.success("payment failed with error code: " + errorCode);
                            break;
                        default:
                            pendingResult.success("unknow status");
                            break;
                    }

                    break;
                case RESULT_CANCELED:
                    pendingResult.success("user aborted payment");
                    break;
            }
        }

        return false;
    }

}