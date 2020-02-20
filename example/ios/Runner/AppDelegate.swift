import UIKit
import Flutter
import VinIDPaySDK

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)
    VinIDPay.sharedInstance.returnURLScheme = "com.vingroup.vinidpaySdkExample.vinidpay"
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
    
    override func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        VinIDPay.sharedInstance.handleReturnURL(url)
        return true
    }

}
