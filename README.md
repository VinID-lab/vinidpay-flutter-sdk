# VinIDPay SDK
[![pub package](https://img.shields.io/badge/vinidpay__sdk-0.0.2%2B1-blue)](https://pub.dev/packages/vinidpay_sdk/)

VinIDPay SDK helps you handle payments with VinID iOS and Android app using Flutter.

**[Android SDK](https://github.com/VinID-lab/vinidpay-android-sdk)**

**[iOS SDK](https://github.com/VinID-lab/vinidpay-ios-sdk)**

## Requirements

### Android

- Minimum SDK version: 16
- Recommend SDK version: 21 // Current minimum SDK version of VinID App
- Minimum VinID App's version: // TBD

### iOS

- Xcode 10+ and iOS 9.0+ Base SDK
- iOS 9.0+ deployment target

## How to install

### Android

- No configuration required

### iOS

## Setup for app switch

#### Update query scheme

To redirect users from your app to VinID App to start payment process, you must update your `Info.plist` file to support querying VinID App's scheme. The scheme is the app bundle ID, so if you use sandbox mode, make sure to add bundle ID of VinID UAT app also:

```
<key>LSApplicationQueriesSchemes</key>
<array>
    <string>com.vingroup.VinIDApp</string>
    <string>com.vingroup.VinIDApp.UAT</string>
</array>
```

#### Register a URL type

To redirect users from VinID app to your app during payment process, you must register a URL type and configure your app to handle return URLs.

1. In Xcode, click on your project in the Project Navigator and navigate to App Target > Info > URL Types
2. Click [+] to add a new URL type
3. Under URL Schemes, enter your app switch return URL scheme. This scheme must start with your app's Bundle ID and be dedicated to VinID app switch returns. For example, if the app bundle ID is `com.your-company.Your-App`, then your URL scheme could be `com.your-company.Your-App.vinidpay`.

**IMPORTANT**: If you have multiple app targets, be sure to add the return URL type for all of the targets.

Then in your `AppDelegate`'s `application:didFinishLaunchingWithOptions:` implementation, use `setReturnURLScheme:` with the URL type you set above.

```Swift
func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
    VinIDPay.sharedInstance.returnURLScheme = "com.your-company.Your-App.vinidpay"
    return true
}
```

#### Forward payment results

In your `AppDelegate`, make sure to pass the return URL to VinIDPay to handle payment results:

```Swift
func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
    VinIDPay.sharedInstance.handleReturnURL(url)
    return true
}
```

If your app uses `SceneDelegate`, you must update the delegate to handle return URL:

```Swift
func scene(_ scene: UIScene, openURLContexts URLContexts: Set<UIOpenURLContext>) {
    for context in URLContexts {
        VinIDPay.sharedInstance.handleReturnURL(context.url)
    }
}
```

## Usage

| Property                    | Description                                                                                             | Type                  |
|-----------------------------|---------------------------------------------------------------------------------------------------------|-----------------------|
| `orderId`              | Your Order ID| String   
| `signature`              | Your Signature| String               
| `sandboxMode`              | Sandbox Mode| bool|   


## Utilities

We support some utility methods to help you work better with VinIDPay SDK:

- Validate if VinID App is installed or not.

```dart
Future<void> isVinIdAppInstalled(bool sandboxMode) async {
  bool _status;
  _status = await VinidpaySdk.isVinIdAppInstalled(sandboxMode);
  print(_status);
}
```

- Open VinID App on Store:

```dart
void openVinIDInstallPage(bool sandboxMode) {
  VinidpaySdk.openVinIDInstallPage(sandboxMode);
}
```

## Example

````dart

import 'package:vinidpay_sdk/vinidpay_sdk.dart';

// Platform messages are asynchronous, so we initialize in an async method.
Future<void> proceedPayment(
  String orderId,
  String signature,
  bool sandboxMode,
) async {
  VinidpaySdkStatus _status;
  // Platform messages may fail, so we use a try/catch PlatformException.
  try {
    _status = await VinidpaySdk.proceedPayment(
      orderId,
      signature,
      sandboxMode,
    );
  } on PlatformException {
    _status = VinidpaySdkStatus.UNKNOW;
  }

  // If the widget was removed from the tree while the asynchronous platform
  // message was in flight, we want to discard the reply rather than calling
  // setState to update our non-existent appearance.
  if (!mounted) return;

  //Doing something
  switch (_status) {
    case VinidpaySdkStatus.SUCCESS:
  
    case VinidpaySdkStatus.ABORT:
  
    case VinidpaySdkStatus.FAIL:
  
    case VinidpaySdkStatus.UNKNOW:
  
    default:
  
  }

}

Future<void> isVinIdAppInstalled(bool sandboxMode) async {
  bool _status;
  _status = await VinidpaySdk.isVinIdAppInstalled(sandboxMode);
  print(_status);
}

openVinIDInstallPage(bool sandboxMode) {
  VinidpaySdk.openVinIDInstallPage(sandboxMode);
}
    
````

