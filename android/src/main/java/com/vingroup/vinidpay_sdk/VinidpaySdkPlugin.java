package com.vingroup.vinidpay_sdk;

import androidx.appcompat.app.AppCompatDelegate;

import org.jetbrains.annotations.NotNull;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * VinidpaySdkPlugin
 */
public class VinidpaySdkPlugin implements MethodCallHandler {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String CHANNEL = "vinidpay_sdk";

    private final VinidpaySdkDelegate delegate;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        if (registrar.activity() == null) {
            // If a background flutter view tries to register the plugin, there will be no activity from the registrar,
            // we stop the registering process immediately because the ImageCropper requires an activity.
            return;
        }

        final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);

        final VinidpaySdkDelegate delegate = new VinidpaySdkDelegate(registrar.activity());
        registrar.addActivityResultListener(delegate);

        channel.setMethodCallHandler(new VinidpaySdkPlugin(delegate));
    }

    @Override
    public void onMethodCall(MethodCall call, @NotNull Result result) {
        switch (call.method) {
            case "proceedPayment":
                delegate.proceedPayment(call, result);
                break;
            case "isVinIdAppInstalled":
                delegate.isVinIdAppInstalled(call, result);
                break;
            case "openVinIDInstallPage":
                delegate.openVinIDInstallPage(call);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private VinidpaySdkPlugin(VinidpaySdkDelegate delegate) {
        this.delegate = delegate;
    }
}
